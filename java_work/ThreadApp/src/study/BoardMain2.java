package study;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

public class BoardMain2 extends JFrame implements ActionListener {
	JPanel p_north;
	JTextField t_keyword;
	JButton bt_search;

	JPanel p_west;
	JTextField t_title;
	JTextField t_writer;
	JTextField t_content;
	JButton bt_regist;

	JTable table;
	JScrollPane scroll;

	JPanel p_east;
	JTextField t_title2;
	JTextField t_writer2;
	JTextField t_content2;
	JButton bt_edit, bt_del;

	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "javase";
	String pass = "1234";
	Connection con;
	TableModel model;
	int selected_id; // 유저가 선택한 바로 그 게시물의 board_id

	public BoardMain2() {
		connect();

		// 북쪽
		p_north = new JPanel();
		t_keyword = new JTextField(25);
		bt_search = new JButton("검색");

		p_north.add(t_keyword);
		p_north.add(bt_search);
		add(p_north, BorderLayout.NORTH);

		// 서쪽
		p_west = new JPanel();
		t_title = new JTextField(12);
		t_writer = new JTextField(12);
		t_content = new JTextField(12);
		bt_regist = new JButton("등록");

		p_west.setPreferredSize(new Dimension(150, 500));
		t_content.setPreferredSize(new Dimension(140, 150));

		p_west.add(t_title);
		p_west.add(t_writer);
		p_west.add(t_content);
		p_west.add(bt_regist);
		add(p_west, BorderLayout.WEST);

		// 센터
		table = new JTable(model = new BoardModel2(this)); // this는 BoardModel의 con 정보를 넘겨주기 위해 쓰임
		scroll = new JScrollPane(table);
		add(scroll);

		// 동쪽
		p_east = new JPanel();
		t_title2 = new JTextField(12);
		t_writer2 = new JTextField(12);
		t_content2 = new JTextField(12);
		bt_edit = new JButton("수정");
		bt_del = new JButton("삭제");

		p_east.setPreferredSize(new Dimension(150, 500));
		t_content2.setPreferredSize(new Dimension(140, 150));

		p_east.add(t_title2);
		p_east.add(t_writer2);
		p_east.add(t_content2);
		p_east.add(bt_edit);
		p_east.add(bt_del);
		add(p_east, BorderLayout.EAST);

		setSize(900, 500);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// 버튼에 리스너 연결
		bt_regist.addActionListener(this);
		bt_search.addActionListener(this);
		bt_edit.addActionListener(this);
		bt_del.addActionListener(this);
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row=table.getSelectedRow();
				System.out.println("선택된 층수는 "+row);
				
				BoardModel2 boardModel2=(BoardModel2)model;
				String[] record=boardModel2.data[row];  //게시물 한건, 즉 일차원배열 반환
				System.out.println(record[0]);  
				getDetail(record);
			}
		});

	}

	// 접속
	public void connect() {
		try {
			// 드라이버 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 접속
			con = DriverManager.getConnection(url, user, pass);
			if (con != null) {
				setTitle(user + " 로 접속 성공");
			} else {
				setTitle("접속실패");
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 접속끊기
	public void release(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// DML 사용 후 해제
	public void release(PreparedStatement pstmt) {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// Select 사용 후 해제
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

	// 데이터 등록
	public void regist() {
		BoardModel2 boardModel2 = (BoardModel2) model;
		int result = boardModel2.insert(t_title.getText(), t_writer.getText(), t_content.getText());

		if (result > 0) {
			boardModel2.select(); // 이차원배열 갱신
			table.updateUI(); // 테이블 갱신
		}
	}

	//상세보기
	public void getDetail(String[] record) {
		selected_id=Integer.parseInt(record[0]);
		t_title2.setText(record[1]);
		t_writer2.setText(record[2]);
		t_content2.setText(record[3]);
	}
	
	// 데이터 삭제하기
	public void del() {
		if(JOptionPane.showConfirmDialog(this, "정말 삭제하시겠습니까?")==JOptionPane.OK_OPTION) {
			BoardModel2 boardModel2=(BoardModel2)model;
			int result=boardModel2.delete(selected_id);
			if(result>0) {
				boardModel2.select();  //select 갱신
				table.updateUI();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource(); // 이벤트를 일으킨 컴포넌트
		if (obj == bt_regist) {
			regist();
		} else if (obj == bt_search) {

		} else if (obj == bt_edit) {

		} else if (obj == bt_del) {
			del();
		}
	}

	public static void main(String[] args) {
		new BoardMain2();
	}
}
