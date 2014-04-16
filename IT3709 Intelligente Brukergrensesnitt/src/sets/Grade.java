package sets;

public class Grade {

	Point left, right;
	Line line;

	public Grade(Point left, Point right) {
		this.left = left;
		this.right = right;
		line = new Line(left, right);
	}

	public double withinSet(double value) {
		if (value <= left.getX())
			return left.getY();
		else if (value > left.getX() && value <= right.getX())
			return (line.getA() * value) + line.getB();
		return 0.0;
	}
}
