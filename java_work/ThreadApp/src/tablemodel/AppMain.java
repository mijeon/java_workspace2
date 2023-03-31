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

/*JDBC 프로그래밍 절차 
 1. 드라이버 로드
 2. 접속
 3. 쿼리문 수행
 4. 자원해제
*/
public class AppMain extends JFrame {
	JTable table;
	JScrollPane scroll; // 스크롤을 이용해야 컬럼 제목이 노출됨
	String[][] data; // emp 연동예정
	String[] column = { "empno", "ename", "job", "mgr", "hiredate", "sal", "comm", "deptno" };
	String[] column2 = { "DEPTNO", "DNAME", "LOC" };

	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "java";
	String pass = "1234";
	Connection con; // 맴버변수로 선언한 이유? 원할 때 접속을 끊기 위해 (접속정보를 가지고 있음)

	public AppMain() {
		// 오라클 연동하기
		connect(); // 오라클 접속
		// select(); //emp 레코드 가져오기
		selectDept(); // dept 레코드 가져오기

		table = new JTable(data, column2);
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
				System.exit(0); // 프로세스 종료
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

	// emp에서 레코드 가져오기
	public void select() {
		// 아래의 두 객체는 쿼리문 마다 1:1 대응하여 생성해야 하므로,
		// 쿼리문 수행 후 객체를 닫음, 따라서 지역변수로 선언함
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "select * from emp order by empno asc";

		try {
			pstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); // 쿼리수행 객체
																												// 생성
			rs = pstmt.executeQuery(); // select 문 수행 시 사용할 메서드 sql문 - update()
			// 현재 rs에는 emp 테이블이 들어있음 따라서 커서를 이동해가면서 원하는 레코드에 접근할 수 있음
			// JTable의 생성자가 rs가 아닌 이차원배열을 원하므로, rs에 들어있는 데이터를 이차원배열로 전환하자
			rs.last();
			int total = rs.getRow();
			System.out.println("사원수는 " + total);

			data = new String[total][column.length];

			// 커서를 다시 복귀시키자
			rs.beforeFirst(); // 첫번째 레코드 보다도 위쪽 (아무것도 가르키지 않음)

			for (int i = 0; i < total; i++) {
				rs.next(); // 커서 한칸(행) 전진
				data[i][0] = Integer.toString(rs.getInt("empno"));
				data[i][1] = rs.getString("ename");
				data[i][2] = rs.getString("job");
				data[i][3] = Integer.toString(rs.getInt("mgr"));
				data[i][4] = rs.getString("hiredate");
				data[i][5] = Integer.toString(rs.getInt("sal"));
				data[i][6] = Integer.toString(rs.getInt("comm"));
				data[i][7] = Integer.toString(rs.getInt("deptno"));
			}

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

	// 부서테이블 가져오기
	public void selectDept() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql2 = "select * from dept order by deptno asc";

		try {
			pstmt = con.prepareStatement(sql2, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery(); // select 수행 및 표변환

			rs.last();
			int total = rs.getRow();
			System.out.println("총 사원 수는" + total);

			data = new String[total][column2.length];

			rs.beforeFirst(); // 커서 원상복귀

			for (int i = 0; i < total; i++) {
				rs.next();
				data[i][0] = Integer.toString(rs.getInt("deptno"));
				data[i][1] = rs.getString("dname");
				data[i][2] = rs.getString("loc");
			}

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

	public static void main(String[] args) {
		new AppMain();
	}

}
