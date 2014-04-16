package sets;

public class Triangle {

	Line lineOne, lineTwo;
	Point top, left, right;

	public Triangle(Point left, Point top, Point right) {
		this.top = top;
		this.left = left;
		this.right = right;
		lineOne = new Line(left, top);
		lineTwo = new Line(top, right);
	}

	public double withinSet(double value) {
		double r = 0.0;
		if (value > left.getX())
			if (value <= top.getX()) {
				r = (lineOne.getA() * value) + lineOne.getB();
			} else if (value > top.getX())
				if (value <= right.getX()) {
					r = (lineTwo.getA() * value) + lineTwo.getB();
				}
		return r;
	}
}
