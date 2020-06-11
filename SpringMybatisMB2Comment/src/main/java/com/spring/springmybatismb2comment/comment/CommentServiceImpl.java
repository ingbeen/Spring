package com.spring.springmybatismb2comment.comment;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.mapper.CommentMapper;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public int commentCount() throws Exception {
		CommentMapper commentMapper = sqlSession.getMapper(CommentMapper.class);
		return commentMapper.commentCount();
	}

	@Override
	public List<CommentVO> commentListService(int bno) throws Exception {
		CommentMapper commentMapper = sqlSession.getMapper(CommentMapper.class);
		return commentMapper.commentList(bno);
	}

	@Override
	public int commentInsertService(CommentVO comment) throws Exception {
		CommentMapper commentMapper = sqlSession.getMapper(CommentMapper.class);
		return commentMapper.commentInsert(comment);
	}

	@Override
	public int commentUpdateService(CommentVO comment) throws Exception {
		CommentMapper commentMapper = sqlSession.getMapper(CommentMapper.class);
		return commentMapper.commentUpdate(comment);
	}

	@Override
	public int commentDeleteService(int cno) throws Exception {
		CommentMapper commentMapper = sqlSession.getMapper(CommentMapper.class);
		return commentMapper.commentDelete(cno);
	}

}
