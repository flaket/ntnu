package sets;

public class Line {

	double a;
	double b;

	public Line(Point start, Point end) {
		a = (start.getY() - end.getY()) / (start.getX() - end.getX());
		b = start.getY() - a * start.getX();
	}

	public double getA() {
		return a;
	}

	public double getB() {
		return b;
	}

}
