package page;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
//회사 소개의 내용을 보여줄 이미지
public class CompanyPage extends JPanel{
	public CompanyPage() {
		this.setBackground(Color.darkGray);
		this.setPreferredSize(new Dimension(AppMain.PAGE_WIDTH, AppMain.PAGE_HEIGHT));
	}
}
