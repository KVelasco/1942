package game;

public abstract class EnemyPlane extends Vehicle
{
	EnemyPlane(String ref, int xOffset, int yOffset)
	{
		super(ref, xOffset, yOffset);
		
	}
	
	//remove enemy if out of range
	public boolean remove() {
		if (getYOffset() < -300) {
			return true;
		}

		if (getXOffset() < -300) {
			return true;
		}
		if (getXOffset() >= Game.WIDTH * Game.SCALE + 300) {
			return true;
		}
		if (getYOffset() >= Game.HEIGHT * Game.SCALE + 300) {
			return true;
		}

		return false;
	}
	
}
