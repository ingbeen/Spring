package com.spring.memberboard.member;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.mapper.MemberMapper;

@Service
public class MemberService {

	@Autowired(required = true)
	private SqlSession sqlSession = null;
	
	public ArrayList<MemberVO> getMemberlist() {
		MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
		ArrayList<MemberVO> member_list = memberMapper.getMemberlist();

		return member_list;
	}
	
	public MemberVO selectMember(MemberVO memberVO) {
		MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
		MemberVO resultMemberVO = memberMapper.selectMember(memberVO);

		return resultMemberVO;
	}
	
	public int insertMember(MemberVO memberVO) {
		MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
		int res = memberMapper.insertMember(memberVO);

		return res;
	}

	public boolean userCheck(MemberVO memberVO) {
		MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
		MemberVO resultMemberVO = memberMapper.selectMember(memberVO);
		boolean check = false;
		if(memberVO.getPassword().equals(resultMemberVO.getPassword())) {
			check = true;
		}

		return check;
	}

	public int updateMember(MemberVO memberVO) {
		MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
		int res = memberMapper.updateMember(memberVO);

		return res;
	}

	public int deleteMember(MemberVO memberVO) {
		MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
		int res = memberMapper.deleteMember(memberVO);
		
		return res;
	}
}
