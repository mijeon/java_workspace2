package gallery;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JButton;

/*1) JButton 현재 우리의 상황에 맞지 않기 때문에, 우리가 원하는 버튼으로 그림을 재정의 하기 위함*/
public class CustomButton extends JButton{
	int width;
	int height;
	
	//4) 버튼 초기화
	public CustomButton(int width, int height) {
		this.width=width;
		this.height=height;
		this.setBorder(null);
		this.setPreferredSize(new Dimension(width, height));
	}
	protected void paintComponent(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(0, 0, width, height);
		
		g.setColor(Color.pink);
		g.fillOval(0, 0, width, height);  //(0, 0, 20, 20)
	}
}
