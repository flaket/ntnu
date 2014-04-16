package sets;

public class Trapezoid {

	Line lineOne, lineTwo, lineThree;
	Point topLeft, topRight, left, right;

	public Trapezoid(Point left, Point topLeft, Point topRight, Point right) {
		this.topLeft = topLeft;
		this.topRight = topRight;
		this.left = left;
		this.right = right;
		lineOne = new Line(left, topLeft);
		lineTwo = new Line(topLeft, topRight);
		lineThree = new Line(topRight, right);
	}

	public double withinSet(double value) {
		if (left.getX() <= value && value <= topLeft.getX())
			return (lineOne.getA() * value) + lineOne.getB();
		else if (topLeft.getX() <= value && value <= topRight.getX())
			return (lineTwo.getA() * value) + lineTwo.getB();
		else if (topRight.getX() < value && value <= right.getX())
			return (lineThree.getA() * value) + lineThree.getB();
		return 0.0;
	}
}
