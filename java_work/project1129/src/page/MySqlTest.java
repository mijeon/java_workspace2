package page;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySqlTest {

	public static void main(String[] args) {
		//MySql용 드라이버를 로드하자
		try {
			Class.forName("com.mysql.jdbc.Driver");  //클래스 로드, static 원본코드
			System.out.println("mysql용 드라이버 로드 성공");
			
			//접속시도
			String url="jdbc:mysql://localhost:3306/java";
			String user="root";
			String pass="1234";
			
			Connection con=DriverManager.getConnection(url, user, pass);
			if(con!=null) {
				System.out.println("접속성공");
			}
			String sql="insert into emp2(empno, ename) value('8000', 'mijeon')";
			PreparedStatement pstmt=con.prepareStatement(sql);
			int result=pstmt.executeUpdate();  //쿼리수행
			if(result>0) {
				System.out.println("성공");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}  
	}
}
