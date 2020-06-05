package com.spring.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import com.spring.memberboard.board.BoardVO;

public interface BoardMapper {
	int getListCount();
	ArrayList<BoardVO> getBoardList(HashMap<String, Integer> startEndRow);
	Integer getMaxNumber();
	void insertBoard(BoardVO boardVO);
	BoardVO getDetail(BoardVO boardVO);
	void setReadCountUpdate(BoardVO boardVO);
	void setRe_seqUpdate(BoardVO boardVO);
	void boardReply(BoardVO boardVO);
	void boardModify(BoardVO boardVO);
	void boardDelete(BoardVO boardVO);
}
