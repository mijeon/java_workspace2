package game.stooting;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

public class GameMain extends JFrame{
	GamePanel gamePanel;
	
	public GameMain() {
		gamePanel=new GamePanel();  //gameLoop 호출
		add(gamePanel);
		pack();  //윈도우가 패널 크기만큼 조절됨
		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);  //화면 정중앙에 위치시킴
		
		//키보드 리스너 연결하기
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println("눌렀어?");
				int key=e.getKeyCode();
				
				switch(key){
					case KeyEvent.VK_LEFT:gamePanel.moveX(-5);break;
					case KeyEvent.VK_RIGHT:gamePanel.moveX(5);break;
					case KeyEvent.VK_UP:gamePanel.moveY(-5);break;
					case KeyEvent.VK_DOWN:gamePanel.moveY(5);break;
					case KeyEvent.VK_SPACE:gamePanel.fire();break;
				}
			}
			//손떼었을 때 속도 없애기
			@Override
			public void keyReleased(KeyEvent e) {
				int key=e.getKeyCode();
				switch(key){
				case KeyEvent.VK_LEFT:gamePanel.moveX(0);break;
				case KeyEvent.VK_RIGHT:gamePanel.moveX(0);break;
				case KeyEvent.VK_UP:gamePanel.moveY(0);break;
				case KeyEvent.VK_DOWN:gamePanel.moveY(0);break;
			}
			}
		});
	}
	
	public static void main(String[] args) {
		new GameMain();
	}

}
