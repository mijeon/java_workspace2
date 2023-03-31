package page;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
//로그인 내용을 보여줄 이미지
public class LoginPage extends JPanel{
	public LoginPage() {
		this.setBackground(Color.pink);
		this.setPreferredSize(new Dimension(AppMain.PAGE_WIDTH, AppMain.PAGE_HEIGHT));	}
}
