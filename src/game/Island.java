package game;

import java.awt.Graphics;
import java.util.Random;

public class Island extends GameObject {

	Random gen;

	public Island(String ref, int xOffset, int yOffset, int speed, Random gen) {
		super(ref, xOffset, yOffset);
		this.setSpeed(speed);
		this.gen = gen;
	}

	public void draw() {

	}

	public void draw(Graphics g) {
		g.drawImage(this.sprite.getImage(), this.getXOffset(),
				this.getYOffset(), null);

	}
	
	public boolean remove(){
		return false;
	}
	
	public void update() {
		this.setYOffset(this.getYOffset() + this.getSpeed());
		if (this.getYOffset() >= (Game.HEIGHT * Game.SCALE)) {
			this.setYOffset(-100);
			this.setXOffset(Math.abs(gen.nextInt())
					% ((Game.WIDTH * Game.SCALE) - 30));
		}
	}

	public void move() {

	}

}
