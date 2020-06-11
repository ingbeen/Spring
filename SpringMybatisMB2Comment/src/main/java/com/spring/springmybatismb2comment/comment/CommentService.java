package com.spring.springmybatismb2comment.comment;

import java.util.List;

public interface CommentService {
	public int commentCount() throws Exception; // ´ñ±Û °³¼ö
	public List<CommentVO> commentListService(int bno) throws Exception; // ´ñ±Û ¸®½ºÆ®
	public int commentInsertService(CommentVO comment) throws Exception; // ´ñ±Û »ðÀÔ
	public int commentUpdateService(CommentVO comment) throws Exception; // ´ñ±Û ¼öÁ¤
	public int commentDeleteService(int cno) throws Exception; // ´ñ±Û »èÁ¦
}
