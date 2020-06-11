package com.spring.springmybatismb2comment.comment;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	// ¥Ò±€ ∏ÆΩ∫∆Æ
	@RequestMapping(value="comment_list.bo", produces = "application/json;charset=utf-8") 
	private List<CommentVO> commentServiceList(@RequestParam int bno) throws Exception {
		List<CommentVO> comment_list = commentService.commentListService(bno);
	
		return comment_list;
	}
	
	// ¥Ò±€ ¿€º∫
	@RequestMapping(value="comment_insert.bo", produces = "application/json;charset=utf-8") 
	private int commentServiceInsert(CommentVO comment, HttpSession session) throws Exception {
		comment.setWriter((String) session.getAttribute("id"));
		
		return commentService.commentInsertService(comment);
	}
	
	// ¥Ò±€ ºˆ¡§
	@RequestMapping(value="comment_update.bo", produces = "application/json;charset=utf-8") 
	private int commentServiceUpdateProc(@RequestParam int cno, @RequestParam String content) throws Exception {
		CommentVO comment = new CommentVO();
		comment.setCno(cno);
		comment.setContent(content);
		
		return commentService.commentUpdateService(comment);
	}
	
	// ¥Ò±€ ªË¡¶
	@RequestMapping(value="comment_delete.bo", produces = "application/json;charset=utf-8") 
	private int commentServiceDelete(@RequestParam int cno) throws Exception {
		
		return commentService.commentDeleteService(cno);
	}
}