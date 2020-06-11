package com.spring.springwebsocket2;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	SqlSession sqlSession;

	@Override
	public int insertMember(MemberVO vo) {
		MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
		int res = memberMapper.insertMember(vo);
		System.out.println(res);
		return res;
	}

	@Override
	public int userCheckMember(MemberVO vo) {
		MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
		int res = memberMapper.userCheckMember(vo.getId());	
		System.out.println(res);
		return res;
	}
	
	@Override
	public String pickNameMember(MemberVO vo) {
		MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
		String name = memberMapper.pickNameMember(vo.getId()); 
		return name;
	}
}
