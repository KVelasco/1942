package game;

import java.awt.Graphics;
import java.util.ArrayList;

public class WhitePlane extends EnemyPlane {

	private ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	private Animation animation;

	private long firingTimer;
	private long firingDelay;

	WhitePlane(String ref, int xOffset, int yOffset, int speed) {
		super(ref, xOffset, yOffset);

		this.setSpeed(speed);

		sprites.add(this.sprite);
		this.sprite = ImageLoader.get().getSprtie("enemy3_2.png");
		sprites.add(this.sprite);
		this.sprite = ImageLoader.get().getSprtie("enemy3_3.png");
		sprites.add(this.sprite);

		animation = new Animation(2, sprites);

		firingTimer = System.nanoTime();
		firingDelay = 1000;
	}

	public void draw(Graphics g) {
		animation.drawAnimation(g, this.getXOffset(), this.getYOffset(), 0);
	}

	
	public void shoot() {
		long elapsed = (System.nanoTime() - firingTimer) / 1000000;

		if (elapsed > firingDelay) {
			Game.enemyBullets.add(new Bullet("enemybullet2.png", this
					.getXOffset() + 15, this.getYOffset() + 2, 6, 4));
			firingTimer = System.nanoTime();
		}

	}


	@Override
	public void update() {
		animation.runAnimation();
		this.move();
		this.shoot();
	}

	@Override
	public void move() {
		if (xOffset >= (Game.WIDTH * Game.SCALE) / 2) {
			this.setYOffset(this.getYOffset() + this.getSpeed());
			this.setXOffset(this.getXOffset() + this.getSpeed());
		} else {
			this.setXOffset(this.getXOffset() + this.getSpeed());
		}

	}

}
