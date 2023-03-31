package page;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
//알림 내용을 보여줄 이미지
public class NoticePage extends JPanel{
	public NoticePage() {
		this.setBackground(Color.gray);
		this.setPreferredSize(new Dimension(AppMain.PAGE_WIDTH, AppMain.PAGE_HEIGHT));	}
}
