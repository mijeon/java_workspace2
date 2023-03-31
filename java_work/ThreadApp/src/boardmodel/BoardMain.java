package boardmodel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

//응용프로그램 개발시, 디자인영역과, 데이터 및 그 처리영역(업무로직)은 서로 분리시켜 개발해야 좋음(유지보수성)
//MVC - Model(데이터, CRVD) View(디자인) Controller(model과 view를 분리함)
public class BoardMain extends JFrame implements ActionListener{
	JPanel p_north;
	JTextField t_keyword; // 검색어 입력
	JButton bt_search; // 검색버튼
	JComboBox combo;  //awt Choice

	JPanel p_west;
	JTextField t_title; // 제목
	JTextField t_writer; // 작성자
	JTextField t_content; // 내용
	JButton bt_regist; // 등록버튼

	JPanel p_east;
	JTextField t_title2; // 제목
	JTextField t_writer2; // 작성자
	JTextField t_content2; // 내용
	JButton bt_edit, bt_del; // 수정, 삭제버튼

	JTable table;
	JScrollPane scroll;

	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "javase";
	String pass = "1234";
	Connection con; // 접속정보를 가지고 있기 때문에 원할 때 닫기 위해서 맴버변수로 선언해줌
	TableModel model;
	int selected_id;  //유저가 선택한 바로 그 게시물의 board_id

	public BoardMain() {
		connect();

		// 북쪽 영역
		p_north = new JPanel();
		combo=new JComboBox();
		t_keyword = new JTextField(25);
		bt_search = new JButton("검색");
		
		combo.addItem("제목");
		combo.addItem("작성자");
		combo.addItem("내용");
		
		p_north.add(combo);
		p_north.add(t_keyword);
		p_north.add(bt_search);
		add(p_north, BorderLayout.NORTH);

		// 서쪽 영역
		p_west = new JPanel();
		t_title = new JTextField(12);
		t_writer = new JTextField(12);
		t_content = new JTextField();
		bt_regist = new JButton("등록");
		p_west.setPreferredSize(new Dimension(150, 500));
		t_content.setPreferredSize(new Dimension(140, 150));

		p_west.add(t_title);
		p_west.add(t_writer);
		p_west.add(t_content);
		p_west.add(bt_regist);
		add(p_west, BorderLayout.WEST);

		// 센터 영역
		table = new JTable(model=new BoardModel(this));  //this???
		scroll = new JScrollPane(table);
		add(scroll);

		// 동쪽 영역
		p_east = new JPanel();
		t_title2 = new JTextField(12);
		t_writer2 = new JTextField(12);
		t_content2 = new JTextField();
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
		// setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				release(con);
				System.exit(0); // 프로세스 정상종료
			}
		});
		
		//버튼들과 리스너 연결
		bt_regist.addActionListener(this);
		bt_search.addActionListener(this);
		bt_edit.addActionListener(this);
		bt_del.addActionListener(this);
		
		//테이블에 리스너 연결
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row=table.getSelectedRow();
				System.out.println("선택된 층수 "+row);
				
				BoardModel boardModel=(BoardModel)model;
				String[] record=boardModel.data[row];  //게시물 한 건, 즉 일차원배열 반환
				System.out.println(record[1]);
				getDetail(record);  //레코드 한건 전달
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
				setTitle(user + "로 접속성공");
			} else {
				setTitle("접속되어 있지 않습니다");
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	// 접속 끊기
	public void release(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// DML(insert, update, delete) 사용 후 해제 
	public void release(PreparedStatement pstmt) {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// Select 사용 후 해제  //데이터베이스에 들어 있는 데이터를 조회하거나 검색
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
	
	//데이터 등록
	public void regist() {
		//모델이 보유한 insert 메서드 호출하여, 등록처리하자
		BoardModel boardModel=(BoardModel)model;
		int result=boardModel.insert(t_title.getText(), t_writer.getText(), t_content.getText());  //제목, 작성자, 내용
		if(result>0) {
			boardModel.select(null, null);  //이차원 갱신때문에
			table.updateUI();  //테이블 갱신
		}	
	}
	
	//상세보기
	public void getDetail(String[] record) {
		//선택된 row에 해당하는 배열을 우측영역에 출력
		//board_id는 출력대상이 아니므로, 어딘가에 보관해두자
		selected_id=Integer.parseInt(record[0]);
		t_title2.setText(record[1]);
		t_writer2.setText(record[2]);
		t_content2.setText(record[3]);
	}
	
	//한건 삭제
	public void del() {
		if(JOptionPane.showConfirmDialog(this, "정말 삭제하시겠습니까?")==JOptionPane.OK_OPTION) {
			BoardModel boardModel=(BoardModel)model;
			int result=boardModel.delete(selected_id);
			if(result!=0) {
				boardModel.select(null, null);  //select 갱신
				table.updateUI();
			}
		}
	}
	
	//한건 수정
	public void edit() {
		BoardModel boardModel=(BoardModel)model;  
		int result=boardModel.update(t_title2.getText(), t_writer2.getText(), t_content2.getText(), selected_id);
		if(result>0) {  //반영된 레코드 수가 있다면
			//갱신
			boardModel.select(null, null);
			table.updateUI();
		}
	}
	
	//검색
	public void getSearchList() {
		BoardModel boardmodel=(BoardModel)model;
		int index=combo.getSelectedIndex()+1;  //유저가 선택한 콤보박스의 순번
		
		//제목 -> title(1), 작성자 -> writer(2), 내용 -> content(3)
		//{ "board_id", "title", "writer", "content", "regdate", "hit" }  //0, 1, 2, 3, 4, 5
		String category=boardmodel.column[index];
		boardmodel.select(category, t_keyword.getText());   //콤보박스값, 텍스트박스값
		table.updateUI();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();  //이벤트를 일으킨 컴포넌트
		if(obj==bt_regist) {
			regist();
		}else if(obj==bt_search) {
			getSearchList();
		}else if(obj==bt_edit) {
			edit();
		}else if(obj==bt_del) {
			del();
		}
	}

	public static void main(String[] args) {
		new BoardMain();
	}
}
