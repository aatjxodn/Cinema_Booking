package mystudy.cinema.copy.backup;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cinema_Progress {
	private Scanner scan = new Scanner(System.in);
	private DAO_Booked_Insert dbi = new DAO_Booked_Insert();
	private DAO_Booked_Select dbs = new DAO_Booked_Select();
	private DAO_Booked_Update dbu = new DAO_Booked_Update();
	private CinemaVO vo;

	public int register() {
		vo = null;
		int result = 0;
		System.out.println("--회원가입을 진행해주세요--");
		System.out.println("1.아이디를 입력해주세요.");
		String id = scan.nextLine();
		System.out.println("2.비밀번호를 입력해주세요.");
		String password = scan.nextLine();
		System.out.println("3.이름을 입력해주세요");
		String name = scan.nextLine();
		System.out.println("4.핸드폰 번호를 입력해주세요");
		String phone = scan.nextLine();
		System.out.println("5.이메일주소를 입력해주세요.");
		String email = scan.nextLine();

		vo = new CinemaVO(id, password, name, phone, email);

		result = dbi.Insert(vo);

		return result;
	}

	public CinemaVO login() {
		vo = null;
		System.out.println("로그인을 해주세요.");
		while (vo == null) {
			System.out.print("ID :");
			String id = scan.nextLine();
			System.out.print("Password :");
			String password = scan.nextLine();
			vo = dbs.selectOne(id, password);
			if (vo != null) {
				System.out.println("[로그인 성공]");
				break;
			} else
				System.out.println(">> 아이디, 비밀번호를 다시 확인해주세요.");
		}
		return vo;
	}

	public BookingVO moviebook(String voID) {
		BookingVO Bvo = null;
		String m_code = null;
		String t_code = null;
		String screencode = null;
		int idx = 0;
		System.out.println("\n--------------------");
		System.out.println("영화 예매를 진행합니다.");
		System.out.println("영화를 선택해주세요.");
		while (true) {
			// -------------------------------영화 선택			
			List<MovieVO> list = dbs.showMovie();
			idx = 1;
			for (MovieVO mvo : list) {
				System.out.println(idx + "." + mvo.getMoviename() + " " + mvo.movieRunningTime);
				idx++;
			}
			m_code = scan.nextLine();
			System.out.println("--------------------");
			// -------------------------------시간 선택

			List<TimeVO> listtime = dbs.showTime(m_code);
			idx = 1;
			if (listtime.isEmpty()) {
				System.out.println("해당 시간에 상영 중인 영화가 없습니다.");
				System.out.println("=== 다른 영화를 선택해주세요 ===");		
				continue;
			} else
				for (TimeVO tvo : listtime) {
					System.out.println(tvo.timecode + "." + tvo.moviedate + " " + tvo.starttime + " ~ " + tvo.endtime);
					idx++;
				}
			break;
		}
		System.out.println("시간을 선택해주세요.");
		t_code = scan.nextLine();			
		// -------------------------------상영관 선택
//		System.out.println("--------------------");
//		System.out.println("상영관을 선택해주세요.");
//		List<ScreenVO> listscreen = dbs.showScreen(m_code, t_code);
//		idx = 1;
//		for (ScreenVO svo : listscreen) {
//			System.out.println(idx + "." + svo.s_name);
//			idx++;
//		}
//		String s_num = scan.nextLine();
//		System.out.println("---------------------");
		// -------------------------------좌석 선택
//		idx = 1;
		List<SeatVO> listseat = dbs.showSeat(m_code, t_code);
		
//		for(SeatVO seatvo : listseat) {	
//			System.out.println(seatvo.getSeatcode() + "." + seatvo.getSeatname());			
//		}		
		listseat.get(0).getSeatcode();
		listseat.indexOf(listseat.get(0)); // 0부터 시작 +1
//		svo.get(0).getSeatCode(); 2부터는 7~12
		listseat.get(0).getSeatcode().charAt(0); // A B C D E

		char line = 'A';
		for (int i = 0; i < listseat.size(); i++) {
			if (!String.valueOf(listseat.get(i).getSeatname().charAt(0)).equalsIgnoreCase(String.valueOf(line))) {
				System.out.println();
				line = listseat.get(i).getSeatname().charAt(0);
//				System.out.print(listseat.get(i).getSeatName() + line);
				if (listseat.get(i).getseatStatus() == 1) {
//						System.out.print(listseat.indexOf(listseat.get(i)) + 1);
					System.out.print(listseat.get(i).getSeatcode());
					screencode = listseat.get(i).getScreenCode();
				} else if (listseat.get(i).getseatStatus() == 0) {
					System.out.print("□");
				}
			} else {
				if (listseat.get(i).getseatStatus() == 1) {
//					System.out.print(listseat.indexOf(listseat.get(i)) + 1);
					System.out.print(listseat.get(i).getSeatcode());
					screencode = listseat.get(i).getScreenCode();
				} else if (listseat.get(i).getseatStatus() == 0) {
					System.out.print("□");
				}
//				System.out.print(listseat.get(i).getSeatName() + line);
			}			
			System.out.print(" ");
		}
		
		System.out.println();
		System.out.println("좌석을 선택해주세요.");
		String seat_code = scan.nextLine();
//		dbu.update(seat_code);
		Bvo = new BookingVO(voID, m_code, t_code, screencode, seat_code);

		return Bvo;
	}

}
