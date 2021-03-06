package com.spring.sungjuk;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@Autowired
	private SungjukService sungjukService;

	@RequestMapping(value = "sungjuklist.su")
	public String sungjukList(Model model) {
		ArrayList<SungjukVO> sungjukList = sungjukService.getSungjuklist();
		model.addAttribute("sungjukList", sungjukList);

		return "sungjukList";
	}

	@RequestMapping(value = "insertform.su")
	public String insertForm() {

		return "insertForm";
	}
	
	@RequestMapping(value = "insertsungjuk.su")
	public String insertSungjuk(SungjukVO vo, HttpServletResponse response) throws Exception {
		int res = sungjukService.insertSungjuk(vo);
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		// PrintWriter writer = response.getWriter();때문에 예외처리
		PrintWriter writer = response.getWriter();
		if (res != 0) {
			writer.write("<script>alert('성적 입력 성공!!');</script>");
		} else {
			writer.write("<script>alert('성적 입력 실패!!');</script>");
		}
		
		writer.write("<script>location.href='./sungjuklist.su';</script>");
		return null;
	}
	
	@RequestMapping(value = "selectsungjuk.su")
	public String insertSungjuk(SungjukVO vo, Model model) {
		SungjukVO sungjukVO = sungjukService.selectSungjuk(vo);
		
		model.addAttribute("sungjukVO", sungjukVO);
		return "sungjukInfo";
	}
	
	@RequestMapping(value = "updateform.su")
	public String updateForm(SungjukVO vo, Model model) {
		model.addAttribute("hakbun", vo.getHakbun());
		model.addAttribute("irum", vo.getIrum());
		return "updateForm";
	}
	
	@RequestMapping(value = "updatesungjuk.su")
	public String updateSungjuk(SungjukVO vo, HttpServletResponse response) throws Exception {
		int res = sungjukService.updateSungjuk(vo);
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter writer = response.getWriter();
		if (res != 0) {
			writer.write("<script>alert('성적 수정 성공!!');</script>");
		} else {
			writer.write("<script>alert('성적 수정 실패!!');</script>");
		}
		
		writer.write("<script>location.href='./selectsungjuk.su?hakbun=" + vo.getHakbun() +"';</script>");
		return null;
	}
	
	@RequestMapping(value = "deletesungjuk.su")
	public String deleteSungjuk(SungjukVO vo, Model model, HttpServletResponse response) throws Exception {
		int res = sungjukService.deleteSungjuk(vo);
		
		ArrayList<SungjukVO> sungjukList = sungjukService.getSungjuklist();
		model.addAttribute("sungjukList", sungjukList);
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter writer = response.getWriter();
		if (res != 0) {
			writer.write("<script>alert('성적 삭제 성공!!');</script>");
		} else {
			writer.write("<script>alert('성적 삭제 실패!!');</script>");
		}
		
		writer.write("<script>location.href='./sungjuklist.su';</script>");
		
		return null;
	}
}
