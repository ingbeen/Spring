package com.spring.mapper; // MemberMapper.xml과 동일한 패키지에 작성할 것!! 

import com.spring.springwebsocket2.MemberVO;

public interface MemberMapper {
	int insertMember(MemberVO vo);
	int userCheckMember(String id);
	String pickNameMember(String id);
}
