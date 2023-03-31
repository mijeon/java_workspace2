package com.edu.shop.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

//하나의 상품을 표현할 패널 정의
public class ProductItem extends JPanel{
	
	public ProductItem() {
		this.setPreferredSize(new Dimension(780, 80));
	}
	
	protected void paintComponent(Graphics g) {
		g.setColor(Color.pink);
		g.fillRect(0, 0, 850, 80);
	}
}
