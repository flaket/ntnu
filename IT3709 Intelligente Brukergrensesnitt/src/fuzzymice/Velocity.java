package fuzzymice;

public class Velocity {

	double magnitude, direction;

	public Velocity(double magnitude, double direction) {
		setMagnitude(magnitude);
		setDirection(direction);
	}

	public double getMagnitude() {
		return magnitude;
	}

	public void setMagnitude(double magnitude) {
		this.magnitude = magnitude;
	}

	public double getDirection() {
		return direction;
	}

	public void setDirection(double direction) {
		this.direction = direction;
	}

}
