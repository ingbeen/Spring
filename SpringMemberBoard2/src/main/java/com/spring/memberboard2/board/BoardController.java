package com.spring.memberboard2.board;

import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

import javax.servlet.ServletOutputStream;

// �ڵ����� : VO -> DAO -> Service -> ServiceImpl -> Controller

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class BoardController {

	// ��Ʈ�ѷ��� ����Ǹ� ������ ���Կ� ���� �ڵ����� new MemberService�� �ȴ�
	// memberService�� ���ԵǴ� Ŭ������ MemberServiceImpl.java������
	// @Service("memberService")�� �ش�Ǵ� Ŭ������ �ִ´�
	// @Service("memberService")�� ���� ���α׷��� �������ڸ���
	// memberService�̶�� bean��ü�� ��������� ������ �����ϴ�
	@Autowired
	private BoardService boardService; // =new MemberServiceImpl();
											// @Autowired�� ���� ���� ������ ȿ���� ����

	@RequestMapping("boardlist.bo")
	public String getBoardList(HttpServletRequest request, Model model) {
		boardService.getBoardList(request, model);
		
		
		return "board/board_list";
	}
	
	@RequestMapping("boardwriteform.bo")
	public String getBoardList() {

		return "board/board_write";
	}
	
	@RequestMapping("boardwrite.bo")
	public String BoardWrite(BoardVO boardVO) throws Exception {
		MultipartFile mf = boardVO.getFile();
		if (!(mf.getOriginalFilename().equals(""))) {
			// ������ ���ε��� ���
			String uploadPath = "C:\\Project156\\upload\\";
			// ������ Ȯ���� ����
			String originalFileExtension = mf.getOriginalFilename().substring(mf.getOriginalFilename().lastIndexOf("."));
			// ���� ���ε��� �������ϸ� ����
			String storedFileName = UUID.randomUUID().toString().replaceAll("-", "") + originalFileExtension;
			
			// ������ �ּҿ� ���� ����
			if (mf.getSize() != 0) {
				// mf.transferTo(new File(uploadPath + "/" + mf.originalFileExtension()));
				// ���� ���ε带 �����ϴ� �κ�
				mf.transferTo(new File(uploadPath + storedFileName));
			}
			
			boardVO.setOrg_file(mf.getOriginalFilename());
			boardVO.setUp_file(storedFileName);
		}
		
		int newNum = boardService.BoardWrite(boardVO);
		
		return "redirect:/boarddetail.bo?num=" + newNum;
	}

	@RequestMapping("boarddetail.bo")
	public String getDetail(HttpServletRequest request, Model model) {
		BoardVO boardVO = boardService.getDetail(request);
		model.addAttribute("boardVO", boardVO);

		return "board/board_view";
	}
	
	@RequestMapping("boardreplyform.bo")
	public String boardReplyForm(HttpServletRequest request, Model model) {
		BoardVO boardVO = boardService.getDetail(request);
		model.addAttribute("boardVO", boardVO);

		return "board/board_reply";
	}
	
	@RequestMapping("boardreply.bo")
	public String boardReply(BoardVO boardVO) {
		int newNum = boardService.boardReply(boardVO);

		return "redirect:/boarddetail.bo?num=" + newNum;
	}
	
	@RequestMapping("boardmodifyform.bo")
	public String boardModifyForm(HttpServletRequest request, Model model) {
		BoardVO boardVO = boardService.boardModifyForm(request);
		if (boardVO == null) {
			boardVO = boardService.getDetail(request);
			model.addAttribute("boardVO", boardVO);
			System.out.println("�ۼ��ڰ� �ƴմϴ�");
			
			return "board/board_view";
		}
		
		model.addAttribute("boardVO", boardVO);
		return "board/board_modify";
	}
	
	@RequestMapping("boardmodify.bo")
	public String boardModify(BoardVO boardVO) {
		boardService.boardModify(boardVO);

		return "redirect:/boarddetail.bo?num=" + boardVO.getNum();
	}
	
	
	@RequestMapping("boarddelete.bo")
	public String boardDelete(HttpServletRequest request) {
		boardService.boardDelete(request);

		return "redirect:/boardlist.bo";
	}
	
	@RequestMapping("filedownload.bo")
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