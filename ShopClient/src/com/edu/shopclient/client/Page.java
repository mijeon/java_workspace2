package com.edu.shopclient.client;

import java.awt.Dimension;

import javax.swing.JPanel;

//쇼핑 클라이언트 화면에 사용될 모든 페이지들의 최상위 객체
public class Page extends JPanel{
	public Page() {
		setPreferredSize(new Dimension(880, 400));
		setVisible(false);
	}
}
