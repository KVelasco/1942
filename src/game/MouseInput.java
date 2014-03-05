package game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {

	private Game game;

	public MouseInput(Game game) {
		this.game = game;
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
	
		int mx = e.getX();
		int my = e.getY();

	
		if (Game.state == Game.STATE.MENU) {

			// one player
			if (mx >= Game.WIDTH / 2 + 160 && mx <= Game.WIDTH + 360) {
				if (my >= 150 && my <= 200) {
					Game.state = Game.STATE.ONEPLAYER;
				}
			}

			// two players
			if (mx >= Game.WIDTH / 2 + 160 && mx <= Game.WIDTH + 360) {
				if (my >= 250 && my <= 300) {
					Game.state = Game.STATE.TWOPLAYER;
				}
			}

			// quit game
			if (mx >= Game.WIDTH / 2 + 160 && mx <= Game.WIDTH + 360) {
				if (my >= 350 && my <= 400) {
					System.exit(1);
				}
			}

		}

		//game over screen
		else if (Game.state == Game.STATE.GAMEOVER) {

			// back to main menu
			if (mx >= Game.WIDTH / 2 + 160 && mx <= Game.WIDTH + 360) {
				if (my >= 250 && my <= 300) {
					Game.state = Game.STATE.MENU;
					game.start();
					
				}
			}

			// quit game
			if (mx >= Game.WIDTH / 2 + 160 && mx <= Game.WIDTH + 360) {
				if (my >= 350 && my <= 400) {
					System.exit(1);
				}
			}
		}

		//game won screen
		else if (Game.state == Game.STATE.GAMEWON) {

			// back to main menu
			if (mx >= Game.WIDTH / 2 + 160 && mx <= Game.WIDTH + 360) {
				if (my >= 250 && my <= 300) {
					Game.state = Game.STATE.MENU;
					game.start();
				}
			}

			// quit game
			if (mx >= Game.WIDTH / 2 + 160 && mx <= Game.WIDTH + 360) {
				if (my >= 350 && my <= 400) {
					System.exit(1);
				}
			}
		}

	}

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

}
