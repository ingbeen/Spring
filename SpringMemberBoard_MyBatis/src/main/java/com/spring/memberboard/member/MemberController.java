package com.spring.memberboard.member;

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

	@Autowired
	private MemberService memberService;

	@RequestMapping("/login.me")
	public String userCheck(MemberVO memberVO, HttpSession session, HttpServletResponse response) throws Exception {
		boolean check = memberService.userCheck(memberVO);

		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter writer = response.getWriter();
		if (check == true) {
			session.setAttribute("id", memberVO.getId());
			writer.write("<script>alert('로그인 성공!!');location.href='boardlist.bo';</script>");
		} else {
			writer.write("<script>alert('로그인 실패!!');location.href='loginform.me';</script>");
		}
		return null;
	}

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
		int res = memberService.insertMember(memberVO);

		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter writer = response.getWriter();
		if (res != 0) {
			writer.write("<script>alert('회원 가입 성공!!');" + "location.href='loginform.me';</script>");
		} else {
			writer.write("<script>alert('회원 가입 실패!!');" + "location.href='joinform.me';</script>");
		}
		return null;
	}

	@RequestMapping("memberlist.me")
	public String getMemberlist(Model model) {
		ArrayList<MemberVO> member_list = memberService.getMemberlist();
		model.addAttribute("member_list", member_list);

		return "member/member_list";
	}

	@RequestMapping("memberinfo.me")
	public String selectMember(MemberVO memberVO, Model model) {
		MemberVO resultMemberVO = memberService.selectMember(memberVO);
		model.addAttribute("memberVO", resultMemberVO);

		return "member/member_info";
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

	@RequestMapping("logout.me")
	public String logout(HttpSession session) {
		session.invalidate();

		return "redirect:/loginform.me";
	}
}