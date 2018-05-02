package util;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

// Utility functions
public abstract class TextFunctions {

	public static void drawCenteredText(Graphics2D g, String text, int x, int y) {
		FontMetrics metrics = g.getFontMetrics();
		int tlx = x - metrics.stringWidth(text) / 2;
		int tly = y - metrics.getHeight() / 2 + metrics.getAscent();
		g.drawString(text, tlx, tly);
	}
	public static void drawOutlinedText(Graphics2D g, int x, int y, String text, Color inside) {
		// Outside
		g.setColor(Color.BLACK);
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				drawCenteredText(g,text,x+i,y+j);
			}
		}
		// Inside
		g.setColor(inside);
		drawCenteredText(g,text,x,y);
	}
}
