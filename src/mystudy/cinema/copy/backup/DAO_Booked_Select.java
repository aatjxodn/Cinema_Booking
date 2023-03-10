package mystudy.cinema.copy.backup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class DAO_Booked_Select {
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
			return DriverManager.getConnection(URL, USER, PASSWORD); // try ~ catch가 아니라 던지기도 가능
		} catch (SQLException e) {
			System.out.println("[DB연결실패]");
		}
		return null;
	}

	// 자원 닫기
	public static void close(Connection conn, Statement stmt, ResultSet rs) {
		// 5. 클로징 처리에 의한 자원 반납
		// PreparedStatement extends Statement
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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

	// SELECT 고객조회

	public CinemaVO selectOne(String id, String password) {
		CinemaVO vo = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * ");
			sql.append(" FROM CUSTOMER ");
			sql.append(" WHERE CUST_ID =? ");
			sql.append(" AND CUST_PASSWORD = ?");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				vo = new CinemaVO(rs.getString("CUST_ID"), rs.getString("CUST_PASSWORD"), rs.getString("CUST_NAME"),
						rs.getString("CUST_PHONE"), rs.getString("CUST_EMAIL"));

			}
		} catch (Exception e) {
			System.out.println("올바른 아이디, 비밀번호를 입력해주세요.");
		} finally {
			close(conn, pstmt, rs);
		}

		return vo;
	}
	public String selectbookingid(String cust_id, String moviecode, 
									 String timecode, String screencode) {
		BookingVO vo = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT MAX(BOOKINGCODE) ");
			sql.append(" FROM BOOKING ");
			sql.append(" WHERE CUST_ID =? ");	
			sql.append(" AND MOVIECODE = ?");
			sql.append(" AND TIMECODE = ?");
			sql.append(" AND SCREENCODE = ?");			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, cust_id);
			pstmt.setString(2, moviecode);
			pstmt.setString(3, timecode);
			pstmt.setString(4, screencode);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				 vo = new BookingVO(rs.getString(1));
			}
		} catch (Exception e) {
			System.out.println("올바른 아이디, 비밀번호를 입력해주세요.");
		} finally {
			close(conn, pstmt, rs);
		}

		return vo.getBooking_code();
	}

	public List<MovieVO> showMovie() {
		List <MovieVO> list = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT MOVIECODE,MOVIENAME,RUNNINGTIME ");
			sql.append(" FROM MOVIE ");
			sql.append("ORDER BY MOVIECODE  ");
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			list = new ArrayList<MovieVO>();		
			while (rs.next()) {
				MovieVO movie = new MovieVO(rs.getString(1),rs.getString(2),rs.getString(3));
				list.add(movie);
			}
		} catch (Exception e) {
			
		} finally {
			close(conn, pstmt, rs);
		}
		
		return list;
	}
	
	public List<TimeVO> showTime(String number) {
		List <TimeVO> list = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT TIMECODE, MOVIEDATE , STARTTIME, ENDTIME ");
			sql.append(" FROM TIME ");			
			sql.append(" WHERE MOVIECODE = ?");
			sql.append("ORDER BY TIMECODE");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, number);	
			rs = pstmt.executeQuery();
			list = new ArrayList<TimeVO>();
			while (rs.next()) {
				TimeVO tvo = new TimeVO(rs.getString("TIMECODE"),
											rs.getString("MOVIEDATE"),
											rs.getString("STARTTIME"),
										    rs.getString("ENDTIME")
										    );
				list.add(tvo);
				
			}			
		} catch (Exception e) {
			
		} finally {
			close(conn, pstmt, rs);
		}
		return list;
	}
	
	public List<ScreenVO> showScreen(String moviecode, String timecode) {
		List<ScreenVO> list = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT NAME ");
			sql.append(" FROM VW_CHOOSE_TIME ");			
			sql.append(" WHERE MOVIECODE =? ");
			sql.append(" AND TIMECODE =? ");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, moviecode);	
			pstmt.setString(2, timecode);	
			rs = pstmt.executeQuery();
			list = new ArrayList<ScreenVO>();
			while (rs.next()) {
				ScreenVO svo = new ScreenVO(rs.getString("NAME"));					
				list.add(svo);
			}			
		} catch (Exception e) {
			
		} finally {
			close(conn, pstmt, rs);
		}
		return list;
	}
	public List<SeatVO> showSeat(String moviecode, String timecode) {
		List<SeatVO> list = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * ");
			sql.append(" FROM VW_BEFORE_SEAT ");			
			sql.append(" WHERE MOVIECODE =? ");
			sql.append(" AND TIMECODE =? ");
//			sql.append(" AND SCREENCODE =? ");
//			sql.append(" AND SEATSTATUS =1 ");
			sql.append("ORDER BY SEATCODE");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, moviecode);	
			pstmt.setString(2, timecode);	
//			pstmt.setString(3, screencode);				
			rs = pstmt.executeQuery();
			list = new ArrayList<SeatVO>();
			while (rs.next()) {
				SeatVO seatvo = new SeatVO(rs.getString("SEATNAME"), rs.getString("SEATCODE"),rs.getInt("SEATSTATUS")
										   ,rs.getString("SCREENCODE"));					
				list.add(seatvo);
			}			
		} catch (Exception e) {
			
		} finally {
			close(conn, pstmt, rs);
		}
		return list;
	}
	
	public List<BookingVO> showbook(String voID){
		List<BookingVO> list = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * ");
			sql.append(" FROM VW_BOOKINGINFO ");			
			sql.append(" WHERE CUST_ID = ? ");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, voID);
			rs = pstmt.executeQuery();
			list = new ArrayList<BookingVO>();
			while (rs.next()) {
				BookingVO bvo = new BookingVO(rs.getString("BOOKINGCODE"),
											  rs.getString("CUST_ID"),
											  rs.getString("CUST_NAME"),
											  rs.getString("MOVIENAME"),
											  rs.getString("RUNNINGTIME"),
											  rs.getString("NAME"),
											  rs.getString("MOVIEDATE"),
											  rs.getString("TIME"),
											  rs.getString("SEATNAME"),
											  rs.getString("SEATCODE"));
											  
				list.add(bvo);
			}			
		} catch (Exception e) {
			
		} finally {
			close(conn, pstmt, rs);
		}
		return list;
				
	}
	
	public BookingVO showInfo(String voID){
		BookingVO vo = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * ");
			sql.append(" FROM VW_BOOKINGINFO ");			
			sql.append(" WHERE CUST_ID = ? ");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, voID);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
					vo = new BookingVO(rs.getString("BOOKINGCODE"),
											  rs.getString("CUST_ID"),
											  rs.getString("CUST_NAME"),
											  rs.getString("MOVIENAME"),
											  rs.getString("RUNNINGTIME"),
											  rs.getString("NAME"),
											  rs.getString("MOVIEDATE"),
											  rs.getString("TIME"),
											  rs.getString("SEATNAME"),
											  rs.getString("SEATCODE"));
											  				
			}			
		} catch (Exception e) {
			
		} finally {
			close(conn, pstmt, rs);
		}
		return vo;
				
	}
	
}
