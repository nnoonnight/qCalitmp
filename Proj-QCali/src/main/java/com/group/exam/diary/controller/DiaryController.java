package com.group.exam.diary.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.group.exam.board.command.BoardlistCommand;
import com.group.exam.board.vo.BoardVo;
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
	
	@GetMapping(value = "/write")
	public String insertDiary(@ModelAttribute("diaryData") DiaryVo diaryVo, HttpSession session) {

		return "diary/writeForm";
	}

	@PostMapping(value = "/write")
	public String insertBoard(@Valid @ModelAttribute("boardData") DiaryVo diaryVo, BindingResult bindingResult,
			Criteria cri, HttpSession session, Model model) {
		// not null 체크
		if (bindingResult.hasErrors()) {

			return "diary/writeForm";
		}

		LoginCommand loginMember = (LoginCommand) session.getAttribute("memberLogin");

		boolean memberAuth = diaryService.memberAuth(loginMember.getMemberSeq()).equals("F");
		if (memberAuth == true) {
			return "errors/memberAuthError"; // 이메일 인증 x -> 예외 페이지

		}

		// 세션에서 멤버의 mSeq 를 boardVo에 셋팅
		diaryVo.setMemberSeq(loginMember.getMemberSeq());

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

		return "redirect:/diary/list?memberSeq="+loginMember.getMemberSeq();
	}
	
	
	// 해당list 내 글 모아보기
		@GetMapping(value = "/list")
		public String boardListMy(@RequestParam("memberSeq") int memberSeq, Model model, Criteria cri,
				HttpSession session) {

			int total = diaryService.diaryListCount(memberSeq);

			List<DiaryListCommand> list = diaryService.diaryList(cri, memberSeq);
			model.addAttribute("boardList", list);

			PaginVo pageCommand = new PaginVo();
			pageCommand.setCri(cri);
			pageCommand.setTotalCount(total);
			model.addAttribute("boardTotal", total);
			model.addAttribute("pageMaker", pageCommand);

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

			return "diary/Detail";
		}
	
	
		// 게시글 수정
		@GetMapping(value = "/edit")
		public String diaryEdit(@ModelAttribute("diaryEditData") BoardVo boardVo, HttpSession session, Model model) {

			return "diary/editForm";
		}

		// 게시글 수정
		@PostMapping(value = "/edit")
		public String diaryEdit(@Valid @ModelAttribute("diaryEditData") DiaryUpdateCommand updateCommand,
				BindingResult bindingResult, Model model, HttpSession session) {

			if (bindingResult.hasErrors()) {

				return "diary/editForm";
			}

			// 세션 값 loginMember에 저장
			LoginCommand loginMember = (LoginCommand) session.getAttribute("memberLogin");

			if (loginMember != null) {
				// 세션에서 멤버의 mSeq 를 boardVo에 셋팅

				int diarySeq = updateCommand.getDiarySeq();

				DiaryListCommand list = diaryService.diaryDetail(diarySeq);

				model.addAttribute("diaryList", list);
				diaryService.updateDiary(updateCommand.getDiaryTitle(), updateCommand.getDiaryContent(), diarySeq);
				System.out.println(" 수정 성공");
			} else {
				System.out.println("수정 실패");
				return "errors/mypageChangeError";
			}

			return "redirect:/diary/list";
		}

		// 게시글 삭제
		@GetMapping(value = "/delete")
		public String diaryDelect(@RequestParam int diarySeq, Model model, HttpSession session, Criteria cri) {

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

			return "redirect:/diary/list";
		}

	
}
