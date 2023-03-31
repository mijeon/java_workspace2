package com.edu.shopclient.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.edu.shopclient.domain.Product;
import com.edu.shopclient.domain.SubCategory;
import com.edu.shopclient.domain.TopCategory;
import com.edu.shopclient.util.DBManager;

//product 테이블에 대한 CRUD를 수행할 객체
public class ProductDAO {
	DBManager dbManager = DBManager.getInstance(); // getInstance() : 메모리에 올림

	public int insert(Product product) { // int subcategory_idx, String product_name, String brand, int price, String
											// filename 매개변수 이용한 sql문
		Connection con = null;
		PreparedStatement pstmt = null;

		con = dbManager.getConnection();

		String sql = "insert into product(product_idx, subcategory_idx, product_name, brand, price, filename)";
		sql += " values(seq_product.nextval, ?, ?, ?, ?, ?)";
		// sql+=" values(seq_product.nextval, "+subcategory_idx+", '"+product_name+"',
		// '"+brand+"', "+price+", '"+filename+"')";
		// System.out.println(sql);

		int result = 0;
		try {
			pstmt = con.prepareStatement(sql);
			// 쿼리 수행 전 바인드 변수값을 결정짓자
			pstmt.setInt(1, product.getSubcategory().getSubcategory_idx());
			pstmt.setString(2, product.getProduct_name());
			pstmt.setString(3, product.getBrand());
			pstmt.setInt(4, product.getPrice());
			pstmt.setString(5, product.getFilename());

			result = pstmt.executeUpdate(); // DML 수행

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
		return result;
	}

	// 하위카테고리와 조인한 모든 상품 가져오기
	public List selectAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Product> list = new ArrayList<Product>();
		con = dbManager.getConnection();

		StringBuffer sb = new StringBuffer();
		sb.append("select s.subcategory_idx as subcategory_idx, subcategory_name,");
		sb.append("product_idx, product_name, brand, price, filename");
		sb.append(" from subcategory s, product p");
		sb.append(" where s.subcategory_idx=p.subcategory_idx");

		try {
			pstmt = con.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Product product = new Product(); // 빈 product //매번 new를 하는 이유? 테이블의 내용을 가지고 있는 rs에 대응하여 같은 수의 인스턴스를 생성하여
													// 값을 넣어주기 위함
				SubCategory subCategory = new SubCategory();
				product.setSubcategory(subCategory); // subCategory를 product에 넣음

				subCategory.setSubcategory_idx(rs.getInt("subcategory_idx"));
				subCategory.setSubcategory_name(rs.getString("subcategory_name"));
				product.setProduct_idx(rs.getInt("product_idx"));
				product.setProduct_name(rs.getString("product_name"));
				product.setBrand(rs.getString("brand"));
				product.setPrice(rs.getInt("price"));
				product.setFilename(rs.getString("filename"));

				// 이미 Product가 가진 Subcategory DTO이므로 Product만 담으면 됨 (Has a 관계임)
				list.add(product);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}

	// 선택한 상위카테고리에 소속된 모든 상품 가져오기
	public List selectAll(String topcategory_name) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Product> list = new ArrayList<Product>();
		con = dbManager.getConnection();

		StringBuffer sb = new StringBuffer();
		sb.append("s.subcategory_idx as subcategory_idx, subcategory_name, product_idx");
		sb.append("product_idx, product_name, brand, price, filename");
		sb.append(" from topcategory t, subcategory s, product p");
		sb.append(" where t.topcategory_idx=s.topcategory_idx");
		sb.append(" and s.subcategory_idx=p.subcategory_idx");
		sb.append(" and topcategory_name='하의'");

		try {
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setString(1, topcategory_name);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Product product = new Product(); // 빈 product //매번 new를 하는 이유? 테이블의 내용을 가지고 있는 rs에 대응하여 같은 수의 인스턴스를 생성하여
													// 값을 넣어주기 위함
				SubCategory subCategory = new SubCategory();
				product.setSubcategory(subCategory); // subCategory를 product에 넣음

				subCategory.setSubcategory_idx(rs.getInt("subcategory_idx"));
				subCategory.setSubcategory_name(rs.getString("subcategory_name"));
				product.setProduct_idx(rs.getInt("product_idx"));
				product.setProduct_name(rs.getString("product_name"));
				product.setBrand(rs.getString("brand"));
				product.setPrice(rs.getInt("price"));
				product.setFilename(rs.getString("filename"));

				// 이미 Product가 가진 Subcategory DTO이므로 Product만 담으면 됨 (Has a 관계임)
				list.add(product);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return list;
	}

	// 상, 하위 카테고리와 조인하여 상품 한건 가져오기
	public Product select(int produce_idx) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Product product = null; // return 받기 위해

		con = dbManager.getConnection();

		StringBuffer sb = new StringBuffer();
		sb.append(
				"select t.topcategory_idx as topcategory_idx, topcategory_name, s.subcategory_idx as subcategory_idx, subcategory_name, product_idx, product_name, brand, price, filename");
		sb.append(" from topcategory t, subcategory s, product p");
		sb.append(" where t.topcategory_idx=s.topcategory_idx and s.subcategory_idx=p.subcategory_idx");
		sb.append(" and p.product_idx=?"); // product_idx는 primary키로 하나밖에 없으므로 if문 사용

		try {
			pstmt = con.prepareStatement(sb.toString()); // sb는 스트링이 아니므로 스트링 처리 해줘야함
			// 쿼리 실행 전 바인드 변수 지정
			pstmt.setInt(1, produce_idx);
			// 쿼리 수행 및 결과표 반환
			rs = pstmt.executeQuery();

			// 레코드가 있다면 커서 이동 시 true 반환
			if (rs.next()) {
				product = new Product();
				SubCategory subCategory = new SubCategory();
				TopCategory topCategory = new TopCategory();

				// new 했을 경우엔 클래스간의 관련성이 없기 때문에 관련성을 만들어줌
				subCategory.setTopCategory(topCategory);
				product.setSubcategory(subCategory);

				// 상위 담기
				topCategory.setTopcategory_idx(rs.getInt("topcategory_idx"));
				topCategory.setTopcategory_name(rs.getString("topcategory_name"));

				// 하위 담기
				subCategory.setSubcategory_idx(rs.getInt("subcategory_idx"));
				subCategory.setSubcategory_name(rs.getString("subcategory_name"));

				// 상품 담기
				product.setProduct_idx(rs.getInt("product_idx"));
				product.setProduct_name(rs.getString("product_name"));
				product.setBrand(rs.getString("brand"));
				product.setPrice(rs.getInt("price"));
				product.setFilename(rs.getString("filename"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return product;
	}

	// 레코드 한건 삭제
	public int delete(int product_idx) {
		Connection con = null;
		PreparedStatement pstmt = null;
		con = dbManager.getConnection();

		String sql = "delete product where product_idx=?"; // oracle의 경우 from 안적어도 됨

		int result = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, product_idx);
			result = pstmt.executeUpdate(); // 삭제

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
		return result;
	}

	// 레코드 1건 수정
	public int update(Product product) {
		Connection con = null;
		PreparedStatement pstmt = null;
		con = dbManager.getConnection();

		String sql = "update product set subcategory_idx=?";
		sql += " , product_name=?, brand=?, price=?, filename=?";
		sql += " where product_idx=?";

		int result = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, product.getSubcategory().getSubcategory_idx());
			pstmt.setString(2, product.getProduct_name());
			pstmt.setString(3, product.getBrand());
			pstmt.setInt(4, product.getPrice());
			pstmt.setString(5, product.getFilename());
			pstmt.setInt(6, product.getProduct_idx());

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt);
		}
		return result;
	}
}
