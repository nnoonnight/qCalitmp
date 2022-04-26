package com.group.exam.diary.vo;

public class DiaryHeartVo {
	
	private int heartSeq;
	private int diarySeq;
	private int memberSeq;
	
	public int getHeartSeq() {
		return heartSeq;
	}
	public void setHeartSeq(int heartSeq) {
		this.heartSeq = heartSeq;
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
		return "DiaryHeartVo [heartSeq=" + heartSeq + ", diarySeq=" + diarySeq + ", memberSeq=" + memberSeq + "]";
	}
	

	
	
	
	
	

}
