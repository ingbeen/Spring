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
	 * Ŀ�ǵ� ��ü(VO, DTO) �� ���� ���ε� ���� ���� Ŀ�ǵ� Ŭ������ �Ķ���Ϳ� ������ �̸��� MultipartFileŸ�� ������Ƽ��
	 * �߰����ֱ⸸ �ϸ� ���ε� ���������� Ŀ�ǵ尴ü�� ���� ���� ������ �ְ� �ȴ�
	 */

	@RequestMapping(value = "/fileUpload1.bo")
	public ModelAndView fileUpload1(HttpServletRequest request, RequestModel model) throws Exception {

		System.out.println("request.getParameter = " + request.getParameter("name"));

		// ���ε� ���Ͽ� ���� �����͸� mf��ü�� ��� �����ϴ� ��
		MultipartFile mf = model.getFile();
		System.out.println("model.getFile = " + model.getFile());

		/*
		 * request.getServletContext()�� ���� 3.0���ĺ��� ���� ������Ʈ ��������
		 * C:\Project156\Spring_Source\.metadata\.plugins\org.eclipse.wst.server.core\
		 * tmp0\webapps.... �ڵ����� �����Ǵ� ������ �������� �Ʒ��� �Ѵ� ��, ������ ������ ������ �����ɼ��� �־ ��õ���� �ʴ´�
		 * String uploadPath =
		 * request.getSession().getServletContext().getRealPath("/upload");
		 */

		// server.xml�� ��ϵ� docBase�ּҿ� �����ؾߵȴ�
		// �׷��� ���߿� �ٿ�ε��Ҷ� docBasc �������� ������ ã�� ���� �����ϴ�
		String uploadPath = "C:\\Project156\\upload\\";

		// Ȯ���ڸ� ���ϱ����� ���������̸��� .���� ����
		String originalFileExtension = mf.getOriginalFilename().substring(mf.getOriginalFilename().lastIndexOf("."));
		System.out.println("originalFileExtension = " + originalFileExtension);

		// ���� ���ε忡 ���� ������ �߰��� �����̸�
		String storedFileName = UUID.randomUUID().toString().replaceAll("-", "") + originalFileExtension;

		// ������ �ּҿ� ���� ����
		if (mf.getSize() != 0) {
			// mf.transferTo(new File(uploadPath + "/" + mf.originalFileExtension()));
			// ���� ���ε带 �����ϴ� �κ�
			mf.transferTo(new File(uploadPath + storedFileName));
		}

		ModelAndView mav = new ModelAndView();
		mav.setViewName("download");
		mav.addObject("name", model.getName());
		mav.addObject("paramName", mf.getName());
		mav.addObject("fileName", mf.getOriginalFilename());
		mav.addObject("fileSize", mf.getSize());
		mav.addObject("storedFileName", storedFileName);

		// �������� �⺻������ ����ó�� ������� utf-8�� ����ϹǷ�
		// ������ ���ε�� �ѱ� ������ �ٿ�ε� �ϱ� ���ؼ� utf-8�� encoding �Ѵ�
		String downlink = "fileDownload.bo?of=" + URLEncoder.encode(storedFileName, "utf-8") + "&of2="
				+ URLEncoder.encode(mf.getOriginalFilename(), "utf-8");
		System.out.println("downlink = " + downlink);

		mav.addObject("downlink", downlink);
		return mav;
	}

	// MultipartHttpServletRequest�� �̿��� ���ε� ���� ����
	@RequestMapping("/fileUpload2.bo")
	public ModelAndView fileUpload2(MultipartHttpServletRequest request) throws Exception {

		// Multipart ��û�� ���ö� ���������� ���� HttptServletRequest ��� ���Ǵ� �������̽�.
		// MultipartHttpServletRequest �������̽���
		// HttpServletRequest �������̽��� MultipartRequest�������̽��� ��ӹް��ִ�.
		// �� ��û ������ ���ϱ� ���� getParameter()�� ���� �޼���� Multipart���� �޼��带 ��� ��밡��.

		// �Ϲ� ����� ������ ����ϴ� ��İ� ���� �����͸� �����ü�����
		String name = request.getParameter("name");
		MultipartFile mf = request.getFile("file"); // ����

		// ---------1���� ��������� �ٸ� �޼ҵ��� �Ķ���Ͱ� ���� ū ������.

		// String uploadPath =
		// request.getSession().getServletContext().getRealPath("/upload");
		String uploadPath = "C:\\Project156\\upload\\"; // ���� ���ε�� ��ġ ����
		String originalFileExtension = mf.getOriginalFilename().substring(mf.getOriginalFilename().lastIndexOf("."));
		String storedFileName = UUID.randomUUID().toString().replaceAll("-", "") + originalFileExtension;

		// �������ּҿ� ���� ����
		if (mf.getSize() != 0) {
			// mf.transferTo(new File(uploadPath+"/"+mf.getOriginalFilename()));
			mf.transferTo(new File(uploadPath + storedFileName));
		}

		// �� ����
		ModelAndView mav = new ModelAndView();
		mav.setViewName("download");

		// �信 ����� ������ �𵨿� ����
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

	// ���� �ٿ�ε� ���
	@RequestMapping("/fileDownload.bo")
	public void fileDownload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");

		String of = request.getParameter("of"); // ������ ���ε�� ����� ���� ���ϸ�
		String of2 = request.getParameter("of2"); // �������� ���ϸ�

		/*
		 * ������Ʈ ��Ʈ���丮�� ���� ��ũ���� ��� �˾Ƴ���.
		 * 
		 * String uploadPath =
		 * request.getSession().getServletContext().getRealPath("/upload");
		 * 
		 * String fullPath = uploadPath+"/"+of;
		 */
		
		String uploadPath = "C:\\Project156\\upload\\"; // ���� ��� ����
		String fullPath = uploadPath + of;
		System.out.println(fullPath);
		File downloadFile = new File(fullPath);

		// ���� �ٿ�ε带 ���� ������ Ÿ���� application/download ����
		response.setContentType("application/download; charset=UTF-8");
		// ���� ������ ����
		response.setContentLength((int) downloadFile.length());
		// �ٿ�ε� â�� ���� ���� ��� ����
		response.setHeader("Content-Disposition", "attachment;filename=" + new String(of2.getBytes(), "ISO8859_1"));
		response.setHeader("Content-Transfer-Encoding", "binary");
		/*
		 * Content-disposition �Ӽ� 1) "Content-disposition: attachment" ������ �ν� ����Ȯ���ڸ�
		 * �����Ͽ� ��� Ȯ������ ���ϵ鿡 ����, �ٿ�ε�� ������ "���� �ٿ�ε�" ��ȭ���ڰ� �ߵ��� �ϴ� ����Ӽ��̴� 2)
		 * "Content-disposition: inline" ������ �ν� ����Ȯ���ڸ� ���� ���ϵ鿡 ���ؼ��� �������� �󿡼� �ٷ� ������
		 * ����, �׿��� ���ϵ鿡 ���ؼ��� "���� �ٿ�ε�" ��ȭ���ڰ� �ߵ��� �ϴ� ����Ӽ��̴�.
		 */

		// ������ ��ο� ����� ����Ȱ�(downloadFile)�� �Ҵ��Ѵ�
		FileInputStream fin = new FileInputStream(downloadFile);
		ServletOutputStream sout = response.getOutputStream();

		byte[] buf = new byte[1024];
		int size = -1;

		// while���� ���Ǿȿ��� fin.read�޼ҵ忡 ���� 1024Byte(1KB)��ŭ
		// �ݺ������� �о�ͼ� sout.write(buf, 0, size[1024]);�� ����
		// �����͸� ����Ѵ�
		while ((size = fin.read(buf, 0, buf.length)) != -1) {
			sout.write(buf, 0, size);
		}
		fin.close();
		sout.close();
	}
}
