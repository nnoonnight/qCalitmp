package com.group.exam.diary.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.group.exam.diary.command.DiaryLikeCommand;
import com.group.exam.diary.command.DiaryListCommand;
import com.group.exam.diary.command.DiaryUpdateCommand;
import com.group.exam.diary.service.DiaryService;
import com.group.exam.diary.vo.DiaryHeartVo;
import com.group.exam.diary.vo.DiaryVo;
import com.group.exam.member.command.LoginCommand;
import com.group.exam.member.service.MemberService;
import com.group.exam.utils.Criteria;
import com.group.exam.utils.PaginVo;

@Controller
@RequestMapping("/diary")
public class DiaryController {
	
	private DiaryService diaryService;

	private MemberService memberService;

	@Autowired
	public DiaryController(DiaryService diaryService, MemberService memberService) {
		this.diaryService = diaryService;
		this.memberService = memberService;
	}
	
	@GetMapping(value = "/write/{memberSeq}")
	public String insertDiary(@PathVariable int memberSeq, @ModelAttribute("diaryData") DiaryVo diaryVo, HttpSession session) {
		LoginCommand loginMember = (LoginCommand) session.getAttribute("memberLogin");
		
		//login seq 와 요청된(작성)seq 비교
		if (memberSeq != loginMember.getMemberSeq()) {

			return "errors/memberAuthErrorDiary";
		}
		return "diary/writeForm";
	}

