package tablemodel;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
//JTable에서 TableModel 사용이 상당히 큰 비중을 차지하는데, 왜 사용해야 하는지를 이해하기 위한 예제
//사용했을 경우와 사용하지 않았을 경우를 비교
import javax.swing.table.TableModel;

/*JDBC 프로그래밍 절차 
 1. 드라이버 로드
 2. 접속
 3. 쿼리문 수행
 4. 자원해제
*/
public class AppMain2 extends JFrame {
	JTable table;
	JScrollPane scroll; // 스크롤을 이용해야 컬럼 제목이 노출됨

	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "java";
	String pass = "1234";
	Connection con; // 맴버변수로 선언한 이유? 원할 때 접속을 끊기 위해 (접속정보를 가지고 있음)
	TableModel model;  //EMP 테이블과 DEPT 테이블의 상위 자료형으로 표시
	

	public AppMain2() {
		// 오라클 연동하기
		connect(); // 오라클 접속

		table = new JTable(model=new EmpModel(this));  //(data, column)
		scroll = new JScrollPane(table);

		add(scroll);

		setSize(600, 400);
		setVisible(true);
		// setDefaultCloseOperation(EXIT_ON_CLOSE);

		// 윈도우와 리스너 연결
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				release(con); // 오라클 연결 해제
				System.exit(0); // 프로세스 정상종료, 에러 발생 시 강제종료:exit(1)
			}
		});
	}

	// 오라클에 접속해보자
	public void connect() {
		try {
			// 드라이버 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 접속
			con = DriverManager.getConnection(url, user, pass);
			if (con == null) {
				this.setTitle("접속실패");
			} else {
				this.setTitle("유저계정으로 접속완료");
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	// 접속 해제
	public void release(Connection con) {
		
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void release(PreparedStatement pstmt, ResultSet rs) {
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(pstmt!=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new AppMain2();
	}

}
