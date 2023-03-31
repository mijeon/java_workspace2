package db.emp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;

//EMP 테이블에 대한 CRUD 및 JTable에 정보제공
public class EmpModel extends AbstractTableModel{
	String[] column = {"deptno", "dname", "loc", "empno", "ename", "sal", "job"};
	String[][] data;
	EmpMain empMain;
	
	public EmpModel(EmpMain empMain){
		this.empMain=empMain;
		select();
	}
	//부서와 사원테이블을 조인하여 가져오기
	public void select() {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		String sql="select d.deptno as deptno, dname, loc, empno, ename, sal, job";
		sql+=" from emp e inner join dept d";  //inner join 명시형
		sql+=" on e.deptno=d.deptno";
		sql+=" order by deptno desc";
		
		System.out.println(sql);
		
		try {
			pstmt=empMain.con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs=pstmt.executeQuery();
			
			rs.last();
			int total=rs.getRow();
			
			data=new String[total][column.length];
			rs.beforeFirst();
			
			for(int i=0;i<total;i++) {
				rs.next();
				data[i][0]=Integer.toString(rs.getInt("deptno"));  //부서번호
				data[i][1]=rs.getString("dname");  //부서명
				data[i][2]=rs.getString("loc");  //부서위치
				data[i][3]=Integer.toString(rs.getInt("empno"));  //사원번호
				data[i][4]=rs.getString("ename");  //사원명
				data[i][5]=Integer.toString(rs.getInt("sal"));  //급여
				data[i][6]=rs.getString("job");  //업무
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			empMain.release(pstmt, rs);
		}
	}
	
	//사원 한명 등록 (부서번호를 넘겨받아 insert 할 예정)
	public int insert(int deptno, String ename, int sal, String job) {
		PreparedStatement pstmt=null;  //쿼리 실행 객체
		int result=0;
		
		String sql="insert into emp(empno, deptno, ename, sal, job)";
		sql+=" values(seq_emp.nextval, "+deptno+", '"+ename+"', "+sal+", '"+job+"')";
	
		try {
			pstmt=empMain.con.prepareStatement(sql);
			result=pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			empMain.release(pstmt);
		}
		
				
		return result;
	}
	
	public static void main(String[] args) {
		
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
	
	@Override
	public String getColumnName(int col) {
		return column[col];
	}
}
