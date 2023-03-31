package study;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;


//DEPT 테이블에 대한 CRUD 및 JTable에 정보제공
public class DeptModel2 extends AbstractTableModel{
	EmpMain2 empMain2;  // EmpMain에 Connection이 있으므로
	String[] column= {"deptno", "dname", "loc"};
	String[][] data;

	public DeptModel2(EmpMain2 empMain2) {
		this.empMain2=empMain2;
		selectAll();
	}
	
	//CRUD
	public void selectAll() {
		PreparedStatement pstmt=null;  // 쿼리 수행 객체
		ResultSet rs=null;   // 결과 테이블을 담는 객체
		
		String sql="select * from dept order by deptno desc";
		
		try {
			pstmt=empMain2.con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs=pstmt.executeQuery();  // select문 실행 시 결과표 반환
			rs.last();
			int total=rs.getRow();
			rs.beforeFirst();
			data=new String[total][column.length];
			for(int i=0;i<total;i++) {
				rs.next();
				data[i][0]=Integer.toString(rs.getInt("deptno"));  //부서번호
				data[i][1]=rs.getString("dname");  //부서명
				data[i][2]=rs.getString("loc");  //부서위치
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			empMain2.release(pstmt, rs);
		}
	}

	//레코드 한건 등록
	public int insert(String dname, String loc) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;  //시퀀스 담기 위해
		
		String sql="insert into dept(deptno, dname, loc)";
		sql+=" values(seq_dept.nextval, '"+dname+"', '"+loc+"')";
		
		int deptno=0;
		try {
			pstmt=empMain2.con.prepareStatement(sql);
			int result=pstmt.executeUpdate();// insert, update, delete용
			// 반환값 int 형의 의미 : 이 DML에 의해 영향을 받은 레코드 수를 반환
			// 따라서 insert의 경우 성공 시 언제나 1, 실패 시 0 반환
			//반환 시 레코드 1건만 반환함
			if(result>0) {
				sql="select seq_dept.currval as deptno from dual";
				// PreparedStatment와 ResultSet은 쿼리문 마다 1:1 대응이라 그때마다 생성해야 함
				pstmt=empMain2.con.prepareStatement(sql);
				rs=pstmt.executeQuery();
				rs.next();
				deptno=rs.getInt("deptno");	// 성공여부와 스퀀스 값을 한번에 반환받기 	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			empMain2.release(pstmt, rs);
		}
		return deptno;
	}
	
	
	//컬럼명 재정의 //테이블이 자동 호출해줌
	@Override
	public String getColumnName(int col) {
		return column[col];
	}
	
	@Override
	public int getRowCount() {
			return data.length;
	}

	@Override
	public int getColumnCount() {
		return column.length;
	}

	@Override
	public Object getValueAt(int row, int col) {
		return data[row][col];
	}
}
