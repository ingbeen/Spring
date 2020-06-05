package com.spring.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import com.spring.springmybatis.MemberVO;

// MemberMapper���� ����ϴ� �޼ҵ� �̸��� xml������ id�� ���Ǿ�� �Ѵ�
public interface MemberMapper {
	ArrayList<MemberVO> getMembers();
	// ArrayList<MemberVO> getMembers(String t);
	// MemberVO getMember(String id);
	HashMap<String, String> getMember(String id); // hashMap �̿�� �߰��κ�
	// ���� �� ������ ��� ���� ��ȯ�ϱ� ���� ��ȯ���� int�� ������
	int insertMember(MemberVO member);
	void insertMember2(HashMap<String, String> map);
	void updateMember(MemberVO member);
	void deleteMember(String id);
	int getCount();
}