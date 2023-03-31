package homework;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class ShurekGallery extends JFrame {
	//버튼 7개 생성예정(우리가 커스텀한 버튼)
	ArrayList<CustomButton> btnList;  //컴파일 시점부터 사용할 자료형을 미리 지정할 방식 - 제너릭
	
	JPanel p;
	JPanel p_south;  //버튼 7개가 올려질 남쪽 패널
	ArrayList<Image> imageList=new ArrayList<Image>();  //이미지가 담길 배열, size 0
	int targetX=-1000;  //목표지점
	double a=0.08;
	Thread thread;  //루프용 쓰레드  //만일 메인쓰레드를 이용해 루프를 돌리면 ? 프로그램 실행부가 루프에 얶매여 있어서, 
							//프로그램 운영이 멈춤
	double x;  //그림의 x축 위치를 결정짓는 변수  //int x;
	
	public ShurekGallery() {
		super("슈렉갤러리");
		btnList=new ArrayList<CustomButton>();  //size 0
		
		//2) 버튼 생성
		for(int i=0;i<7;i++) {
			//List는 자바스크립트 배열과 거의 동일(크기가 유동적인 측면)
			btnList.add(new CustomButton(this, i, 30, 30));  //정신차려 이자식아 
		}
			
		//5-1) 그림을 그리기 전에, 이미지를 준비하자
		createImage();
		
		p=new JPanel() {  
			//paintCoponent() 재정의 예정
			protected void paintComponent(Graphics g) {  //6
				Graphics2D g2=(Graphics2D)g;
				for(int i=0;i<imageList.size();i++) {  //7
					g2.drawImage(imageList.get(i), (i*500)+(int)x, 0, 500, 340, ShurekGallery.this);  //물어봐야지	
				}
			}
		};
		thread=new Thread() {
			public void run() {
				while(true) {
					gameLoop();
					try {
						thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		thread.start();
		p.setPreferredSize(new Dimension(500, 340));
		p.setBackground(Color.PINK);
		
		p_south=new JPanel();
		p_south.setPreferredSize(new Dimension(500, 60));
		p_south.setBackground(Color.gray);
		
		//p_south는 디폴트 레이아웃이 FlowLayout으로 적용되어 있음
		//우리가 사용하고 있는 모든 GUI 컴포넌트는 다음과 같이 2가지 유형으로 분휴됨
		/*
		 * GUI 컴포넌트란? 사용자 입력을 받는 모든 컨트롤... ex) Button, TextBox, CheckBox
		 * 1) 컨테이너류 : 남을 포함할 수 있는 者 ex) Frame - BorderLayout, Panel - FlowLayout
		 * 						컨테이너는 남을 포함해야 하기 때문에 배치관리자가 지원됨
		 * 
		 * 2)비쥬얼컴포넌트 : 컨테이너에게 포함되는 者 ex) Button, TextField
		 * */
		//개선된 for문은 Collection 형에 최적화되어 있기 때문에 내부적으로 반복횟수를 알고 있음
		//3) 남쪽패널에 버튼 7개 넣기
		for(CustomButton bt:btnList) {
			p_south.add(bt);  //패널에 부착
		}
		
		
		//조립
		add(p);
		add(p_south, BorderLayout.SOUTH);
		
		setSize(500, 400);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
	//5) 클래스 패스에 존재하는 이미지들을 대상으로, 이미지객체 생성하기
	public void createImage() {
		Class myClass=this.getClass();
		for(int i=0;i<btnList.size();i++) {
			//URL : 자원의 위치를 표현하는 객체
			URL url=myClass.getClassLoader().getResource("res/shurek/img"+i+".jpg");			
				try {
					Image image = ImageIO.read(url);  //BufferedImage 도이미지의 자식이므로 Image형임
					imageList.add(image);  //리스트에 추가 
				} catch (IOException e) {
					e.printStackTrace();
				}   
		}
		System.out.println("생성된 이미지는 총 "+imageList.size()+"개");
	}
	public void tick() {
		//x축 방향으로 부드러운 감속도
		x=x+a*(targetX-x);  //더하기 연산자의 경우 타입이 같아야 함
	}
	public void render() {
		p.repaint();
	}
	public void gameLoop() {
		tick();
		render();
	}
	public static void main(String[] args) {
		new ShurekGallery();
		
	}
}
