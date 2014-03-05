package game;

import java.util.ArrayList;

public class PlayerTwo extends PlayerPlane{

	PlayerTwo(String ref, int xOffset, int yOffset, int speed,
			InputHandler input,ArrayList<Bullet> bullets) {
		super(ref, xOffset, yOffset, speed, input, bullets);
		
		sprites.add(this.sprite);
		this.sprite = ImageLoader.get().getSprtie("myplane1_2.png");
		sprites.add(this.sprite);
		this.sprite = ImageLoader.get().getSprtie("myplane1_3.png");
		sprites.add(this.sprite);
		
		animation = new Animation(2, sprites);
	}

	public void move() {
		
		if (input.up2.isPressed()) {
			this.setYOffset(this.getYOffset() - this.getSpeed());
		}
		if (input.down2.isPressed()) {
			this.setYOffset(this.getYOffset() + this.getSpeed());
		}
		if (input.left2.isPressed()) {
			this.setXOffset(this.getXOffset() - this.getSpeed());
		}
		if (input.right2.isPressed()) {
			this.setXOffset(this.getXOffset() + this.getSpeed());
		}
	}

	public boolean fire() {
		return input.fire2.isPressed();
	}
}
