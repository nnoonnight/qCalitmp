package com.group.exam.diary.service;

import java.util.List;

import com.group.exam.diary.command.DiaryListCommand;
import com.group.exam.diary.vo.DiaryHeartVo;
import com.group.exam.diary.vo.DiaryVo;
import com.group.exam.utils.Criteria;

public interface DiaryService {


	public void insertDiary (DiaryVo diaryVo); 
	
	public void updateDiary (String diaryTitle, String diaryContent, int diarySeq);
	
	public void deleteDiary (int diarySeq, int memberSeq); 
	
	public List<DiaryListCommand> DiaryList(Criteria cri,int memberSeq);
		
	public DiaryListCommand DiaryDetail (int diarySeq);
	
	public void diaryCountup (int diarySeq); 

	//좋아요 기능 관련
	public void insertDiaryLike(DiaryHeartVo vo);
	
	public void deleteDiaryLike(DiaryHeartVo vo);
	
	public int getDiaryLike(DiaryHeartVo vo);
	
    
}
