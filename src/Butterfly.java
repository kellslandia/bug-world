
public class Butterfly extends Bug {
	
	private float speed;

	public Butterfly(float x, float y, float rad) {
		super(x, y, rad);
	}

	@Override
	public float getSpeed() {
		return speed = -1f;
	}
		
}
