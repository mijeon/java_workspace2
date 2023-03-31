package game.stooting;

import java.awt.Graphics2D;

public class Enemy extends GameObject{

	public Enemy(GamePanel gamePanel, int x, int y, int widht, int heigth, int velX, int velY, String path) {
		super(gamePanel, x, y, widht, heigth, velX, velY);
		image=imageManager.getImage(path, widht, heigth);
	}

	@Override
	public void tick() {
		x+=velX;
	}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(image, x, y, widht, heigth, null);
	}

}
