package com.spring.springmember;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * 아래 3개의 어노테이션은 프로젝트가 실행되면
 * 자동으로 한개씩 Bean객체가 만들어진다
 * @ Controller
 * @ Service
 * @ Repository
 */

// 괄호안에 들어갈 값은 Bean객체의 이름이다
// 없으면 클래스 이름을 사용하여 첫문자를 소문자로 바꾸어서
// 디폴트값으로 생성된다
@Service("memberService")
public class MemberServiceImpl implements MemberService {
 
	/*
	 * 의존성 주입 / DI(dependency Injection)
	 * 객체를 직접 만들기 않고 객체를 전달하여 사용하는 방식(유지보수 편리)
	 * 한 마디로 뭔가 필요한게 있으면 내가 가서 찾아오던지 직접 만드는 대신 무엇이 필요하다고 선언만 하면 외부에서 알아서 제공해주겠다는 것입니다.
	 * 그렇게 하는 이유는 여러가지가 있겠지만, 가장 큰 이유는 "그런 건 내(컨테이너)가 할테니 넌(빈/서비스/컴포넌트) 
	 * 네가 할 일(비즈니스)이나 신경써라"라고 할 수 있고, 이를 조금 더 정제해서 표현하면
	 *  '관심의 분리(separation of concern)'를 달성하기 위해서라고 할 수 있습니다.
	 */
	
	// 프로젝트가 실행이 되면 해당 클래스 객체를 만들어서 대입을 시켜준다
	// MemberDAO는 null값 이지만,
	// MemberDAO.java의 MemberDAO클래스에 @ Repository가 붙어 있으므로
	// MemberDAO클래스의 Bean객체는 자동으로 생성된다
	// 그 자동 생선된 Bean객체는 @Autowired에 맵핑된다
	// required=false는 MemberDAO클래스가 없어도 오류가 발생 안 한다
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
