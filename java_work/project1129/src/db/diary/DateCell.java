package db.diary;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;

//날짜에 사용하기 위한 셀
public class DateCell extends Cell{
	Color color=Color.white;
	DiaryMain diaryMain;
	
	public DateCell(DiaryMain diaryMain, String title, String content, int fontSize, int x, int y) {
		super(title, content, fontSize, x, y);
		this.diaryMain=diaryMain;
		
		Border border=new LineBorder(Color.DARK_GRAY);
		setBorder(border);
		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(color==Color.white) {
					color=Color.pink;
					//날짜 콤보박스에 날짜 채워넣기
					diaryMain.setDateInfo(DateCell.this.title);  //this는 내부익명 클래스를 나타내므로, this 앞에 클래스명을 명시해줌
				}else {
					color=Color.white;
				}
				repaint();
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2=(Graphics2D)g;
		//배경색 적용하기
		g2.setColor(color);
		g2.fillRect(0, 0, 120, 120);
				
		g2.setColor(Color.black);
		g2.drawString(title, x, y);  //날짜 그리기
		g2.drawString(content, x-30, y+20);  //내용 그리기
	}
}
