package db;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import util.ImageManager;

/**/
public class TableTest extends JFrame implements ActionListener {
	JTable table;
	JPanel p_west;
	JTextField t_id, t_name;
	JButton bt;
	JPasswordField t_pass;
	ImageManager imageManager;
	JScrollPane scroll;
	// int x;

	// JTable에 사용할 데이터
	// String[] d= new String[] {"111", "111", "111"};
	// String[] d1= {"SCOTT", "111", "스캇"};
	// String[] d2= {"MIJEON", "111", "미전"};
	// String[] d3= {"YOUNGHEE", "111", "영희"};

	// 배열의 경우 크기가 변하지 않음
	// String[][] data = { { "SCOTT", "111", "스캇" }, { "MIJEON", "111", "미전" }, {
	// "YOUNGHEE", "111", "영희" } }; // {d1, d2, d3}

	// JTable에 사용할 컬럼 String[] column = { "Id", "Pass", "Name" };

	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "java";
	String pass = "1234";

	// JFrame에서 사용할 컬럼
	String[] column = { "EMPNO", "ENAME", "JOB", "MGR", "HIREDATE", "SAL", "COMM", "DEPNO" };

	Connection con = null; // 접속 정보를 가진 객치

	public TableTest() {
		// 서쪽 영역 컴포넌트들
		p_west = new JPanel();
		t_id = new JTextField();
		t_pass = new JPasswordField();
		t_name = new JTextField();
		bt = new JButton();
		imageManager = new ImageManager();
		bt = new JButton(imageManager.getIcon("res/app/signup.png", 40, 25));

		// 스타일설정
		p_west.setPreferredSize(new Dimension(150, 500));
		Dimension d = new Dimension(130, 30);
		t_id.setPreferredSize(d);
		t_pass.setPreferredSize(d);
		t_name.setPreferredSize(d);
		bt.setPreferredSize(d);

		// table = new JTable(data, column); // 10층 5호수
		// scroll = new JScrollPane(table); // 바깥쪽이 스크롤

		// add(table);
		// add(scroll);

		// 서쪽 조립
		p_west.add(t_id);
		p_west.add(t_pass);
		p_west.add(t_name);
		p_west.add(bt);
		add(p_west, BorderLayout.WEST); // 패널을 서쪽에 붙이기

		setSize(600, 500);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		bt.addActionListener(this);
		connect();

		// 프레임과 리스너 연결
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				release();
				System.exit(0);
			}
		});
		connect();
		getData();
	}

	// 오라클 접속
	public void connect() {
		// this.setTitle("오라클에 접속한 상태");
		// 1) 드라이버 로드
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버 로드 성공");

			// 2) 접속
			con = DriverManager.getConnection(url, user, pass);
			if (con != null) {
				this.setTitle("오라클에 접속한 상태");
			} else {
				this.setTitle("오라클에 접속 실패");
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// 사원테이블 가져오기(select)
	// 오라클에 select 문을 수행하여, jtable이 사용할 이차원배열을 생성
	public void getData() {
		PreparedStatement pstmt = null; // 쿼리 수행 객체
		ResultSet rs = null; // 결과 테이블을 담는 객체

		String sql = "select * from emp order by empno asc";
		try {
			pstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery(); // select문 실행 시 결과 표 반환

			int row = 0;
			while (rs.next()) {
				row = rs.getRow(); // 현재 커서가 레코드 어디에 있는지 알려줌
			}
			System.out.println("총 레코드 수는" + row);
			String[][] data = new String[row][8];

			// rs를 다시 복귀시키자
			// 아래의 코드가 에러나는 이유 ? ResultSet은 생성 시, 옵션을 부여하지 않으면
			// 전방향 전용 레코드 셋임, 즉 forward 방향으로만 진행 가능함
			rs.beforeFirst(); // 첫번째 레코드 보다도 이전, 처음 탄생위치 (최초의 위치)

			for (int i = 0; i < row; i++) {
				rs.next(); // 커서 위치를 맨 윗 공간에서 하나 이동시켜줌
				data[i][0] = Integer.toString(rs.getInt("empno")); // EMPNO 컬럼
				data[i][1] = rs.getString("ename"); // ENAE 컬럼
				data[i][2] = rs.getString("job"); // JOB 컬럼
				data[i][3] = Integer.toString(rs.getInt("mgr")); // MGR 컬럼
				data[i][4] = rs.getString("hiredate"); // HIREDATE 컬럼
				data[i][5] = Integer.toString(rs.getInt("sal")); // SAL 컬럼
				data[i][6] = Integer.toString(rs.getInt("comm")); // COMM 컬럼
				data[i][7] = Integer.toString(rs.getInt("deptno")); // DEPTNO 컬럼
			}

			System.out.println("생성된 2차원 배열의 크기는 " + data.length);
			table = new JTable(data, column);
			scroll = new JScrollPane(table);
			add(scroll);

			SwingUtilities.updateComponentTreeUI(this);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
	}

	// 오라클 접속해제
	public void release() {
		System.out.println("db관련 자원 해제");
		if (con != null) {
			try {
				con.close(); // SQL 플러스 창 닫기와 같음
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void regist() {
		// table.setValueAt("미전", 2, 1); //JTable에 넣을 대상엔 조건이 없음
		System.out.println("등록할께요");

		// 입력한 값 얻기
		String id = t_id.getText(); // 입력한 아이디
		String pass = new String(t_pass.getPassword()); // 비밀번호의 반환값이 char형 배열이므로 new 해줌, '1' '2' '3' '4' -> '1234'
		String name = t_name.getText(); // 입력한 이름

		/*
		 * String[] list = {id, pass, name};
		 * 
		 * for(int i = 0; i < list.length; i++) { //하나의 레코드 넣기 table.setValueAt(list[i],
		 * x, i); } x++;
		 */

	}

	// 버튼과 리스너 연결
	@Override
	public void actionPerformed(ActionEvent e) {
		regist();

	}

	public static void main(String[] args) {
		new TableTest();
	}
}
