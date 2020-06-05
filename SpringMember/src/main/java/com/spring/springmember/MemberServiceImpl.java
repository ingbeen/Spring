package com.spring.springmember;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * �Ʒ� 3���� ������̼��� ������Ʈ�� ����Ǹ�
 * �ڵ����� �Ѱ��� Bean��ü�� ���������
 * @ Controller
 * @ Service
 * @ Repository
 */

// ��ȣ�ȿ� �� ���� Bean��ü�� �̸��̴�
// ������ Ŭ���� �̸��� ����Ͽ� ù���ڸ� �ҹ��ڷ� �ٲپ
// ����Ʈ������ �����ȴ�
@Service("memberService")
public class MemberServiceImpl implements MemberService {
 
	/*
	 * ������ ���� / DI(dependency Injection)
	 * ��ü�� ���� ����� �ʰ� ��ü�� �����Ͽ� ����ϴ� ���(�������� ��)
	 * �� ����� ���� �ʿ��Ѱ� ������ ���� ���� ã�ƿ����� ���� ����� ��� ������ �ʿ��ϴٰ� ���� �ϸ� �ܺο��� �˾Ƽ� �������ְڴٴ� ���Դϴ�.
	 * �׷��� �ϴ� ������ ���������� �ְ�����, ���� ū ������ "�׷� �� ��(�����̳�)�� ���״� ��(��/����/������Ʈ) 
	 * �װ� �� ��(����Ͻ�)�̳� �Ű���"��� �� �� �ְ�, �̸� ���� �� �����ؼ� ǥ���ϸ�
	 *  '������ �и�(separation of concern)'�� �޼��ϱ� ���ؼ���� �� �� �ֽ��ϴ�.
	 */
	
	// ������Ʈ�� ������ �Ǹ� �ش� Ŭ���� ��ü�� ���� ������ �����ش�
	// MemberDAO�� null�� ������,
	// MemberDAO.java�� MemberDAOŬ������ @ Repository�� �پ� �����Ƿ�
	// MemberDAOŬ������ Bean��ü�� �ڵ����� �����ȴ�
	// �� �ڵ� ������ Bean��ü�� @Autowired�� ���εȴ�
	// required=false�� MemberDAOŬ������ ��� ������ �߻� �� �Ѵ�
	@Autowired(required = false)
	private MemberDAO memberDAO = null;

	@Override
	public int insertMember(MemberVO memberVO) {
		int res = memberDAO.insertMember(memberVO);

		return res;
	}

	@Override
	public int userCheck(MemberVO memberVO) {
		int res = memberDAO.userCheck(memberVO);

		return res;
	}

	@Override
	public ArrayList<MemberVO> getMemberlist() {
		ArrayList<MemberVO> member_list = new ArrayList<MemberVO>();
		member_list = memberDAO.getMemberlist();

		return member_list;
	}

	@Override
	public MemberVO selectMember(MemberVO memberVO) {
		MemberVO vo = memberDAO.selectMember(memberVO);

		return vo;
	}

	@Override
	public int deleteMember(MemberVO memberVO) {
		int res = memberDAO.deleteMember(memberVO);

		return res;
	}

	@Override
	public int updateMember(MemberVO memberVO) {
		int res = memberDAO.updateMember(memberVO);

		return res;
	}
}
