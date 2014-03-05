package game;

import java.awt.Graphics;

public class PowerUp extends GameObject {
	
	public PowerUp(String ref, int xOffset, int yOffset, int speed) {
		super(ref, xOffset, yOffset);
		this.setSpeed(speed);
	}

	public void draw(Graphics g) {
			g.drawImage(this.sprite.getImage(), this.getXOffset(),
					this.getYOffset(), null);
	}

	public boolean remove() {

		if (getYOffset() < -300) {
			return true;
		}

		if (getXOffset() < -300) {
			return true;
		}
		if (getXOffset() >= Game.WIDTH * Game.SCALE + 300) {
			return true;
		}
		if (getYOffset() >= Game.HEIGHT * Game.SCALE + 300) {
			return true;
		}

		return false;

	}


	public void update() {
		this.move();
	}

	public void move() {
		this.setYOffset(this.getYOffset() + this.getSpeed());
	}

}
