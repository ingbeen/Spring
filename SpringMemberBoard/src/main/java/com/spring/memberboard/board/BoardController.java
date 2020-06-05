package com.spring.memberboard.board;

// 코딩순서 : VO -> DAO -> Service -> ServiceImpl -> Controller

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BoardController {

	// 컨트롤러가 실행되면 의존성 주입에 의해 자동으로 new MemberService가 된다
	// memberService에 대입되는 클래스는 MemberServiceImpl.java파일의
	// @Service("memberService")에 해당되는 클래스를 넣는다
	// @Service("memberService")로 인해 프로그램이 구동되자마자
	// memberService이라는 bean객체가 만들어지기 때문에 가능하다
	@Autowired
	private BoardService boardService; // =new MemberServiceImpl();
											// @Autowired에 의해 위와 동일한 효과를 낸다

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
			System.out.println("작성자가 아닙니다");
			
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
//			writer.write("<script>alert('회원정보 수정 성공!!');location.href='memberinfo.me?id=" + memberVO.getId()
//					+ "';</script>");
//		} else {
//			writer.write("<script>alert('회원정보 수정 실패!!');location.href='memberlist.me';</script>");
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
//			writer.write("<script>alert('회원정보 삭제 성공!!');location.href='memberlist.me?id=" + memberVO.getId()
//					+ "';</script>");
//		} else {
//			writer.write("<script>alert('회원정보 삭제 실패!!');location.href='memberlist.me';</script>");
//		}
//		return null;
//	}
}