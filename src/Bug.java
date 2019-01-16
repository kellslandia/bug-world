import java.util.ArrayList;

import javafx.scene.Scene;

public class Bug extends Entity{
	private float speed;
	private double energy;
	private float dx = this.getSpeed(), dy = this.getSpeed();
	
	public Bug(float x, float y, double rad) {
		super(x, y, rad);
		this.energy = 20;
	}

	public void growEnergy() {
		if(energy > 0) {
			energy = energy - 0.1;
		} else {
			energy = 0;
		}
		
//		System.out.println(energy);	
	}	
	
	public double getEnergy() {
		return energy;
	}

	public void setEnergy(double energy) {
		this.energy = energy;
	}
	
	public float getDx() {
		return dx;
	}

	public void setDx(float dx) {
		this.dx = dx;
	}

	public float getDy() {
		return dy;
	}

	public void setDy(float dy) {
		this.dy = dy;
	}

	public float getSpeed() {
		return speed = 2f;
	}

	public void movement(Scene scene) {
		
		randomMove();
		growEnergy();

		if(getCenterX() + getTranslateX() > scene.getWidth() - getRadius()) { 
			setTranslateX(scene.getWidth() - getRadius() - getCenterX());
			dx = -dx;
		}	
		if(getCenterX() + getTranslateX() < this.getRadius()) {
			setTranslateX(getRadius() - getCenterX());
			dx = -dx;
		}	
		if(getCenterY() + getTranslateY() > scene.getHeight() - getRadius()) { 
			setTranslateY(scene.getHeight() - getRadius() - getCenterY());
			dy = -dy;
		}	
		if(getCenterY() + getTranslateY() < this.getRadius() ) { 
			setTranslateY(getRadius() - getCenterY());
			dy = -dy;
		}

		setTranslateX(getTranslateX() + dx);
		setTranslateY(getTranslateY() + dy);			
	}	
	
	public void randomMove() {
		double ran = Math.random();
		if(ran > 0.9) {
			double random = Math.random();
			if(random < 0.25) {
				dx = + dx;
			} else if(random < 0.5) {
				dx = - dx;
			} else if(random < 0.75) {
				dy = + dy;
			} else {
				dy = - dy;
			}
		}
	}

	public void regulateMove() {

	}

	
	
}