	@PostMapping(value = "/write/{memberSeq}")
	public String insertDiary(@PathVariable int memberSeq, @Valid @ModelAttribute("diaryData") DiaryVo diaryVo, BindingResult bindingResult,
			Criteria cri, HttpSession session, Model model, HttpServletRequest request) throws IllegalStateException, IOException {
		LoginCommand loginMember = (LoginCommand) session.getAttribute("memberLogin");
			
		//login seq 와 요청된(작성)seq 비교
		if (memberSeq != loginMember.getMemberSeq()) {

			return "errors/memberAuthErrorDiary";
		}		
		
		// not null 체크
		if (bindingResult.hasErrors()) {

			return "diary/writeForm";
		}

		boolean memberAuth = diaryService.memberAuth(loginMember.getMemberSeq()).equals("F");
		if (memberAuth == true) {
			return "errors/memberAuthError"; // 이메일 인증 x -> 예외 페이지
		}
		

		
		//공개여부 세팅
		String diaryOpen = request.getParameter("open");
		
		diaryVo.setDiaryOpen(diaryOpen);
		// 세션에서 멤버의 mSeq 를 diaryVo에 셋팅
		diaryVo.setMemberSeq(loginMember.getMemberSeq());

		//첨부파일 세팅
		MultipartFile file = diaryVo.getImg();
		
		//파일명
		String originalFileName = file.getOriginalFilename();
		
		//파일명 중 확장자만 추출
		String originalFileExtension = FilenameUtils.getExtension(originalFileName); //확장자 구하기
		
		//저장할때 쓸 파일명 랜덤생성 + 확장자
		String storedFileName = UUID.randomUUID().toString().replaceAll("-", "")+"."+originalFileExtension;
		
		//파일 저장을 위한 File 객체
		String rootPath = request.getSession().getServletContext().getRealPath("/");  

	    String attachPath = "resources/upload/";
		file.transferTo(new File(rootPath + attachPath +storedFileName));
	
		
		diaryVo.setDiaryImg(storedFileName); //저장할 파일 (랜덤생성된)이름을 vo에 셋팅
		

		// insert
		diaryService.insertDiary(diaryVo);

		// update

//		int mytotal = diaryService.mylistCount(loginMember.getMemberSeq());

//		if (mytotal > 10) {
//			int memberLevel = boardService.memberLevelup(loginMember.getMemberSeq(), mytotal,
//					loginMember.getMemberLevel());
//
//			if (memberLevel == 1) {
//
//				LoginCommand member = memberService.login(loginMember.getMemberId());
//
//				LoginCommand login = member;
//
//				session.setAttribute("memberLogin", login);
//
//				model.addAttribute("level", login.getMemberLevel());
//				model.addAttribute("nickname", login.getMemberNickname());
//
//				return "/member/member_alert/levelUp";
//
//			}
//		}

		return "redirect:/diary/list/"+ loginMember.getMemberSeq();
	}
	
	
	// 해당list 내 글 모아보기
		@GetMapping(value = "/list/{memberSeq}")
		public String boardListMy(@PathVariable int memberSeq, Model model, Criteria cri,
				HttpSession session) {

			int total = diaryService.diaryListCount(memberSeq);

			List<DiaryListCommand> list = diaryService.diaryList(cri, memberSeq);
			model.addAttribute("diaryList", list);
	
			String diaryNickname = diaryService.diaryNickname(memberSeq);
			
			model.addAttribute("diaryNickname", diaryNickname);
			PaginVo pageCommand = new PaginVo();
			pageCommand.setCri(cri);
			pageCommand.setTotalCount(total);
			model.addAttribute("diaryTotal", total);
			model.addAttribute("pageMaker", pageCommand);

			model.addAttribute("testMemberSeq", memberSeq);
			return "diary/list";
		}
	
		
		// 게시글 디테일
		@GetMapping(value = "/detail")
		public String diaryDetail(@RequestParam int diarySeq, Model model, HttpSession session) {

			diaryService.diaryCountup(diarySeq);

			DiaryListCommand list = diaryService.diaryDetail(diarySeq);
			boolean myArticle = false;
			// 세션 값 loginMember에 저장

			LoginCommand loginMember = (LoginCommand) session.getAttribute("memberLogin");

			if (loginMember != null) {
				// 세션에서 멤버의 mSeq 를 boardVo에 셋팅
				int memberSeq = loginMember.getMemberSeq();
				// 세션에 저장된 mSeq와 게시글의 mSeq를 비교하여 내 글이면 수정 삭제 버튼이 뜨게
				if (memberSeq == list.getMemberSeq()) {
					myArticle = true;
				}

				model.addAttribute("myArticle", myArticle);
			}

			model.addAttribute("diaryList", list);
			model.addAttribute("diarySeq", diarySeq);

			DiaryHeartVo likeVo = new DiaryHeartVo();

			likeVo.setDiarySeq(diarySeq);
			likeVo.setMemberSeq(loginMember.getMemberSeq());

			int diarylike = diaryService.getDiaryLike(likeVo);

			model.addAttribute("diaryHeart", diarylike);
			System.out.println(list);
			return "diary/detail";
		}
	
	
		@PostMapping(value = "/heart", produces = "application/json")
		@ResponseBody
		public int boardLike(@RequestBody DiaryLikeCommand command, HttpSession session) {

			LoginCommand loginMember = (LoginCommand) session.getAttribute("memberLogin");

			DiaryHeartVo likeVo = new DiaryHeartVo();

			likeVo.setDiarySeq(command.getDiarySeq());
			likeVo.setMemberSeq(loginMember.getMemberSeq());

			if (command.getHeart() >= 1) {
				diaryService.deleteDiaryLike(likeVo);
				command.setHeart(0);
			} else {

				diaryService.insertDiaryLike(likeVo);
				command.setHeart(1);
			}

			// String result = Integer.toString(heart);

			return command.getHeart();

		}

		
		
		
		// 게시글 수정
		@GetMapping(value = "/edit/{diarySeqTest}")
		public String diaryEdit(@PathVariable int diarySeqTest, @ModelAttribute("DiaryUpdateCommand") DiaryUpdateCommand updateCommand, Model model, HttpSession session) {

//			//login seq 와 요청된(작성)seq 비교
//			if (diarySeqTest != updateCommand.getDiarySeq()) {
//
//				return "errors/memberAuthErrorDiary";
//			}
			LoginCommand loginMember = (LoginCommand) session.getAttribute("memberLogin");
			DiaryListCommand list = diaryService.diaryDetail(diarySeqTest);
			if(loginMember.getMemberSeq()==list.getMemberSeq()) {
				model.addAttribute("diaryList", list);
				return "diary/editForm";				
			}else {
				return "errors/memberAuthErrorDiary";
			}
		}

