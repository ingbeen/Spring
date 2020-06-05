package com.spring.inputform;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class InputController {
	
	
	@RequestMapping(value = "inputForm.me")
	public String inputForm() {
		return "inputForm";
	}

	@RequestMapping(value = "inputChk.me", method = RequestMethod.POST)
	public String inputChk(MemberVO vo, Model model) {
		
		String[] hobby = vo.getHobby();
		
		String hobbyResult = "";
		for(String value : hobby) {
			hobbyResult += value + " ";
		}

			
		model.addAttribute("id", vo.getId());
		model.addAttribute("password1", vo.getPassword1());
		model.addAttribute("jumin1", vo.getJumin1());
		model.addAttribute("jumin2", vo.getJumin2());
		model.addAttribute("sex", vo.getSex());
		model.addAttribute("tel1", vo.getTel1());
		model.addAttribute("tel2", vo.getTel2());
		model.addAttribute("tel3", vo.getTel3());
		model.addAttribute("emailFront", vo.getEmailFront());
		model.addAttribute("emailBack", vo.getEmailBack());
		model.addAttribute("hobby", hobbyResult);
		model.addAttribute("intro", vo.getIntro());

		return "inputChk";
	}
}
