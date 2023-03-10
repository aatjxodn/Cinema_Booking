package mystudy.cinema.copy.backup;

public class BookingVO extends CinemaVO {
	private String booking_code;
	private String cust_id;
	private String movie_code;
	private String time_code;
	private String booking_time;
	private String screen_code;
	private String movie_name;
	private String time;
	private String moviedate;
	private String cust_name;
	private String runningtime;
	private String screenname;
	private String seat_code;
	private String seatname;
	
	public BookingVO(String booking_code, 
					 String cust_id, String movie_code, 
					 String time_code, String booking_time,
					 String screen_code) 
	{		
		this.booking_code = booking_code;
		this.cust_id = cust_id;
		this.movie_code = movie_code;
		this.time_code = time_code;
		this.booking_time = booking_time;
		this.screen_code = screen_code;
	}

	public BookingVO() {};
	
	public BookingVO(String cust_id, String movie_code, String time_code, String screen_code) {
		this.cust_id = cust_id;
		this.movie_code = movie_code;
		this.time_code = time_code;
		this.screen_code = screen_code;
	}
	
	public BookingVO(String cust_id, String movie_code, String time_code, String screen_code, String seat_code) {
		this.cust_id = cust_id;
		this.movie_code = movie_code;
		this.time_code = time_code;
		this.screen_code = screen_code;
		this.seat_code = seat_code;
	}
	public BookingVO(String booking_code, String cust_id, String cust_name, String movie_name, String runningtime, String name,
			String moviedate, String time, String seatname, String seat_code) {
		
		this.booking_code = booking_code;
		this.cust_id = cust_id;
		this.cust_name = cust_name;
		this.movie_name = movie_name;
		this.runningtime = runningtime;
		this.screenname = name;
		this.moviedate = moviedate;
		this.screen_code = moviedate;
		this.time = time;		
		this.seatname = seatname;
		this.seat_code = seat_code;
	}
	
	
	protected BookingVO(String booking_code) {
		this.booking_code = booking_code;
	}

	protected String getBooking_code() {
		return booking_code;
	}
	
	protected String getSeatname() {
		return seatname;
	}
	protected void setBooking_code(String booking_code) {
		this.booking_code = booking_code;
	}

	protected String getCust_id() {
		return cust_id;
	}

	protected void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}

	protected String getMovie_code() {
		return movie_code;
	}

	protected void setMovie_code(String movie_code) {
		this.movie_code = movie_code;
	}

	protected String getTime_code() {
		return time_code;
	}

	protected void setTime_code(String time_code) {
		this.time_code = time_code;
	}

	protected String getBooking_time() {
		return booking_time;
	}

	protected void setBooking_time(String booking_time) {
		this.booking_time = booking_time;
	}

	protected String getScreen_code() {
		return screen_code;
	}
	
	protected String getCust_name() {
		return cust_name;
	}
	protected String getMovieName() {
		return movie_name;
	}
	protected String getrunning() {
		return runningtime;
	}
	protected String gettime() {
		return time;
	}
	protected String getMoviedate() {
		return moviedate;
	}
	protected String getScreen() {
		return screenname;
	}

	protected String getSeatCode() {		
		return seat_code;
	}

}
