package mystudy.cinema;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;



public class DAO_Booked_Delete {
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
		
	public int Delete(CinemaVO vo) {
		int result = 0;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM CUSTOMER ");
			sql.append("WHERE CUST_ID = ?" );
			sql.append(" AND CUST_PASSWORD = ?");			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPassword());			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("예매 내역이 있음으로 탈퇴가 불가능합니다.");
		} finally {
			close(conn, pstmt);
		}
		
		return result;		
	}
	
	public int deleteBooking(String vo, int delete) {
		int result = 0;
		try {
			conn = getConnection();			
			StringBuilder sql = new StringBuilder();
			sql.append("Delete from BOOKING ");
			sql.append("WHERE CUST_ID = ?");
			sql.append(" AND BOOKINGCODE = ?");
			pstmt = conn.prepareStatement(sql.toString());			
			pstmt.setString(1,vo);			
			pstmt.setInt(2, delete);			
			result = pstmt.executeUpdate();			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt);
		}		
		return result;		
	}

	public int deleteBookedSeat(int bookingcode) {
		int result = 0;
		try {
			conn = getConnection();			
			StringBuilder sql = new StringBuilder();
			sql.append("Delete from AFTER_SEAT ");
			sql.append("WHERE BOOKINGCODE = ?");			
			pstmt = conn.prepareStatement(sql.toString());			
			pstmt.setInt(1,bookingcode);						
			result = pstmt.executeUpdate();			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt);
		}		
		return result;		
	}
}
	
	
	