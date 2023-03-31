package game.stooting;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

//총알 정의
public class Bullet extends GameObject{
	
	public Bullet(GamePanel gamePanel, int x, int y, int widht, int heigth, int velX, int velY) {
		super(gamePanel, x, y, widht, heigth, velX, velY);
	}

	//총알에 맞는 물리량 변화코드 (부모의 메서드 재정의함)
	public void tick() {
		this.x+=this.velX;	
		
		//총알과 적군이 서로 교차하는지 조사해보자
		collsionCheck();
	}
	public void collsionCheck() {
		//나와 적군들 교차여부 확인
		for(int i=0;i<gamePanel.enemyList.size();i++) {
			Enemy enemy=gamePanel.enemyList.get(i);
			boolean result=this.rect.intersects(enemy.rect);
			
			//컬렉션에서 제거하기
			//총알(나)없애고
			int myIndex=gamePanel.bulletList.indexOf(this);
			gamePanel.bulletList.remove(myIndex);
			
			//적군(enemy) 죽고
			int youIndex=gamePanel.enemyList.indexOf(enemy);
			gamePanel.enemyList.remove(youIndex);
		}
	}
	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.pink);
		g.fillOval(x, y, widht, heigth);
	}




	
}
