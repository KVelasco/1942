package game;

import java.awt.Graphics;
import java.util.ArrayList;

public abstract class PlayerPlane extends Vehicle {
	

	private long firingTimer;
	private long firingDelay;

	private int xStart;
	private int yStart;

	protected InputHandler input;
	protected ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	private ArrayList<Bullet> bullets; 
	protected Animation animation;

	private int lives;
	private boolean powerUp = false;
	private boolean gameOver = false;
	
	private boolean recovering = false; 
	private long recoverTimer;
	private long recoverDelay;
	private long recoverTotal = 0;
	
	private int score;

	PlayerPlane(String ref, int xOffset, int yOffset, int speed,
			InputHandler input, ArrayList<Bullet> bullets)
	{
		super(ref, xOffset, yOffset);
		this.setSpeed(speed);
		this.input = input;
		this.bullets = bullets;
		firingTimer = System.nanoTime();
		firingDelay = 200;

		this.health = 4;
		this.lives = 2;
		xStart = xOffset;
		yStart = yOffset;
		
		recoverTimer = System.nanoTime();
		recoverDelay = 15;
		score = 0;
	}

	public void shoot() {

		if (this.fire()) {
			long elapsed = (System.nanoTime() - firingTimer) / 1000000;

			if (elapsed > firingDelay) {
				if (!powerUp) {
					bullets.add(new Bullet("bullet.png",
							this.getXOffset() + 14, this.getYOffset() - 15, 6,
							0));
				} else {
					bullets.add(new Bullet("bullet.png",
							this.getXOffset() + 14, this.getYOffset() - 15, 6,
							0));
					bullets.add(new Bullet("bulletRight.png", this
							.getXOffset() + 30, this.getYOffset() - 5, 6, 1));

					bullets.add(new Bullet("bulletLeft.png", this
							.getXOffset() - 15, this.getYOffset() + 5, 6, 2));

				}
				firingTimer = System.nanoTime();
			}

		}
	}

	public void draw(Graphics g) {
		//recovering when play spawns again
		if(recovering){
			long elapsed = (System.nanoTime() - recoverTimer) / 1000000;
			if (elapsed > recoverDelay) {
				animation.drawAnimation(g, this.getXOffset(), this.getYOffset(), 0);
				recoverTimer = System.nanoTime();
				recoverTotal += recoverDelay; 
				if(recoverTotal > 2000){
					recoverTotal = 0;
					recovering = false;
				}
			}
		}else {
			animation.drawAnimation(g, this.getXOffset(), this.getYOffset(), 0);
		}
	}

	
	public boolean remove(){
		if(this.getHealth() <= 0 && this.getLives() <= 0){
			 gameOver = true;
			return gameOver;
		}
		return gameOver;
	}

	public void update() {
		
	
		
		this.remove();
		if (this.getHealth() <= 0) {
			recovering = true;
			health = 4;
			lives--;
			xOffset = xStart;
			yOffset = yStart;
			setPowerUp(false);
		}
		
		this.move();
		this.bound();
		this.shoot();
		animation.runAnimation();
	}

	//bounds of the frame 
	public void bound(){
		if (getXOffset() <= 0) {
			setXOffset(0);
		}
		if (getXOffset() >= Game.WIDTH * Game.SCALE - 55) {
			setXOffset(Game.WIDTH * Game.SCALE - 55);
		}
		if (getYOffset() <= 0) {
			setYOffset(0);
		}
		if (getYOffset() >= Game.HEIGHT * Game.SCALE - 55) {
			setYOffset(Game.HEIGHT * Game.SCALE - 55);
		}
	}

	public abstract void move();
	
	public abstract boolean fire();

	public int getLives() {
		return this.lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}
	
	public void setPowerUp(boolean powerUp)
	{
		this.powerUp = powerUp;
	}
	
	public boolean isRecovering(){
		return recovering;
	}
	
	public int getScore(){
		return score;
	}
	
	public void setScore(int score){
		this.score = score;
	}

}
