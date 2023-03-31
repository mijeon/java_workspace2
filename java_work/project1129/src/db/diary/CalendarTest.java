package db.diary;

import java.util.Calendar;

public class CalendarTest {
	
	public static void main(String[] args) {
		//날짜 객체는 생성 시, 디폴트로 현재 날짜 정보를 가짐
		Calendar cal=Calendar.getInstance();
		cal.set(Calendar.DATE, 25);  //날짜 조작
		cal.set(Calendar.MONTH, 10);  //11월
		
		int yy=cal.get(Calendar.YEAR);  //현재 연도
		System.out.println("연도는 "+yy);
		
		//현재월
		int mm=cal.get(Calendar.MONTH);
		System.out.println("월은 "+mm);  //월은 0부터 시작함
		
		//날수
		int dd=cal.get(Calendar.DATE);  //현재날
		System.out.println(dd+"일");
		
		//요일
		int dayOfWeek=cal.get(Calendar.DAY_OF_WEEK);
		System.out.println(dayOfWeek+"요일");
	}
}
