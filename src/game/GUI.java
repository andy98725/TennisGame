package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import main.Application;
import util.TextFunctions;

public class GUI {
	private int topY, botY;
	// Draw the helper text?
	private boolean drawRight, drawLeft;
	// The helper text
	private String textRight, textLeft;
	// Rally font
	Font rallyfont = new Font("Calibri", Font.BOLD, 24);
	Font scorefont = new Font("CAlibri", Font.PLAIN, 18);
	// Indicator colors
	private Color[] indicatorColor;
	private double[] indicatorTimer;
	public GUI(String l, String r) {
		// Settings
		topY = 20;
		botY = Application.hei*15/16+1;
		drawLeft = true;
		textLeft = l;
		drawRight = true;
		textRight = r;
		indicatorColor = new Color[2];
		indicatorTimer = new double[2];
		
	}
	public void draw(Graphics2D g) {
		// Settings
		g.setColor(Color.WHITE);
		// Draw texts sometimes
		drawHelperTexts(g);
		// Draw current scores
		drawScores(g);
		// Draw indicators
		drawIndicators(g);
	}
	public void logic() {
		for(int i = 0; i < 2; i++) {
		if(indicatorColor[i] != null) {
			indicatorTimer[i] -= Application.delta;
			if(indicatorTimer[i] <= 0) {
				indicatorColor[i] = null;
			}
		}
		}
	}
	public void pressed(boolean right) {
		if(right) drawRight = false;
		else drawLeft = false;
	}
	public void setIndicatorColor(int dir, Color col) {
		if(dir == Bracket.B_LEFT) {
			indicatorColor[0] = col;
			indicatorTimer[0] = 1;
		}
		else {
			indicatorColor[1] = col;
			indicatorTimer[1] = 1;
		}
	}
	private void drawHelperTexts(Graphics2D g) {
		g.setFont(rallyfont);
		if(drawLeft) {
			TextFunctions.drawCenteredText(g, textLeft, Application.wid/4, Application.hei/2);
		}
		if(drawRight) {
			TextFunctions.drawCenteredText(g, textRight, Application.wid*3/4, Application.hei/2);
		}
		
	}
	private void drawScores(Graphics2D g) {
		// Draw current bounce count
		g.setFont(rallyfont);
		TextFunctions.drawCenteredText(g, Integer.toString(Game.getBounces()), Application.wid/2, topY);
		// Draw current scores
		g.setFont(scorefont);
		TextFunctions.drawCenteredText(g, Integer.toString(Game.score[0]), Application.wid/4+20, topY);
		TextFunctions.drawCenteredText(g, Integer.toString(Game.score[1]), Application.wid*3/4-20, topY);
	}
	private void drawIndicators(Graphics2D g) {
		int w = Application.wid/4, h = Application.hei/8-2;
		if(indicatorColor[0] != null) { // Left side
			int x1 = 20;
			g.setColor(indicatorColor[0]);
			g.fillRect(x1, botY-h/2, w, h);
		}
		if(indicatorColor[1] != null) {
			int x2 = Application.wid - 20 - w;
			g.setColor(indicatorColor[1]);
			g.fillRect(x2, botY-h/2, w, h);
		}
	}
	

}
