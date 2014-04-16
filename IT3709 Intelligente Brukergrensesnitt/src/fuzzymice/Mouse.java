package fuzzymice;

import java.awt.Color;
import java.awt.Graphics;

public class Mouse {

	private double health, x, y, rating;
	private Brain brain;
	private State state;
	private Velocity velocity;
	private int width = 25;
	private int heigth = 25;
	private int sprint = 10;

	public Mouse(double x, double y, Velocity v, Brain b) {
		setHealth(99.9);
		setX(x);
		setY(y);
		setVelocity(v);
		setBrain(b);
		setState(State.NORMAL);
		setRating(Math.random() * 100);
	}

	public void damage(double amount) {
		health -= amount;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double r) {
		this.rating = r;
	}

	/**
	 * Called on every animation frame.
	 */
	public void tick(AnimationPanel s) {
		// Turn mice around if they reach edge of screen area.
		if (getX() < 1.0)
			setVelocity(new Velocity(sprint * 0.5, 1));
		if (getX() > 574.0)
			setVelocity(new Velocity(sprint * 0.5, -1));
		Mouse closestMouse = null;
		double d = s.getMouseView();
		for (int i = 0; i < s.getMice().size(); i++) {
			if (s.getMice().get(i).equals(this))
				continue;
			else {
				if ((s.getMice().get(i).getX() > this.getX() && this
						.getVelocity().getDirection() > 0)
						|| s.getMice().get(i).getX() < this.getX()
						&& this.getVelocity().getDirection() < 0) {
					double temp = s.calculateDistance(this, s.getMice().get(i));
					if (temp < d) {
						d = temp;
						closestMouse = s.getMice().get(i);
					}
				}
			}
		}
		executeAction(getBrain().reason(false, d, this, closestMouse));
	}

	public void executeAction(double action) {
		if (action == -1.0) {
			System.out.println("No rules fired.");
			setState(State.NORMAL);
			setVelocity(new Velocity(5, getVelocity().getDirection()));
		} else if (action >= 0.5) {
			if (action > 0.55) {
				System.out.println("Mouse wants to ATTACK.");
				setState(State.ATTACK);
			} else
				setState(State.NORMAL);
			setVelocity(new Velocity(sprint * Math.pow(action, 2),
					getVelocity().getDirection()));
			setX(getX() + getVelocity().getMagnitude()
					* getVelocity().getDirection());
		} else {
			System.out.println("Mouse wants to FLEE.");
			setState(State.FLEE);
			// Run in opposite direction of other mouse.
			setVelocity(new Velocity(sprint * Math.pow(1 - action, 2), (-1)
					* (getVelocity().getDirection())));
			setX(getX() + getVelocity().getMagnitude()
					* getVelocity().getDirection());
		}
	}

	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		if (this.getHealth() > 75.0)
			g.setColor(Color.BLACK);
		else if (this.getHealth() > 50.0)
			g.setColor(Color.DARK_GRAY);
		else if (this.getHealth() > 0.0)
			g.setColor(Color.LIGHT_GRAY);
		if (this.getState() == State.FIGHT)
			g.setColor(Color.RED);
		g.fillOval((int) (getX()), (int) (getY()), width, heigth);
	}

	// ---Getters & Setters---

	public Brain getBrain() {
		return brain;
	}

	public void setBrain(Brain brain) {
		this.brain = brain;
	}

	public Velocity getVelocity() {
		return velocity;
	}

	public void setVelocity(Velocity velocity) {
		this.velocity = velocity;
	}

	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setState(State state) {
		this.state = state;
	}

	private State getState() {
		return state;
	}
}
