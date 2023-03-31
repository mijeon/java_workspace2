package db.diary;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

//요일을 처리하기 위한 셀
public class DayCell extends Cell{
	//DayCell 생성 시 요일을 넘겨받아 보관해놓자
	//paintComponent 
	public DayCell(String title, String content, int fontSize, int x, int y) {
		super(title, content, fontSize, x, y);
	}
	
	@Override
	protected void paintChildren(Graphics g) {
		Graphics2D g2=(Graphics2D)g;
		g2.setColor(Color.gray);
		g2.fillRect(0, 0, 100, 80);
		
		g2.setColor(Color.white);
		Font font=new Font("dodum", Font.BOLD | Font.ITALIC, fontSize);
		g2.setFont(font);
		g2.drawString(title, x, y);
		
	}
	
}
