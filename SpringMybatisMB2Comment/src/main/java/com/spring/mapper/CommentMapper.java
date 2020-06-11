package com.spring.mapper;
// mapper.xml과 동일한 패키지에 소스파일 생성할것  

import java.util.List;

import com.spring.springmybatismb2comment.comment.CommentVO;

public interface CommentMapper {
	public int commentCount() throws Exception; // 댓글 개수
	public List<CommentVO> commentList(int bno) throws Exception; // 댓글 리스트
	public int commentInsert(CommentVO comment) throws Exception; // 댓글 삽입
	public int commentUpdate(CommentVO comment) throws Exception; // 댓글 수정
	public int commentDelete(int cno) throws Exception; // 댓글 삭제
}