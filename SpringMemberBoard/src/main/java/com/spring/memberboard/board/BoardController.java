package com.spring.memberboard.board;

// �ڵ����� : VO -> DAO -> Service -> ServiceImpl -> Controller

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public String BoardWrite(BoardVO boardVO) {
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
	
	

//	@RequestMapping("/updateform.me")
//	public String updateForm(BoardVO memberVO, Model model) {
//		model.addAttribute("id", memberVO.getId());
//		return "member/updateform";
//	}
//
//	@RequestMapping("/memberupdate.me")
//	public String updateMember(BoardVO memberVO, Model model, HttpServletResponse response) throws Exception {
//		int res = memberService.updateMember(memberVO);
//
//		response.setCharacterEncoding("utf-8");
//		response.setContentType("text/html; charset=utf-8");
//		PrintWriter writer = response.getWriter();
//		if (res != 0) {
//			writer.write("<script>alert('ȸ������ ���� ����!!');location.href='memberinfo.me?id=" + memberVO.getId()
//					+ "';</script>");
//		} else {
//			writer.write("<script>alert('ȸ������ ���� ����!!');location.href='memberlist.me';</script>");
//		}
//		return null;
//	}
//
//	@RequestMapping("/memberdelete.me")
//	public String deleteMember(BoardVO memberVO, Model model, HttpServletResponse response) throws Exception {
//		int res = memberService.deleteMember(memberVO);
//
//		response.setCharacterEncoding("utf-8");
//		response.setContentType("text/html; charset=utf-8");
//		PrintWriter writer = response.getWriter();
//		if (res != 0) {
//			writer.write("<script>alert('ȸ������ ���� ����!!');location.href='memberlist.me?id=" + memberVO.getId()
//					+ "';</script>");
//		} else {
//			writer.write("<script>alert('ȸ������ ���� ����!!');location.href='memberlist.me';</script>");
//		}
//		return null;
//	}
}