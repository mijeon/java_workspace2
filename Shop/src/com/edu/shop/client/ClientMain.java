package com.edu.shop.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.edu.shop.util.DBManager;

public class ClientMain extends JFrame{
	DBManager dbManager;
	//북쪽영역
	JPanel p_north;  //메뉴버튼들이 올 패널
	List<MenuButton> menuList;  //메뉴 버튼 리스트
	MenuButton m_login;
	MenuButton m_join;
	MenuButton m_cart;
	JPanel container; //화면 전환에 사용될 루프 컨테이너, 여기에 화면들 여러개를 부착할 예정
	Page[] pageList=new Page[4];  //값이 정해져있으므로 배열로 감
	
	public ClientMain() {
		p_north=new JPanel();
		menuList=new ArrayList<MenuButton>();	
		
		createMenu();  //상품카테고리 수만큼의 버튼 생성
		//고정메뉴 - 로그인, 회원가입, 장바구니
		m_login=new MenuButton("로그인");
		m_join=new MenuButton("회원가입");
		m_cart=new MenuButton("장바구니");
		
		//북쪽영역에 부착
		add(p_north, BorderLayout.NORTH);
		p_north.add(m_login);
		p_north.add(m_join);
		p_north.add(m_cart);
		
		//모든 page들을 품고 있을 패널
		container=new JPanel();
		container.setBackground(Color.white);

		//센터에 부착
		add(container);
		
		//페이지 생성하여 붙여넣기 
		pageList[0]=new ProductPage();
		pageList[1]=new LoginPage();
		pageList[2]=new JoinPage();
		pageList[3]=new CartPage();
		
		//부착
		for(int i=0;i<pageList.length;i++) {
			container.add(pageList[i]);
		}
		
		setSize(900, 500);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);  //임시, db연동할 때는 안함
		
		//디폴트로 보여주고 싶은 페이지 지정
		showHide(0);  //상품페이지
	}
	
	//버튼 생성
	public void createMenu() {
		//추후 db 연동 예정
		String[] menuName= {"상의", "하의", "액세서리", "신발"};
		
		for(int i=0;i<4;i++) {
			MenuButton menu=new MenuButton(menuName[i]);
			p_north.add(menu);  //북쪽 패널에 부착
		}
	}
	
	//보여줄 페이지와 가려질 페이지를 처리하여 화면전환 효과를 냄
	//n은 보여질 index를 결정 예) 2를 넘기면 3번째 페이지가 보여짐
	public void showHide(int n) {
		for(int i=0;i<pageList.length;i++) {
			if(i==n) {  //n과 i가 같다면 보여짐 
			pageList[i].setVisible(true);
			}else {
				pageList[i].setVisible(false);
			}
		}
	}
	
	public static void main(String[] args) {
		new ClientMain();
	}

}
