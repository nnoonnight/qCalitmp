package com.group.exam.diary.vo;

public class DiaryHeartVo {
	
	private int dHeartSeq;
	private int diarySeq;
	private int memberSeq;
	

	public int getdHeartSeq() {
		return dHeartSeq;
	}
	public void setdHeartSeq(int dHeartSeq) {
		this.dHeartSeq = dHeartSeq;
	}
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
	@Override
	public String toString() {
		return "DiaryHeartVo [dHeartSeq=" + dHeartSeq + ", diarySeq=" + diarySeq + ", memberSeq=" + memberSeq + "]";
	}


	
	
	
	
	

}
