package com.spring.memberboard2.board;

import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

import javax.servlet.ServletOutputStream;

// 코딩순서 : VO -> DAO -> Service -> ServiceImpl -> Controller

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

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
	public String BoardWrite(BoardVO boardVO) throws Exception {
		MultipartFile mf = boardVO.getFile();
		if (!(mf.getOriginalFilename().equals(""))) {
			// 파일을 업로드할 경로
			String uploadPath = "C:\\Project156\\upload\\";
			// 파일의 확장자 추출
			String originalFileExtension = mf.getOriginalFilename().substring(mf.getOriginalFilename().lastIndexOf("."));
			// 실제 업로드할 랜덤파일명 생성
			String storedFileName = UUID.randomUUID().toString().replaceAll("-", "") + originalFileExtension;
			
			// 지정한 주소에 파일 저장
			if (mf.getSize() != 0) {
				// mf.transferTo(new File(uploadPath + "/" + mf.originalFileExtension()));
				// 실제 업로드를 실행하는 부분
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
	
	@RequestMapping("filedownload.bo")
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