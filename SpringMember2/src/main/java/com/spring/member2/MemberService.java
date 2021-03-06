package com.spring.member2;

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
		ArrayList<MemberVO> member_list = null;
		
		try {
			MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
			member_list = memberMapper.getMemberlist();
		} catch (Exception ex) {
			System.out.println("getMemberlist 에러 = " + ex.getMessage());
			ex.printStackTrace();
		}

		return member_list;
	}
	
	public MemberVO selectMember(MemberVO memberVO) {
		MemberVO vo = null;
		
		try {
			MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
			vo = memberMapper.selectMember(memberVO);
		} catch (Exception ex) {
			System.out.println("selectMember 에러 = " + ex.getMessage());
			ex.printStackTrace();
		}

		return vo;
	}
	
	public int insertMember(MemberVO memberVO) {
		int res = 0;
		
		try {
			MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
			res = memberMapper.insertMember(memberVO);
		} catch (Exception ex) {
			System.out.println("insertMember 에러 = " + ex.getMessage());
			ex.printStackTrace();
		}

		return res;
	}

	public boolean userCheck(MemberVO memberVO) {
		MemberVO vo = null;
		boolean check = false;
		
		try {
			MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
			vo = memberMapper.selectMember(memberVO);
			if(memberVO.getPassword().equals(vo.getPassword())) {
				check = true;
			}
		} catch (Exception ex) {
			System.out.println("userCheck 에러 = " + ex.getMessage());
			ex.printStackTrace();
		}

		return check;
	}

	public int updateMember(MemberVO memberVO) {
		int res = 0;
		
		try {
			MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
			res = memberMapper.updateMember(memberVO);
		} catch (Exception ex) {
			System.out.println("updateMember 에러 = " + ex.getMessage());
			ex.printStackTrace();
		}

		return res;
	}

	public int deleteMember(MemberVO memberVO) {
		int res = 0;
		
		try {
			MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
			res = memberMapper.deleteMember(memberVO);
		} catch (Exception ex) {
			System.out.println("deleteMember 에러 = " + ex.getMessage());
			ex.printStackTrace();
		}

		return res;
	}
}
