package fuzzymice;

import java.util.ArrayList;

import javax.swing.JFrame;

public class Main extends JFrame {

	private static int frameSize = 600;
	private int numberOfMice = 4;
	private static AnimationPanel animationPanel;

	public Main() {
		initialize();
	}

	public static void main(String[] args) {
		new Main();
		loop();
	}

	private static void loop() {
		while (true) {
			animationPanel.tick();
			ArrayList<Mouse> mice = animationPanel.getMice();
			for (int i = 0; i < mice.size(); i++) {
				Mouse mouse = mice.get(i);
				ArrayList<Mouse> collidingMice = animationPanel
						.getCollisions(mouse);
				for (int j = 0; j < collidingMice.size(); j++) {
					Mouse mouse2 = collidingMice.get(j);
					if (!mouse2.equals(mouse)) {
						mouse.setState(State.FIGHT);
						mouse.setVelocity(new Velocity(0, mouse.getVelocity()
								.getDirection()));
						mouse2.setState(State.FIGHT);
						mouse2.setVelocity(new Velocity(0, mouse.getVelocity()
								.getDirection()));
						fight(mouse, mouse2);
					}
				}
				if (mouse.getHealth() < 0.0 || mouse.getX() < -25
						|| mouse.getX() > frameSize + 25)
					mice.remove(mouse);
			}
			// animationPanel.toConsole();
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (mice.size() == 1) {
				System.out.println("We have a winner!");
				break;
			}
		}
	}

	private void initialize() {
		this.setSize(frameSize, frameSize);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Main.animationPanel = new AnimationPanel(frameSize, frameSize);

		for (int i = 0; i < numberOfMice; i++) {
			Brain brain = new Brain();
			Mouse m;
			if (i % 2 == 0)
				m = new Mouse(Math.random() * frameSize, frameSize * 0.5,
						new Velocity(2, 1), brain);
			else
				m = new Mouse(Math.random() * frameSize, frameSize * 0.5,
						new Velocity(2, -1), brain);
			animationPanel.addMouse(m);
		}
		this.add(animationPanel);
		this.setVisible(true);
	}

	private static void fight(Mouse one, Mouse two) {
		System.out.println("FIGHT!");
		if (one.getRating() * Math.random() > two.getRating() * Math.random())
			two.damage(Math.random() * one.getRating() * 0.5);
		else
			one.damage(Math.random() * two.getRating() * 0.5);
	}

}
