package com.group.exam.diary.command;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;


public class DiaryupdateCommand {
	
	private int diarySeq;
	private int memberSeq;
	@NotBlank(message="제목을 입력해 주세요")
	private String diaryTitle;
	@NotEmpty(message="내용을 입력해 주세요")
	private String diaryContent;
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

	
	


	
}
