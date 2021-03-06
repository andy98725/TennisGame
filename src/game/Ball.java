package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

import main.Application;

public class Ball {
	// Starting speed
	public static final double initialSpeed = 192;
	// Rate of increase if hit best bracket
	public static final double expRate = 1.02;
	// Current speed (px/second)
	private double curSpeed;
	// Previous direction at start (-1, 1)
	private int prevDir;
	// Current direction (-1, 1)
	private int dir;
	// X position
	private double x;
	// Is in passed state?
	private boolean passed;
	// Cooldown on passed
	private double passedCooldown;
	
	// Create
	public Ball() {
		//Randomize starting dir
		prevDir = (Math.random() > 0.5)? 1 : -1;
		initDefaults();
	}
	private void initDefaults() {
		curSpeed = initialSpeed;
		dir = -prevDir;
		prevDir = dir;
		x = Application.wid/2;
		passed = false;
		passedCooldown = 0;
		
	}
	// Move ball
	public void logic() {
		// Check passed
		int rounded = (int) Math.round(x);
		if(rounded < Game.leftBracket.x || rounded > Game.rightBracket.x) {
			if(passed) {
				// Decrement cooldown timer
				passedCooldown -= Application.delta;
				// Check if time to end turn
				if(passedCooldown <= 0) {
					// Reset and call
					int bumper = dir;
					initDefaults();
					Game.declareWinner(bumper);
				}
			}
			else {
				// Just detected passed. Setup
				passed = true;
				passedCooldown = 1.5;
			}
			return;
		}
		// Move ball
		x += curSpeed * Application.delta * ((double) dir);
	}

	public void draw(Graphics2D g) {
		// Save current stroke
		Stroke old = g.getStroke();
		// Draw
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(7.0f));
		g.drawLine((int) Math.round(x), Application.hei/4, (int) Math.round(x), Application.hei*3/4);
		g.setColor(!passed? Color.WHITE : Color.RED);
		g.setStroke(new BasicStroke(3.0f));
		g.drawLine((int) Math.round(x), Application.hei/4, (int) Math.round(x), Application.hei*3/4);
		// Restore stroke
		g.setStroke(old);
	}
	public double getSpeed() {
		return curSpeed;
	}
	public double getX() {
		return x;
	}
	public int getDir() {
		return dir;
	}
	public void reverseDir(double factor) { //Rate of change
		double totScale = Math.pow(expRate, factor);
		curSpeed = Math.pow(curSpeed, totScale);
		dir *= -1;
		// Do check
		if(curSpeed/initialSpeed > Application.bestSpeed) {
			Application.bestSpeed = curSpeed/initialSpeed;
		}
	}
}
