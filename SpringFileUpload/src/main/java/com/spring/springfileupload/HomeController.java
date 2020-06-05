package com.spring.springfileupload;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	@RequestMapping(value = "/fileUploadForm.bo")
	public String home() {

		return "fileUploadForm";
	}

	/*
	 * 커맨드 객체(VO, DTO) 를 통한 업로드 파일 접근 커맨드 클래스에 파라미터와 동일한 이름의 MultipartFile타입 프로퍼티를
	 * 추가해주기만 하면 업로드 파일정도를 커맨드객체를 통해 전달 받을수 있게 된다
	 */

	@RequestMapping(value = "/fileUpload1.bo")
	public ModelAndView fileUpload1(HttpServletRequest request, RequestModel model) throws Exception {

		System.out.println("request.getParameter = " + request.getParameter("name"));

		// 업로드 파일에 대한 데이터를 mf객체에 담아 생성하는 것
		MultipartFile mf = model.getFile();
		System.out.println("model.getFile = " + model.getFile());

		/*
		 * request.getServletContext()는 서블릿 3.0이후부터 지원 프로젝트 폴더내의
		 * C:\Project156\Spring_Source\.metadata\.plugins\org.eclipse.wst.server.core\
		 * tmp0\webapps.... 자동으로 생성되는 폴더에 넣으려면 아래로 한다 단, 서버가 닫히면 파일이 삭제될수도 있어서 추천하지 않는다
		 * String uploadPath =
		 * request.getSession().getServletContext().getRealPath("/upload");
		 */

		// server.xml에 등록된 docBase주소와 동일해야된다
		// 그래야 나중에 다운로드할때 docBasc 기준으로 파일을 찾는 것이 가능하다
		String uploadPath = "C:\\Project156\\upload\\";

		// 확장자를 구하기위해 오리지날이름의 .까지 간다
		String originalFileExtension = mf.getOriginalFilename().substring(mf.getOriginalFilename().lastIndexOf("."));
		System.out.println("originalFileExtension = " + originalFileExtension);

		// 실제 업로드에 사용될 난수가 추가된 파일이름
		String storedFileName = UUID.randomUUID().toString().replaceAll("-", "") + originalFileExtension;

		// 지정한 주소에 파일 저장
		if (mf.getSize() != 0) {
			// mf.transferTo(new File(uploadPath + "/" + mf.originalFileExtension()));
			// 실제 업로드를 실행하는 부분
			mf.transferTo(new File(uploadPath + storedFileName));
		}

		ModelAndView mav = new ModelAndView();
		mav.setViewName("download");
		mav.addObject("name", model.getName());
		mav.addObject("paramName", mf.getName());
		mav.addObject("fileName", mf.getOriginalFilename());
		mav.addObject("fileSize", mf.getSize());
		mav.addObject("storedFileName", storedFileName);

		// 스프링은 기본적으로 문자처리 방식으로 utf-8을 사용하므로
		// 서버에 업로드된 한글 파일을 다운로드 하기 위해서 utf-8로 encoding 한다
		String downlink = "fileDownload.bo?of=" + URLEncoder.encode(storedFileName, "utf-8") + "&of2="
				+ URLEncoder.encode(mf.getOriginalFilename(), "utf-8");
		System.out.println("downlink = " + downlink);

		mav.addObject("downlink", downlink);
		return mav;
	}

	// MultipartHttpServletRequest를 이용한 업로드 파일 접근
	@RequestMapping("/fileUpload2.bo")
	public ModelAndView fileUpload2(MultipartHttpServletRequest request) throws Exception {

		// Multipart 요청이 들어올때 내부적으로 원본 HttptServletRequest 대신 사용되는 인터페이스.
		// MultipartHttpServletRequest 인터페이스는
		// HttpServletRequest 인터페이스와 MultipartRequest인터페이스를 상속받고있다.
		// 웹 요청 정보를 구하기 위한 getParameter()와 같은 메서드와 Multipart관련 메서드를 모두 사용가능.

		// 일반 양식은 이전에 사용하던 방식과 같이 데이터를 가져올수있음
		String name = request.getParameter("name");
		MultipartFile mf = request.getFile("file"); // 파일

		// ---------1번만 여기까지만 다름 메소드의 파라미터가 가장 큰 차이점.

		// String uploadPath =
		// request.getSession().getServletContext().getRealPath("/upload");
		String uploadPath = "C:\\Project156\\upload\\"; // 직접 업로드될 위치 지정
		String originalFileExtension = mf.getOriginalFilename().substring(mf.getOriginalFilename().lastIndexOf("."));
		String storedFileName = UUID.randomUUID().toString().replaceAll("-", "") + originalFileExtension;

		// 지정한주소에 파일 저장
		if (mf.getSize() != 0) {
			// mf.transferTo(new File(uploadPath+"/"+mf.getOriginalFilename()));
			mf.transferTo(new File(uploadPath + storedFileName));
		}

		// 뷰 지정
		ModelAndView mav = new ModelAndView();
		mav.setViewName("download");

		// 뷰에 출력한 데이터 모델에 저장
		mav.addObject("name", name);
		mav.addObject("paramName", mf.getName());
		mav.addObject("fileName", mf.getOriginalFilename());
		mav.addObject("fileSize", mf.getSize());
		// mav.addObject("uploadPath", uploadPath);
		mav.addObject("storedFileName", storedFileName);
		String downlink = "fileDownload.bo?of=" + URLEncoder.encode(storedFileName, "UTF-8") + "&of2="
				+ URLEncoder.encode(mf.getOriginalFilename(), "UTF-8");
		mav.addObject("downlink", downlink);

		return mav;
	}

	// 파일 다운로드 방식
	@RequestMapping("/fileDownload.bo")
	public void fileDownload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");

		String of = request.getParameter("of"); // 서버에 업로드된 변경된 실제 파일명
		String of2 = request.getParameter("of2"); // 오리지날 파일명

		/*
		 * 웹사이트 루트디렉토리의 실제 디스크상의 경로 알아내기.
		 * 
		 * String uploadPath =
		 * request.getSession().getServletContext().getRealPath("/upload");
		 * 
		 * String fullPath = uploadPath+"/"+of;
		 */
		
		String uploadPath = "C:\\Project156\\upload\\"; // 직접 경로 지정
		String fullPath = uploadPath + of;
		System.out.println(fullPath);
		File downloadFile = new File(fullPath);

		// 파일 다운로드를 위해 컨텐츠 타입을 application/download 설정
		response.setContentType("application/download; charset=UTF-8");
		// 파일 사이즈 지정
		response.setContentLength((int) downloadFile.length());
		// 다운로드 창을 띄우기 위한 헤더 조작
		response.setHeader("Content-Disposition", "attachment;filename=" + new String(of2.getBytes(), "ISO8859_1"));
		response.setHeader("Content-Transfer-Encoding", "binary");
		/*
		 * Content-disposition 속성 1) "Content-disposition: attachment" 브라우저 인식 파일확장자를
		 * 포함하여 모든 확장자의 파일들에 대해, 다운로드시 무조건 "파일 다운로드" 대화상자가 뜨도록 하는 헤더속성이다 2)
		 * "Content-disposition: inline" 브라우저 인식 파일확장자를 가진 파일들에 대해서는 웹브라우저 상에서 바로 파일을
		 * 열고, 그외의 파일들에 대해서는 "파일 다운로드" 대화상자가 뜨도록 하는 헤더속성이다.
		 */

		// 파일의 경로와 사이즈가 저장된것(downloadFile)을 할당한다
		FileInputStream fin = new FileInputStream(downloadFile);
		ServletOutputStream sout = response.getOutputStream();

		byte[] buf = new byte[1024];
		int size = -1;

		// while문의 조건안에서 fin.read메소드에 의해 1024Byte(1KB)만큼
		// 반복적으로 읽어와서 sout.write(buf, 0, size[1024]);에 의해
		// 데이터를 출력한다
		while ((size = fin.read(buf, 0, buf.length)) != -1) {
			sout.write(buf, 0, size);
		}
		fin.close();
		sout.close();
	}
}
