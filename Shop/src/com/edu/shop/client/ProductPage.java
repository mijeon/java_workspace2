package com.edu.shop.client;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

//상품을 출력해주는 페이지
public class ProductPage extends Page{
	//스크롤 안에는 하나의 컴포넌트만 올 수 있으므로, 
	//상품패널들을 품울 수 있는 패널이 필요, 이 패널이 스크롤에 들어갈 것임
	JPanel wrapper;  //상품 아이템들을 포함할 패널
	JScrollPane scroll;
	List<ProductItem> itemList;
	
	
	public ProductPage() {
		wrapper=new JPanel();
		wrapper.setPreferredSize(new Dimension(780, 1200));  
		itemList=new  ArrayList<ProductItem>();
		
		//상품패널 10개 생성
		for(int i=0;i<10;i++) {
			ProductItem item=new ProductItem();
			wrapper.add(item);	
		}
		scroll=new JScrollPane(wrapper);
		scroll.setPreferredSize(new Dimension(800, 400));
		add(scroll);
	}
}
