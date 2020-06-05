package com.spring.springmybatis;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MybatisController {

	@Autowired
	private MemberServiceImpl memberService;
	
	// ���� ����ȭ��
	@RequestMapping("list.do")
	public ModelAndView main() {
		// view ȭ���� main.jsp�� DB�κ��� �о�� �����͸� �����ش�
		ModelAndView result = new ModelAndView();
		
		// addObject view�� �Ѿ�� ������
		List<MemberVO> memberList = memberService.getMembers();
		result.addObject("memberList", memberList);
		result.setViewName("list");
		return result;
	}
	
	// insert ��ư Ŭ���� ���� �����ͼ� list.jsp�� ȭ����ȯ�� ���ش�
	@RequestMapping("insert.do")
	public ModelAndView insert(MemberVO member) {
		memberService.insertMember(member);
		/*
		// HashMap ����
		HashMap<String, String> map = new HashMap<String, String>();
		mpa.put("id", member.getId());
		mpa.put("name", member.getName());
		mpa.put("email", member.getEmail());
		mpa.put("phone", member.getPhone());
		memberService.insertMember2(map);
		// HashMap ��
		*/
		
		ModelAndView result = new ModelAndView();
		List<MemberVO> memberList = memberService.getMembers();
		result.addObject("memberList", memberList);
		result.setViewName("list");
		return result;
	}
	
	@RequestMapping("updateForm.do")
	public ModelAndView updateForm(MemberVO member) {
		String id = member.getId();
		member = memberService.getMember(id);
		System.out.println("updateForm complete");
		
		ModelAndView result = new ModelAndView();
		result.addObject("member", member);
		result.setViewName("updateForm");
		return result;
	}
	
	@RequestMapping("update.do")
	public ModelAndView update(MemberVO member) {
		memberService.updateMember(member);
		System.out.println("update complete");
		
		ModelAndView result = new ModelAndView();
		List<MemberVO> memberList = memberService.getMembers();
		result.addObject("memberList", memberList);
		result.setViewName("list");
		return result;
	}
	
	@RequestMapping("delete.do")
	public ModelAndView delete(MemberVO member) {
		memberService.deleteMember(member.getId());
		System.out.println("delete complete");
		
		ModelAndView result = new ModelAndView();
		List<MemberVO> memberList = memberService.getMembers();
		result.addObject("memberList", memberList);
		result.setViewName("list");
		return result;
	}
}