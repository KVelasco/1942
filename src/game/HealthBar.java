package game;

import java.awt.Graphics;
import java.util.ArrayList;

public class HealthBar {
	private Sprite sprite;
	private ArrayList<Sprite> health = new ArrayList<Sprite>();
	private int xOffset;
	private int yOffset;
	private PlayerPlane plane;

	public HealthBar(String ref,int xOffset, int yOffset, PlayerPlane plane) {
		for (int i = 0; i < 4; i++) {
			this.sprite = ImageLoader.get().getSprtie("HealthBar" + i + ".png");
			health.add(this.sprite);
		}

		this.sprite = ImageLoader.get().getSprtie(ref);

		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.plane = plane;
	}

	public void draw(Graphics g) {

		if (plane.getHealth() == 4) {
			g.drawImage(health.get(0).getImage(), this.xOffset, this.yOffset,
					null);
		}
		if (plane.getHealth() == 3) {
			g.drawImage(health.get(1).getImage(), this.xOffset, this.yOffset,
					null);
		}
		if (plane.getHealth() == 2) {
			g.drawImage(health.get(2).getImage(), this.xOffset, this.yOffset,
					null);
		}
		if (plane.getHealth() == 1) {
			g.drawImage(health.get(3).getImage(), this.xOffset, this.yOffset,
					null);
		}

		if (plane.getLives() == 2) {
			g.drawImage(this.sprite.getImage(), this.xOffset,
					this.yOffset - 22, null);

			g.drawImage(this.sprite.getImage(), this.xOffset + 28,
					this.yOffset - 22, null);

		}else if (plane.getLives() == 1) {
			g.drawImage(this.sprite.getImage(), this.xOffset,
					this.yOffset - 22, null);

		}
	}
	
	
	public int getX(){
		return xOffset;
	}
}
