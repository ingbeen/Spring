package com.spring.springjsoup2;


import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	@RequestMapping(value = "home.do")
	public String home() {
		return "home";
	}
	
	@RequestMapping(value = "crawl.do")
	public ModelAndView craw1(ModelAndView model) {
		// Jsoup : http://jsoup.org/
		// Jsoup�� �̿��ؼ� ���̹� ������ ũ�Ѹ�
		String url = "https://sports.news.naver.com/wfootball/index.nhn";
		Document doc = null;
		
		try {
			// post(), get()
			doc = Jsoup.connect(url).get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// �ֿ� ������ ������ �±׸� ã�Ƽ� ���������� �Ѵ�.
		// div �±��� class�� home_news �ΰ��� �Ҵ��Ѵ�
		Elements element = doc.select("div.home_news");
		System.out.println("############ div.home_news ##############");
		System.out.println(element);
		
		// 1. ��� �κ��� ������ ���� �´�.
		// ������ ���� Ŭ�������� h2�±׸� ã�´�
		String title  = element.select("h2").text().substring(0, 4);
		
		System.out.println("==============================");
		System.out.println(title);
		System.out.println("==============================");
		
		ArrayList<String> list_text = new ArrayList<String>();
		ArrayList<String> list_link = new ArrayList<String>();
		
		// 2. ���� ���� ������ for�� ���鼭 ���
		for (Element el : element.select("li")) {
			String text = el.text().toString();
			String link = "https://sports.news.naver.com/" + el.select("a").attr("href");
			System.out.println(text);
			System.out.println(link);
			System.out.println("-------------------------------");
			list_text.add(text);
			list_link.add(link);
		}
		
		model.addObject("title", title);
		model.addObject("list_text", list_text);
		model.addObject("list_link", list_link);
		model.setViewName("crawl");
		
		return model;
	}
	
}
