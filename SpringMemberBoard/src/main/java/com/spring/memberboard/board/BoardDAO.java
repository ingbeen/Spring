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

	// ���� ���� ���ϱ�.
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
			System.out.println("getListCount ���� : " + ex);
			ex.printStackTrace();
		} finally {
			JDBCUtil.closeResource(rs, pstmt, conn);
		}
		return result;
	}

	// �� ��� ����
	public ArrayList<BoardVO> getBoardList(int page, int limit) {
		String sql = "select * from " + "(select rownum rnum, num, id, subject, content, "
				+ "re_ref, re_lev, re_seq, readcount, boarddate from " + "(select * from smemberboard order by "
				+ "re_ref desc, re_seq asc)) " + "where rnum>=? and rnum<=?";

		ArrayList<BoardVO> list = new ArrayList<>();

		// �б� ������ row ��ȣ
		int startrow = (page - 1) * 10 + 1;

		// ���� ������ row ��ȣ
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
					boardVO.setSubject("������ �����ϴ�");
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
			System.out.println("getBoardList ����  : " + ex);
			ex.printStackTrace();
		} finally {
			JDBCUtil.closeResource(rs, pstmt, conn);
		}
		return list;
	}

	// �� ���
	public int BoardWrite(BoardVO boardVO) {
		int newNum = 0;
		int result = 0;

		try {
			conn = JDBCUtil.getConnection();

			String maxSQL = "select max(num) from smemberboard";

			// �۹�ȣ�� ���ϱ� ���� sql(���� ū ��ȣ�� �ҷ��ͼ� +1 ���ش�)
			pstmt = conn.prepareStatement(maxSQL);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				newNum = rs.getInt(1) + 1;
			} else {
				newNum = 1;
			}

			String insertSQL = "insert into smemberboard values(?, ?, ?, ?, ?, ?, ?, ?, sysdate)";
			pstmt = conn.prepareStatement(insertSQL);
			pstmt.setInt(1, newNum); // �۹�ȣ
			pstmt.setString(2, boardVO.getId());
			pstmt.setString(3, boardVO.getSubject());
			pstmt.setString(4, boardVO.getContent());
			pstmt.setInt(5, newNum); // �׷��ȣ, ������ �۹�ȣ�� �Ѵ�
			pstmt.setInt(6, 0); // �鿩���� ����
			pstmt.setInt(7, 0); //
			pstmt.setInt(8, 0); // ��ȸ��

			result = pstmt.executeUpdate();
			if (result == 0) {
				return 0;
			}

		} catch (Exception ex) {
			System.out.println("boardInsert ���� : " + ex);
			ex.printStackTrace();
		} finally {
			JDBCUtil.closeResource(rs, pstmt, conn);
		}
		return newNum;
	}

	// �� ���� ����
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
					board.setSubject("������ �����ϴ�");
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
			System.out.println("getDetail ����  : " + ex);
			ex.printStackTrace();
		} finally {
			JDBCUtil.closeResource(rs, pstmt, conn);
		}
		return null;
	}

	// ��ȸ�� ������Ʈ
	public void setReadCountUpdate(int num) {
		String sql = "update smemberboard set readcount = readcount+1 where num = " + num;
		try {
			conn = JDBCUtil.getConnection();
			;
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
		} catch (Exception ex) {
			System.out.println("setReadCountUpdate ���� : " + ex);
			ex.printStackTrace();
		} finally {
			JDBCUtil.closeResource(pstmt, conn);
		}
	}

	// �� �亯
	public int boardReply(BoardVO boardVO) {
		int newNum = 0;

		// ������ �ۿ� ���� ����
		int re_ref = boardVO.getRe_ref();
		int re_lev = boardVO.getRe_lev();
		int re_seq = boardVO.getRe_seq();

		try {
			conn = JDBCUtil.getConnection();
			// ���̺��� �����Ͱ� ������ �˻�
			// �ִٸ� +1���ش�
			// ������ ù��° ���̹Ƿ� 1
			String maxSQL = "select max(num) from smemberboard";
			pstmt = conn.prepareStatement(maxSQL);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				newNum = rs.getInt(1) + 1;
			} else {
				newNum = 1;
			}

			// where ���� : �׷��� ������ �߿��� �����ѱۺ��� SEQ�� ū�͵��� +1�� ���ش�
			// �׷��Ƿ� �����ѱ۰� �׿� ���� �亯�� ���� �͵��� �Ѵܰ辿 �о��༭
			// ���� ���� �亯�ۿ� ���� ����(��ġ, �۹�ȣ)�� Ȯ���ϴ°��̴�
			String updateSQL = "update smemberboard set re_seq=re_seq+1 " + "where re_ref=? and re_seq>?";
			pstmt = conn.prepareStatement(updateSQL);
			pstmt.setInt(1, re_ref);
			pstmt.setInt(2, re_seq);
			pstmt.executeUpdate();

			// �亯�� ���� LEV���� SEQ���� ������ �ۺ��� +1 ���ش�
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
			pstmt.setInt(5, re_ref); // ���� �׷��̹Ƿ� ����
			pstmt.setInt(6, re_lev); // �� re_lev = re_lev + 1;
			pstmt.setInt(7, re_seq); // �� re_seq = re_seq + 1
			pstmt.setInt(8, 0);

			int result = pstmt.executeUpdate();
			if (result == 0) {
				return 0;
			}
		} catch (SQLException ex) {
			System.out.println("boardReply ���� : " + ex);
			ex.printStackTrace();
		} finally {
			JDBCUtil.closeResource(rs, pstmt, conn);
		}
		return newNum;
	}

	// �۾������� Ȯ��
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
			System.out.println("isBoardWriter ���� : " + ex);
			ex.printStackTrace();
		} finally {
			JDBCUtil.closeResource(pstmt, conn);
		}
		return false;
	}

	// �� ����
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
			System.out.println("boardModify ���� : " + ex);
			ex.printStackTrace();
		} finally {
			JDBCUtil.closeResource(rs, pstmt, conn);
		}
		return false;
	}

	// �� ����
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
			System.out.println("boardDelete ���� : " + ex);
			ex.printStackTrace();
		} finally {
			JDBCUtil.closeResource(pstmt, conn);
		}
		return false;
	}
}