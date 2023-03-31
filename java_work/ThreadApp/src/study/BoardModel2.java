package study;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;

public class BoardModel2 extends AbstractTableModel {
	String[] column = { "board_id", "title", "writer", "content", "regdate", "hit" };
	String[][] data;
	BoardMain2 boardMain2;
	
	public BoardModel2(BoardMain2 boardMain2) {
		this.boardMain2=boardMain2;
		select();
	}
	
	// 레코드 가져오기
	public void select() {
		PreparedStatement pstmt = null; // 쿼리 수행 객체
		ResultSet rs = null; // 결과표를 표현한 객체
		String sql = "select * from board order by board_id desc"; // desc : 내림차순, 최신순으로 정렬

		try {
			pstmt=boardMain2.con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery(); // select 수행 후 표 반환
			rs.last(); // 표의 길이를 구함
			int total = rs.getRow(); // 총 몇건인지 반환 (행의 수)

			// rs는 곧 죽으므로, rs를 대신하여 데이터를 담게될 이차원배열 생성
			data = new String[total][column.length];

			rs.beforeFirst(); // 커서 위치 원상복귀
			for (int i = 0; i < total; i++) {
				rs.next();

				data[i][0] = Integer.toString(rs.getInt("board_id"));
				data[i][1] = rs.getString("title");
				data[i][2] = rs.getString("writer");
				data[i][3] = rs.getString("content");
				data[i][4] = rs.getString("regdate");
				data[i][5] = Integer.toString(rs.getInt("hit"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			boardMain2.release(pstmt, rs);
		}
	}
	
	//레코드 1건 넣기(DML)  result 매개변수가 boardMain에서 어떻게 동작..?
	public int insert(String title, String writer, String content) {
		PreparedStatement pstmt=null;
		int result=0;
		
		String sql="insert into board(board_id, title, writer, content)";
		sql+=" values(seq_board.nextval, '"+title+"', '"+writer+"', '"+content+"')";
		
		System.out.println(sql);
		try {
			pstmt=boardMain2.con.prepareStatement(sql);
			//반환값 int 형의 의미 : 이 DML에 의해 영향을 받은 레코드 수를 반환
			//따라서 insert의 경우 성공 시 언제나 1, 실패 시 0 반환
			//반환 시 레코드 1건을 반환함
			result=pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			boardMain2.release(pstmt);				
		}
		return result;  //호출하는 곳으로 결과값을 반환해줌
	}
	
	//레코드 1건 삭제하기(DML)
	//이 메서드를 호출하는 자는 board_id를 넘겨야함
	public int delete(int board_id) {
		PreparedStatement pstmt=null;
		int result=0;
		
		String sql="delete from board where board_id="+board_id;
		try {
			pstmt=boardMain2.con.prepareStatement(sql);
			result=pstmt.executeUpdate();  //DML 수행
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			boardMain2.release(pstmt);
		}
		System.out.println(sql);
		return result;
	}
	
	//컬럼제목 재정의
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