/*쓰레드를 2개 이상 생성하여 실행하는 프로그램 작성하기
 * 2개 이상의 쓰레드를 정의한다면, .java로 만들 필요가 있음
 * 따라서 쓰레드를 별도의 클래스로 정의하자*/
package basic;
public class StratPrint {
	
	public static void main(String[] args) {
		StarThread st1=new StarThread("★");  //쓰레드 1개째 생성
		StarThread st2=new StarThread("☆");  //쓰레드 2개째 생성
		
		st1.start();  //Runnable로 진입시키자
		st2.start();  //Runnable로 진입시키자		
	}
}
