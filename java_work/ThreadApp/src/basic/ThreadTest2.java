package basic;

import javax.swing.plaf.synth.SynthOptionPaneUI;

public class ThreadTest2 {
	//메인쓰레드 -> 동기방식
	//메인쓰레드는 프로그램 운영을 담당함, 개발자가 생성한 것이 아니라, 시스템에서 지원하는 실행부임
	//GUI 프로그램에서는 만일 메인쓰레드를 루프나 대기상태에 두면 이벤트나 그래픽처리가 동작하지 않게됨
	//일반적인 응용프로그래밍 분야에서는, 아예 금지사항임
	public static void main(String[] args) {
		while(true) {
			System.out.println("★");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	/*	for(int i=0;i<100;i++) {
			System.out.println(i);			
		}
		//개발자가 정의한 쓰레드 생성 및 실행 -> 비동기방식(Asyncro) : JVM이 Thread를 실행하지 않고 메인부터 실행함
		Thread t=new Thread() {
			public void run() {
				System.out.println("A");
			}
		};
		t.start();
		System.out.println("B");
	}*/
}
