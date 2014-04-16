package sets;

public class ReverseGrade {

	Point left, right;
	Line line;

	public ReverseGrade(Point left, Point right) {
		this.left = left;
		this.right = right;
		line = new Line(left, right);
	}

	public double withinSet(double value) {
		if (value >= right.getX())
			return right.getY();
		else if (value >= left.getX() && value < right.getX())
			return (line.getA() * value) + line.getB();
		return 0.0;
	}
}
