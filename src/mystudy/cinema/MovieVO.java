package mystudy.cinema;

public class MovieVO {
	
	String moviecode;
	String moviename;
	String movieRunningTime;
	public MovieVO(String moviecode, String moviename, String movieRunningTime) {
		this.moviecode = moviecode;
		this.moviename = moviename;
		this.movieRunningTime = movieRunningTime;
	}
	
	protected String getMoviecode() {
		return moviecode;
	}
	protected String getMoviename() {
		return moviename;
	}
	protected String getMovieRunningTime() {
		return movieRunningTime;
	}

	
}
