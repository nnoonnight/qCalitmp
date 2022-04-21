package com.group.exam.diary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.group.exam.board.service.BoardService;
import com.group.exam.diary.service.DiaryService;
import com.group.exam.member.service.MemberService;

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
	
	
}
