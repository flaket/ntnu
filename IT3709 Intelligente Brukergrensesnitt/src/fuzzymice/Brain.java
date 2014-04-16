package fuzzymice;

import sets.*;

public class Brain {

	// Linguistic variables:
	ReverseGrade close, wimp, poor;
	Triangle nearby, good;
	Trapezoid solid;
	Grade far, mufasa, great;
	//
	ReverseGrade flee;
	Triangle wait;
	Grade attack;
	//
	double attackValue, waitValue, fleeValue;
	int aCount, wCount, fCount;

	public Brain() {
		close = new ReverseGrade(new Point(0.0, 1.0), new Point(50.0, 0.0));
		nearby = new Triangle(new Point(20.0, 0.0), new Point(50.0, 1.0),
				new Point(80.0, 0.0));
		far = new Grade(new Point(70.0, 0.0), new Point(100.0, 1.0));
		wimp = new ReverseGrade(new Point(0.0, 1.0), new Point(50.0, 0.0));
		solid = new Trapezoid(new Point(20.0, 0.0), new Point(40.0, 1.0),
				new Point(60.0, 1.0), new Point(80.0, 0.0));
		mufasa = new Grade(new Point(70.0, 0.0), new Point(100.0, 1.0));
		poor = new ReverseGrade(new Point(0.0, 1.0), new Point(50.0, 0.0));
		good = new Triangle(new Point(20.0, 0.0), new Point(50.0, 1.0),
				new Point(80.0, 0.0));
		great = new Grade(new Point(75.0, 0.0), new Point(100.0, 1.0));

		flee = new ReverseGrade(new Point(0.0, 1.0), new Point(0.5, 0.0));
		wait = new Triangle(new Point(0.3, 0.0), new Point(0.5, 1.0),
				new Point(0.7, 0.0));
		attack = new Grade(new Point(0.5, 0.0), new Point(1.0, 1.0));
	}

	public double reason(boolean sugeno, double d, Mouse myself,
			Mouse closestMouse) {
		if (closestMouse == null)
			return 0.5;
		else
			return (mamdadiEvaluation(sugeno, d, myself.getHealth(),
					closestMouse.getRating()));
	}

	public double mamdadiEvaluation(boolean sugeno, double d, double h, double r) {
		attackValue = 0.0;
		waitValue = 0.0;
		fleeValue = 0.0;
		aCount = 0;
		wCount = 0;
		fCount = 0;
		// 1. Fuzzification: determine how much each crisp input belong to the
		// proper set.
		// Distance
		double dClose = close.withinSet(d);
		double dNear = nearby.withinSet(d);
		double dFar = far.withinSet(d);
		// Health
		double hPoor = poor.withinSet(h);
		double hGood = good.withinSet(h);
		double hGreat = great.withinSet(h);
		// Rating
		double rWimp = wimp.withinSet(r);
		double rSolid = solid.withinSet(r);
		double rMufasa = mufasa.withinSet(r);

		// 2. Rule evaluation and 3. Aggregation of rule outputs
		// // if (health is good) AND (rate is solid OR wimp) then action is
		// // ATTACK.
		attackValue = Math.min(hGood, Math.max(rSolid, rWimp));
		if (attackValue > 0.0)
			aCount++;
		// // if (health is great) AND (rate is not mufasa) then action is
		// ATTACK.
		attackValue = Math.min(hGreat, 1 - rMufasa);
		if (attackValue > 0.0)
			aCount++;
		// // if (health is slightly poor) AND (rate is solid OR mufasa) then
		// action is
		// // FLEE.
		fleeValue = Math.min(hedgeSlightly(hPoor), Math.max(rSolid, rMufasa));
		if (fleeValue > 0.0)
			fCount++;
		// // if (rating is mufasa AND distance is near) OR (rating is solid AND
		// // distance is close) then action is FLEE.
		fleeValue = Math
				.max(Math.min(rMufasa, dNear), Math.min(rSolid, dClose));
		if (fleeValue > 0.0)
			fCount++;
		// // if (health is good) AND (rate is solid OR mufasa) then action is
		// // WAIT.
		waitValue = Math.min(hGood, Math.max(rSolid, rMufasa));
		if (waitValue > 0.0)
			wCount++;
		// // if (distance is very far) AND (health is good OR poor) then action
		// is
		// // WAIT.
		waitValue = Math.max(hedgeVery(dFar), Math.min(hGood, hPoor));
		if (waitValue > 0.0)
			wCount++;

		// Averaging the values contributing to each action.
		if (attackValue != 0.0 && aCount != 0)
			attackValue = attackValue / aCount;
		if (waitValue != 0.0 && wCount != 0)
			waitValue = waitValue / wCount;
		if (fleeValue != 0.0 && fCount != 0)
			fleeValue = fleeValue / fCount;

		if (sugeno == true) {
			double numerator = fleeValue * 0.2 + waitValue * 0.5 + attackValue
					* 0.8;
			double denominator = fleeValue + waitValue + attackValue;
			double COG = numerator / denominator;
			if (Double.isNaN(COG))
				return -1.0;
			else
				return COG;
		} else {
			// Finding center of gravity
			double numerator = (0.1 + 0.2 + 0.3) * fleeValue
					+ (0.4 + 0.5 + 0.6 + 0.7) * waitValue + (0.8 + 0.9 + 1.0)
					* attackValue;
			double denominator = 3 * fleeValue + 4 * waitValue + 3
					* attackValue;
			double COG = numerator / denominator;
			System.out.println("COG: " + COG);
			if (Double.isNaN(COG))
				return -1.0;
			else
				return COG;
		}
	}

	// Hedge functions:
	public double hedgeVery(double d) {
		return Math.pow(d, 2);
	}

	public double hedgeExtremely(double d) {
		return Math.pow(d, 3);
	}

	public double hedgeALittle(double d) {
		return Math.pow(d, 1.3);
	}

	public double hedgeSlightly(double d) {
		return Math.pow(d, 1.7);
	}

	public double hedgeSomewhat(double d) {
		return Math.sqrt(d);
	}
}
