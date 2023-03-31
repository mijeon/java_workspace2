package study;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class AppMain2 extends JFrame{
	JPanel p_north, p_center;
	Menu2[] menu2=new Menu2[4];
	String[] path= {"res/app/company.png", "res/app/signup.png", "res/app/login.png", "res/app/notice.png"};
	ImageManager2 imageManager2; 
	
	//모든 페이지들을 상위 자료형인 JPanel로 만듬
	JPanel[] pageArray=new JPanel[4];
	
	//모든 페이지의 너비와 높이의 기준을 정한 상수 정의
	public static final int PAGE_WIDTH=800;
	public static final int PAGE_HEIGHT=500;
	
	public AppMain2() {
		//패널 생성
		p_north=new JPanel();
		p_center=new JPanel();
		
		//p_north.setPreferredSize(new Dimension(800,100));
		
		//디자인 
		p_north.setBackground(Color.white);
		p_center.setBackground(Color.gray);
		
		//아이콘이 있는 버튼들 생성
		imageManager2=new ImageManager2();
		
		//add 메소드에는 component만 붙일 수 있음
		for(int i=0;i<menu2.length;i++) {
			menu2[i]=new Menu2(this, imageManager2.getIcon(path[i], 100, 80), 100, 80, i);
			p_north.add(menu2[i]);
		}
		
		//패널에 부착
		add(p_north, BorderLayout.NORTH);
		add(p_center);
		
		//4개의 페이지를 미리 화면에 생성하여 붙이기
		pageArray[0]=new CompanyPage2();
		pageArray[1]=new MamberPage2();
		pageArray[2]=new LoginPage2();
		pageArray[3]=new NoticePage2();
		
		//생성된 페이지들을 센터 p_center 패널에 붙이자
		for(int i=0;i<pageArray.length;i++) {
			p_center.add(pageArray[i]);
		}

		//윈도우 설정
		setSize(800, 500);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	//메뉴를 눌렀을 때 해당 페이지 보여주기
	public void showHide(int currentPage) {
		for(int i=0;i<pageArray.length;i++) {
			if(currentPage==i) {
				pageArray[i].setVisible(true);
			}else {
				pageArray[i].setVisible(false);
			}
		}
	}
	
	public static void main(String[] args) {
		new AppMain2();
	}

}
