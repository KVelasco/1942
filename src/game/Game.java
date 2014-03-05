package game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 160;
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static final int SCALE = 4;
	public static final String NAME = "Wingman Game";
	public static final Dimension DIMENSIONS = new Dimension(WIDTH * SCALE,
			HEIGHT * SCALE);

	public boolean running = false;
	public int tickCount = 0;

	private PlayerPlane p1;
	private PlayerPlane p2;
	private Background sea = new Background("water.png");

	private InputHandler input;
	private InputHandler input2;

	private ArrayList<Bullet> playerTwoBullets;
	private ArrayList<Bullet> playerOneBullets;

	private ArrayList<EnemyPlane> enemies;
	private ArrayList<Explosion> explosion;
	public static ArrayList<Bullet> enemyBullets;
	private Sound soundExlopsion;
	private Sound powerUpSound;
	private Sound soundExlopsion2;
	private Sound music;
	private ArrayList<PowerUp> powerUps;

	private Random generator = new Random(1234567);
	private Island I1, I2, I3;

	private HealthBar healthBar;
	private HealthBar playerTwoHealthBar;
	private TimeLine timeLine;

	public static enum STATE {
		MENU, ONEPLAYER, TWOPLAYER, GAMEOVER, GAMEWON, PAUSE
	};

	public static STATE state;
	public static STATE temp;
	private Menu menu;
	public JFrame frame;

	public Game(boolean isApplet) {
		if (!isApplet) {
			setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
			setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
			setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

			frame = new JFrame(NAME);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLayout(new BorderLayout());

			frame.add(this, BorderLayout.CENTER);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			frame.setResizable(false);
		}
	}

	public void init() {

		input = new InputHandler(this);
		input2 = new InputHandler(this);

		playerOneBullets = new ArrayList<Bullet>();
		playerTwoBullets = new ArrayList<Bullet>();

		p1 = new PlayerOne("myplane2_1.png", 100, 260, 5, input,
				playerOneBullets);
		p2 = new PlayerTwo("myplane1_1.png", 500, 260, 5, input2,
				playerTwoBullets);

		I1 = new Island("island1.png", 100, 100, 1, generator);
		I2 = new Island("island2.png", 200, 400, 1, generator);
		I3 = new Island("island3.png", 300, 200, 1, generator);

		enemyBullets = new ArrayList<Bullet>();
		enemies = new ArrayList<EnemyPlane>();
		explosion = new ArrayList<Explosion>();
		powerUps = new ArrayList<PowerUp>();

		healthBar = new HealthBar("life2.png", 10, HEIGHT * SCALE - 24, p1);
		playerTwoHealthBar = new HealthBar("life1.png", WIDTH * SCALE - 200,
				HEIGHT * SCALE - 24, p2);

		soundExlopsion = new Sound("snd_explosion1.wav");
		powerUpSound = new Sound("life_pickup.wav");
		soundExlopsion2 = new Sound("snd_explosion2.wav");
		music = new Sound("lone.wav");
		timeLine = new TimeLine(powerUps, enemies);
		menu = new Menu();
		this.addMouseListener(new MouseInput(this));
		state = STATE.MENU;
		temp = null;
	}

	public synchronized void start() {
		running = true;
		new Thread(this).start();

	}

	public synchronized void stop() {
		running = false;
	}

	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000.0 / 60.0;

		// int ticks = 0;
		// int frames = 0;

		long lastTimer = System.currentTimeMillis();
		double delta = 0; // how many nano secs have gone by

		init();
		music.play(true);
		while (running) {

			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;

			while (delta >= 1) {
				// ticks++;
				tick();
				delta -= 1;
				shouldRender = true;
			}

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (shouldRender) {
				// frames++;
				render();
			}

			if (System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				// System.out.println(ticks + " tikcs, " + frames + " frames");
				// frames = 0;
				// ticks = 0;
			}
		}
		music.stop();
	}

	public void tick() {
		tickCount++;

		I1.update();
		I2.update();
		I3.update();

		checkGameStatus();

		if ((state == STATE.ONEPLAYER || state == STATE.TWOPLAYER)) {
			if (!p1.remove()) {
				p1.update();
			}

			if (state == STATE.TWOPLAYER) {
				if (!p2.remove()) {
					p2.update();
				}
			}

			timeLine.update();

			// update enemies
			for (int i = 0; i < enemies.size(); i++) {
				boolean remove = enemies.get(i).remove();
				enemies.get(i).update();
				if (remove) {
					enemies.remove(i);
					i--;
				}
			}

			// update player one bullets
			if (!p1.remove()) {
				for (int i = 0; i < playerOneBullets.size(); i++) {
					boolean remove = playerOneBullets.get(i).remove();
					playerOneBullets.get(i).update();
					if (remove) {
						playerOneBullets.remove(i);
						i--;
					}
				}
			}

			if (state == STATE.TWOPLAYER) {
				// update player two bullets
				if (!p2.remove()) {
					for (int i = 0; i < playerTwoBullets.size(); i++) {
						boolean remove = playerTwoBullets.get(i).remove();
						playerTwoBullets.get(i).update();
						if (remove) {
							playerTwoBullets.remove(i);
							i--;
						}
					}
				}
			}

			// update enemy bullets
			for (int i = 0; i < enemyBullets.size(); i++) {
				boolean remove = enemyBullets.get(i).remove();
				enemyBullets.get(i).update();
				if (remove) {
					enemyBullets.remove(i);
					i--;
				}

			}

			// Update Explosions
			for (int i = 0; i < explosion.size(); i++) {
				if (explosion.get(i).remove()) {
					explosion.remove(i);
				} else {
					explosion.get(i).update();
				}

			}

			// powerUp update
			for (int i = 0; i < powerUps.size(); i++) {
				boolean remove = powerUps.get(i).remove();
				powerUps.get(i).update();
				if (remove) {
					powerUps.remove(i);
					i--;
				}
			}

			// player collision
			if (!p1.remove()) {
				playerOneCollision();
			}

			if (state == STATE.TWOPLAYER) {
				if (!p2.remove()) {
					playerTwoCollision();
				}

			}
		}

	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		// Draw Sea BackGround
		sea.draw(g);

		// Draw Islands
		I1.draw(g);
		I2.draw(g);
		I3.draw(g);

		/*
		 * g.drawString("Game State: " + state, 10, 100);
		 * g.drawString("temp State: " + temp, 10, 120);
		 */
		// main menu
		if (state == STATE.MENU) {
			menu.draw(g);
		}

		// game over
		else if (state == STATE.GAMEOVER) {

			g.setFont(new Font("Century Gothic", Font.BOLD, 20));
			String s = " PLAYER ONE SCORE: " + p1.getScore();
			g.setColor(new Color(255, 255, 255, 150));
			g.drawString(s, 10, 20);

			if (temp == STATE.TWOPLAYER) {
				g.setFont(new Font("Century Gothic", Font.BOLD, 20));
				String s1 = " PLAYER TWO SCORE: " + p2.getScore();
				g.setColor(new Color(255, 255, 255, 150));
				g.drawString(s1, Game.WIDTH * Game.SCALE - 240, 20);
			}
			menu.drawGameOverMenu(g);
		}

		// game won
		else if (state == STATE.GAMEWON) {

			g.setFont(new Font("Century Gothic", Font.BOLD, 20));
			String s = " PLAYER ONE SCORE: " + p1.getScore();
			g.setColor(new Color(255, 255, 255, 150));
			g.drawString(s, 10, 20);

			if (temp == STATE.TWOPLAYER) {
				g.setFont(new Font("Century Gothic", Font.BOLD, 20));
				String s1 = " PLAYER TWO SCORE: " + p2.getScore();
				g.setColor(new Color(255, 255, 255, 150));
				g.drawString(s1, Game.WIDTH * Game.SCALE - 240, 20);
			}
			menu.drawGameWonMenu(g);
		}

		else if (state == STATE.ONEPLAYER || state == STATE.TWOPLAYER) {

			// Draw player one HealthBar
			if (!p1.remove()) {
				healthBar.draw(g);
			}

			// draw player two healthBar
			if (state == STATE.TWOPLAYER) {
				if (!p2.remove()) {
					playerTwoHealthBar.draw(g);
				}

			}

			// Draw Player one
			if (!p1.remove()) {
				p1.draw(g);
			}

			// draw player two
			if (state == STATE.TWOPLAYER) {
				if (!p2.remove()) {
					p2.draw(g);
				}
			}

			/*
			 * g.drawString("num p.bullets:" + playerOneBullets.size(), 10, 20);
			 * g.drawString("enemies:" + enemies.size(), 10, 40);
			 * 
			 * g.drawString("Player One Health: " + p1.getHealth(), 10, 60);
			 * g.drawString("Player One Lives: " + p1.getLives(), 10, 80);
			 */

			// Draw Enemies
			for (int i = 0; i < enemies.size(); i++) {
				enemies.get(i).draw(g);
			}

			// draw player one bullets
			if (!p1.remove()) {
				for (int i = 0; i < playerOneBullets.size(); i++) {
					playerOneBullets.get(i).draw(g);
				}
			}

			if (state == STATE.TWOPLAYER) {
				// draw player two bullets
				if (!p2.remove()) {
					for (int i = 0; i < playerTwoBullets.size(); i++) {
						playerTwoBullets.get(i).draw(g);
					}
				}
			}

			// Draw Enemies Bullets
			for (int i = 0; i < enemyBullets.size(); i++) {
				enemyBullets.get(i).draw(g);
			}

			// Draw Explosions
			for (int i = 0; i < explosion.size(); i++) {
				explosion.get(i).draw(g);
			}

			// draw powerUps
			for (int i = 0; i < powerUps.size(); i++) {
				powerUps.get(i).draw(g);
			}

			// Player One Score
			if (!p1.remove()) {
				g.setFont(new Font("Century Gothic", Font.PLAIN, 18));
				String s = "SCORE: " + p1.getScore();
				g.setColor(new Color(255, 255, 255, 150));
				g.drawString(s, 70, HEIGHT * SCALE - 24);

			}

			if (state == STATE.TWOPLAYER) {
				// Player Two Score
				if (!p2.remove()) {
					g.setFont(new Font("Century Gothic", Font.PLAIN, 18));
					String s2 = "SCORE: " + p2.getScore();
					g.setColor(new Color(255, 255, 255, 150));
					g.drawString(s2, WIDTH * SCALE - 140, HEIGHT * SCALE - 24);
				}
			}
		}

		g.dispose();
		bs.show();

	}

	public void playerOneCollision() {
		// player bullet collision with enemy planes
		for (int i = 0; i < playerOneBullets.size(); i++) {
			for (int j = 0; j < enemies.size(); j++) {
				if (playerOneBullets.get(i).collision(enemies.get(j))) {
					if (enemies.get(j) instanceof Boss) {
						enemies.get(j)
								.setHealth(enemies.get(j).getHealth() - 1);
						explosion.add(new Explosion(playerOneBullets.get(i)
								.getXOffset(), playerOneBullets.get(i)
								.getYOffset(), 1));
						soundExlopsion.play(false);
						// score += 100;
						p1.setScore(p1.getScore() + 100);
						playerOneBullets.remove(i);
						break;
					} else {
						explosion.add(new Explosion(
								enemies.get(j).getXOffset(), enemies.get(j)
										.getYOffset(), 2));
						soundExlopsion.play(false);
						// score += 100;
						p1.setScore(p1.getScore() + 100);
						enemies.remove(j);
						playerOneBullets.remove(i);
						break;
					}
				}
			}
		}

		// player one collision with enemy planes
		for (int i = 0; i < enemies.size(); i++) {
			if (p1.collision(enemies.get(i)) && !p1.isRecovering()) {
				// p1.setHealth(p1.getHealth() - 1);
				if (enemies.get(i) instanceof Boss) {
					p1.setHealth(0);
					explosion.add(new Explosion(p1.getXOffset(), p1
							.getYOffset(), 2));
					soundExlopsion2.play(false);
					break;
				} else {
					p1.setHealth(p1.getHealth() - 1);
					if (p1.getHealth() <= 0) {
						explosion.add(new Explosion(p1.getXOffset(), p1
								.getYOffset(), 2));
						soundExlopsion2.play(false);
						enemies.remove(i);
						break;
					} else {
						explosion.add(new Explosion(
								enemies.get(i).getXOffset(), enemies.get(i)
										.getYOffset(), 1));
						soundExlopsion.play(false);
						enemies.remove(i);
						break;
					}
				}
			}
		}

		// Player one collision with enemy bullets
		for (int i = 0; i < enemyBullets.size(); i++) {
			if (p1.collision(enemyBullets.get(i)) && !p1.isRecovering()) {
				p1.setHealth(p1.getHealth() - 1);
				if (p1.getHealth() <= 0) {
					explosion.add(new Explosion(p1.getXOffset(), p1
							.getYOffset(), 2));
					soundExlopsion2.play(false);
					enemyBullets.remove(i);
					break;
				} else {
					explosion
							.add(new Explosion(
									enemyBullets.get(i).getXOffset(),
									enemyBullets.get(i).getYOffset(), 1));
					soundExlopsion.play(false);
					enemyBullets.remove(i);
					break;
				}
			}
		}

		// player one powerUp collision
		for (int i = 0; i < powerUps.size(); i++) {
			if (p1.collision(powerUps.get(i))) {
				powerUpSound.play(false);
				p1.setPowerUp(true);
				powerUps.remove(i);
				break;
			}
		}

	}

	public void playerTwoCollision() {

		// player bullet collision with enemy planes
		for (int i = 0; i < playerTwoBullets.size(); i++) {
			for (int j = 0; j < enemies.size(); j++) {
				if (playerTwoBullets.get(i).collision(enemies.get(j))) {
					if (enemies.get(j) instanceof Boss) {
						enemies.get(j)
								.setHealth(enemies.get(j).getHealth() - 1);
						explosion.add(new Explosion(playerTwoBullets.get(i)
								.getXOffset(), playerTwoBullets.get(i)
								.getYOffset(), 1));
						soundExlopsion.play(false);
						// playerTwoScore += 100;
						p2.setScore(p2.getScore() + 100);
						playerTwoBullets.remove(i);
						break;
					} else {
						explosion.add(new Explosion(
								enemies.get(j).getXOffset(), enemies.get(j)
										.getYOffset(), 2));
						soundExlopsion2.play(false);
						// playerTwoScore += 100;
						p2.setScore(p2.getScore() + 100);
						enemies.remove(j);
						playerTwoBullets.remove(i);
						break;
					}
				}
			}
		}

		// player two collision with enemy planes
		for (int i = 0; i < enemies.size(); i++) {
			if (p2.collision(enemies.get(i)) && !p2.isRecovering()) {
				// p2.setHealth(p2.getHealth() - 1);
				if (enemies.get(i) instanceof Boss) {
					p2.setHealth(0);
					explosion.add(new Explosion(p2.getXOffset(), p2
							.getYOffset(), 2));
					soundExlopsion2.play(false);
					break;
				} else {
					p2.setHealth(p2.getHealth() - 1);
					if (p2.getHealth() <= 0) {
						explosion.add(new Explosion(p2.getXOffset(), p2
								.getYOffset(), 2));
						soundExlopsion2.play(false);
						enemies.remove(i);
						break;
					} else {
						explosion.add(new Explosion(
								enemies.get(i).getXOffset(), enemies.get(i)
										.getYOffset(), 1));
						soundExlopsion.play(false);
						enemies.remove(i);
						break;
					}
				}
			}
		}

		// Player two collision with enemy bullets
		for (int i = 0; i < enemyBullets.size(); i++) {
			if (p2.collision(enemyBullets.get(i)) && !p2.isRecovering()) {
				p2.setHealth(p2.getHealth() - 1);
				if (p2.getHealth() <= 0) {
					explosion.add(new Explosion(p2.getXOffset(), p2
							.getYOffset(), 2));
					soundExlopsion2.play(false);
					enemyBullets.remove(i);
					break;
				} else {
					explosion
							.add(new Explosion(
									enemyBullets.get(i).getXOffset(),
									enemyBullets.get(i).getYOffset(), 1));
					soundExlopsion.play(false);
					enemyBullets.remove(i);
					break;
				}
			}
		}

		// player two powerUp collision
		for (int i = 0; i < powerUps.size(); i++) {
			if (p2.collision(powerUps.get(i))) {
				powerUpSound.play(false);
				p2.setPowerUp(true);
				powerUps.remove(i);
				break;
			}
		}

	}

	public void checkGameStatus() {

		if (state == STATE.ONEPLAYER && p1.remove()) {
			state = STATE.GAMEOVER;
			this.stop();
		} else if (state == STATE.TWOPLAYER && p1.remove() && p2.remove()) {
			temp = state;
			state = STATE.GAMEOVER;
			this.stop();
		} else if (state == STATE.GAMEWON) {
			this.stop();
		}
	}

	public static void main(String[] args) {
		new Game(false).start();
	}
}
