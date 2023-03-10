package mystudy.cinema;

public class TimeVO {
	String timecode;
	String moviedate;
	String starttime;
	String endtime;
	String screencode;
	String moviecode;
	public TimeVO(String timecode, String moviedate, String starttime, String endtime) {
		
		this.timecode = timecode;
		this.moviedate = moviedate;
		this.starttime = starttime;
		this.endtime = endtime;
		
	}
	public String timecode() {
		return timecode;
		
	}


}
