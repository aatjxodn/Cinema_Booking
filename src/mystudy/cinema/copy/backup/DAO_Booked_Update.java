package mystudy.cinema.copy.backup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAO_Booked_Update {
	private static final String DRIVER = "oracle.jdbc.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "cinema";
	private static final String PASSWORD = "cinema";
	

	private Connection conn;
	private PreparedStatement pstmt;

	// static 초기화 구문
	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println("[예외발생] JDBC 드라이버 로딩 실패!!!");
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		try {			
			return DriverManager.getConnection(URL , USER, PASSWORD); //try ~ catch가 아니라 던지기도 가능
		} catch (SQLException e) {
			System.out.println("[연결실패]");
		}
		return null;
	}
	//자원 닫기
	public static void close(Connection conn, Statement stmt) {
		// 5. 클로징 처리에 의한 자원 반납
		try {
			if (stmt != null)
				stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
	public int update(String seatcode) {
		int result = 0;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE BEFORE_SEAT ");
			sql.append(" SET SEATSTATUS = 0 ");
			sql.append(" WHERE SEATCODE =? ");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, seatcode);			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt);
		}
		
		return result;		
	}
	
	public int undoSeat(String seatcode) {
		int result = 0;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE BEFORE_SEAT ");
			sql.append(" SET SEATSTATUS = 1 ");
			sql.append(" WHERE SEATCODE = ? ");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, seatcode);			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt);
		}
		
		return result;		
	}
	
	public int custUpdate(String cust_id, String password, String name, String phonenum,String email) {
		int result = 0;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE CUSTOMER ");
			sql.append(" SET CUST_PASSWORD = ? ,  ");
			sql.append("CUST_NAME = ? , ");
			sql.append("CUST_PHONE = ? , ");
			sql.append("CUST_EMAIL = ?  ");			
			sql.append(" WHERE CUST_ID = ? ");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, password);			
			pstmt.setString(2, name);			
			pstmt.setString(3, phonenum);			
			pstmt.setString(4, email);
			pstmt.setString(5, cust_id);			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt);
		}
		
		return result;
		
	}
}
