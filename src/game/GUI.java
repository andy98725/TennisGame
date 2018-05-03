package game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
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
	private int[] scoreDisplayValue;
	private double[] scoreDisplayTimer;

	public GUI(String l, String r) {
		// Settings
		topY = 20;
		botY = Application.hei * 15 / 16 + 1;
		drawLeft = true;
		textLeft = l;
		drawRight = true;
		textRight = r;
		indicatorColor = new Color[2];
		indicatorTimer = new double[2];
		scoreDisplayValue = new int[2];
		scoreDisplayTimer = new double[2];
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
		// Draw score display
		drawScoreDisplays(g);
	}

	public void logic() {
		for (int i = 0; i < 2; i++) {
			if (indicatorColor[i] != null) {
				indicatorTimer[i] -= Application.delta;
				if (indicatorTimer[i] <= 0) {
					indicatorColor[i] = null;
				}
			}
		}
		for (int i = 0; i < 2; i++) {
			if (scoreDisplayValue[i] != 0) {
				scoreDisplayTimer[i] -= Application.delta;
				if (scoreDisplayTimer[i] <= 0) {
					scoreDisplayValue[i] = 0;
				}
			}
		}
	}

	public void pressed(boolean right) {
		if (right)
			drawRight = false;
		else
			drawLeft = false;
	}

	public void setIndicatorColor(int dir, Color col) {
		if (dir == Bracket.B_LEFT) {
			indicatorColor[0] = col;
			indicatorTimer[0] = 1;
		} else {
			indicatorColor[1] = col;
			indicatorTimer[1] = 1;
		}
	}

	public void setScoreDisplay(int dir, int val) {
		if (dir == Bracket.B_LEFT) {
			scoreDisplayValue[0] = val;
			scoreDisplayTimer[0] = 1;
		} else {
			scoreDisplayValue[1] = val;
			scoreDisplayTimer[1] = 1;
		}

	}

	private void drawHelperTexts(Graphics2D g) {
		g.setFont(rallyfont);
		if (drawLeft) {
			TextFunctions.drawOutlinedText(g, textLeft, Application.wid / 4, Application.hei / 2, Color.WHITE);
		}
		if (drawRight) {
			TextFunctions.drawOutlinedText(g, textRight, Application.wid * 3 / 4, Application.hei / 2, Color.WHITE);
		}

	}

	private void drawScores(Graphics2D g) {
		// Draw current bounce count
		g.setFont(rallyfont);
		TextFunctions.drawOutlinedText(g, Integer.toString(Game.getBounces()), Application.wid / 2, topY, Color.WHITE, Color.DARK_GRAY);
		// Draw current scores
		g.setFont(scorefont);
		TextFunctions.drawOutlinedText(g, Integer.toString(Game.score[0]), Application.wid / 4 + 20, topY, Color.WHITE);
		TextFunctions.drawOutlinedText(g, Integer.toString(Game.score[1]), Application.wid * 3 / 4 - 20, topY, Color.WHITE);
	}

	private void drawIndicators(Graphics2D g) {
		int w = Application.wid / 4, h = Application.hei / 8 - 2;
		if (indicatorColor[0] != null) { // Left side
			// Save old composite
			Composite oldC = g.getComposite();
			// Set to semi-transparent
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) indicatorTimer[0]));
			int x1 = 20;
			g.setColor(indicatorColor[0]);
			g.fillRect(x1, botY - h / 2, w, h);
			// Restore
			g.setComposite(oldC);
		}
		if (indicatorColor[1] != null) {
			// Save old composite
			Composite oldC = g.getComposite();
			// Set to semi-transparent
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) indicatorTimer[1]));
			int x2 = Application.wid - 20 - w;
			g.setColor(indicatorColor[1]);
			g.fillRect(x2, botY - h / 2, w, h);
			// Restore
			g.setComposite(oldC);
		}
	}

	private void drawScoreDisplays(Graphics2D g) {
		// Draw current scores
		g.setFont(scorefont);
		if (scoreDisplayValue[0] != 0) {
			// Save old composite
			Composite oldC = g.getComposite();
			// Set to semi-transparent
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) scoreDisplayTimer[0]));
			// Draw text
			TextFunctions.drawOutlinedText(g, '+' + Integer.toString(scoreDisplayValue[0]), Application.wid / 4 - 20,
					topY, Color.WHITE, Color.GRAY);
			// Restore
			g.setComposite(oldC);
		}
		if (scoreDisplayValue[1] != 0) {
			// Save old composite
			Composite oldC = g.getComposite();
			// Set to semi-transparent
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) scoreDisplayTimer[1]));
			// Draw text
			TextFunctions.drawOutlinedText(g, '+' + Integer.toString(scoreDisplayValue[1]),
					Application.wid * 3 / 4 + 20, topY, Color.WHITE, Color.GRAY);
			// Restore
			g.setComposite(oldC);
		}

	}

}
