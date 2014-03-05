package game;



import java.awt.Graphics;

public class Boss extends EnemyPlane {

	private long firingTimer;
	private long firingDelay;
	private int direction = 1;

	Boss(String ref, int xOffset, int yOffset, int speed) {
		super(ref, xOffset, yOffset);

		this.setSpeed(speed);
		this.setHealth(50);

		firingTimer = System.nanoTime();
		firingDelay = 400;
	}

	public void shoot() {
		long elapsed = (System.nanoTime() - firingTimer) / 1000000;

		if (elapsed > firingDelay) {
			if(this.getHealth() <= 25){
				Game.enemyBullets.add(new Bullet("bigBullet.png",
						this.getXOffset() + 65, this.getYOffset() + 55, 6, 6));
				Game.enemyBullets.add(new Bullet("bigBullet.png",
						this.getXOffset() + 130, this.getYOffset() + 55, 6, 7));
			}
			Game.enemyBullets.add(new Bullet("bigBullet.png",
					this.getXOffset() + 65, this.getYOffset() + 55, 6, 3));
			Game.enemyBullets.add(new Bullet("bigBullet.png",
					this.getXOffset() + 130, this.getYOffset() + 55, 6, 3));
			
			firingTimer = System.nanoTime();
		}

	}

	public void draw(Graphics g) {
		g.drawImage(this.sprite.getImage(), getXOffset(), getYOffset(), null);
	}


	public void update() {
		if(getHealth() <= 0){
			Game.temp = Game.state;
			Game.state = Game.STATE.GAMEWON;
		}
		this.move();
		this.shoot();
	}

	public void move() {

		if (direction == 2) {
			if(getXOffset() <= 0){
				direction = 3;
			}else {
				this.setXOffset(this.getXOffset() - this.getSpeed());
			}
		} if (direction == 3) {
			if(getXOffset() >= Game.WIDTH * Game.SCALE - 250){
				direction = 2;
			}else {
				this.setXOffset(this.getXOffset() + this.getSpeed());
			}
		} if (direction == 1){
			if(getYOffset() >= Game.HEIGHT * Game.SCALE/2 - 235){
				direction = 2;
			}else{
				this.setYOffset(this.getYOffset() + this.getSpeed());
			}
			
		}
	}

}
