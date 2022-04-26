package com.group.exam.diary.command;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;


public class DiaryUpdateCommand {
	
	private int diarySeq;
	private int memberSeq;
	@NotBlank(message="제목을 입력해 주세요")
	private String diaryTitle;
	@NotEmpty(message="내용을 입력해 주세요")
	private String diaryContent;
	private String diaryOpen;
	public int getDiarySeq() {
		return diarySeq;
	}
	public void setDiarySeq(int diarySeq) {
		this.diarySeq = diarySeq;
	}
	public int getMemberSeq() {
		return memberSeq;
	}
	public void setMemberSeq(int memberSeq) {
		this.memberSeq = memberSeq;
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
	public String getDiaryOpen() {
		return diaryOpen;
	}
	public void setDiaryOpen(String diaryOpen) {
		this.diaryOpen = diaryOpen;
	}
	@Override
	public String toString() {
		return "DiaryUpdateCommand [diarySeq=" + diarySeq + ", memberSeq=" + memberSeq + ", diaryTitle=" + diaryTitle
				+ ", diaryContent=" + diaryContent + ", diaryOpen=" + diaryOpen + "]";
	}
	
	
	
	
	


	
}
