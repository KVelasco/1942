package game;

import java.util.ArrayList;

public class PlayerOne extends PlayerPlane {

	PlayerOne(String ref, int xOffset, int yOffset, int speed,
			InputHandler input,ArrayList<Bullet> bullets) {
		super(ref, xOffset, yOffset, speed, input, bullets);
		
		sprites.add(this.sprite);
		this.sprite = ImageLoader.get().getSprtie("myplane2_2.png");
		sprites.add(this.sprite);
		this.sprite = ImageLoader.get().getSprtie("myplane2_3.png");
		sprites.add(this.sprite);
		
		animation = new Animation(2, sprites);
	}

	
	public void move() {
		if (input.up.isPressed()) {
			this.setYOffset(this.getYOffset() - this.getSpeed());
		}
		if (input.down.isPressed()) {
			this.setYOffset(this.getYOffset() + this.getSpeed());
		}
		if (input.left.isPressed()) {
			this.setXOffset(this.getXOffset() - this.getSpeed());
		}
		if (input.right.isPressed()) {
			this.setXOffset(this.getXOffset() + this.getSpeed());
		}
	}

	public boolean fire() {
		return input.fire.isPressed();
	}

	
}
