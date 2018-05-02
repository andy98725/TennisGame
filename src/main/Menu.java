package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

import util.TextFunctions;

public class Menu extends JComponent implements MouseListener {
	private static final long serialVersionUID = 1L;

	// Font and border data
	final Font titlefont = new Font("Calibri", Font.BOLD, 64);
	final Font buttonfont = new Font("Calibri", Font.PLAIN, 24);
	final Stroke borderStroke = new BasicStroke(3.0f);
	// Title data
	final Point titlePoint = new Point(Application.wid / 2, Application.hei / 6);
	final String titleText = "Game of Tennis";
	// Best rally data
	final Point rallyPoint = new Point(Application.wid / 2, Application.hei * 3 / 4);
	final String rallyText = "Best Rally: ";
	// Buttons data
	final int btnwid = Application.wid / 4;
	final int btnhei = Application.hei / 4;
	// Play button data
	final Rectangle playHitbox = new Rectangle(Application.wid / 4 - btnwid/2, Application.hei / 2 - btnhei/2,btnwid,
			btnhei);
	final Color playColor = new Color(255, 191, 127);
	final Color playColorBor = new Color(255, 127, 0);
	final String playText = "Start Game";
	// Quit button data
	final Rectangle quitHitbox = new Rectangle(Application.wid * 3/4 - btnwid/2, Application.hei / 2 - btnhei/2, btnwid,
			btnhei);
	final Color quitColor = new Color(127, 191, 255);
	final Color quitColorBor = new Color(0, 127, 255);
	final String quitText = "Quit";

	public Menu() {
		// Add listener
		Application.app.addMouseListener(this);
	}

	public void draw(Graphics2D g) {
		// Save presets
		Stroke oldS = g.getStroke();
		g.setColor(Color.WHITE);
		// Set stroke
		g.setStroke(borderStroke);
		// Draw title
		g.setFont(titlefont);
		TextFunctions.drawCenteredText(g, titleText, titlePoint.x, titlePoint.y);
		// Draw best rally
		g.setFont(buttonfont);
		TextFunctions.drawCenteredText(g, rallyText + Integer.toString(Application.bestRally), rallyPoint.x, rallyPoint.y);
		// Draw play button
		drawButton(g, playHitbox, playText, playColor, playColorBor);
		// Draw quit button
		drawButton(g, quitHitbox, quitText, quitColor, quitColorBor);

		// Restore presets
		g.setStroke(oldS);
	}
	private void drawButton(Graphics2D g, Rectangle bounds, String text, Color bg, Color bor) {
		// Draw background
		g.setColor(bg);
		g.fill(bounds);
		// Draw border
		g.setColor(bor);
		g.draw(bounds);
		// Draw text
		TextFunctions.drawOutlinedText(g, bounds.x+bounds.width/2, bounds.y+bounds.height/2, text, Color.WHITE);
		}

	// Button clicks
	@Override
	public void mouseClicked(MouseEvent e) {
		if (playHitbox.contains(e.getPoint())) {
			Application.startGame();
		}
		if (quitHitbox.contains(e.getPoint())) {
			System.exit(0);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

}
