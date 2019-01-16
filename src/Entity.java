import javafx.scene.Scene;
import javafx.scene.shape.Circle;

public abstract class Entity extends Circle {
	private float x;
	private float y;
	private double rad;
//	private float speed;
	
	public Entity(float x, float y, double rad) {
		super(x, y, rad);
	}

	public double getRad() {
		return rad;
	}

	public void setRad(double rad) {
		this.rad = rad;
	}

	public float getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
