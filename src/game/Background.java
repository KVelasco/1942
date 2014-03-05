package game;

import java.awt.Graphics;

public class Background {
	Sprite sea;
	public int speed = 1;
	public int move = 0;

	public Background(String ref) {
		sea = ImageLoader.get().getSprtie(ref);
	}

	// draws the background and moves it as well
	public void draw(Graphics g) {
		int TileWidth = sea.getWidth();
		int TileHeight = sea.getHeight();

		int NumberX = (int) ((Game.WIDTH * Game.SCALE) / TileWidth);
		int NumberY = (int) ((Game.HEIGHT * Game.SCALE) / TileHeight);

		for (int i = -1; i <= NumberY; i++) {
			for (int j = -1; j <= NumberX; j++) {
				g.drawImage(sea.getImage(), j * TileWidth, i * TileHeight
						+ (move % TileHeight), TileWidth, TileHeight, null);
			}
		}
		move += speed;
	}
}
