package game;

import java.awt.Graphics;
import java.util.ArrayList;

public class BluePlane extends EnemyPlane {

	private ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	private Animation animation;
	
	//firing variables. delays firing 
	private long firingTimer;
	private long firingDelay;

	public BluePlane(String ref, int xOffset, int yOffset, int speed) {
		super(ref, xOffset, yOffset);

		this.setSpeed(speed);

		sprites.add(this.sprite);
		this.sprite = ImageLoader.get().getSprtie("enemy4_2.png");
		sprites.add(this.sprite);
		this.sprite = ImageLoader.get().getSprtie("enemy4_3.png");
		sprites.add(this.sprite);

		animation = new Animation(2, sprites);

		firingTimer = System.nanoTime();
		firingDelay = 500;
	}

	//shoot bullets
	public void shoot() {
		long elapsed = (System.nanoTime() - firingTimer) / 1000000;

		if (elapsed > firingDelay) {
			Game.enemyBullets.add(new Bullet("enemybullet2.png", this
					.getXOffset() + 2, this.getYOffset() - 15, 6, 0));
			firingTimer = System.nanoTime();
		}
	}
	
	//draw 
	public void draw(Graphics g) {
		animation.drawAnimation(g, this.getXOffset(), this.getYOffset(), 0);

	}


	@Override
	//update
	public void update() {
		animation.runAnimation();
		this.move();
		this.shoot();
	}

	//move
	public void move() {
		if (yOffset <= (Game.HEIGHT * Game.SCALE) / 2 + 75) {
			this.setYOffset(this.getYOffset() - this.getSpeed());
			this.setXOffset(this.getXOffset() - this.getSpeed());
		} else {
			this.setYOffset(this.getYOffset() - this.getSpeed());
		}

	}

}
