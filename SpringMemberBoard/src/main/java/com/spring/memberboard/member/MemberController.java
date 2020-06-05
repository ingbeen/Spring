package com.spring.memberboard.member;

// �ڵ����� : VO -> DAO -> Service -> ServiceImpl -> Controller

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

	// ��Ʈ�ѷ��� ����Ǹ� ������ ���Կ� ���� �ڵ����� new MemberService�� �ȴ�
	// memberService�� ���ԵǴ� Ŭ������ MemberServiceImpl.java������
	// @Service("memberService")�� �ش�Ǵ� Ŭ������ �ִ´�
	// @Service("memberService")�� ���� ���α׷��� �������ڸ���
	// memberService�̶�� bean��ü�� ��������� ������ �����ϴ�
	@Autowired
	private MemberService memberService; // =new MemberServiceImpl();
											// @Autowired�� ���� ���� ������ ȿ���� ����

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
		// form��Ŀ��� �޴� �͵��� �Ķ���ͷ� �ѱ��
		int res = memberService.insertMember(memberVO);

		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter writer = response.getWriter();
		if (res != 0) {
			writer.write("<script>alert('ȸ�� ���� ����!!');location.href='loginform.me';</script>");
		} else {
			writer.write("<script>alert('ȸ�� ���� ����!!');location.href='joinform.me';</script>");
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
			writer.write("<script>alert('�α��� ����!!');location.href='boardlist.bo';</script>");
			// return "redirect:/main.me"; = ���â�� ��� �ʿ䰡 ���ٸ� �̷��� �ص� �ȴ�
		} else {
			writer.write("<script>alert('�α��� ����!!');location.href='loginform.me';</script>");
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
			writer.write("<script>alert('ȸ������ ���� ����!!');location.href='memberinfo.me?id=" + memberVO.getId()
					+ "';</script>");
		} else {
			writer.write("<script>alert('ȸ������ ���� ����!!');location.href='memberlist.me';</script>");
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
			writer.write("<script>alert('ȸ������ ���� ����!!');location.href='memberlist.me?id=" + memberVO.getId()
					+ "';</script>");
		} else {
			writer.write("<script>alert('ȸ������ ���� ����!!');location.href='memberlist.me';</script>");
		}
		return null;
	}
}