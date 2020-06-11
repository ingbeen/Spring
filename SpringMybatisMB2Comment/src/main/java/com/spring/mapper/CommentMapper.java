package com.spring.mapper;
// mapper.xml�� ������ ��Ű���� �ҽ����� �����Ұ�  

import java.util.List;

import com.spring.springmybatismb2comment.comment.CommentVO;

public interface CommentMapper {
	public int commentCount() throws Exception; // ��� ����
	public List<CommentVO> commentList(int bno) throws Exception; // ��� ����Ʈ
	public int commentInsert(CommentVO comment) throws Exception; // ��� ����
	public int commentUpdate(CommentVO comment) throws Exception; // ��� ����
	public int commentDelete(int cno) throws Exception; // ��� ����
}