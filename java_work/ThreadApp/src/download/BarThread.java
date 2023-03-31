package download;

import javax.swing.JProgressBar;

/*하나의 프로그램 내에서 각각 틀린 방식으로, 동작할 프로그래스바를 구현하기 위해 
 	재사용 가능성이 예상되므로, 별도의 .java로 정의함*/
public class BarThread extends Thread{
	JProgressBar jProgresBar;
	boolean flag=true;  //쓰레드 종료여부를 결정짓는 변수
	int n;
	int velX;  //var의 증가속도를 결정짓는 변수
	int time;  //쓰레드의 종료 여부를 결정짓는 변수
	
	//var에 데이터를 출력하는 메서드
	public BarThread(JProgressBar jProgresBar, int velX, int time) {
		this.jProgresBar=jProgresBar;
		this.velX=velX;
		this.time=time;
	}
	//생성 시 이 바 쓰레드가 얼마의 속도로 진행할지 여부를 결정짓는 매개변수
	public void printValue() {
		n+=velX;
		jProgresBar.setValue(n);
		if(n>=100) {
			flag=false;
		}
	}
	
	
	public void run() {
		while(flag) {
			printValue();
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
