package game.stooting;

import java.awt.Graphics2D;

public class BgObject extends GameObject{

	public BgObject(GamePanel gamePanel, int x, int y, int widht, int heigth, int velX, int velY) {
		super(gamePanel, x, y, widht, heigth, velX, velY);
		image=imageManager.getImage("res/game.jpg", widht, heigth);
	}

	@Override
	public void tick() {
		this.x+=this.velX;
		if(x<=-900) {
			x=0;
		}
	}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(image, x, y, widht, heigth, null);
		g.drawImage(image, x+900, y, widht, heigth, null);
	}

}
