package com.group.exam.diary.dao;

import java.util.HashMap;
import java.util.List;

import com.group.exam.diary.command.DiaryListCommand;
import com.group.exam.diary.vo.DiaryHeartVo;
import com.group.exam.diary.vo.DiaryVo;
import com.group.exam.utils.Criteria;

public interface DiaryDao {
	
	
	public void insertDiary (DiaryVo diaryVo); 
	
	public void updateDiary (HashMap<String, Object> map);
	
	public void deleteDiary (HashMap<String, Integer> map); 
	
	public List<DiaryListCommand> DiaryList(HashMap<String, Object> map);
		
	public DiaryListCommand DiaryDetail (int diarySeq);
	
	public void diaryCountup (int diarySeq); 

	//좋아요 기능 관련
    public int getDiaryLike(DiaryHeartVo vo);

    public void insertDiaryLike(DiaryHeartVo vo);

    public void deleteDiaryLike(DiaryHeartVo vo);

    public void updateDiaryLike(int diarySeq);

}
