package com.edu.shop.model.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.edu.shop.admin.AdminMain;
import com.edu.shop.domain.Product;

public class ProductModel extends AbstractTableModel{
	AdminMain adminMain;
	List<Product> productList;
	Vector<String> column=new Vector<String>();
	
	//매개변수로 넘겨받는 이유 ? 기존의 AdminMain이 보유중인 각종 객체들을 사용하기 위함 (ProductDAO 등등 필요한게 많음)
	public ProductModel(AdminMain adminMain) {
		this.adminMain=adminMain;
		column.add("subcategory_idx");
		column.add("하위카테고리명");
		column.add("상품코드");
		column.add("상품명");
		column.add("브랜드");
		column.add("가격");
		column.add("이미지명");
		
		getProductList();
	}
	
	//product DAO 호출
	public void getProductList() {
		productList=adminMain.productDAO.selectAll();  //접근제한자를 public으로 변경해줘서 다른 패키지에 있는 클래스를 사용할 수 있음		
	}
	
	@Override
	public int getRowCount() {
		return productList.size();
	}

	@Override
	public int getColumnCount() {
		return 7;
	}

	@Override
	public Object getValueAt(int row, int col) {
		//우리가 보유한 List는 현재 일차원이므로 row, col 형태로 데이터에 접근할 수 없음
		//하지만, 객체지향적인 직관성을 버리지 말고 개발에 적용해보자 따라서, col을 버림
		Product product=productList.get(row);
		
		//col변수는 table에서 몇번째 호수에 넣어야 할지를 결정지음
		String value=null;
		if(col==0) {
			value=Integer.toString(product.getSubcategory().getSubcategory_idx());
		}else if(col==1) {
			value=product.getSubcategory().getSubcategory_name();
		}else if(col==2) {
			value=Integer.toString(product.getProduct_idx());
		}else if(col==3) {
			value=product.getProduct_name();
		}else if(col==4) {
			value=product.getBrand();
		}else if(col==5) {
			value=Integer.toString(product.getPrice());
		}else {
			value=product.getFilename();
		}
		
		return value;
	}
	
	//컬럼 제목 넣기
	public String getColumnName(int col) {
		return column.elementAt(col);
	}
	
}
