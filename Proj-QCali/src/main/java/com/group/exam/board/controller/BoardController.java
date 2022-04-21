package com.group.exam.board.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.group.exam.board.command.BoardLikeCommand;
import com.group.exam.board.command.BoardlistCommand;
import com.group.exam.board.command.BoardupdateCommand;
import com.group.exam.board.command.QuestionAdayCommand;
import com.group.exam.board.service.BoardService;
import com.group.exam.board.vo.BoardHeartVo;
import com.group.exam.board.vo.BoardVo;
import com.group.exam.member.command.LoginCommand;
import com.group.exam.member.service.MemberService;
import com.group.exam.utils.PaginVo;
import com.group.exam.utils.Criteria;

@Controller
@RequestMapping("/board")
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	public static int num;

	private BoardService boardService;

	private MemberService memberService;

	@Autowired
	public BoardController(BoardService boardService, MemberService memberService) {
		this.boardService = boardService;

		this.memberService = memberService;

	}

	@GetMapping(value = "/write")
	public String insertBoard(@ModelAttribute("boardData") BoardVo boardVo, HttpSession session) {

		return "board/writeForm";
	}

	@PostMapping(value = "/write")
	public String insertBoard(@Valid @ModelAttribute("boardData") BoardVo boardVo, BindingResult bindingResult,
			Criteria cri, HttpSession session, Model model) {
		// not null 체크
		if (bindingResult.hasErrors()) {

			return "board/writeForm";
		}

		LoginCommand loginMember = (LoginCommand) session.getAttribute("memberLogin");

		boolean memberAuth = boardService.memberAuth(loginMember.getMemberSeq()).equals("F");
		if (memberAuth == true) {
			return "errors/memberAuthError"; // 이메일 인증 x -> 예외 페이지

		}

		// 세션에서 멤버의 mSeq 를 boardVo에 셋팅
		boardVo.setMemberSeq(loginMember.getMemberSeq());

		// insert
		boardService.insertBoard(boardVo);

		// update

		int mytotal = boardService.mylistCount(loginMember.getMemberSeq());

		if (mytotal > 10) {
			int memberLevel = boardService.memberLevelup(loginMember.getMemberSeq(), mytotal,
					loginMember.getMemberLevel());

			if (memberLevel == 1) {

				LoginCommand member = memberService.login(loginMember.getMemberId());

				LoginCommand login = member;

				session.setAttribute("memberLogin", login);

				model.addAttribute("level", login.getMemberLevel());
				model.addAttribute("nickname", login.getMemberNickname());

				return "/member/member_alert/levelUp";

			}
		}

		return "redirect:/board/list";
	}

	// 리스트 전체
	@GetMapping(value = "/list")
	public String boardListAll(Criteria cri, Model model, HttpSession session) {

		/*
		 * @RequestParam null 허용 방법 - (required = false) == true 가 기본 설정임 - @Nullable
		 * 어노테이션 추가
		 * 
		 * - int 형의 경우 (defaultValue="0")
		 * 
		 */

//		if (currentPage == 0) {
//			currentPage = 1;
//		}

		int total = boardService.listCount();

		if (total == 0) {
			total = 1;
		}
		/*
		 * 1 1,10 2 11, 20
		 */

		List<BoardlistCommand> list = boardService.boardList(cri);

		model.addAttribute("boardList", list);

		// model.addAttribute("currentPage", currentPage);
		PaginVo pageCommand = new PaginVo();
		pageCommand.setCri(cri);
		pageCommand.setTotalCount(total);
		model.addAttribute("pageMaker", pageCommand);
		model.addAttribute("boardTotal", total);

		// 질문 출력 관련
		if (num == 0) {

			num = boardService.currentSequence();

			if (num == 0) {
				num = 1;
			}
		}
		logger.info("" + num);
		QuestionAdayCommand question = boardService.questionselect(num);

		model.addAttribute("boardQuestion", question);

		return "board/list";
	}

	@Scheduled(cron = "0 0 12 1/1 * ?") // 하루마다 출력으로 표현식
	public void getSequence() {
		logger.info(new Date() + "스케쥴러 실행");
		num = boardService.getSequence();
	}

	// 해당list 내 글 모아보기
	@GetMapping(value = "/mylist")
	public String boardListMy(@RequestParam("memberSeq") int memberSeq, Model model, Criteria cri,
			HttpSession session) {

		int total = boardService.mylistCount(memberSeq);

		List<BoardlistCommand> list = boardService.boardMyList(cri, memberSeq);
		model.addAttribute("boardList", list);

		PaginVo pageCommand = new PaginVo();
		pageCommand.setCri(cri);
		pageCommand.setTotalCount(total);
		model.addAttribute("boardTotal", total);
		model.addAttribute("pageMaker", pageCommand);

		return "board/mylist";
	}

	// 게시글 디테일
	@GetMapping(value = "/detail")
	public String boardListDetail(@RequestParam int boardSeq, Model model, HttpSession session) {

		boardService.boardCountup(boardSeq);

		BoardlistCommand list = boardService.boardListDetail(boardSeq);
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

		model.addAttribute("boardList", list);
		model.addAttribute("boardSeq", boardSeq);

		BoardHeartVo likeVo = new BoardHeartVo();

		likeVo.setBoardSeq(boardSeq);
		likeVo.setMemberSeq(loginMember.getMemberSeq());

		int boardlike = boardService.getBoardLike(likeVo);

		model.addAttribute("boardHeart", boardlike);

		return "board/listDetail";
	}

	@PostMapping(value = "/heart", produces = "application/json")
	@ResponseBody
	public int boardLike(@RequestBody BoardLikeCommand command, HttpSession session) {

		LoginCommand loginMember = (LoginCommand) session.getAttribute("memberLogin");

		BoardHeartVo likeVo = new BoardHeartVo();

		likeVo.setBoardSeq(command.getBoardSeq());
		likeVo.setMemberSeq(loginMember.getMemberSeq());

		if (command.getHeart() >= 1) {
			boardService.deleteBoardLike(likeVo);
			command.setHeart(0);
		} else {

			boardService.insertBoardLike(likeVo);
			command.setHeart(1);
		}

		// String result = Integer.toString(heart);

		return command.getHeart();

	}

	// 게시글 수정
	@GetMapping(value = "/edit")
	public String boardEdit(@ModelAttribute("boardEditData") BoardVo boardVo, HttpSession session, Model model) {

		return "board/editForm";
	}

	// 게시글 수정
	@PostMapping(value = "/edit")
	public String boardEdit(@Valid @ModelAttribute("boardEditData") BoardupdateCommand updateCommand,
			BindingResult bindingResult, Model model, HttpSession session) {

		if (bindingResult.hasErrors()) {

			return "board/editForm";
		}

		// 세션 값 loginMember에 저장
		LoginCommand loginMember = (LoginCommand) session.getAttribute("memberLogin");

		if (loginMember != null) {
			// 세션에서 멤버의 mSeq 를 boardVo에 셋팅

			int boardSeq = updateCommand.getBoardSeq();

			BoardlistCommand list = boardService.boardListDetail(boardSeq);

			model.addAttribute("boardList", list);
			boardService.updateBoard(updateCommand.getBoardTitle(), updateCommand.getBoardContent(), boardSeq);
			System.out.println(" 수정 성공");
		} else {
			System.out.println("수정 실패");
			return "errors/mypageChangeError";
		}

		return "redirect:/board/list";
	}

	// 게시글 삭제
	@GetMapping(value = "/delete")
	public String boardDelect(@RequestParam int boardSeq, Model model, HttpSession session, Criteria cri) {

		// 세션 값 loginMember에 저장
		LoginCommand loginMember = (LoginCommand) session.getAttribute("memberLogin");

		if (loginMember != null) {
			// 세션에서 멤버의 mSeq 를 boardVo에 셋팅
			int memberSeq = loginMember.getMemberSeq();
			boardService.deleteBoardOne(boardSeq, memberSeq);
			System.out.println("삭제 성공");
		} else {
			System.out.println("삭제 실패");
			return "errors/mypageChangeError";
		}

		return "redirect:/board/list";
	}

	// 닉네임 , 제목으로 검색
	@GetMapping(value = "/search")
	public String boardSearchList(@RequestParam("searchOption") String searchOption,
			@RequestParam("searchWord") String searchWord, Model model, Criteria cri) {
		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("searchOption", searchOption);
		map.put("searchWord", searchWord);
		map.put("rowStart", cri.getRowStart());
		map.put("rowEnd", cri.getRowEnd());
		List<BoardlistCommand> list = boardService.boardSearch(map);
		model.addAttribute("boardList", list);

		return "/board/list";
	}

}
