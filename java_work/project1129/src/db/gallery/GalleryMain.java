package db.gallery;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import util.StringUtil;

public class GalleryMain extends JFrame implements ActionListener {
	// 서쪽영역
	JPanel p_west;
	JTextField t_title;
	JTextField t_writer;
	JTextArea t_content;
	JPanel p_sign;  //싸인 처리할 패널
	JPanel p_preview; // 이미지 미리보기 패널
	JButton bt_open; // 첨부이미지 찾기 버튼
	JButton bt_regist;

	// 센터영역
	JTable table;
	JScrollPane scroll;

	// 동쪽영역
	JPanel p_east;
	JTextField t_title2;
	JTextField t_writer2;
	JTextArea t_content2;
	JPanel p_preview2; // 이미지 미리보기
	JButton bt_edit;
	JButton bt_del;

	JFileChooser chooser;
	Image image; // 패널이 이미지를 그릴 수 있도록 맴버변수로 선언
	Image detailImage;  //우측 패널에서 그려질 이미지
	File file; // 유저가 탐색기를 통해 선택한 파일 (재선언)
	String dir = "C:/java_workspace2/data/project1129/images";
	String filename; // 개발자가 새롭게 정의한 파일명 (재선언)

	// DB 관련
	Connection con;
	GalleryModel model; // 하나만 사용할거라 자료형 고정	
	ArrayList<int[]> history=new ArrayList<int[]>();
	SignModel signModel;

