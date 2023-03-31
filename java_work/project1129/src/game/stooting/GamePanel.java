package game.stooting;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

//실제적으로 게임이 구현되는 영역
public class GamePanel extends JPanel {
	Thread loopThread;  //게임엔진 역활을 수행할 루프쓰레드
	BgObject bgObject;
	Hero hero;
	ArrayList<Bullet> bulletList=new ArrayList<Bullet>();
	ArrayList<Enemy> enemyList=new ArrayList<Enemy>();
	String[] enemypath= {"res/larva.png", "res/larva2.png", "res/larva3.png", "res/벌2.png"};
	
	public GamePanel() {
		setPreferredSize(new Dimension(900, 500));
		setBackground(Color.pink);  //기존배경
		
		createBg();
		createHero();  //히어로 탄생
		createEnemy();  //적군 탄생
		
		loopThread=new Thread() {  
			@Override
			public void run() {
				while(true) {
					gameLoop();
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		loopThread.start();  //쓰레드 시작(Runnable로 진입)
	}
	
	//패널에 그래픽 처리
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2=(Graphics2D)g;
		//기존 그림 지우기
		g2.clearRect(0, 0, 900, 500);
		
		//배경처리
		bgObject.render(g2);
		hero.render(g2);  //그래픽메서드를 가진 곳에서 hero 호출
		
		//총알 개수만큼 render()
		for(int i=0;i<bulletList.size();i++) {
			Bullet bullet=bulletList.get(i);
			bullet.render(g2);
		}
		
		//적군 개수만큼 render()
			for(int i=0;i<enemyList.size();i++) {
				Enemy enemy=enemyList.get(i);
				enemy.render(g2);
			}
	}
	
	//배경 객체 생성
	public void createBg() {
		bgObject=new BgObject(this, 0, 0, 900, 500, -1, 0);  //왼쪽으로 움직여야 해서 -1
	}
	
	//주인공 생성
	public void createHero() {
		hero=new Hero(this, 50, 100, 80, 60, 2, 0);
	}
	
	//적군 생성
	public void createEnemy() {
		for(int i=0;i<enemypath.length;i++) {
			Enemy enemy=new Enemy(this, 850, 120*i, 85, 70, -3, 0, enemypath[i]);  //-3 : 배경보다 적군이 더 빨리 와야 함
			enemyList.add(enemy);  //컬렉션에 추가, tick, render에서 반복문 호출하기 위해
		}
		
	}
	
	//좌우방향 처리
	public void moveX(int velX) {
		hero.velX=velX;
	}
	
	//위아래 처리
	public void moveY(int velY) {
		hero.velY=velY;
	}
	
	//총알발사
	public void fire() {
		Bullet bullet=new Bullet(this, hero.x+hero.widht, hero.y+hero.heigth/2, 20, 20, 10, 0);
		bulletList.add(bullet);  //컬렉션에 총알 추가
	}
	
	//물리량 변화
	public void tick() {
		bgObject.tick();
		hero.tick();
		
		//총알 개수만큼 tick()
		for(int i=0;i<bulletList.size();i++) {
			Bullet bullet=bulletList.get(i);
			bullet.tick();
		}
		
		//적군 개수만큼 tick()
		for(int i=0;i<enemyList.size();i++) {
			Enemy enemy=enemyList.get(i);
			enemy.tick();
		}
	}
	
	//렌더링 처리
	public void render() {
		repaint();
	}
	
	public void gameLoop() {
		//System.out.println("gameLoop() called");
		tick();
		render();
	}
}
