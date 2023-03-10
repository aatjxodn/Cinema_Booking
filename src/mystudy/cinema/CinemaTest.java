package mystudy.cinema;

import java.util.List;
import java.util.Scanner;

public class CinemaTest {

	public static void main(String[] args) {
		Cinema_Progress cpg = new Cinema_Progress();
		DAO_Booked_Select dbs = new DAO_Booked_Select();
		DAO_Booked_Insert dbi = new DAO_Booked_Insert();
		DAO_Booked_Delete dlt = new DAO_Booked_Delete();
		DAO_Booked_Update dbu = new DAO_Booked_Update();
		Scanner scan = new Scanner(System.in);
		System.out.println("========= 영화 예매 시스템 ==========");

		while (true) {
			try {
				System.out.println("1.회원 가입 2. 로그인 3. 회원 정보 삭제　4.종료");
				System.out.print(": ");
				int select = Integer.parseInt(scan.nextLine());				
				if (select < 0 || select > 4) {
					System.out.println(">> 0 ~ 4의 값을 입력하세요.");
					continue;
				}

				if (select == 1) {
					int result = cpg.register();
					if (result != 0) {
						System.out.println("회원가입 완료");
					}
					break;

				} else if (select == 2) {
					CinemaVO cinemaVO = cpg.login();
					while (true) {
						try {
							System.out.println("1.영화 예매 2. 영화 내역 조회 3. 예매 내역 삭제 4.회원 정보 수정 5. 로그 아웃");
							System.out.print(": ");
							select = Integer.parseInt(scan.nextLine());
							if (select < 0 || select > 5) {
								System.out.println(">> 1번 ~ 5번 사이에서 골라주세요.");
								continue;
							}
							if (select == 1) {
								while (true) {
									BookingVO vo = cpg.moviebook(cinemaVO.getId());
									int result = dbi.InsertBooking(vo);

									String bookingcode = dbs.selectbookingid(vo.getCust_id(), vo.getMovie_code(),
											vo.getTime_code(), vo.getScreen_code());

									int seat_result = dbi.InsertSeat(bookingcode, vo.getSeatCode());

									if (result != 0 && seat_result != 0) {
										System.out.println("예매 완료!");
									}

									break;
								}
							} else if (select == 2) {
								List<BookingVO> list = dbs.showbook(cinemaVO.getId());
								if (list.isEmpty()) {
									System.out.println(">> 예매 내역이 없습니다.");
									System.out.println("   예매를 진행해주세요.");
								} else
									for (BookingVO vo : list) {
										System.out.println("예매번호 :" + vo.getBooking_code() + " " + "예매 ID : " + vo.getCust_id()
												+ " " + "예매 고객 이름 : " + vo.getCust_name() + " ");
										System.out.println("---------------------------");
										System.out.println("영화 제목 : " + vo.getMovieName() + " " + "러닝 타임 : "
												+ vo.getrunning() + " " + "상영관 : " + vo.getScreen() + " ");
										System.out.println("---------------------------");
										System.out.println("영화 날짜 : " + vo.getMoviedate() + " " + "영화 시간 : " + vo.gettime()
												+ " " + "좌석 : " + vo.getSeatname());
										System.out.println("===========================");
									}															
							} else if (select == 3) {								
								BookingVO a = dbs.showInfo(cinemaVO.getId());
								a.getBooking_code();
								a.getSeatCode();
								System.out.println("예매 내역을 삭제합니다.");
								List<BookingVO> list = dbs.showbook(cinemaVO.getId());
								if (list.isEmpty()) {
									System.out.println(">> 예매 내역이 없습니다.");
									System.out.println("   예매를 진행해주세요.");
								} else
									for (BookingVO vo : list) {
										System.out.println("예매번호 :" + vo.getBooking_code() + " " + "예매 ID : " + vo.getCust_id()
												+ " " + "예매 고객 이름 : " + vo.getCust_name() + " ");
										System.out.println("---------------------------");
										System.out.println("영화 제목 : " + vo.getMovieName() + " " + "러닝 타임 : "
												+ vo.getrunning() + " " + "상영관 : " + vo.getScreen() + " ");
										System.out.println("---------------------------");
										System.out.println("영화 날짜 : " + vo.getMoviedate() + " " + "영화 시간 : " + vo.gettime()
												+ " " + "좌석 : " + vo.getSeatname());
										System.out.println("===========================");
									}		
								System.out.println("예매 코드를 입력해주세요.");
								System.out.print(": ");
								int bookingcode = Integer.parseInt(scan.nextLine());
								System.out.println("---- 삭제 할 내역을 확인해주세요 ----");								
								System.out.println("삭제하시려면 Y, 전단계는 N을 눌러주세요.");
								String answer = scan.nextLine();
								while(true) {
									if (answer.equalsIgnoreCase("Y")) {
										int result = dlt.deleteBooking(cinemaVO.getId(), bookingcode);
										dlt.deleteBookedSeat(bookingcode);
										dbu.undoSeat(a.getSeatCode());
										if (result != 0) {
											System.out.println("삭제 완료");
											System.out.println("===========================");
										break;
										} else {
											System.out.println("예약 정보가 없습니다.");
											break;
										}
										
									}
									else if (answer.equalsIgnoreCase("N")) {
										System.out.println("전단계로 돌아갑니다.");
										break;
									} 
									else {
										System.out.println("올바른 값을 입력해주세요.");
										break;
									}
								}
							} else if (select == 4) {
								System.out.println("-- 회원 정보 수정-- ");
								System.out.println("수정 하실 정보를 입력해주세요.");
								System.out.print("비밀번호 :");
								String password = scan.nextLine();
								System.out.println();
								System.out.print("이름 :");
								String name = scan.nextLine();
								System.out.println();
								System.out.print("번호 :");
								String phone = scan.nextLine();
								System.out.println();
								System.out.print("이메일 :");
								String email = scan.nextLine();
								System.out.println();
								int result = dbu.custUpdate(cinemaVO.getId(), password, name, phone, email);
								if (result != 0) {
									System.out.println("회원 정보 수정 완료");
								}
								break;
							}

							else if (select == 5) {
								System.out.println("로그 아웃");
								break;
							}
						} catch (Exception e) {
							System.out.println("숫자를 입력해주세요.");
						}
					}
				}

				else if (select == 3) {
					System.out.println("-- 회원 정보 삭제 --");
					System.out.println("로그인을 진행해주세요.");
					int result = dlt.Delete(cpg.login());
					if (result != 0) {
						System.out.println("회원 정보 삭제 완료.");
					}
					break;
				} else if (select == 4) {
					System.out.println("--종료--");
					break;
				}
			} catch (Exception e) {
				System.out.println("숫자를 입력해주세요.");
			}
		}
	}

}
