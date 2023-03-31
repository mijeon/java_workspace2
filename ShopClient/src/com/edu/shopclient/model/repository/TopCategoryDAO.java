package com.edu.shopclient.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.edu.shopclient.domain.TopCategory;
import com.edu.shopclient.util.DBManager;

/*이 클래스는 오직 TopCategory 테이블에 대해서 CRUD만을 수행하는 목적으로 정의
    select문을 AdminMain에서 작성해도, 프로그램은 수행될 수 있으나, 개발 방법적인
    측면에서 본다면, 유지보수에 좋지 않음 DAO로 별도로 정의해놓으면, admin뿐 아니라
    고객측 앱에서도 이용이 가능함 (재사용==시간 save==돈)*/
public class TopCategoryDAO {
	DBManager dbManager=DBManager.getInstance(); //싱글턴 패턴으로 올림
	
	//모두 가져오기
	public List selectAll() {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		ArrayList<TopCategory> list=new ArrayList<TopCategory>();
		
		con=dbManager.getConnection();
		
		String sql="select * from topcategory order by topcategory_idx asc";
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {  //레코드가 있는 만큼 반복문 수행
				TopCategory topcategory=new TopCategory();  //null
				topcategory.setTopcategory_idx(rs.getInt("topcategory_idx"));
				topcategory.setTopcategory_name(rs.getString("topcategory_name"));
				list.add(topcategory);  //DTO 추가    왜 last안써도 되는거야 이해못해써 !!!!!!!!!
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt, rs);
		}
		return list;  //rs대신 list를 반환		
	}
	
	//카테고리 이름으로 pk 반환하기
	public int getTopcategoryIdx(String topcategory_name) {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int topcategory_idx=0;  //pk
		
		String sql="select topcategory_idx from topcategory";
		sql+=" where topcategory_name=?";  //상의 하의... 한국어가 넘어옴
		
		con=dbManager.getConnection();
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, topcategory_name);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {  //레코드가 존재한다면
				topcategory_idx=rs.getInt("topcategory_idx");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt, rs);
		}	
		return topcategory_idx;
	}
}
