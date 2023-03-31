/*쓰레드 정의*/
package basic;
public class StarThread extends Thread {  //Thread는 java.lang 패키지에 들어있는 기본으로 import 할 필요가 없음
	String star;//멤버변수 선언
	
	public StarThread(String star) {//매개변수 선언
		//지역에서 변수를 사용할경우 가장 가까이에 보이는 변수를 사용한다.
		this.star=star; // 사용
		//this : 자신 클래스의 인스턴스,  . : 접근 , star 접근한 인스터스의 멤버변수를 의미
	}
	
	public void run() {
		//개발자는 독립실행을 원하는 코드를 run()에 작성해 놓기만 하면 됨
		while(true) {
			System.out.println(Thread.currentThread().getName()+star);  //현재 쓰레드의 이름
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	
	}
}
