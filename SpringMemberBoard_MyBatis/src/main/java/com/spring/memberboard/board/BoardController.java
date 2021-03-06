package com.spring.memberboard.board;

// �ڵ����� : VO -> DAO -> Service -> ServiceImpl -> Controller

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;

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
		int newNum = boardService.BoardWrite(boardVO);
		
		return "redirect:/boarddetail.bo?num=" + newNum;
	}

	@RequestMapping("boarddetail.bo")
	public String getDetail(BoardVO boardVO, Model model) {
		boardService.getDetail(boardVO, model);

		return "board/board_view";
	}
	
	@RequestMapping("filedownload.bo")
	public void fileDownload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		boardService.fileDownload(request, response);
	}
	
	@RequestMapping("boardreplyform.bo")
	public String boardReplyForm(BoardVO boardVO, Model model) {
		boardService.getDetail(boardVO, model);

		return "board/board_reply";
	}
	
	@RequestMapping("boardreply.bo")
	public String boardReply(BoardVO boardVO) {
		int newNum = boardService.boardReply(boardVO);

		return "redirect:/boarddetail.bo?num=" + newNum;
	}
	
	@RequestMapping("boardmodifyform.bo")
	public String boardModifyForm(BoardVO boardVO, HttpSession session, Model model) {
		boolean check = boardService.boardModifyForm(boardVO, session, model);
		if (check == false) {
			return "redirect:/boarddetail.bo?num=" + boardVO.getNum();
		}
		
		return "board/board_modify";
	}
	
	@RequestMapping("boardmodify.bo")
	public String boardModify(BoardVO boardVO) {
		int newNum = boardService.boardModify(boardVO);

		return "redirect:/boarddetail.bo?num=" + newNum;
	}
	
	@RequestMapping("boarddelete.bo")
	public String boardDelete(BoardVO boardVO, HttpSession session) {
		boolean check = boardService.boardDelete(boardVO, session);
		if (check == false) {
			return "redirect:/boarddetail.bo?num=" + boardVO.getNum();
		}

		return "redirect:/boardlist.bo";
	}
}