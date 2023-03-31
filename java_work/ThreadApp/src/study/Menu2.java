package study;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

// 어플리케이션에 사용할 네비게이션 메뉴를 클래스로 정의
public class Menu2 extends JLabel{
	int index;
	AppMain2 appMain2;
	
	public Menu2(AppMain2 appMain2, ImageIcon icon, int with, int height, int index) {
		this.appMain2= appMain2;
		this.index=index;
		System.out.println("index : "+index);
		this.setIcon(icon);  //라벨에 사용할 아이콘
		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("저는 "+index+"메뉴예요");
				appMain2.showHide(index);
			}
		});
	}
}
