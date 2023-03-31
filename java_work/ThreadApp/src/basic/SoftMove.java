package basic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SoftMove extends JFrame {
	JButton bt;
	JPanel p;
	Thread thread;  //게임루프용 쓰레드
	double x;
	double y=50;
	double a=0.08;
	int targetX;  //목표지점
	int targetY;  //목표지점
	
	
	public SoftMove() {
		bt = new JButton("이동");
		p = new JPanel() {
			protected void paintComponent(Graphics g) {
				Graphics2D g2=(Graphics2D)g;
				g2.setColor(Color.black);  //색선택
				g2.fillRect(0, 0, 600, 550);
				
				g2.setColor(Color.pink);
				g2.fillOval((int)x, (int)y, 20, 20);  //double의 경우 정수.00 출력되어서 int로 다운캐스팅 해줌
			}
		};
		thread =new Thread() {
			public void run() {
				while(true) {
					gameLoop();
					try {
						thread.sleep(10);   //Non-Runnable 영역으로 지정한 시간동안 머물다가 다시 Runnable 영역으로 올라오게 함
					} catch (InterruptedException e) {
						e.printStackTrace();
					} 	
				}
			}
		};
		thread.start();  //Runnable..
		
		p.setPreferredSize(new Dimension(600, 550));
		
		setLayout(new FlowLayout());
		add(bt);
		add(p);
		
		setSize(600,600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//패널과 마우스 리스너와 연결
		//이벤트의 리스너의 재정의할 메서드가 3개 이상 될 경우, 어댑터 존재여부 확인할 필요가 있음
		p.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				targetX=e.getPoint().x;
				targetY=e.getPoint().y;
			}
		});
	}
	//물리량 변화코드
	public void tick() {
		//나의 위치=현재 나의위치+a*(목표지점-현재 나의위치)
		x=x+a*(targetX-x);
		y=y+a*(targetY-y);
	}
	public void render() {
		p.repaint();
	}
	public void gameLoop() {
		//System.out.println("gameLoop() called..");
		tick();
		render();
	}

	public static void main(String[] args) {
		new SoftMove();
	}

}
