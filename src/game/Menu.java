package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Menu {

	public Rectangle onePlayer = new Rectangle(Game.WIDTH/2 + 160, 150, 200, 50);
	public Rectangle twoPlayer = new Rectangle(Game.WIDTH/2 + 160, 250, 200, 50);
	public Rectangle quit = new Rectangle(Game.WIDTH/2 + 165, 350, 200, 50);
	
	public Rectangle menu = new Rectangle(Game.WIDTH/2 + 160, 250, 200, 50);

	private Sprite gameOver = ImageLoader.get().getSprtie("gameOver.png");
	private Sprite gameWon = ImageLoader.get().getSprtie("youWin.png");
	
	public void draw(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;
		Font font1 = new Font("Impact", Font.BOLD, 50);
		g.setFont(font1);
		g.setColor(Color.WHITE);
		g.drawString("1942", Game.WIDTH/2 +200, 100);
		
		Font font2 = new Font("Impact", Font.BOLD, 30);
		g.setFont(font2);
		
		g.drawString("One Player", onePlayer.x+25, onePlayer.y+35); 
		g.drawString("Two Players", twoPlayer.x+25, twoPlayer.y+35); 
		g.drawString("Quit", quit.x+75, quit.y+35); 
		
		g2d.draw(onePlayer);
		g2d.draw(twoPlayer);
		g2d.draw(quit);

		
	}
	
	
	public void drawGameOverMenu(Graphics g){
		
		Graphics2D g2d = (Graphics2D) g;
		Font font = new Font("Impact", Font.BOLD, 30);
		g.setColor(Color.WHITE);
		g.setFont(font);
		
		g.drawImage(gameOver.getImage(), Game.WIDTH / 2 + 120, 50, null);
		g.drawString("Menu", menu.x+65, menu.y+35); 
		g.drawString("Quit", quit.x+65, quit.y+35); 
		
		g2d.draw(menu);
		g2d.draw(quit);
	}
	
	public void drawGameWonMenu(Graphics g){
		
		Graphics2D g2d = (Graphics2D) g;
		Font font = new Font("Impact", Font.BOLD, 30);
		g.setColor(Color.WHITE);
		g.setFont(font);
		
		g.drawImage(gameWon.getImage(), Game.WIDTH / 2 + 135, 50, null);
		g.drawString("Menu", menu.x+65, menu.y+35); 
		g.drawString("Quit", quit.x+65, quit.y+35); 
		
		g2d.draw(menu);
		g2d.draw(quit);
		
		
	}
}