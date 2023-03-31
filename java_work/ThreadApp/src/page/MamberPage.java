package page;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
//회원가입 내용을 보여줄 이미지
public class MamberPage extends JPanel{
	public MamberPage() {
		this.setBackground(Color.orange);
		this.setPreferredSize(new Dimension(AppMain.PAGE_WIDTH, AppMain.PAGE_HEIGHT));	}
}
