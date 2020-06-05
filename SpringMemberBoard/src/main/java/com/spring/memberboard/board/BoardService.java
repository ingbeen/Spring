package com.spring.memberboard.board;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.spring.memberboard.member.MemberVO;

/*
 * 아래 3개의 어노테이션은 프로젝트가 실행되면
 * 자동으로 한개씩 Bean객체가 만들어진다
 * @ Controller 디스패처
 * @ Service  BoardService = 데이터베이스와 컨트롤러의 중간 BoardService boardService = new BoardService()
 * @ Repository BoardDAO = 데이터베이스 유지관리 BoardDAO ~~~~
 */

// 괄호안에 들어갈 값은 Bean객체의 이름이다
// 없으면 클래스 이름을 사용하여 첫문자를 소문자로 바꾸어서
// 디폴트값으로 생성된다
@Service
public class BoardService {

	/*
	 * 의존성 주입 / DI(dependency Injection) 객체를 직접 만들기 않고 객체를 전달하여 사용하는 방식(유지보수 편리) 한
	 * 마디로 뭔가 필요한게 있으면 내가 가서 찾아오던지 직접 만드는 대신 무엇이 필요하다고 선언만 하면 외부에서 알아서 제공해주겠다는 것입니다.
	 * 그렇게 하는 이유는 여러가지가 있겠지만, 가장 큰 이유는 "그런 건 내(컨테이너)가 할테니 넌(빈/서비스/컴포넌트) 네가 할
	 * 일(비즈니스)이나 신경써라"라고 할 수 있고, 이를 조금 더 정제해서 표현하면 '관심의 분리(separation of concern)'를
	 * 달성하기 위해서라고 할 수 있습니다.
	 */

	// 프로젝트가 실행이 되면 해당 클래스 객체를 만들어서 대입을 시켜준다
	// MemberDAO는 null값 이지만,
	// MemberDAO.java의 MemberDAO클래스에 @ Repository가 붙어 있으므로
	// MemberDAO클래스의 Bean객체는 자동으로 생성된다
	// 그 자동 생선된 Bean객체는 @Autowired에 맵핑된다
	// required=false는 MemberDAO클래스가 없어도 오류가 발생 안 한다
	@Autowired(required = false)
	private BoardDAO boardDAO = null;

	public void getBoardList(HttpServletRequest request, Model model) {
		ArrayList<BoardVO> boardList = new ArrayList<BoardVO>();

		// 처음 보이는 페이지의 디폴트값
		int page = 1;

		// 한 화면에 보이는 글의 갯수
		int limit = 10;

		// 최초일때는 page는 1
		// 그전에 하단 페이지링크를 클릭했다면 그 페이지위치를 얻어옴
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}

		// 총 리스트 수를 받아옴
		int listcount = boardDAO.getListCount();

		// 글 내용를 받아옴
		boardList = boardDAO.getBoardList(page, limit);

		// 0.9를 더해서 페이지를 총페이지수를 구함(글이 1개이면 1페이지, 11개면 2페이지, 101개면 11페이지)
		int maxpage = (int) ((double) listcount / limit + 0.9);

		// 하단 페이지링크 갯수[이전] [1] ... [10] [다음]의 [1]에 해당
		int startpage = (((int) ((double) page / 10 + 0.9)) - 1) * 10 + 1;

		// 하단 페이지링크 갯수[이전] [1] ... [10] [다음]의 [10]에 해당
		int endpage = maxpage;

		// 글이 1개이면 startpage는 1이 되고 maxpage는 1이 된다
		// 글이 11개이면 startpage는 1이 되고 maxpage는 2이 된다
		// 글이 101개이면 startpage는 1이 되고 maxpage는 11이 된다
		// 글이 101인 상태에서는 하단에 page링크는 1~10까지만 떠야되며 11은 뜨지 말아야 된다
		// 그걸 위해 startpage가 1일때는 10까지만 11일때는 20까지만 뜨게 한다
		if (endpage > startpage + 10 - 1) {
			endpage = startpage + 10 - 1;
		}

		// 현재 페이지 수
		model.addAttribute("page", page);
		// 최대 페이지 수
		model.addAttribute("maxpage", maxpage);
		// 현재 페이지에서 표시할 첫 페이지 수
		model.addAttribute("startpage", startpage);
		// 현재 페이지를 표시할 끝 페이지 수
		model.addAttribute("endpage", endpage);
		// 글수
		model.addAttribute("listcount", listcount);
		// 글내용
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
