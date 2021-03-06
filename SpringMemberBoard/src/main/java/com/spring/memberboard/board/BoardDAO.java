package com.spring.memberboard.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.spring.memberboard.JDBCUtil;

@Repository
public class BoardDAO {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	// 글의 개수 구하기.
	public int getListCount() {
		int result = 0;
		try {
			conn = JDBCUtil.getConnection();
			pstmt = conn.prepareStatement("select count(*) from smemberboard");
			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception ex) {
			System.out.println("getListCount 에러 : " + ex);
			ex.printStackTrace();
		} finally {
			JDBCUtil.closeResource(rs, pstmt, conn);
		}
		return result;
	}

	// 글 목록 보기
	public ArrayList<BoardVO> getBoardList(int page, int limit) {
		String sql = "select * from " + "(select rownum rnum, num, id, subject, content, "
				+ "re_ref, re_lev, re_seq, readcount, boarddate from " + "(select * from smemberboard order by "
				+ "re_ref desc, re_seq asc)) " + "where rnum>=? and rnum<=?";

		ArrayList<BoardVO> list = new ArrayList<>();

		// 읽기 시작할 row 번호
		int startrow = (page - 1) * 10 + 1;

		// 읽을 마지막 row 번호
		int endrow = startrow + limit - 1;

		try {
			conn = JDBCUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, endrow);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				BoardVO boardVO = new BoardVO();
				boardVO.setNum(rs.getInt("num"));
				boardVO.setId(rs.getString("id"));

				if (rs.getString("subject") == null) {
					boardVO.setSubject("제목이 없습니다");
				} else {
					boardVO.setSubject(rs.getString("subject"));
				}

				if (rs.getString("content") == null) {
					boardVO.setContent("");
				} else {
					boardVO.setContent(rs.getString("content"));
				}

				boardVO.setRe_ref(rs.getInt("re_ref"));
				boardVO.setRe_lev(rs.getInt("re_lev"));
				boardVO.setRe_seq(rs.getInt("re_seq"));
				boardVO.setReadcount(rs.getInt("readcount"));
				boardVO.setBoarddate(rs.getDate("boarddate"));

				list.add(boardVO);
			}
		} catch (Exception ex) {
			System.out.println("getBoardList 에러  : " + ex);
			ex.printStackTrace();
		} finally {
			JDBCUtil.closeResource(rs, pstmt, conn);
		}
		return list;
	}

	// 글 등록
	public int BoardWrite(BoardVO boardVO) {
		int newNum = 0;
		int result = 0;

		try {
			conn = JDBCUtil.getConnection();

			String maxSQL = "select max(num) from smemberboard";

			// 글번호를 구하기 위한 sql(제일 큰 번호를 불러와서 +1 해준다)
			pstmt = conn.prepareStatement(maxSQL);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				newNum = rs.getInt(1) + 1;
			} else {
				newNum = 1;
			}

			String insertSQL = "insert into smemberboard values(?, ?, ?, ?, ?, ?, ?, ?, sysdate)";
			pstmt = conn.prepareStatement(insertSQL);
			pstmt.setInt(1, newNum); // 글번호
			pstmt.setString(2, boardVO.getId());
			pstmt.setString(3, boardVO.getSubject());
			pstmt.setString(4, boardVO.getContent());
			pstmt.setInt(5, newNum); // 그룹번호, 원글은 글번호로 한다
			pstmt.setInt(6, 0); // 들여쓰기 레벨
			pstmt.setInt(7, 0); //
			pstmt.setInt(8, 0); // 조회수

			result = pstmt.executeUpdate();
			if (result == 0) {
				return 0;
			}

		} catch (Exception ex) {
			System.out.println("boardInsert 에러 : " + ex);
			ex.printStackTrace();
		} finally {
			JDBCUtil.closeResource(rs, pstmt, conn);
		}
		return newNum;
	}

	// 글 내용 보기
	public BoardVO getDetail(int num) {
		BoardVO board = null;
		String sql = "select * from smemberboard where num = ?";

		try {
			conn = JDBCUtil.getConnection();
			;
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				board = new BoardVO();
				board.setNum(rs.getInt("num"));
				board.setId(rs.getString("id"));

				if (rs.getString("subject") == null) {
					board.setSubject("제목이 없습니다");
				} else {
					board.setSubject(rs.getString("subject"));
				}

				if (rs.getString("content") == null) {
					board.setContent(" ");
				} else {
					board.setContent(rs.getString("content"));
				}

				board.setRe_ref(rs.getInt("re_ref"));
				board.setRe_lev(rs.getInt("re_lev"));
				board.setRe_seq(rs.getInt("re_seq"));
				board.setReadcount(rs.getInt("readcount"));
				board.setBoarddate(rs.getDate("boarddate"));

			}
			return board;
		} catch (Exception ex) {
			System.out.println("getDetail 에러  : " + ex);
			ex.printStackTrace();
		} finally {
			JDBCUtil.closeResource(rs, pstmt, conn);
		}
		return null;
	}

	// 조회수 업데이트
	public void setReadCountUpdate(int num) {
		String sql = "update smemberboard set readcount = readcount+1 where num = " + num;
		try {
			conn = JDBCUtil.getConnection();
			;
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
		} catch (Exception ex) {
			System.out.println("setReadCountUpdate 에러 : " + ex);
			ex.printStackTrace();
		} finally {
			JDBCUtil.closeResource(pstmt, conn);
		}
	}

	// 글 답변
	public int boardReply(BoardVO boardVO) {
		int newNum = 0;

		// 선택한 글에 대한 정보
		int re_ref = boardVO.getRe_ref();
		int re_lev = boardVO.getRe_lev();
		int re_seq = boardVO.getRe_seq();

		try {
			conn = JDBCUtil.getConnection();
			// 테이블에 데이터가 없는지 검사
			// 있다면 +1해준다
			// 없으면 첫번째 글이므로 1
			String maxSQL = "select max(num) from smemberboard";
			pstmt = conn.prepareStatement(maxSQL);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				newNum = rs.getInt(1) + 1;
			} else {
				newNum = 1;
			}

			// where 설명 : 그룹이 같은것 중에서 선택한글보다 SEQ가 큰것들을 +1씩 해준다
			// 그러므로 선택한글과 그에 딸린 답변에 대한 것들을 한단계씩 밀어줘서
			// 새로 생길 답변글에 대한 공간(위치, 글번호)를 확보하는것이다
			String updateSQL = "update smemberboard set re_seq=re_seq+1 " + "where re_ref=? and re_seq>?";
			pstmt = conn.prepareStatement(updateSQL);
			pstmt.setInt(1, re_ref);
			pstmt.setInt(2, re_seq);
			pstmt.executeUpdate();

			// 답변에 대한 LEV값과 SEQ값을 선택한 글보다 +1 해준다
			re_lev = re_lev + 1;
			re_seq = re_seq + 1;

			String insertSQL = "insert into smemberboard (num, id, subject,"
					+ "content, re_ref, re_lev, re_seq, readcount, boarddate) "
					+ "values(?, ?, ?, ?, ?, ?, ?, ?, sysdate)";
			pstmt = conn.prepareStatement(insertSQL);
			pstmt.setInt(1, newNum);
			pstmt.setString(2, boardVO.getId());
			pstmt.setString(3, boardVO.getSubject());
			pstmt.setString(4, boardVO.getContent());
			pstmt.setInt(5, re_ref); // 같은 그룹이므로 동일
			pstmt.setInt(6, re_lev); // 위 re_lev = re_lev + 1;
			pstmt.setInt(7, re_seq); // 위 re_seq = re_seq + 1
			pstmt.setInt(8, 0);

			int result = pstmt.executeUpdate();
			if (result == 0) {
				return 0;
			}
		} catch (SQLException ex) {
			System.out.println("boardReply 에러 : " + ex);
			ex.printStackTrace();
		} finally {
			JDBCUtil.closeResource(rs, pstmt, conn);
		}
		return newNum;
	}

	// 글쓴이인지 확인
	public boolean isBoardWriter(int num, String id) {
		String sql = "select * from smemberboard where num = ?";

		try {
			conn = JDBCUtil.getConnection();

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			rs.next();
			if (id.equals(rs.getString("id"))) {
				return true;
			}

		} catch (SQLException ex) {
			System.out.println("isBoardWriter 에러 : " + ex);
			ex.printStackTrace();
		} finally {
			JDBCUtil.closeResource(pstmt, conn);
		}
		return false;
	}

	// 글 수정
	public boolean boardModify(BoardVO boardVO) {
		String sql = "update smemberboard set subject = ?, content = ? where num = ?";

		try {
			conn = JDBCUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			System.out.println("boardVO.getSubject()" + boardVO.getSubject());
			System.out.println("boardVO.getContent()" + boardVO.getContent());
			System.out.println("boardVO.getNum()" + boardVO.getNum());
			pstmt.setString(1, boardVO.getSubject());
			pstmt.setString(2, boardVO.getContent());
			pstmt.setInt(3, boardVO.getNum());
			
			pstmt.executeUpdate();
			return true;
		} catch (Exception ex) {
			System.out.println("boardModify 에러 : " + ex);
			ex.printStackTrace();
		} finally {
			JDBCUtil.closeResource(rs, pstmt, conn);
		}
		return false;
	}

	// 글 삭제
	public boolean boardDelete(int num) {
		String sql = "delete from smemberboard where num = ?";
		int result = 0;

		try {
			conn = JDBCUtil.getConnection();

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			result = pstmt.executeUpdate();
			if (result == 0) {
				return false;
			}

			return true;
		} catch (Exception ex) {
			System.out.println("boardDelete 에러 : " + ex);
			ex.printStackTrace();
		} finally {
			JDBCUtil.closeResource(pstmt, conn);
		}
		return false;
	}
}