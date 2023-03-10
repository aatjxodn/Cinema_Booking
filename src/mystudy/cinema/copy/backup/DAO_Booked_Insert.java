package mystudy.cinema.copy.backup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class DAO_Booked_Insert {
	private static final String DRIVER = "oracle.jdbc.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "cinema";
	private static final String PASSWORD = "cinema";
	

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

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
		
	public int Insert(CinemaVO vo) {
		int result = 0;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO CUSTOMER ");
			sql.append(" (CUST_ID, CUST_PASSWORD, CUST_NAME, CUST_PHONE, CUST_EMAIL) ");
			sql.append(" VALUES (?, ?, ?, ?, ?) ");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getPhone());
			pstmt.setString(5, vo.getEmail());			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt);
		}
		
		return result;		
	}
	

//	public int InsertBooking(BookingVO vo) {
//		int result = 0;
//		try {
//			conn = getConnection();
//			StringBuilder sql = new StringBuilder();
//			sql.append("INSERT INTO BOOKING_TABLE ");
//			sql.append(" (B_ID, CUST_ID, CINEMA_ID, MOVIE_ID, SEAT_ID, B_DATE, B_PEOPLE, B_PRICE) ");
//			sql.append(" VALUES (?, ?, ?, ?, ?, ?, ? ,?) ");
//			pstmt = conn.prepareStatement(sql.toString());
//			pstmt.setInt(1, vo.getB_id());
//			pstmt.setString(2, vo.getCust_id());
//			pstmt.setInt(3, vo.getCinema_id());
//			pstmt.setInt(4, vo.getM_id());
//			pstmt.setInt(5, vo.getS_id());
//			pstmt.setString(6, vo.getM_Date());
//			pstmt.setInt(7, vo.getB_People());
//			pstmt.setInt(8, vo.getB_Price());
//			result = pstmt.executeUpdate();			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			close(conn, pstmt);
//		}		
//		return result;		
//	}
	public int InsertBooking(BookingVO vo) {
		int result = 0;
		try {
			conn = getConnection();			
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO BOOKING ");
			sql.append(" (BOOKINGCODE, CUST_ID,MOVIECODE,TIMECODE,SCREENCODE)");
			sql.append(" VALUES (BOOKINGCODE.NEXTVAL,?,?,?,?) ");
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, vo.getCust_id());
			pstmt.setString(2, vo.getMovie_code());
			pstmt.setString(3, vo.getTime_code());
			pstmt.setString(4, vo.getScreen_code());
			result = pstmt.executeUpdate();			
		} catch (SQLException e) {
			System.out.println("[회원 예매 정보 등록 오류] 예매 내용을 다시 확인해주십쇼.");
		} finally {
			close(conn, pstmt);
		}		
		return result;		
	}
	
	public int InsertSeat(String bookingcode, String seatcode) {
		int result = 0;
		try {
			conn = getConnection();			
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO AFTER_SEAT ");
			sql.append(" (BOOKINGCODE,SEATCODE)");
			sql.append(" VALUES (?, ?) ");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, bookingcode);
			pstmt.setString(2, seatcode);			
			result = pstmt.executeUpdate();			
		} catch (SQLException e) {
			System.out.println("[좌석 배정 오류] 예매를 다시 진행해주십쇼.");
		} finally {
			close(conn, pstmt);
		}		
		return result;		
	}
}
	
	
	