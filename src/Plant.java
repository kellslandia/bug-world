
public class Plant extends Entity {
	
	public Plant(float x, float y, double rad) {
		super(x, y, rad);
	}

	public void grow() {
		if(this.getRadius() < 25 && this.getRadius() >= 0) {
			this.setRadius(this.getRadius() + 0.01);
		}	
	}

}
