package db.diary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 	이 객체에는 dB관련 CRUD 이외의 코드는 두지 않음
 	즉 insert, update, delete, select 실행에 대한 코드만 두어야 함
 	즉, 쿼리전담객체임 이러한 목적의 클래스를 가리켜 DAO(Data Access Object)라 함
 	ex) 대형어플리케이션 분야에서는 테이블이 25개라면 DAO도 25개 존재해야 함
 */
public class DiaryDAO { 
	DbManager dbManager=DbManager.getInstance();

	//CRUD 중 insert 를 정의함
	public int insert(Diary diary) {  //전달용
		System.out.println("호출 후 d"+diary);
		PreparedStatement pstmt=null;
		
		String sql="insert into diary(diary_idx, yy, mm, dd, content, icon)";
		sql+=" values(seq_diary.nextval, ?, ?, ?, ?, ?)";  //순서가 1부터 시작
		
		Connection con=dbManager.getConnection();
		int result=0;
		
		try {
			pstmt=con.prepareStatement(sql);
			
			//값 받기
			pstmt.setInt(1, diary.getYy());
			pstmt.setInt(2, diary.getMm());
			pstmt.setInt(3, diary.getDd());
			pstmt.setString(4, diary.getContent());
			pstmt.setString(5, diary.getIcon());
			
			result=pstmt.executeUpdate();  //쿼리 실행
					
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt);
		}
		return result;
	}
	
	//해당 월에 등록된 다이어리 가져오기
	public List selectAll(int yy, int mm){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		ArrayList<Diary> list=new ArrayList<Diary>();
		
		String sql="select * from diary where yy=?";
		sql +=" and mm=? order by diary_idx asc";
		
		
		try {
			pstmt=dbManager.getConnection().prepareStatement(sql);
			pstmt.setInt(1, yy);
			pstmt.setInt(2, mm);
			rs=pstmt.executeQuery();  //쿼리 수행 및 테이블 반환
			
			while(rs.next()) {
				//비어있는 DTO 하나 생성하자 
				//이유는 ? 레코드 한건을 담기위해
				Diary diary=new Diary();  //현재 빈값
				diary.setDiary_idx(rs.getInt("diary_idx"));
				diary.setYy(rs.getInt("yy"));
				diary.setMm(rs.getInt("mm"));
				diary.setDd(rs.getInt("dd"));
				diary.setContent(rs.getString("content"));
				diary.setIcon(rs.getString("icon"));
				
				//채워진 DTO를 ArrayList에 추가하자
				list.add(diary);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt, rs);
		}	
		return list;
	}
}
