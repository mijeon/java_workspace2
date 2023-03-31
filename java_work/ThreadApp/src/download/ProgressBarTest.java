package download;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

public class ProgressBarTest extends JFrame{
	JProgressBar bar1, bar2;
	JButton bt;
	int n;
	BarThread t1, t2;  //개발자가 정의한 쓰레드 2개 선언
	
	public ProgressBarTest() {
		bar1=new JProgressBar();
		bar2=new JProgressBar();
		bt=new JButton("다운로드");
		
		//디자인 적용
		Dimension d=new Dimension(680, 80);
		bar1.setPreferredSize(d);
		bar2.setPreferredSize(d);
		bar1.setStringPainted(true);  //바안에 텍스트를 둘 수 있음
		bar2.setStringPainted(true);
		
		
		//부착
		add(bar1);
		add(bar2);
		add(bt);
		
		setLayout(new FlowLayout());
		setSize(700, 250);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				t1=new BarThread(bar1, 1, 100);  //생성
				t2=new BarThread(bar2, 5, 100);  //생성
				t1.start();
				t2.start();
			}
		});
	}
	public static void main(String[] args) {
		new ProgressBarTest();
	}

}
