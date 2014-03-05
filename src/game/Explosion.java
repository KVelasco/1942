package game;

import java.awt.Graphics;
import java.util.ArrayList;

public class Explosion {

	private ArrayList<Sprite> explosion = new ArrayList<Sprite>();
	private Sprite sprite;
	private Animation animation;

	int yOffset;
	int xOffset;
	//int type;

	Explosion(int xOffset, int yOffset, int type) {

		if(type == 1){
			for (int i = 1; i <= 6; i++) {
				this.sprite = ImageLoader.get().getSprtie(
						"explosion1_" + i + ".png");
				explosion.add(this.sprite);
			}
		}else if(type == 2){
			for (int i = 1; i <= 7; i++) {
				this.sprite = ImageLoader.get().getSprtie(
						"explosion2_" + i + ".png");
				explosion.add(this.sprite);
			}
		}
		
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		animation = new Animation(8, explosion);
	}

	//if last frame remove explosion
	public boolean remove() {
		if ((explosion.size() - 1) == animation.getIndex()) {
			return true;
		}
		return false;
	}
	
	//draw
	public void draw(Graphics g) {
		animation.drawAnimation(g, this.xOffset, this.yOffset, 0);
	}

	//update 
	public void update() {
		animation.runAnimation();
	}
}
