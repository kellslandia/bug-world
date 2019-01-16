import javafx.scene.image.ImageView;

public class Ladybug extends Bug {
	float speed;
	
	public Ladybug(float x, float y, float rad) {
		super(x, y, rad);
	}	
	
	@Override
	public float getSpeed() {
		return speed = -2.5f;
	}
	
}
