package page;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
//import javax.swing.JLabel;
import javax.swing.JPanel;

import util.ImageManager;

public class AppMain extends JFrame {
	JPanel p_north; // 네비게이션 버튼을 올려놓을 북쪽 패널
	Menu[] menu = new Menu[4]; // 버튼에 대한 공간 확보
	ImageManager imageManager;
	String[] path = { "res/app/company.png", "res/app/signup.png", "res/app/login.png", "res/app/notice.png", }; // 각각의 아이콘이 나오는 경로
																												
	// 우리가 사용할 페이지들이 공존할 수 있도록 FlowLayout을 적용할 영역때문에 센터에 부착될 것임 (대왕만하게)
	JPanel p_center;

	/*
	 * //우리가 사용할 페이지들 선언 CompanyPage companyPage; MamberPage mamberPage; LoginPage
	 * loginPage; NoticePage noticePage;
	 */
	// 모든 페이지들을 상위 자료형인 JPanel로 만듬
	JPanel[] pageArray = new JPanel[4];

	// 모든 페이지의 너비와 높이의 기준을 정한 상수정의
	public static final int PAGE_WIDTH = 800;
	public static final int PAGE_HEIGHT = 500;

	// db관련 정보
	FileInputStream fis; // 파일을 대상으로 한 입력 스트림
	Properties props; // Map의 손자 key-value
	String driver;
	String url2;
	String user;
	String pass;
	Connection con;
	boolean loginFlag;  //false가 디폴트(최초엔 미로그인 상태이므로)

	public AppMain() {
		loadInfo();
		connect();

		p_north = new JPanel();
		p_north.setBackground(Color.white);

		imageManager = new ImageManager();

		// 아이콘이 있는 버튼들을 생성
		for (int i = 0; i < menu.length; i++) {
			menu[i] = new Menu(this, imageManager.getIcon(path[i], 100, 80), 60, 50, i); // "res/app/company.png"
			p_north.add(menu[i]);

			// 내부익명클래스는 외부클래스의 맴버변수는 맘대로 접근이 가능하지만 메서드의 지역변수는 final로 선언된 변수는 읽기만 가능
			menu[i].addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					// showHide(0); //호출
				}
			});
		}
		// 센터에 붙을 패널 생성
		p_center = new JPanel();
		p_center.setBackground(Color.lightGray);

		// 패널 붙이기
		add(p_north, BorderLayout.NORTH); // 북쪽에 패널 붙이기
		add(p_center);

		// 4개의 페이지를 미리 화면에 생성하여 붙이기
		pageArray[0] = new CompanyPage();
		pageArray[1] = new MamberPage(this);
		pageArray[2] = new LoginPage(this);
		pageArray[3] = new NoticePage();

		// 생성된 페이지들을 센터 p_center 패널에 붙이자
		for (int i = 0; i < pageArray.length; i++) {
			p_center.add(pageArray[i]);
		}

		setSize(800, 500);
		setVisible(true);
		// setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		showHide(2); // 기본페이지 호출

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				release(con);
				System.exit(0);
			}
		});
	}

	//페이지를 보여주기 전에 로그인 완료여부 확인
	public void checkLogin(int currentPage) {
		//회원가입과 로그인 페이지인 경우, 검사하지 않음
		if(currentPage==1 || currentPage==2) {
			showHide(currentPage);
		}else {  //검증이 필요한 나머지 페이지들
			if(loginFlag==false) {
				JOptionPane.showMessageDialog(this, "로그인이 필요한 서비스입니다");
			}else {
				showHide(currentPage);
			}
		}
	}
	
	// 메뉴를 눌렀을 때 해당 페이지 보여주기
	public void showHide(int currentPage) {
		for (int i = 0; i < pageArray.length; i++) {
			if (currentPage == i) {
				pageArray[i].setVisible(true);
			} else {
				pageArray[i].setVisible(false);
			}
		}
	}

	public void loadInfo() {
		// fis=new
		// 우리가 스트림을 생성할 자원이 package경로에 있으므로
		URL url = this.getClass().getClassLoader().getResource("res/db/db.properties");

		try {
			URI uri = url.toURI();
			File file = new File(uri);
			fis = new FileInputStream(file);

			props = new Properties(); // 맵의 손자 생성
			props.load(fis); // 파일을 인식한 맵이 되는 순간
			// System.out.println(value);
			driver = props.getProperty("driver"); // value
			url2 = props.getProperty("url");
			user = props.getProperty("user");
			pass = props.getProperty("pass");

			/*
			 * int data=-1; while(true) { data=fis.read(); if(data==-1) break;
			 * System.out.println(data); }
			 */

		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void connect() {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url2, user, pass);
			if (con != null) {
				setTitle("접속성공");
			} else {
				setTitle("접속실패");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 접속해제
	public void release(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// DML인 경우
	public void release(PreparedStatement pstmt) {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// SELECT 인 경우
	public void release(PreparedStatement pstmt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new AppMain();
	}

}
