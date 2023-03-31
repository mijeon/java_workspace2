package event;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EventTest extends JFrame {
	JButton bt;
	JTextField t;
	JPanel p;
	int x;
	int y;
	
	public EventTest() {
		bt=new  JButton("버튼");
		t=new JTextField(20);
		p=new JPanel( ) {
			public void paint(Graphics g) {
				//패널만한 크기의 사각형으로, 패널 전체를 배경색으로 칠함 (배경 지우고 다시그림) 
				g.clearRect(0, 0, 290, 300);
				g.drawOval(x, y, 100, 100);
			}
		};
		p.setPreferredSize(new Dimension(290, 300));
		p.setBackground(Color.PINK);
		
		setLayout(new FlowLayout());
		
		add(bt);
		add(t);
		add(p);
		
		setSize(300, 400);
		setVisible(true);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//버튼과 리스너 연결
		//bt.addActionListener(new MyActionListener());  //다른 클래스에 리스너 단거
		bt.addActionListener(new ActionListener() {  //재사용성이 없을 경우 메서드 안에 이벤트처리코드 넣어서 사용
			public void actionPerformed(ActionEvent e) {
				//System.out.println("눌렀어!");
				
				//내부익명클래스는, 외부클래스의 맴버들을 자기것처럼 씀
				x+=2;
				y+=2;
				p.repaint();  //다시 그리기 update() -> paint()
			}
		}); 
		
		//텍스트필드와 리스너 연결
		//이벤트 구현 시, 개발자가 재정의할 메서드 수가 3개 이상 넘어가면,
		//sun에서 이미 해당 인터페이스를 구현해놓은 즉, 재정의 해놓은 객체를
		//지원해주는데 이 객체들을 가르켜 어뎁터라 함
		//KeyListener를 우리 대신 구현한 어뎁터 -> KeyAdapter 클래스
		//WindowListener를 대신 구현한 어뎁터 -> WindowAdapter 클래스
		//우리가 짊어질 재정의 의무를 Adapter들이 재정의했으므로,
		//개발자는 필요한 메서드만 다시 재정의하면 됨
		t.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				System.out.println("눌렀어");
			}
		});
		
		//윈도우와 리스너 연결
		this.addWindowListener(new WindowAdapter() {
			//윈도우 X버튼 누를 때 (윈도우창 닫을 때)
			public void windowClosing(WindowEvent e) {
				//프로세스 종료
				System.exit(0);
			}
		});		
	}
	
	
	public static void main(String[] args) {
		new EventTest();
	}
}
