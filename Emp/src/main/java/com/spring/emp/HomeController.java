package com.spring.emp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@RequestMapping(value = "inputForm.me")
	public String inputForm(Locale locale, Model model) {
		return "inputform";
	}
	
	@RequestMapping(value = "inputProcess.me")
	public String inputProcess(EmpVO vo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			String driver = "oracle.jdbc.driver.OracleDriver";
			String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
			String sql = "INSERT INTO EMP_COPY VALUES "
					+ "(?, ?, ?, ?, SYSDATE, ?, ?, ?)";
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, "scott", "123456");
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, vo.getEmpno());
			pstmt.setString(2, vo.getEname());
			pstmt.setString(3, vo.getJob());
			pstmt.setInt(4, vo.getMgr());
			pstmt.setDouble(5, vo.getSal());
			pstmt.setDouble(6, vo.getComm());
			pstmt.setInt(7, vo.getDeptno());
			pstmt.executeUpdate();
		} catch (Exception e) {
		}
		
		return "index";
	}
	
	@RequestMapping(value = "selectProcess.me")
	public String selectProcess(Model model) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<EmpVO> list = new ArrayList<EmpVO>();
		
		try {
			String driver = "oracle.jdbc.driver.OracleDriver";
			String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
			String sql = "SELECT * FROM EMP_COPY ORDER BY ENAME ASC";
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, "scott", "123456");
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				EmpVO empvo = new EmpVO();
				empvo.setEmpno(rs.getInt("empno"));
				empvo.setEname(rs.getString("ename"));
				empvo.setJob(rs.getString("job"));
				empvo.setMgr(rs.getInt("mgr"));
				empvo.setHiredate(rs.getDate("hiredate"));
				empvo.setSal(rs.getDouble("sal"));
				empvo.setComm(rs.getDouble("comm"));
				empvo.setDeptno(rs.getInt("deptno"));
				list.add(empvo);
			}
			
			model.addAttribute("list", list);
			
		} catch (Exception e) {
		}
		
		return "list";
	}
	
	
	@RequestMapping(value = "selectDept.me")
	public String selectDept(Model model, 
		// 클라이언트가 서버에 데이터를 보낼때 deptno가 있으면 실행
		// value="deptno" : get방식에서는 주소창에 입력된 
		// 			selectDept.me?deptno="" 값이 있을때 실행
		// required=false : 해당 파라미터가 없어서 상관없음
		//		true라면 파라미터가 없으면 오류 발생
		// defaultValue="1" : value="deptno"가 없으면
		// int deptno : value="deptno"의 값이 저장되는 변수
		//		즉, selectDept.me?deptno="?"의 (?) 값이 저장되는 변수
		@RequestParam(value="deptno", required=false, defaultValue="1") 
			int deptno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DeptVO deptvo = null;
		
		try {
			String driver = "oracle.jdbc.driver.OracleDriver";
			String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
			String sql = "SELECT * FROM DEPT_COPY WHERE DEPTNO=?";
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, "scott", "123456");
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, deptno);
			rs = pstmt.executeQuery();
			
			rs.next();
			
			deptvo = new DeptVO();
			deptvo.setDeptno(rs.getInt("deptno"));
			deptvo.setDname(rs.getString("dname"));
			deptvo.setLoc(rs.getString("loc"));
			
			model.addAttribute("deptvo", deptvo);
			
		} catch (Exception e) {
		}
		
		return "deptview";
	}
	
}