		// 게시글 수정
		@PostMapping(value = "/edit/{diarySeqTest}")
		public String diaryEdit(@PathVariable int diarySeqTest, @Valid @ModelAttribute("DiaryUpdateCommand") DiaryUpdateCommand updateCommand, BindingResult bindingResult,Model model,
				 HttpSession session, HttpServletRequest request) throws IllegalStateException, IOException {			
			if (bindingResult.hasErrors()) {
				//공백일 경우 내용 다시 띄워주기
				DiaryListCommand list = diaryService.diaryDetail(diarySeqTest);
				model.addAttribute("diaryList", list);
				return "diary/editForm";
			}		
			
			// 세션 값 loginMember에 저장
			LoginCommand loginMember = (LoginCommand) session.getAttribute("memberLogin");

			if (loginMember != null) {
				
				//공개여부 세팅
				String diaryOpen = request.getParameter("open");		
				updateCommand.setDiaryOpen(diaryOpen);


				//첨부파일 세팅
				MultipartFile file = updateCommand.getImg();
				
				//파일명
				String originalFileName = file.getOriginalFilename();
				
				//파일명 중 확장자만 추출
				String originalFileExtension = FilenameUtils.getExtension(originalFileName); //확장자 구하기
				
				//저장할때 쓸 파일명 랜덤생성 + 확장자
				String storedFileName = UUID.randomUUID().toString().replaceAll("-", "")+"."+originalFileExtension;
				
				//파일 저장을 위한 File 객체
				String rootPath = request.getSession().getServletContext().getRealPath("/");  

			    String attachPath = "resources/upload/";
				file.transferTo(new File(rootPath + attachPath +storedFileName));
			
				
				updateCommand.setDiaryImg(storedFileName); //저장할 파일 (랜덤생성된)이름을 vo에 셋팅
			
				diaryService.updateDiary(updateCommand.getDiaryTitle(), updateCommand.getDiaryContent(), diarySeqTest, updateCommand.getDiaryOpen(),updateCommand.getDiaryImg());
			
				System.out.println(" 수정 성공");
			} else {
				System.out.println("수정 실패");
				return "errors/mypageChangeError";
			}
				
				
			
			
			return "redirect:/diary/list/"+ loginMember.getMemberSeq();
		}

		// 게시글 삭제
		@GetMapping(value = "/delete")
		public String diaryDelete(@RequestParam int diarySeq, Model model, HttpSession session, Criteria cri) {

			// 세션 값 loginMember에 저장
			LoginCommand loginMember = (LoginCommand) session.getAttribute("memberLogin");

			if (loginMember != null) {
				// 세션에서 멤버의 mSeq 를 boardVo에 셋팅
				int memberSeq = loginMember.getMemberSeq();
				diaryService.deleteDiary(diarySeq, memberSeq);
				System.out.println("삭제 성공");
			} else {
				System.out.println("삭제 실패");
				return "errors/mypageChangeError";
			}

			return "redirect:/diary/list/"+ loginMember.getMemberSeq();
		}

		
		// 게시글 삭제
				@GetMapping(value = "/deleteImg")
				public String diaryDeleteImg(@RequestParam int diarySeq, Model model, HttpSession session, Criteria cri) {

					// 세션 값 loginMember에 저장
					LoginCommand loginMember = (LoginCommand) session.getAttribute("memberLogin");

					if (loginMember != null) {
						// 세션에서 멤버의 mSeq 를 boardVo에 셋팅
						int memberSeq = loginMember.getMemberSeq();
						diaryService.deleteDiaryImg(diarySeq);
						System.out.println("삭제 성공");
					} else {
						System.out.println("삭제 실패");
						return "errors/mypageChangeError";
					}

					return "redirect:/diary/detail?diarySeq="+ diarySeq;
				}
		
}
