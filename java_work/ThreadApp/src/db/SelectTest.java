package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*오라클에 접속하여 emp 테이블의 레코드를 가져와보자*/
public class SelectTest {

	// throws란 ? 해당 예외를 개발자가 처리하기 싫을 때, 이 예외가 발생된 메서드를 호출한 호출자에게 예외 처리를 전가시킨다는 뜻
	// public static void main(String[] args) throws ClassNotFoundException{}
	public static void main(String[] args) {
		/*
		 * Java DataBase Connectivity : 자바기반의 데이터베이스 연동 기술(개념), java.sql 패키지에서 지원함
		 * JDBC기반의 DB 연동 절차 1) 드라이버 로드(사용하고자 하는 DBMS 제품에 맞는) -. java언어가 해당 DB제품을 핸들링 하기
		 * 위한 라이브러리 2) 접속 3) 쿼리실행 4) DB관련 자원 해제 (스트림과 DB는 개발자가 반드시 닫아야함)
		 */

		Connection con = null; // java.sql 패키지
		PreparedStatement pstmt = null; // 쿼리 수행 객체
		ResultSet rs = null; // java.sql 결과표를 표현한 객체

		// 인스턴스에 올리는것이 아니라, static 영역으로 지정한 드라이브 클래스를 Load함 //개발자가 올림
		try {
			// 1)드라이버 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 2) 접속시도
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String user = "java";
			String pass = "1234";

			con = DriverManager.getConnection(url, user, pass);
			// 접속이 성공되면, Connection 객체가 채워짐
			// 따라서 con이 null이면 접속실패
			// Connection 안에는 접속정보가 들어있으므로, 접속을 끊을 때 중요한 역활
			if (con != null) {
				System.out.println("접속성공");
			} else {
				System.out.println("접속실패");
			}

			// 쿼리실행
			String sql = "select * from emp order by empno";
			pstmt = con.prepareStatement(sql); // 쿼리 수행 객체 //인터페이스로 new() 불가하므로 con의 객체이용
			rs = pstmt.executeQuery(); // 쿼리 수행 및 표생성

			// rs의 레코드를 사용하기 위해서는 , 커서를 이동시켜야하는데,
			// rs 생성된 초기에는, 그 어떤 레코드도 가르키지 않고 제일 위로 올라와있음
			rs.next();
			rs.next();
			rs.next();
			
			while(rs.next()) {  //next()가 참인 동안
				int empno = rs.getInt("empno");
				String ename = rs.getString("ename");
				String job = rs.getString("job");
				System.out.println(empno + "\t" + ename + "\t" + job);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
