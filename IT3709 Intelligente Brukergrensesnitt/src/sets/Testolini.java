package sets;

public class Testolini {
	public static void main(String[] args) {

		// Point top = new Point(1.0, 0.5);
		//
		// Point left = new Point(0.0, 0.0);
		// Point right = new Point(1.0, 0.0);
		//
		// Point topLeft = new Point(0.2, 1.0);
		// Point topRight = new Point(0.8, 1.0);

		ReverseGrade healthPoor = new ReverseGrade(new Point(0.0, 1.0),
				new Point(30.0, 0.0));
		Triangle healthGood = new Triangle(new Point(20.0, 0.0), new Point(
				50.0, 1.0), new Point(80.0, 0.0));
		Grade healthGreat = new Grade(new Point(70.0, 0.0), new Point(100.0,
				1.0));

		double value = 5.0;
		double poor = healthPoor.withinSet(value);
		double good = healthGood.withinSet(value);
		double great = healthGreat.withinSet(value);

		System.out.println(poor + "\n" + good + "\n" + great);

		// step 4: defuzzification
		double above = (10 + 20 + 30) * poor + (40 + 50 + 60 + 70) * good
				+ (80 + 90 + 100) * great;

		double below = poor * 3 + good * 4 + great * 3;

		double COG = above / below;
		System.out.println(COG);
	}
}
