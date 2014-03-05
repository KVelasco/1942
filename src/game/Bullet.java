package game;

import java.awt.Graphics;

public class Bullet extends Projectile {
	
	private int direction;
	
	
	Bullet(String ref, int xOffset, int yOffset, int speed, int direction) {
		super(ref, xOffset, yOffset);
		this.setSpeed(speed);
		this.direction = direction;
	}

	//draw
	public void draw(Graphics g) {
		g.drawImage(this.sprite.getImage(), this.getXOffset(),
				this.getYOffset(), null);
	}
	
	//update
	public void update() {
		this.move();
	}

	//move depending on the direction 
	public void move() {
		if (direction == 0) {
			this.setYOffset(getYOffset() - getSpeed());
		}
		if (direction == 1) {
			this.setYOffset(getYOffset() - getSpeed());
			this.setXOffset(getXOffset() + getSpeed());

		}
		if (direction == 2) {
			this.setYOffset(getYOffset() - getSpeed());
			this.setXOffset(getXOffset() - getSpeed());
		}
		if (direction == 3) {
			this.setYOffset(getYOffset() + getSpeed());
		}
		
		if (direction == 4) {
			this.setXOffset(getXOffset() + getSpeed());
		}
		
		if (direction == 5) {
			this.setXOffset(getXOffset() - getSpeed());
		}
		if(direction == 6){
			this.setYOffset(getYOffset() + getSpeed());
			this.setXOffset(getXOffset() - getSpeed());

		}
		if (direction == 7) {
			this.setYOffset(getYOffset() + getSpeed());
			this.setXOffset(getXOffset() + getSpeed());
		}
	}

	//remove bullet if out of range
	public boolean remove() {
		if (getYOffset() < -20) {
			return true;
		}

		if (getXOffset() < -20) {
			return true;
		}
		if (getXOffset() >= Game.WIDTH * Game.SCALE + 20) {
			return true;
		}
		if (getYOffset() >= Game.HEIGHT * Game.SCALE + 20) {
			return true;
		}

		return false;
	}

}
