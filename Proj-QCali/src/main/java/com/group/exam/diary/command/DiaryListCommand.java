package com.group.exam.diary.command;

import java.util.Date;

public class DiaryListCommand {
	
	private int diarySeq;
	private String diaryTitle;
	private String diaryContent;
	private String diaryRegday;
	private String diaryOpen;
	private int memberSeq;
	private String memberNickname;
	private int diaryLike;
	private int diaryCount;
	
	
	
	
	
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
	public String getMemberNickname() {
		return memberNickname;
	}
	public void setMemberNickname(String memberNickname) {
		this.memberNickname = memberNickname;
	}
	@Override
	public String toString() {
		return "DiaryListCommand [diarySeq=" + diarySeq + ", diaryTitle=" + diaryTitle + ", diaryContent="
				+ diaryContent + ", diaryRegday=" + diaryRegday + ", diaryOpen=" + diaryOpen + ", memberSeq="
				+ memberSeq + ", memberNickname=" + memberNickname + ", diaryLike=" + diaryLike + ", diaryCount="
				+ diaryCount + "]";
	}

	
	
	

}
