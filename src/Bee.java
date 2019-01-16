
public class Bee extends Bug {
	
	private float speed;

	public Bee(float x, float y, float rad) {
		super(x, y, rad);
	}

	@Override
	public float getSpeed() {
		return speed = -0.5f;
	}
		
}
