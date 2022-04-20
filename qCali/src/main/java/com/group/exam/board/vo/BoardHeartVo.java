package com.group.exam.board.vo;

public class BoardHeartVo {
	
	private int heartSeq;
	private int boardSeq;
	private int memeberSeq;
	
	public int getHeartSeq() {
		return heartSeq;
	}
	public void setHeartSeq(int heartSeq) {
		this.heartSeq = heartSeq;
	}
	public int getBoardSeq() {
		return boardSeq;
	}
	public void setBoardSeq(int boardSeq) {
		this.boardSeq = boardSeq;
	}
	public int getMemeberSeq() {
		return memeberSeq;
	}
	public void setMemeberSeq(int memeberSeq) {
		this.memeberSeq = memeberSeq;
	}
	@Override
	public String toString() {
		return "BoardHeartVo [heartSeq=" + heartSeq + ", boardSeq=" + boardSeq + ", memeberSeq=" + memeberSeq + "]";
	}
	
	
	
	
	
	

}
