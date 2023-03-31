package com.edu.shopclient.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

//쇼핑몰의 메뉴 가능을 담당할 버튼을 커스텀하자
public class MenuButton extends JButton implements ActionListener{
	ClientMain clientMain;
	int pageNum;
	String title;
	
	public MenuButton(ClientMain clientMain, int pageNum, String title) {
		super(title);  //버튼의 제목 설정
		this.clientMain=clientMain;
		this.pageNum=pageNum;
		this.title=title;
		this.addActionListener(this);  //버튼과 리스너 연결
	}	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//System.out.println("나 눌렀어?");
		clientMain.showHide(pageNum);
		
		//물어보자
		//해당 상품에 알맞는 쿼리 요청 단, 상품 관련 버튼메뉴에만 적용
		if(pageNum==clientMain.PRODUCTPAGE){   
			//ProductPage의 selectAll()을 호출하되, title을 넘겨야 '상의', '하의'...텍스트가 전달
			//ProductPage는 현재 나한테 없고 ClientMain에 있음
			ProductPage productPage=(ProductPage)clientMain.pageList[0];  //강제형변환해서 하위자료형으로 사용 가능
			productPage.getProductList(title);
			
			//패널 새로고침
			clientMain.container.updateUI();		
		}
	}
}
