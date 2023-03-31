package study;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

//FRONT 영역
public class EmpMain2 extends JFrame{
	//서쪽 영역
	JPanel p_west;
	JTextField t_dname;
	JTextField t_loc;
	JTextField t_ename;
	JTextField t_sal;
	JTextField t_job;
	JButton bt_regist;
	
	//센터 영역
	JTable table;
	JScrollPane scroll;
	
	//DB
	Connection con;
	DeptModel2 model;
	EmpModel2 empModel2;
	
	public EmpMain2() {
		connect();
		model=new DeptModel2(this);
		
		//동쪽 영역
		p_west=new JPanel();
		t_dname=new JTextField(12);
		t_loc=new JTextField(12);
		t_ename=new JTextField(12);
		t_sal=new JTextField(12);
		t_job=new JTextField(12);
		bt_regist=new JButton("등록");
		
		//디자인
		p_west.setPreferredSize(new Dimension(150, 130));
		p_west.setBackground(Color.white);
		
		//부착
		add(p_west, BorderLayout.WEST);
		p_west.add(t_dname);
		p_west.add(t_loc);
		p_west.add(t_ename);
		p_west.add(t_sal);
		p_west.add(t_job);
		p_west.add(bt_regist);
		
		//센터 영역
		table=new JTable(empModel2=new EmpModel2(this));
		scroll=new JScrollPane(table);
		
		add(scroll);
		
		
		setSize(900, 500);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//윈도우에 리스너 연결
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				release(con);
				System.exit(0);
			}
		});
		
		//등록버튼에 리스너 연결
		bt_regist.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				regist();
			}
		});
	}
	
	//등록
	public void regist() {
		int deptno=model.insert(t_dname.getText(), t_loc.getText());
		System.out.println("현재 등록된 부서번호는 "+deptno);
		
		if(deptno>0) {  //부서가 제대로 등록되었다면, 사원도 1명 등록
			int result=empModel2.insert(deptno, t_ename.getText(), Integer.parseInt(t_sal.getText()), t_job.getText());
			if(result>0) {
				empModel2.select();
				table.updateUI();
			}
		}
	}
	
	//오라클 접속
	public void connect() {
		String url="jdbc:oracle:thin:@localhost:1521:XE";
		String user="javase";
		String pass="1234";
		
		try {					
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection(url, user, pass);
			if(con!=null) {
				setTitle("접속성공");
			}else {
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
		if(con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// DML 사용 후 해제
	public void release(PreparedStatement pstmt) {
		if(pstmt!=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// Select 사용 후 해제
	public void release(PreparedStatement pstmt, ResultSet rs) {
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	public static void main(String[] args) {
		new EmpMain2();
	}

}
