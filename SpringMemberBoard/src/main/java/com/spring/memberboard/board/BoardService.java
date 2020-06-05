package com.spring.memberboard.board;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.spring.memberboard.member.MemberVO;

/*
 * �Ʒ� 3���� ������̼��� ������Ʈ�� ����Ǹ�
 * �ڵ����� �Ѱ��� Bean��ü�� ���������
 * @ Controller ����ó
 * @ Service  BoardService = �����ͺ��̽��� ��Ʈ�ѷ��� �߰� BoardService boardService = new BoardService()
 * @ Repository BoardDAO = �����ͺ��̽� �������� BoardDAO ~~~~
 */

// ��ȣ�ȿ� �� ���� Bean��ü�� �̸��̴�
// ������ Ŭ���� �̸��� ����Ͽ� ù���ڸ� �ҹ��ڷ� �ٲپ
// ����Ʈ������ �����ȴ�
@Service
public class BoardService {

	/*
	 * ������ ���� / DI(dependency Injection) ��ü�� ���� ����� �ʰ� ��ü�� �����Ͽ� ����ϴ� ���(�������� ��) ��
	 * ����� ���� �ʿ��Ѱ� ������ ���� ���� ã�ƿ����� ���� ����� ��� ������ �ʿ��ϴٰ� ���� �ϸ� �ܺο��� �˾Ƽ� �������ְڴٴ� ���Դϴ�.
	 * �׷��� �ϴ� ������ ���������� �ְ�����, ���� ū ������ "�׷� �� ��(�����̳�)�� ���״� ��(��/����/������Ʈ) �װ� ��
	 * ��(����Ͻ�)�̳� �Ű���"��� �� �� �ְ�, �̸� ���� �� �����ؼ� ǥ���ϸ� '������ �и�(separation of concern)'��
	 * �޼��ϱ� ���ؼ���� �� �� �ֽ��ϴ�.
	 */

	// ������Ʈ�� ������ �Ǹ� �ش� Ŭ���� ��ü�� ���� ������ �����ش�
	// MemberDAO�� null�� ������,
	// MemberDAO.java�� MemberDAOŬ������ @ Repository�� �پ� �����Ƿ�
	// MemberDAOŬ������ Bean��ü�� �ڵ����� �����ȴ�
	// �� �ڵ� ������ Bean��ü�� @Autowired�� ���εȴ�
	// required=false�� MemberDAOŬ������ ��� ������ �߻� �� �Ѵ�
	@Autowired(required = false)
	private BoardDAO boardDAO = null;

	public void getBoardList(HttpServletRequest request, Model model) {
		ArrayList<BoardVO> boardList = new ArrayList<BoardVO>();

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
		int listcount = boardDAO.getListCount();

		// �� ���븦 �޾ƿ�
		boardList = boardDAO.getBoardList(page, limit);

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

	public int BoardWrite(BoardVO boardVO) {
		int newNum = boardDAO.BoardWrite(boardVO);
		
		return newNum;
	}
	
	public BoardVO getDetail(HttpServletRequest request) {
		int num = Integer.parseInt(request.getParameter("num"));
		BoardVO vo = boardDAO.getDetail(num);
		boardDAO.setReadCountUpdate(num);
		
		return vo;
	}
	
	public int boardReply(BoardVO boardVO) {
		int newNum = boardDAO.boardReply(boardVO);
		
		return newNum;
	}
	
	public BoardVO boardModifyForm(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		int num = Integer.parseInt(request.getParameter("num"));
		
		boolean writerCheck = boardDAO.isBoardWriter(num, id);
		if (writerCheck) {
			BoardVO boardVO = boardDAO.getDetail(num);
			return boardVO;
		}
		
		return null;
	}
	
	public boolean boardModify(BoardVO boardVO) {
		boolean res = boardDAO.boardModify(boardVO);
		
		return res;
	}
	
	public boolean boardDelete(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		int num = Integer.parseInt(request.getParameter("num"));
		
		boolean writerCheck = boardDAO.isBoardWriter(num, id);
		if (writerCheck) {
			boolean deleteCheck = boardDAO.boardDelete(num);
			return deleteCheck;
		}
		
		return writerCheck;
	}
	

//	public int deleteMember(BoardVO boardVO) {
//		int res = boardDAO.deleteMember(boardVO);
//
//		return res;
//	}
//
//	public int updateMember(BoardVO boardVO) {
//		int res = boardDAO.updateMember(boardVO);
//
//		return res;
//	}
}
