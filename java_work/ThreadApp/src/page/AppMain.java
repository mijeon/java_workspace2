package page;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
//import javax.swing.JLabel;
import javax.swing.JPanel;

import util.ImageManager;

public class AppMain extends JFrame {
	JPanel p_north; // 네비게이션 버튼을 올려놓을 북쪽 패널
	Menu[] menu = new Menu[4]; // 버튼에 대한 공간 확보
	ImageManager imageManager;
	String[] path = { "res/app/company.png", "res/app/signup.png", "res/app/login.png", "res/app/notice.png", };  //각각의 아이콘이 나오는 경로
	// 우리가 사용할 페이지들이 공존할 수 있도록 FlowLayout을 적용할 영역때문에 센터에 부착될 것임 (대왕만하게)
	JPanel p_center;

	/*
	 * //우리가 사용할 페이지들 선언
	 * CompanyPage companyPage; MamberPage mamberPage; LoginPage loginPage; NoticePage noticePage;
	 */
	//모든 페이지들을 상위 자료형인 JPanel로 만듬
	JPanel[] pageArray = new JPanel[4];

	// 모든 페이지의 너비와 높이의 기준을 정한 상수정의
	public static final int PAGE_WIDTH = 800;
	public static final int PAGE_HEIGHT = 500;

	public AppMain() {
		p_north=new JPanel();
		p_north.setBackground(Color.white);
		
		imageManager=new ImageManager();
		
		//아이콘이 있는 버튼들을 생성
		for(int i=0;i<menu.length;i++) {
			menu[i]=new Menu(this, imageManager.getIcon(path[i], 100, 80), 60, 50, i);  //"res/app/company.png"
			p_north.add(menu[i]);
			
			//내부익명클래스는 외부클래스의 맴버변수는 맘대로 접근이 가능하지만 메서드의 지역변수는 final로 선언된 변수는 읽기만 가능
			menu[i].addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//showHide(0);  //호출		
			}
		});
	}
		//센터에 붙을 패널 생성
		p_center=new JPanel();
		p_center.setBackground(Color.lightGray);
		
		//패널 붙이기
		add(p_north, BorderLayout.NORTH);  //북쪽에 패널 붙이기
		add(p_center);
		
		//4개의 페이지를 미리 화면에 생성하여 붙이기
		pageArray[0]=new CompanyPage();
		pageArray[1]=new MamberPage();
		pageArray[2]=new LoginPage();
		pageArray[3]=new NoticePage();
		
		//생성된 페이지들을 센터 p_center 패널에 붙이자
		for(int i=0;i<pageArray.length;i++){
			p_center.add(pageArray[i]);	
		}
		
		setSize(800, 500);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);	
		
		//showHide(0);  //호출
			
	}

	// 메뉴를 눌렀을 때 해당 페이지 보여주기
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
		new AppMain();
	}

}
