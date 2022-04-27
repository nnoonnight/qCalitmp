package com.group.exam.diary.vo;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

public class DiaryVo {
	
	private int diarySeq;
	@NotEmpty(message="제목을 입력해주세요.")
	private String diaryTitle;
	@NotEmpty(message="내용을 입력해주세요.")
	private String diaryContent;
	private String diaryRegday;
	private String diaryOpen;
	private int memberSeq;
	private int diaryLike;
	private int diaryCount;
	public int getDiarySeq() {
		return diarySeq;
	}
	public void setDiarySeq(int diarySeq) {
		this.diarySeq = diarySeq;
	}
	public String getDiaryTitle() {
		return diaryTitle;
	}
	public void setDiaryTitle(String diaryTitle) {
		this.diaryTitle = diaryTitle;
	}
	public String getDiaryContent() {
		return diaryContent;
	}
	public void setDiaryContent(String diaryContent) {
		this.diaryContent = diaryContent;
	}
	public String getDiaryRegday() {
		return diaryRegday;
	}
	public void setDiaryRegday(String diaryRegday) {
		this.diaryRegday = diaryRegday;
	}
	public String getDiaryOpen() {
		return diaryOpen;
	}
	public void setDiaryOpen(String diaryOpen) {
		this.diaryOpen = diaryOpen;
	}
	public int getMemberSeq() {
		return memberSeq;
	}
	public void setMemberSeq(int memberSeq) {
		this.memberSeq = memberSeq;
	}
	public int getDiaryLike() {
		return diaryLike;
	}
	public void setDiaryLike(int diaryLike) {
		this.diaryLike = diaryLike;
	}
	public int getDiaryCount() {
		return diaryCount;
	}
	public void setDiaryCount(int diaryCount) {
		this.diaryCount = diaryCount;
	}
	
	
	
	
	
	
	
	

}
