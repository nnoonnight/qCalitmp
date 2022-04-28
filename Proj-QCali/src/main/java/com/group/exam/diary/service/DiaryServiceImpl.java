package com.group.exam.diary.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group.exam.diary.command.DiaryListCommand;
import com.group.exam.diary.dao.DiaryDao;
import com.group.exam.diary.vo.DiaryHeartVo;
import com.group.exam.diary.vo.DiaryVo;
import com.group.exam.utils.Criteria;
@Service
public class DiaryServiceImpl implements DiaryService {

	
	@Autowired
	private DiaryDao diaryDao;
	
	public DiaryServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public void insertDiary(DiaryVo diaryVo) {
		diaryDao.insertDiary(diaryVo);

	}

	
	@Override
	public void updateDiary(String diaryTitle, String diaryContent, int diarySeq, String diaryOpen, String diaryImg) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("diaryTitle", diaryTitle);
		map.put("diaryContent", diaryContent);
		map.put("diarySeq", diarySeq);
		map.put("diaryOpen", diaryOpen);
		map.put("diaryImg", diaryImg);
		diaryDao.updateDiary(map);
		
		
	}


	@Override
	public void deleteDiary(int diarySeq, int memberSeq) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		
		map.put("diarySeq", diarySeq);
		map.put("memberSeq", memberSeq);
		diaryDao.deleteDiary(map);
		
		
	}

	@Override
	public void deleteDiaryImg (int diarySeq) {
		diaryDao.deleteDiaryImg(diarySeq);
	}
	
	@Override
	public List<DiaryListCommand> diaryList(Criteria cri, int memberSeq){
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("memberSeq", memberSeq);
		map.put("rowStart", cri.getRowStart());
		map.put("rowEnd", cri.getRowEnd());
		return diaryDao.diaryList(map);
	}

	@Override
	public DiaryListCommand diaryDetail(int diarySeq) {
		return diaryDao.diaryDetail(diarySeq);
	}

	@Override
	public void diaryCountup(int diarySeq) {
		diaryDao.diaryCountup(diarySeq);

	}
	
	@Override
	public String memberAuth(int memberSeq) {
		// TODO Auto-generated method stub
		return diaryDao.memberAuth(memberSeq);
	}
	
	@Override
	public String diaryNickname (int memberSeq) {
		return diaryDao.diaryNickname(memberSeq);
	}

	@Override
	public int getDiaryLike(DiaryHeartVo vo) {
		return diaryDao.getDiaryLike(vo);
	}

	@Override
	public void insertDiaryLike(DiaryHeartVo vo) {
		diaryDao.insertDiaryLike(vo);
		diaryDao.updateDiaryLike(vo.getDiarySeq());
		

	}

	@Override
	public void deleteDiaryLike(DiaryHeartVo vo) {
		diaryDao.deleteDiaryLike(vo);
		diaryDao.updateDiaryLike(vo.getDiarySeq());

	}

	@Override
	public int diaryListCount(int memberSeq) {
		return diaryDao.diaryListCount(memberSeq);
	}

	





}
