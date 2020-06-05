package com.spring.springemail;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	
	// root-context.xml���� ���� bean��ü�̴�
	@Autowired
	JavaMailSender mailSender; // ���� ���񽺸� ����ϱ� ���� �������� ������
	
	@RequestMapping(value = "/")
	public String home() {
		
		return "home";
	}
	
	@RequestMapping(value = "auth.do")
	public ModelAndView mailSending(HttpServletRequest request, String e_mail, 
			HttpServletResponse response) throws IOException {
		
		Random r = new Random();
		int num = r.nextInt(999999);
		
		// String setfrom = "������ �̸��� �ּ�"; // Gmail ����
		// String setfrom = "���� �̸��� �ּ�"; // Daum ����
		String setfrom = "ingbeen@naver.com";
		String tomail = request.getParameter("email");
		String title = "ȸ������ ���� �̸��� �Դϴ�";
		String content = System.getProperty("line.separator")
				+ "�ȳ��ϼ��� ȸ���� ���� Ȩ�������� ã���ּż� �����մϴ�"
				+ System.getProperty("line.separator")
				+ "������ȣ�� " + num + " �Դϴ�"
				+ System.getProperty("line.separator")
				+ "������ ������ȣ�� Ȩ�������� �Է��� �ֽø� �������� �Ѿ�ϴ�"; // ����
		
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			
			messageHelper.setFrom(setfrom); // �����»�� �����ϸ� �����۵��� ����
			messageHelper.setTo(tomail); // �޴»�� �̸���
			messageHelper.setSubject(title); // ���������� ������ �����ϴ�
			messageHelper.setText(content); // ���ϳ���
			
			mailSender.send(message);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("email_injeung");
		mv.addObject("num", num);
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.println("<script>alert('�̸����� �߼۵Ǿ����ϴ�');</script>");
		writer.flush();
		
		return mv;
	}
	
	/*
	�̸��Ϸ� ���� �������θ� �Է��ϰ� ���� ��ư�� ������ ���εǴ� �޼ҵ�,
	���� �Է��� ������ȣ�� ���Ϸ� �Է��� ������ȣ�� ������ Ȯ���ؼ� ������ ȸ������ �������� �Ѿ��,
	Ʋ���� �ٽ� ���� �������� ���ƿ��� �޼ҵ�
	*/
	@RequestMapping("join_injeung.do")
	public ModelAndView join_injeung(@RequestParam(value="email_injeung") String email_injeung, 
			@RequestParam(value="num") String num, HttpServletResponse response) throws IOException {
		
		ModelAndView mv = new ModelAndView();
		
		if (email_injeung.contentEquals(num)) {
			// ������ȣ�� ��ġ�� ��� ������ȣ�� �´ٴ� â�� ����ϰ� ȸ������â���� �̵���
			mv.setViewName("join");
			
			// ���� ������ȣ�� ���ٸ� �̸����� ȸ������ �������� ���� �Ѱܼ� �̸����� �ѹ��� �Է��� �ʿ䰡 ���� �Ѵ�
			response.setContentType("text/html; charset=utf-8");
			PrintWriter writer = response.getWriter();
			writer.println("<script>alert('������ȣ�� ��ġ�Ͽ����ϴ�. ȸ������â���� �̵��մϴ�');</script>");
			writer.flush();
			
			return mv;
		} else {
			ModelAndView mv2 = new ModelAndView();
			mv2.setViewName("email_injeung");
			
			response.setContentType("text/html; charset=utf-8");
			PrintWriter writer = response.getWriter();
			writer.println("<script>alert('������ȣ�� ��ġ���� �ʽ��ϴ�. ������ȣ�� �ٽ� �Է����ּ���');history.go(-1);</script>");
			writer.flush();
			
			return mv2;
		}
	}
	
}