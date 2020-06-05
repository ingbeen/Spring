package com.spring.memberboard.board;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.spring.mapper.BoardMapper;

@Service
public class BoardService {

	@Autowired(required = true)
	private SqlSession sqlSession = null;

	public void getBoardList(HttpServletRequest request, Model model) {
		ArrayList<BoardVO> boardList = new ArrayList<BoardVO>();
		BoardMapper boardMapper = sqlSession.getMapper(BoardMapper.class);

		// ó�� ���̴� �������� ����Ʈ��
		int page = 1;

		// �� ȭ�鿡 ���̴� ���� ����
		int limit = 10;
		
		// �����϶��� page�� 1
		// ������ �ϴ� ��������ũ�� Ŭ���ߴٸ� �� ��������ġ�� ����
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}

		// �� ����Ʈ ���� �޾ƿ�
		int listcount = boardMapper.getListCount();
		
		// �б� ������ row ��ȣ
		int startrow = (page - 1) * 10 + 1;

		// ���� ������ row ��ȣ
		int endrow = startrow + limit - 1;
		
		// getBoardList���� ����Ǵ� sql���� ���� �Ķ���� �Ҵ�
		HashMap<String, Integer> startEndRow = new HashMap<String, Integer>();
		startEndRow.put("startrow", startrow);
		startEndRow.put("endrow", endrow);
		
		// �� ���븦 �޾ƿ�
		boardList = boardMapper.getBoardList(startEndRow);

		// 0.9�� ���ؼ� �������� ������������ ����(���� 1���̸� 1������, 11���� 2������, 101���� 11������)
		int maxpage = (int) ((double) listcount / limit + 0.9);

		// �ϴ� ��������ũ ����[����] [1] ... [10] [����]�� [1]�� �ش�
		int startpage = (((int) ((double) page / 10 + 0.9)) - 1) * 10 + 1;

		// �ϴ� ��������ũ ����[����] [1] ... [10] [����]�� [10]�� �ش�
		int endpage = maxpage;

		// ���� 1���̸� startpage�� 1�� �ǰ� maxpage�� 1�� �ȴ�
		// ���� 11���̸� startpage�� 1�� �ǰ� maxpage�� 2�� �ȴ�
		// ���� 101���̸� startpage�� 1�� �ǰ� maxpage�� 11�� �ȴ�
		// ���� 101�� ���¿����� �ϴܿ� page��ũ�� 1~10������ ���ߵǸ� 11�� ���� ���ƾ� �ȴ�
		// �װ� ���� startpage�� 1�϶��� 10������ 11�϶��� 20������ �߰� �Ѵ�
		if (endpage > startpage + 10 - 1) {
			endpage = startpage + 10 - 1;
		}

		// ���� ������ ��
		model.addAttribute("page", page);
		// �ִ� ������ ��
		model.addAttribute("maxpage", maxpage);
		// ���� ���������� ǥ���� ù ������ ��
		model.addAttribute("startpage", startpage);
		// ���� �������� ǥ���� �� ������ ��
		model.addAttribute("endpage", endpage);
		// �ۼ�
		model.addAttribute("listcount", listcount);
		// �۳���
		model.addAttribute("boardlist", boardList);
	}
	
	public int BoardWrite(BoardVO boardVO) throws Exception {
		// ���Ͽ� ���� ���� �Ҵ�
		MultipartFile mf = boardVO.getFile();
		BoardMapper boardMapper = sqlSession.getMapper(BoardMapper.class);
		
		int newNum = 1;
		if (boardMapper.getMaxNumber() != null) {
			newNum += boardMapper.getMaxNumber();
		}
		
		// ���� ���� �ľ�
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
		boardVO.setNum(newNum);
		boardVO.setRe_ref(newNum);
		
		boardMapper.insertBoard(boardVO);
		
		return newNum;
	}
	
	public void getDetail(BoardVO boardVO, Model model) {
		BoardMapper boardMapper = sqlSession.getMapper(BoardMapper.class);
		
		boardMapper.setReadCountUpdate(boardVO);
		BoardVO resultBoardVO = boardMapper.getDetail(boardVO);
		
		model.addAttribute("boardVO", resultBoardVO);
	}
	
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
	
	public int boardReply(BoardVO boardVO) {
		BoardMapper boardMapper = sqlSession.getMapper(BoardMapper.class);
		
		boardMapper.setRe_seqUpdate(boardVO);
		
		int newNum = boardMapper.getMaxNumber() + 1;
	
		boardVO.setNum(newNum);
		boardVO.setRe_lev(boardVO.getRe_lev() + 1);
		boardVO.setRe_seq(boardVO.getRe_seq() + 1);
		
		boardMapper.boardReply(boardVO);
		
		return newNum;
	}
	
	public boolean boardModifyForm(BoardVO boardVO, HttpSession session, Model model) {
		BoardMapper boardMapper = sqlSession.getMapper(BoardMapper.class);
		BoardVO resultBoardVO = boardMapper.getDetail(boardVO);
		
		boolean check = false;
		String id = (String) session.getAttribute("id");
		if(resultBoardVO.getId().equals(id)) {
			model.addAttribute("boardVO", resultBoardVO);
			check = true;
		}
		
		return check;
	}
	
	public int boardModify(BoardVO boardVO) {
		BoardMapper boardMapper = sqlSession.getMapper(BoardMapper.class);
		boardMapper.boardModify(boardVO);
		
		int newNum = boardVO.getNum();
		
		return newNum;
	}
	
	public boolean boardDelete(BoardVO boardVO, HttpSession session) {
		BoardMapper boardMapper = sqlSession.getMapper(BoardMapper.class);
		BoardVO resultBoardVO = boardMapper.getDetail(boardVO);
		
		boolean check = false;
		String id = (String) session.getAttribute("id");
		if(resultBoardVO.getId().equals(id)) {
			boardMapper.boardDelete(boardVO);
			check = true;
		}
		
		return check;
	}
}