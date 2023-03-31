package page;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/*어플리케이션에서 사용할 네비게이션 메뉴를 클래스로 정의
 * JLabel을 커스터마이징 하자*/
public class Menu extends JLabel{
	int index;
	
	//AppMain을 제어할 것이기 때문에 주소값을 받아오려고 선언함
	AppMain appMain;
	
	public Menu(AppMain appMain, ImageIcon icon, int width, int height, int index) {
		this.appMain=appMain;
		this.index=index;
		System.out.println("저 태어날 때 받은 번호는 "+index);
		this.setIcon(icon);  //라벨에 사용할 아이콘
		
		//라벨의 크기 //this 생략가능
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.out.println("저는 "+index+"메뉴예요");
				appMain.showHide(index);
			}
		});
	}
}
