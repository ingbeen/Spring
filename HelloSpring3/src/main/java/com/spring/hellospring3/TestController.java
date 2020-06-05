package com.spring.hellospring3;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {
	
	// veiw이름만 반환
	@RequestMapping(value = "input_form.bo")
	public String input() {
		return "input_form";
	}
	
	/*
	@RequestMapping(value = "input.bo", method = RequestMethod.POST)
	public String res(HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		model.addAttribute("id", id);
		model.addAttribute("pw", pw);
		
		return "res";
	}
	
	
	@RequestMapping(value = "input.bo", method = RequestMethod.POST)
	public ModelAndView res(HttpServletRequest request, ModelAndView mav) {
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		mav.addObject("id", id);
		mav.addObject("pw", pw);
		mav.setViewName("res");
		
		return mav;
	}
	
	
	@RequestMapping(value = "input.bo", method = RequestMethod.POST)
	public ModelAndView res(HttpServletRequest request) {
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("id", id);
		mav.addObject("pw", pw);
		mav.setViewName("res");
		
		return mav;
	}
	*/
	
	@RequestMapping(value = "input.bo", method = RequestMethod.POST)
	public String res(LoginVO vo, Model model) {
		// 스프링에서 setter를 자동으로 해준다
		String id = vo.getId();
		String pw = vo.getPw();
		model.addAttribute("id", id);
		model.addAttribute("pw", pw);
		
		return "res";
	}
}
