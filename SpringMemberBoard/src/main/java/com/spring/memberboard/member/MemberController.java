package com.spring.memberboard.member;

// 코딩순서 : VO -> DAO -> Service -> ServiceImpl -> Controller

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MemberController {

	// 컨트롤러가 실행되면 의존성 주입에 의해 자동으로 new MemberService가 된다
	// memberService에 대입되는 클래스는 MemberServiceImpl.java파일의
	// @Service("memberService")에 해당되는 클래스를 넣는다
	// @Service("memberService")로 인해 프로그램이 구동되자마자
	// memberService이라는 bean객체가 만들어지기 때문에 가능하다
	@Autowired
	private MemberService memberService; // =new MemberServiceImpl();
											// @Autowired에 의해 위와 동일한 효과를 낸다

	@RequestMapping("loginform.me")
	public String loginForm() {
		return "member/loginForm";
	}

	@RequestMapping("joinform.me")
	public String joinForm() {
		return "member/joinForm";
	}

	@RequestMapping("joinprocess.me")
	public String insertMember(MemberVO memberVO, HttpServletResponse response) throws Exception {
		// form양식에서 받는 것들을 파라미터로 넘긴다
		int res = memberService.insertMember(memberVO);

		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter writer = response.getWriter();
		if (res != 0) {
			writer.write("<script>alert('회원 가입 성공!!');location.href='loginform.me';</script>");
		} else {
			writer.write("<script>alert('회원 가입 실패!!');location.href='joinform.me';</script>");
		}
		return null;
	}

	@RequestMapping("login.me")
	public String userCheck(MemberVO memberVO, HttpSession session, HttpServletResponse response) throws Exception {
		int res = memberService.userCheck(memberVO);

		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter writer = response.getWriter();
		if (res == 1) {
			session.setAttribute("id", memberVO.getId());
			writer.write("<script>alert('로그인 성공!!');location.href='boardlist.bo';</script>");
			// return "redirect:/main.me"; = 경고창을 띄울 필요가 없다면 이렇게 해도 된다
		} else {
			writer.write("<script>alert('로그인 실패!!');location.href='loginform.me';</script>");
			// return "redirect:/loginform.me";
		}
		return null;
	}
	
	@RequestMapping("logout.me")
	public String logout(HttpSession session) {
		session.invalidate();
		
		return "redirect:/loginform.me";
	}

	@RequestMapping("memberlist.me")
	public String getMemberlist(Model model) {
		ArrayList<MemberVO> member_list = memberService.getMemberlist();
		model.addAttribute("member_list", member_list);

		return "member/member_list";
	}

	@RequestMapping("memberinfo.me")
	public String selectMember(MemberVO memberVO, Model model) {
		MemberVO vo = memberService.selectMember(memberVO);
		model.addAttribute("memberVO", vo);

		return "member/member_info";
	}

	@RequestMapping("updateform.me")
	public String updateForm(MemberVO memberVO, Model model) {
		model.addAttribute("id", memberVO.getId());
		return "member/updateform";
	}

	@RequestMapping("memberupdate.me")
	public String updateMember(MemberVO memberVO, Model model, HttpServletResponse response) throws Exception {
		int res = memberService.updateMember(memberVO);

		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter writer = response.getWriter();
		if (res != 0) {
			writer.write("<script>alert('회원정보 수정 성공!!');location.href='memberinfo.me?id=" + memberVO.getId()
					+ "';</script>");
		} else {
			writer.write("<script>alert('회원정보 수정 실패!!');location.href='memberlist.me';</script>");
		}
		return null;
	}

	@RequestMapping("memberdelete.me")
	public String deleteMember(MemberVO memberVO, Model model, HttpServletResponse response) throws Exception {
		int res = memberService.deleteMember(memberVO);

		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter writer = response.getWriter();
		if (res != 0) {
			writer.write("<script>alert('회원정보 삭제 성공!!');location.href='memberlist.me?id=" + memberVO.getId()
					+ "';</script>");
		} else {
			writer.write("<script>alert('회원정보 삭제 실패!!');location.href='memberlist.me';</script>");
		}
		return null;
	}
}