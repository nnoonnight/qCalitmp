package com.group.exam.board.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.group.exam.board.command.BoardlistCommand;
import com.group.exam.board.command.QuestionAdayCommand;
import com.group.exam.board.vo.BoardHeartVo;
import com.group.exam.board.vo.BoardVo;
import com.group.exam.utils.Criteria;
@Repository
public class BoardDaoImpl implements BoardDao{

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public BoardDaoImpl (SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	
	
	@Override
	public void insertBoard(BoardVo boardVo) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.insert("insertBoard", boardVo);
		
	}

	@Override
	public void updateBoard(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		// 게시글 제목과 내용 수정 
		sqlSessionTemplate.update("updateBoard", map);
		
	}

	@Override
	public void deleteBoardOne(HashMap<String, Integer> map) {
		// TODO Auto-generated method stub
	
		sqlSessionTemplate.delete("deleteBoardOne", map);
		
	}


	@Override
	public List<BoardlistCommand> boardMyList(HashMap<String, Object> map) {
		// TODO Auto-generated method stub

		return sqlSessionTemplate.selectList("boardMylist", map);
	}

	@Override
	public BoardlistCommand boardListDetail(int boardSeq) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("boardlistDetail", boardSeq);
	}

	@Override
	public int listCount() {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("boardlistCount");
	}



	@Override
	public List<BoardlistCommand> boardList(Criteria cri) {
		// TODO Auto-generated method stub

		return sqlSessionTemplate.selectList("boardlist", cri);
	}


	@Override
	public void boardCountup(int boardSeq) {
		// TODO Auto-generated method stub
		
		sqlSessionTemplate.update("boardCountup", boardSeq);
		
	}


	@Override
	public int boardMylistCount(int memberSeq) {
		// TODO Auto-generated method stub

		return sqlSessionTemplate.selectOne("boardMylistCount", memberSeq);
	}
	
	
	//좋아요 기능 관련
	@Override
	public int getBoardLike(BoardHeartVo vo) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("getLike", vo);
	}

	@Override
	public void insertBoardLike(BoardHeartVo vo) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.insert("createLike", vo);
		
	}

	@Override
	public void deleteBoardLike(BoardHeartVo vo) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete("deleteLike", vo);
		
	}

	@Override
	public void updateBoardLike(int boardSeq) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.update("updateLike", boardSeq);
		
	}

	
	@Override
	public String memberAuth(int memberSeq) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("memberAuth", memberSeq);
	}


	//멤버 레벨 관련
	@Override
	public int memberLevelup(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.update("memberLevelup", map);
		
	}


	@Override
	public QuestionAdayCommand questionselect(int num) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("questionselect", num);
	}


	@Override
	public int getSequence() {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("getSequence");
	}


	@Override
	public int currentSequence() {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("currentSequence");
	}


	@Override
	public List<BoardlistCommand> boardSearch(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("boardSearch", map);
	}

}
