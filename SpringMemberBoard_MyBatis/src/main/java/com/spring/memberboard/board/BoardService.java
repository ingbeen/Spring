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
		int listcount = boardMapper.getListCount();
		
		// 읽기 시작할 row 번호
		int startrow = (page - 1) * 10 + 1;

		// 읽을 마지막 row 번호
		int endrow = startrow + limit - 1;
		
		// getBoardList에서 실행되는 sql문을 위한 파라미터 할당
		HashMap<String, Integer> startEndRow = new HashMap<String, Integer>();
		startEndRow.put("startrow", startrow);
		startEndRow.put("endrow", endrow);
		
		// 글 내용를 받아옴
		boardList = boardMapper.getBoardList(startEndRow);

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
	
	public int BoardWrite(BoardVO boardVO) throws Exception {
		// 파일에 대한 정보 할당
		MultipartFile mf = boardVO.getFile();
		BoardMapper boardMapper = sqlSession.getMapper(BoardMapper.class);
		
		int newNum = 1;
		if (boardMapper.getMaxNumber() != null) {
			newNum += boardMapper.getMaxNumber();
		}
		
		// 파일 유무 파악
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
