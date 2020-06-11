package com.spring.springmybatismb2comment.comment;

import java.util.List;

public interface CommentService {
	public int commentCount() throws Exception; // ��� ����
	public List<CommentVO> commentListService(int bno) throws Exception; // ��� ����Ʈ
	public int commentInsertService(CommentVO comment) throws Exception; // ��� ����
	public int commentUpdateService(CommentVO comment) throws Exception; // ��� ����
	public int commentDeleteService(int cno) throws Exception; // ��� ����
}
