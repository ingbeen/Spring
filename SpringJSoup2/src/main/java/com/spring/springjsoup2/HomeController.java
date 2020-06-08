package com.spring.springjsoup2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	
	@RequestMapping(value = "/home.do", method = RequestMethod.GET)
	public String home() {
		
		return "home";
	}
	
	@RequestMapping(value = "/crawl.do", method = RequestMethod.GET)
	public ModelAndView crawl(@RequestParam(value="sel", required=false, defaultValue="cnt") String sel, ModelAndView model) {
		// Jsoup : https://jsoup.org/
		// Jsoup를 이용해서 네이버 영화 랭킹
		String url = "https://movie.naver.com/movie/sdb/rank/rmovie.nhn?sel=" + sel;
        Document doc = null;
        
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements element = doc.select("table.list_ranking");
        System.out.println("################# table.list_ranking ################");
        System.out.println(element);
        
        ArrayList<String> list_title = new ArrayList<String>();
        ArrayList<String> list_point = new ArrayList<String>(); 
        
        if (sel.equals("cnt"))  
        {
	        for(Element el : element.select("div.tit3 a")) { 
	        	String title = el.text().toString();
	            System.out.println(title);
	            System.out.println("--------------");
	            list_title.add(title);
	        }  
        }
        else if (sel.equals("cur")) {
        	for(Element el : element.select("div.tit5 a")) { 
	        	String title = el.text().toString();
	            System.out.println((list_title.size() + 1) + " : " + title);
	            System.out.println("--------------");
	            list_title.add(title);
	        } 
        	for(Element el : element.select("td.point")) { 
	        	String point = el.text().toString();
	            System.out.println((list_point.size() + 1) + " : " + point);
	            System.out.println("--------------");
	            list_point.add(point);
	        }
        }
        else {
        	System.out.println("********pnt*********");
        	for(Element el : element.select("div.tit5 a")) { 
	        	String title = el.text().toString();
	            list_title.add(title);
	        }
        	
        	for(Element el : element.select("td.point")) { 
	        	String point = el.text().toString();
	            list_point.add(point);
	        }
        }

        model.addObject("list_title", list_title);
        model.addObject("list_point", list_point);
        model.addObject("sel", sel);
        model.setViewName("crawl");
       
		return model;
	}
}