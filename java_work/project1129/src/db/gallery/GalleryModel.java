package db.gallery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;

//Gallery 테이블과 관련된 CRUD를 담당하고 JTable에게 정보를 제공해주는 역활의 객체
public class GalleryModel extends AbstractTableModel {
	String[] column = { "gallery_id", "title", "writer", "content", "filename", "regdate" };
	String[][] data;
	GalleryMain galleryMain;

	public GalleryModel(GalleryMain galleryMain) {
		this.galleryMain = galleryMain;
		selectAll();
	}

	// 모든 데이터 가져오기
	public void selectAll() {
		String sql = "select * from gallery order by gallery_id desc";

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = galleryMain.con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY); // 쿼리 수행
			rs = pstmt.executeQuery(); // 결과표 반환
			rs.last();
			int total = rs.getRow();
			data = new String[total][column.length];
			rs.beforeFirst(); // 원상복귀
			for (int i = 0; i < total; i++) {
				rs.next();
				data[i][0] = Integer.toString(rs.getInt("gallery_id"));
				data[i][1] = rs.getString("title");
				data[i][2] = rs.getString("writer");
				data[i][3] = rs.getString("content");
				data[i][4] = rs.getString("filename");
				data[i][5] = rs.getString("regdate");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			galleryMain.release(pstmt, rs);
		}

	}

	// 1건 가져오기
	public void select() {

	}

	// insert
	public int insert(String title, String writer, String content, String filename) {
		PreparedStatement pstmt = null;
		String sql = "insert into gallery(gallery_id, title, writer, content, filename)";
		sql += " values(seq_gallery.nextval,'" + title + "','" + writer + "','" + content + "','" + filename + "')";
		int result = 0;
		try {
			pstmt = galleryMain.con.prepareStatement(sql);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			galleryMain.release(pstmt);
		}
		return result;
	}

	// update
	public void update() {

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
