package study;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;

import page.AppMain;

public class EmpModel2  extends AbstractTableModel{
	String[] column= {"deptno", "dname", "loc", "enpno", "ename", "sal", "job"};
	String[][] data;
	EmpMain2 empMain2;
	
	public EmpModel2(EmpMain2 empMain2) {
		this.empMain2=empMain2;
		select();
	}
	
	//부서와 사원테이블 조인하여 가져오기
	public void select() {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		String sql="select d.deptno as deptno, dname, loc, empno, ename, sal, job";
		sql+=" from emp e inner join dept d";
		sql+=" on e.deptno=d.deptno";
		sql+=" order by deptno desc";
		
		System.out.println(sql);
		
		try {
			pstmt=empMain2.con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs=pstmt.executeQuery();
			rs.last();
			int total=rs.getRow();
			
			data=new String[total][column.length];
			rs.beforeFirst();
			for(int i=0;i<total;i++) {
				rs.next();
				data[i][0]=Integer.toString(rs.getInt("deptno"));
				data[i][1]=rs.getString("dname");
				data[i][2]=rs.getString("loc");
				data[i][3]=Integer.toString(rs.getInt("empno"));
				data[i][4]=rs.getString("ename");
				data[i][5]=Integer.toString(rs.getInt("sal"));
				data[i][6]=rs.getString("job");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			empMain2.release(pstmt, rs);
		}
	}

	//사원 1명 등록
	public int insert(int deptno, String ename, int sal, String job) {
		PreparedStatement pstmt=null;  //쿼리 실행 객체
		int result=0;
		
		String sql="insert into emp(empno, deptno, ename, sal, job)";
		sql+=" values(seq_emp.nextval, "+deptno+", '"+ename+"', "+sal+", '"+job+"')";
		System.out.println(sql);
		try {
			pstmt=empMain2.con.prepareStatement(sql);
			result=pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			empMain2.release(pstmt);
		}
		return result;
	}
	
	//컬럼명 재정의
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
