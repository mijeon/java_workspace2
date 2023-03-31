package tablemodel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;

//JTable은 유지보수성을 높이기 위해, 데이터와 디자인을 분리시키기 위한 방법을 제공해주면
//이때 사용되는 객체가 바로 TableModel임
public class EmpModel extends AbstractTableModel{
	//TableModel이 보유한 메서드들은 JTable이 호출하여 화면에 반영함
	AppMain2 appMain2;
	String[] column= {"empno", "ename", "job", "mgr", "hiredate", "sal", "comm", "deptno"};
	String[][] data;  //=new String[10][column.length]
	
	//태어날 때 AppMain2를 넘겨받자
	public EmpModel(AppMain2 appMain2) {
		this.appMain2=appMain2;
		
		select();
	}
	
	//EMP의 레코드 가져오기
	public void select() {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		String sql="select * from emp order by empno asc";
		
		try {
			pstmt=appMain2.con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);  //스크롤 가능, 읽기 전용
			rs=pstmt.executeQuery();  //select 수행 후 테이블 변환
			
			rs.last();  //스크롤 가능한 rs이어야 함  //커서 맨 아래로 보냄(총길이(행수)를 구하기 위함)
			int total=rs.getRow();  //총 레코드 수 반환
			
			//이 정보들을 이용해 이차원배열 생성
			data=new String[total][column.length];
			
			rs.beforeFirst();  //맨 아래에 있던 커서 다시 원상복귀
			
			for(int i=0;i<total;i++) {
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
		}finally {
			appMain2.release(pstmt, rs);
		}	
	}
	
	//AbstractTableModel 재정의
	//총 레코드 수
	public int getRowCount() {  //층수
		return data.length;
	}

	//컬럼 수
	public int getColumnCount() {  //호수
		return column.length;
	}

	//이차원배열을 이루는 각 요소들의 들어있는 값 반환
	public Object getValueAt(int row, int col) {
		System.out.println("getValueAt("+row+","+col+")호출");  
		return data[row][col];
	}
	
	//위의 3가지 메서드뿐 아니라, 추가적으로 필요한 메서드가 있다면 재정의하자
	@Override
	public String getColumnName(int col) {
		//System.out.println(col+" 번째 컬럼명을 알고 싶어요");
		return column[col];
	}

}
