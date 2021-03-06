package com.spring.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import com.spring.springmybatis.MemberVO;

// MemberMapper에서 사용하는 메소드 이름이 xml문서의 id로 사용되어야 한다
public interface MemberMapper {
	ArrayList<MemberVO> getMembers();
	// ArrayList<MemberVO> getMembers(String t);
	// MemberVO getMember(String id);
	HashMap<String, String> getMember(String id); // hashMap 이용시 추가부분
	// 삽입 후 삽입한 결과 상태 반환하기 위해 반환값을 int로 정해줌
	int insertMember(MemberVO member);
	void insertMember2(HashMap<String, String> map);
	void updateMember(MemberVO member);
	void deleteMember(String id);
	int getCount();
}
