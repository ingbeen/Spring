package com.spring.sungjuk;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

@Repository
public class SungjukDAO {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	public ArrayList<SungjukVO> getSungjuklist() {
		SungjukVO vo = null;
		ArrayList<SungjukVO> sungjukList = null;

		try {
			conn = JDBCUtil.getConnection();

			pstmt = conn.prepareStatement("select hakbun, irum from sungjuk order by hakbun");
			rs = pstmt.executeQuery();

			sungjukList = new ArrayList<SungjukVO>();
			while (rs.next()) {
				vo = new SungjukVO();
				vo.setHakbun(rs.getString("hakbun"));
				vo.setIrum((rs.getString("irum")));
				sungjukList.add(vo);
			}

		} catch (Exception ex) {
			System.out.println("getSungjuklist 오류" + ex.getMessage());
			ex.printStackTrace();
		} finally {
			JDBCUtil.closeResource(rs, pstmt, conn);
		}

		return sungjukList;
	}

	public int insertSungjuk(SungjukVO sungjukVO) {
		int result = 0;

		try {
			conn = JDBCUtil.getConnection();

			pstmt = conn.prepareStatement("insert into sungjuk values(?,?,?,?,?,?,?,?)");
			pstmt.setString(1, sungjukVO.getHakbun());
			pstmt.setString(2, sungjukVO.getIrum());
			pstmt.setInt(3, sungjukVO.getKor());
			pstmt.setInt(4, sungjukVO.getEng());
			pstmt.setInt(5, sungjukVO.getMath());
			sungjukVO.processc();
			pstmt.setInt(6, sungjukVO.getTot());
			pstmt.setDouble(7, sungjukVO.getAvg());
			pstmt.setString(8, sungjukVO.getGrade());

			result = pstmt.executeUpdate();
		} catch (Exception ex) {
			System.out.println("insertSungjuk 오류" + ex.getMessage());
			ex.printStackTrace();
		} finally {
			JDBCUtil.closeResource(pstmt, conn);
		}

		return result;
	}

	public SungjukVO selectSungjuk(SungjukVO sungjukVO) {
		SungjukVO vo = null;

		try {
			conn = JDBCUtil.getConnection();

			pstmt = conn.prepareStatement("select * from sungjuk where hakbun = ?");
			pstmt.setString(1, sungjukVO.getHakbun());
			rs = pstmt.executeQuery();
			rs.next();

			vo = new SungjukVO();
			vo.setHakbun(rs.getString("hakbun"));
			vo.setIrum(rs.getString("irum"));
			vo.setKor(rs.getInt("kor"));
			vo.setEng(rs.getInt("eng"));
			vo.setMath(rs.getInt("math"));
			vo.setTot(rs.getInt("tot"));
			vo.setAvg(rs.getDouble("avg"));
			vo.setGrade(rs.getString("grade"));

		} catch (Exception ex) {
			System.out.println("selectSungjuk 오류" + ex.getMessage());
			ex.printStackTrace();
		} finally {
			JDBCUtil.closeResource(rs, pstmt, conn);
		}

		return vo;
	}

	public int updateSungjuk(SungjukVO sungjukVO) {
		int result = 0;

		try {
			conn = JDBCUtil.getConnection();

			pstmt = conn.prepareStatement(
					"update sungjuk set kor = ?, eng = ?, math = ?, tot = ?, avg = ?, grade = ? " + "where hakbun = ?");
			pstmt.setInt(1, sungjukVO.getKor());
			pstmt.setInt(2, sungjukVO.getEng());
			pstmt.setInt(3, sungjukVO.getMath());
			sungjukVO.processc();
			pstmt.setInt(4, sungjukVO.getTot());
			pstmt.setDouble(5, sungjukVO.getAvg());
			pstmt.setString(6, sungjukVO.getGrade());
			pstmt.setString(7, sungjukVO.getHakbun());
			result = pstmt.executeUpdate();
		} catch (Exception ex) {
			System.out.println("updateSungjuk 오류" + ex.getMessage());
			ex.printStackTrace();
		} finally {
			JDBCUtil.closeResource(pstmt, conn);
		}

		return result;
	}

	public int deleteSungjuk(SungjukVO sungjukVO) {
		int result = 0;

		try {
			conn = JDBCUtil.getConnection();

			pstmt = conn.prepareStatement("delete from sungjuk where hakbun = ?");
			pstmt.setString(1, sungjukVO.getHakbun());
			result = pstmt.executeUpdate();
		} catch (Exception ex) {
			System.out.println("deleteSungjuk 오류" + ex.getMessage());
			ex.printStackTrace();
		} finally {
			JDBCUtil.closeResource(pstmt, conn);
		}

		return result;
	}

}