package game;

import java.util.ArrayList;

public class TimeLine {

	private long waveStartTimer;
	private long waveStartTimerDiff;
	private int waveNumber;
	private boolean waveStart;
	private int waveDelay = 5000;

	private ArrayList<PowerUp> powerUps;
	private ArrayList<EnemyPlane> enemies;

	public TimeLine(ArrayList<PowerUp> powerUps, ArrayList<EnemyPlane> enemies) {
		waveStartTimer = 0;
		waveStartTimerDiff = 0;
		waveStart = true;
		waveNumber = 0;
		this.powerUps = powerUps;
		this.enemies = enemies;
	}

	public void update() {

		if (waveStartTimer == 0) {
			waveNumber++;
			waveStart = false;
			waveStartTimer = System.nanoTime();
		} else {
			waveStartTimerDiff = (System.nanoTime() - waveStartTimer) / 1000000;
			if (waveStartTimerDiff > waveDelay) {
				waveStart = true;
				waveStartTimer = 0;
				waveStartTimerDiff = 0;
			}
		}

		if (waveStart) {
			wave();
		}

	}

	public void wave() {

		if (waveNumber == 1) {
			for (int i = 0; i < 5; i++) {
				enemies.add(new GreenPlane("enemy1_1.png", 30 * i * 5, -150, 2));
			}
		}

		if (waveNumber == 2) {
			for (int i = 0; i < 5; i++) {
				enemies.add(new WhitePlane("enemy3_1.png", -150, 15 * i * 5, 2));
			}
		}

		if (waveNumber == 3) {
			for (int i = 0; i < 5; i++) {
				enemies.add(new BluePlane("enemy4_1.png", 30 * i * 5, 475, 2));
			}
		}

		if (waveNumber == 4) {
			for (int i = 0; i < 5; i++) {
				enemies.add(new YellowPlane("enemy2_1.png", 650, 15 * i * 5, 2));
			}
		}
		if (waveNumber == 5) {
			for (int i = 0; i < 5; i++) {
				enemies.add(new GreenPlane("enemy1_1.png", 20 * i * 3, -150, 2));

				enemies.add(new BluePlane("enemy4_1.png", 350 + (i * 50), 475,
						2));
			}

			powerUps.add(new PowerUp("powerup.png", 100, -50, 2));
			if(Game.state == Game.STATE.TWOPLAYER){
				powerUps.add(new PowerUp("powerup.png", 400, -50, 2));
			}
		}
		if (waveNumber == 6) {
			for (int i = 0; i < 5; i++) {
				enemies.add(new WhitePlane("enemy3_1.png", -150, 20 * i * 3, 2));
				enemies.add(new YellowPlane("enemy2_1.png", 650,
						200 + (i * 50), 2));
			}
		}
		if (waveNumber == 7) {
			for (int i = 0; i < 5; i++) {
				enemies.add(new BluePlane("enemy4_1.png", 350 + (i * 50), 475,
						2));
				enemies.add(new WhitePlane("enemy3_1.png", -150, 20 * i * 3, 2));
			}
		}
		if (waveNumber == 8) {
			for (int i = 0; i < 5; i++) {
				enemies.add(new GreenPlane("enemy1_1.png", 20 * i * 3, -150, 2));
				enemies.add(new YellowPlane("enemy2_1.png", 650,
						200 + (i * 50), 2));
			}
		}
		if (waveNumber == 9) {
			for (int i = 0; i < 5; i++) {
				enemies.add(new GreenPlane("enemy1_1.png", 20 * i * 3, -150, 2));
				enemies.add(new YellowPlane("enemy2_1.png", 650,
						200 + (i * 50), 2));
				enemies.add(new BluePlane("enemy4_1.png", 350 + (i * 50), 475,
						2));
				enemies.add(new WhitePlane("enemy3_1.png", -150, 20 * i * 3, 2));
			}

			powerUps.add(new PowerUp("powerup.png", 100, -50, 2));
			if(Game.state == Game.STATE.TWOPLAYER){
				powerUps.add(new PowerUp("powerup.png", 400, -50, 2));
			}
		
		}

		if (waveNumber == 10) {
			enemies.add(new Boss("boss1.png", Game.WIDTH * Game.SCALE / 2,
					-250, 2));
		}

	}

}