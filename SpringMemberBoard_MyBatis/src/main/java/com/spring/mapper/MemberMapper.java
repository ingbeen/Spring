package com.spring.mapper;

import java.util.ArrayList;

import com.spring.memberboard.member.MemberVO;

public interface MemberMapper {
	ArrayList<MemberVO> getMemberlist();
	MemberVO selectMember(MemberVO memberVO);
	int insertMember(MemberVO memberVO);
	int updateMember(MemberVO memberVO);
	int deleteMember(MemberVO memberVO);
}
