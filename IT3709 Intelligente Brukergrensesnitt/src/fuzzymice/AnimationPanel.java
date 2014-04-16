package fuzzymice;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class AnimationPanel extends JPanel {

	ArrayList<Mouse> mice;
	ArrayList<Mouse> collisions;
	public double d = 100.0;

	public AnimationPanel(int width, int heigth) {
		this.setSize(width, heigth);
		mice = new ArrayList<Mouse>();
		collisions = new ArrayList<Mouse>();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		BufferedImage bImage = new BufferedImage(this.getWidth(),
				this.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics bg = bImage.getGraphics();
		bg.setColor(Color.WHITE);
		bg.fillRect(0, 0, bImage.getWidth(), bImage.getHeight());
		bg.dispose();
		g.drawImage(bImage, 0, 0, this);
		for (Mouse mouse : mice) {
			mouse.draw(g);
		}
	}

	public void tick() {
		for (Mouse mouse : mice) {
			mouse.tick(this);
		}
		repaint();
	}

	public void addMouse(Mouse mouse) {
		mice.add(mouse);
	}

	public ArrayList<Mouse> getCollisions(Mouse mouse) {
		collisions.clear();
		for (Mouse mouse2 : mice) {
			if (calculateDistance(mouse, mouse2) < 25)
				collisions.add(mouse2);
		}
		return collisions;
	}

	public double calculateDistance(Mouse mouse, Mouse mouse2) {
		double xDist = Math.abs(mouse.getX() - mouse2.getX());
		double yDist = Math.abs(mouse.getY() - mouse2.getY());
		return Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));
	}

	public ArrayList<Mouse> getMice() {
		return mice;
	}

	public void toConsole() {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < mice.size(); i++) {
			Mouse mouse = mice.get(i);
			str.append("\nHealth:  " + Math.round(mouse.getHealth())
					+ " X-Position: " + Math.round(mouse.getX())
					+ " Y-Position: " + Math.round(mouse.getY()));
		}
		System.out.println(str);
	}

	public double getMouseView() {
		return d;
	}
}