	public GalleryMain() {
		connect();
		signModel=new SignModel(this);
		
		// 서쪽영역
		p_west = new JPanel();
		t_title = new JTextField(12);
		t_writer = new JTextField(12);
		t_content = new JTextArea(8, 10);
		p_sign=new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2=(Graphics2D)g;
				g2.clearRect(0, 0, 140, 70);
				
				g2.setColor(Color.red);
				for(int i=0;i<history.size();i++) {
					int[] dot=history.get(i);
					g2.fillOval(dot[0], dot[1], 3, 3);
				}
				
			}
		};
		p_preview = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setColor(Color.pink);
				g2.fillRect(0, 0, 150, 130);

				g2.setColor(Color.white);
				g2.drawString("choose your file", 20, 60);
				if (image != null) {
					g2.drawImage(image, 0, 0, 150, 130, GalleryMain.this);
				}
			}
		};
		bt_open = new JButton("첨부");
		bt_regist = new JButton("등록");
		chooser = new JFileChooser();

		p_sign.setPreferredSize(new Dimension(140, 70));
		p_west.setPreferredSize(new Dimension(150, 500));
		p_preview.setPreferredSize(new Dimension(150, 130));

		add(p_west, BorderLayout.WEST);
		p_west.add(t_title);
		p_west.add(t_writer);
		p_west.add(t_content);
		p_west.add(p_sign);
		p_west.add(p_preview);
		p_west.add(bt_open);
		p_west.add(bt_regist);

		// 센터영역
		table = new JTable(model = new GalleryModel(this));
		scroll = new JScrollPane(table);
		add(scroll);

		// 동쪽영역
		p_east = new JPanel();
		t_title2 = new JTextField(12);
		t_writer2 = new JTextField(12);
		t_content2 = new JTextArea(8, 10);
		p_preview2 = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2=(Graphics2D)g;
				g2.clearRect(0, 0, 150, 130);  //이미지를 지우고 다시 그리기 위해 
				g2.drawImage(detailImage, 0, 0, 150, 130, GalleryMain.this);
			}
		};
		
		bt_edit = new JButton("수정");
		bt_del = new JButton("삭제");

		p_east.setPreferredSize(new Dimension(150, 500));
		p_preview2.setPreferredSize(new Dimension(150, 130));
		p_preview2.setBackground(Color.pink);

		add(p_east, BorderLayout.EAST);
		p_east.add(t_title2);
		p_east.add(t_writer2);
		p_east.add(t_content2);
		p_east.add(p_preview2);
		p_east.add(bt_edit);
		p_east.add(bt_del);

		setSize(900, 500);
		setVisible(true);
		// setDefaultCloseOperation(EXIT_ON_CLOSE);

		bt_open.addActionListener(this);
		bt_regist.addActionListener(this);
		bt_edit.addActionListener(this);
		bt_del.addActionListener(this);

		// 윈도우와 리스너 연결
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				release(con);
				System.exit(0);
			}
		});
		
		//테이블과 리스너 연결
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				table.setSelectionBackground(Color.pink);
				
				//일차원배열 추출하기
				int row=table.getSelectedRow();  //유저가 선택한 row
				String[] record=model.data[row];  //하나의 게시물 추출
				
				getDetail(record);
			}
		});
		
		//p_sign과 마우스 리스너 연결
		p_sign.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
				
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				int x=e.getX();
				int y=e.getY();
				System.out.println("X="+x+" , Y="+y);
				int[] dot= {x, y};  //한점을 표현할 배열
				history.add(dot);  //리스트에 추가
				
				p_sign.repaint();
			}
		});
	}


	public void openFile() {
		// 파일 탐색기를 띄우고, 유저가 선택한 이미지 파일을 미리보게하자
		int result = chooser.showOpenDialog(this);

		if (result == JFileChooser.APPROVE_OPTION) {
			// 유저가 선택한 파일 반한받기
			file = chooser.getSelectedFile();
			System.out.println(file.getName());

			try {
				image = ImageIO.read(file);
				p_preview.repaint();

				// 이미지명에 사용할 현재시간(밀리세컨드까지)
				long time = System.currentTimeMillis();
				System.out.println(time);
				filename = time + "." + StringUtil.getExtend(file.getName());
				System.out.println(filename);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void copy() {
		// 오라클에 등록+이미지복사
		FileInputStream fis = null; // 파일을 대상으로 한 입력스트림
		FileOutputStream fos = null; // 파일을 대상으로 한 출력스트림

		try {
			fis = new FileInputStream(file);

			// empty 상태 빈파일 생성
			fos = new FileOutputStream(dir + "/" + filename);

			int data = -1; // 읽혀지지 않았다는 초기화
			byte[] buff=new byte[1024];
			
			while (true) {
				data = fis.read(buff); // 파일 데이터 읽기
				if (data == -1)break;
				// break문 아래 영역은 break문을 만나지 않은 유효한 영역임
				fos.write(buff); // 출력  //data
				System.out.println(data); // 읽혀진 데이터
			}
			System.out.println("복사완료");

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
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	// 등록
	public void regist() {
		copy(); // 이미지 복사
		int result = model.insert(t_title.getText(), t_writer.getText(), t_content.getText(), filename);// 오라클 등록
		
		//싸인정보입력
		for(int i=0;i<history.size();i++) {
			int[] dot=history.get(i);
			signModel.insert(dot[0], dot[1], result);
		}
		
		
		if (result > 0) {
			model.selectAll();
			table.updateUI();
		}
	}
	
	//동쪽 영역에 출력
	public void getDetail(String[] record) {
		//동쪽 영역에 사용자가 선택한 레코드 1거을 출력
		t_title2.setText(record[1]);
		t_writer2.setText(record[2]);
		t_content2.setText(record[3]);
		
		//이미지 처리
		File file=new File(dir+"/"+record[4]);
		try {
			detailImage=ImageIO.read(file);
			p_preview2.updateUI();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == bt_open) {
			openFile();
		} else if (obj == bt_regist) {
			regist();
		} else if (obj == bt_edit) {

		} else if (obj == bt_del) {

		}
	}

	// 접속
	public void connect() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String user = "javase";
			String pass = "1234";

			con = DriverManager.getConnection(url, user, pass);
			System.out.println(con);
			if (con != null) {
				this.setTitle("접속성공");
			} else {
				this.setTitle("실패");
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
		new GalleryMain();
	}
}
