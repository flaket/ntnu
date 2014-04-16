package fuzzymice;

import sets.Grade;
import sets.Point;
import sets.ReverseGrade;
import sets.Trapezoid;
import sets.Triangle;

public class test {
	public static void main(String[] args) {
		double attackValue, waitValue, fleeValue;
		int aCount, wCount, fCount;
		ReverseGrade close, wimp, poor;
		Triangle nearby, good;
		Trapezoid solid;
		Grade far, mufasa, great;
		//
		ReverseGrade flee;
		Triangle wait;
		Grade attack;
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

		attackValue = 0.0;
		waitValue = 0.0;
		fleeValue = 0.0;
		aCount = 0;
		wCount = 0;
		fCount = 0;

		// inputs
		double d = 99;
		double h = 99;
		double r = 40;

		// Mamdani-evaluation
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
		// // if (health is poor) AND (rate is solid OR mufasa) then action is
		// // FLEE.
		fleeValue = Math.min(hPoor, Math.max(rSolid, rMufasa));
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
		// // if (distance is far) AND (health is good OR poor) then action is
		// // WAIT.
		waitValue = Math.max(dFar, Math.min(hGood, hPoor));
		if (waitValue > 0.0)
			wCount++;

		// Averaging the values contributing to each action.
		if (attackValue != 0.0 && aCount != 0)
			attackValue = attackValue / aCount;
		System.out.println(attackValue);
		if (waitValue != 0.0 && wCount != 0)
			waitValue = waitValue / wCount;
		System.out.println(waitValue);
		if (fleeValue != 0.0 && fCount != 0)
			fleeValue = fleeValue / fCount;
		System.out.println(fleeValue);

		// Finding center of gravity
		double numerator = (0.1 + 0.2 + 0.3) * fleeValue
				+ (0.4 + 0.5 + 0.6 + 0.7) * waitValue + (0.8 + 0.9 + 1.0)
				* attackValue;
		double denominator = 3 * fleeValue + 4 * waitValue + 3 * attackValue;
		double COG = numerator / denominator;
//		if (Double.isNaN(COG))
			// return some default
//		else 
			// return COG
		System.out.println(COG);
	}
}
