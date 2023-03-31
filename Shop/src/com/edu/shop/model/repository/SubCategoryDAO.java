package com.edu.shop.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.edu.shop.domain.SubCategory;
import com.edu.shop.domain.TopCategory;
import com.edu.shop.util.DBManager;

/*이 클래스는 오직 SubCategory 테이블에 대한 CRUD만을 담당*/
public class SubCategoryDAO {
	DBManager dbManager=DBManager.getInstance();
	
	//상위 카테고리 중 하나를 선택하면, 해당 하위 카테고리 조회
	public List selectByTopCategory(int topcategory_idx) {  //매겨변수가 적을 경우 dto 사용 x
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		ArrayList<SubCategory> list=new ArrayList<SubCategory>();  //레코드 생성
		
		//새롭게 접속이 발생하는게 아니라, 싱글톤 객체가 이미 보유한 Connection을 얻어오는 것임 
		//즉, 이미 접속된 상태의 Connection 객체를 얻어오는 것임
		con=dbManager.getConnection();
		String sql="select * from subcategory";
		sql+=" where topcategory_idx=?";
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, topcategory_idx);
			rs=pstmt.executeQuery();
			//rs의 값들을 DTO에 담아서, list에 모으자 
			//이렇게 하면 rs는 더이상 필요없음 따라서 close() 가능
			while(rs.next()) {
				SubCategory subCategory=new SubCategory();  //비어있는 DTO 생성
				TopCategory topCategory=new TopCategory();
				subCategory.setTopCategory(topCategory);
				
				subCategory.setSubcategory_idx(rs.getInt("subcategory_idx"));  //pk
				//subCategory.setTopcategory_idx(topcategory_idx);  //fk
				topCategory.setTopcategory_idx(topcategory_idx);  //fk
				subCategory.setSubcategory_name(rs.getString("subcategory_name"));				
				list.add(subCategory);  //리스트에 추가
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(pstmt, rs);
		}
		System.out.println(sql);
		return list;
	}
}
