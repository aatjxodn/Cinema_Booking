package mystudy.cinema.copy.backup;

public class SeatVO {

	private String seatname;
	private String seatcode;
	private int seatStatus;
	private String screencode;
	public SeatVO(String seatname, String seatcode, int seatStatus, String screencode) {
		this.seatname = seatname;
		this.seatcode = seatcode;
		this.seatStatus = seatStatus;
		this.screencode = screencode;
	}

	protected String getSeatname() {
		return seatname;
	}

	protected String getSeatcode() {
		return seatcode;
	}
	
	protected int getseatStatus() {
		return seatStatus;
	}
	
	protected String getScreenCode() {
		return screencode;
	}
}
