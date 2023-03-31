package db.emp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;

//DEPT 테이블에 대한 CRUD 및 JTable에 정보제공
public class DeptModel extends AbstractTableModel {
	String[] column = { "deptno", "dname", "loc" };
	String[][] data;
	EmpMain empMain; // EmpMain에 Connection이 있으므로

	public DeptModel(EmpMain empMain) {
		this.empMain = empMain;
		selectAll();
	}

	// CRUD
	public void selectAll() {
		PreparedStatement pstmt = null;  // 쿼리 수행 객체
		ResultSet rs = null;  // 결과 테이블을 담는 객체

		String sql = "select * from dept order by deptno asc";

		try {
			pstmt = empMain.con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery(); // select 수행 및 결과 테이블 반환

			rs.last();
			int total = rs.getRow();

			data = new String[total][column.length];
			rs.beforeFirst();

			for (int i = 0; i < total; i++) {
				rs.next();
				data[i][0] = Integer.toString(rs.getInt("deptno"));
				data[i][1] = rs.getString("dname");
				data[i][2] = rs.getString("loc");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			empMain.release(pstmt, rs);
		}
	}

	// 한건 등록 insert-dml
	public int insert(String dname, String loc) {
		PreparedStatement pstmt = null;
		ResultSet rs = null; // 시퀀스 담기 위함

		String sql = "insert into dept(deptno, dname, loc)";
		sql += " values(seq_dept.nextval, '"+dname+"', '"+loc+"')";

		int deptno = 0;
		try {
			pstmt = empMain.con.prepareStatement(sql);
			int result = pstmt.executeUpdate();
			if (result > 0) {
				sql = "select seq_dept.currval as deptno from dual";
				// PreparedStatment와 ResultSet은 쿼리문 마다 1:1 대응이라 그때마다 생성해야 함
				pstmt = empMain.con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				rs.next(); // 커서 한 칸 전진 (스크롤 필요없음)
				deptno = rs.getInt("deptno"); // 성공여부와 스퀀스 값을 한번에 반환받기
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			empMain.release(pstmt, rs);
		}

		return deptno;
	}

	@Override
	public int getRowCount() {
		return data.length; // 층수
	}

	@Override
	public int getColumnCount() {
		return column.length; // 호수
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