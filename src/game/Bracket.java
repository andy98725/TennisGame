package game;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;

import main.Application;

public class Bracket {

	public static final int B_LEFT = 1, B_RIGHT = -1;
	public static final int maxWid = Application.wid/4; 
	// x is physical location. Dir is the direction it faces.
	int x, dir;
	// Bracket display and timing data
	Color[] bracketColor = { Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.RED, new Color(191,0,0), new Color(63,0,127), new Color(31,0,31)};
	double[] bracketTiming = { 1.2, 0.6, 0.3, 0.15, 0.075, 0.0375, 0.01875, 0.009375 };
	// Pixel width of each bracket
	int[] bracketWid;
	// Cooldown timer on press
	double cooldown = 0;

	public Bracket(int o) {
		dir = o;
		switch(dir) {
		default:
		case B_LEFT:
			x = 20;
			break;
		case B_RIGHT:
			x = Application.wid-20;
			break;
		}
		updateBrackets();
	}

	public void updateBrackets() {
		// Update bracket width off timing
		bracketWid = new int[bracketTiming.length];
		double speed = Game.ball.getSpeed();
		for (int i = 0; i < bracketWid.length; i++) {
			bracketWid[i] = (int) Math.round(bracketTiming[i] * speed);
			bracketWid[i] = Math.min(bracketWid[i], maxWid);
		}
	}

	public void doBounce() {
		// Check cooldown
		if(cooldown > 0) return;
		//Is ball in range?
		int balpos = (int) Math.round(Game.ball.getX());
		// Make sure it's in the right direction
		if(Game.ball.getDir() == -dir)
		// Check each bracket in reverse order
		for (int i = bracketWid.length-1; i >= 0; i--) {
			// Calculate min and max range
			int min = Math.min(x, x + dir*bracketWid[i]);
			int max = Math.max(x, x + dir*bracketWid[i]);
			// If in range
			if(min < balpos && balpos < max) {
				// Hit! Do things.
				// Calculate scale
				double scale = ((double) i+1)/bracketWid.length;
				// Bounce ball
				Game.ball.reverseDir(scale);
				// Increment counter
				Game.bounces++;
				// Display color
				Game.display.setIndicatorColor(dir, bracketColor[i]);
				// Update size
				Game.updateBumpers();
				return;
			}
		}
		// Missed
		cooldown = 1;
	}

	public void logic() {
		if(cooldown > 0) cooldown -= Application.delta;
	}

	public void draw(Graphics2D g) {
		// Save old clip
		Shape oldClip = g.getClip();
		// Set clip to half of the screen
		g.setClip(new Rectangle((dir == 1)? 0 : Application.wid/2, 0, Application.wid/2, Application.hei));
		//Save old composite
		Composite oldC = g.getComposite();
		//Set to half alpha if cooldown active
		Composite comp;
		if(cooldown > 0)
		comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
		else comp = oldC;
		// Set transparency
		g.setComposite(comp);
		int y = Application.hei/8, h = Application.hei*3/4;
		// Draw brackets
		for(int i = 0; i < bracketColor.length; i++) {
			// Draw specific rectangle
			g.setColor(bracketColor[i]);
			g.fillRect((dir == 1)? x : (x - bracketWid[i]), y, bracketWid[i], h);
		}
		// Draw border
		// Save current stroke
		Stroke oldS = g.getStroke();
		// Update with current draw
		g.setColor(Color.WHITE);
		g.setStroke(new BasicStroke(3.0f));
		// Draw base
		g.drawLine(x, y, x, y+h);
		// Draw bottom and top
		int x1 = (dir == 1)? x : (x - 2 * bracketWid[0]);
		int x2 = (dir == 1)? (x + 2 * bracketWid[0]) : x;
		g.drawLine(x1, y, x2, y);
		g.drawLine(x1, y + h, x2, y + h);
		// Restore settings
		g.setStroke(oldS);
		g.setComposite(oldC);
		g.setClip(oldClip);

	}

}
