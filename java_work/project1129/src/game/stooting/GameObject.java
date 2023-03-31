package game.stooting;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import util.ImageManager;

//게임에 등장할 모든 객체들의 최상위 클래스
public abstract class GameObject {
	int x;
	int y;
	int widht;
	int heigth;
	int velX;
	int velY;
	ImageManager imageManager;
	Image image;
	Rectangle rect;  //사각형
	GamePanel gamePanel;
	
	public GameObject(GamePanel gamePanel, int x, int y, int widht, int heigth, int velX, int velY) {
		this.gamePanel=gamePanel;
		this.x=x;
		this.y=y;
		this.widht=widht;
		this.heigth=heigth;
		this.velX=velX;
		this.velY=velY;
		
		this.x=x;
		this.y=y;
		this.widht=widht;
		this.heigth=heigth;
		this.velX=velX;
		this.velY=velY;
		
		rect=new Rectangle(x, y, widht, heigth);
		imageManager=new ImageManager();
	}
	
	public abstract void tick();  //추상메서드
	public abstract void render(Graphics2D g);
}
