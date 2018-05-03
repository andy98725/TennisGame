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

import game.Game;
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
	final int btnwid = Application.wid / 4 - 10;
	final int btnhei = Application.hei / 4;
	// Casual button data
	final Rectangle casualHitbox = new Rectangle(Application.wid / 4 - btnwid / 2, Application.hei / 2 - btnhei / 2,
			btnwid, btnhei);
	final Color casualColor = new Color(191, 255, 127);
	final Color casualColorBor = new Color(127, 255, 0);
	final String casualText = "Casual";
	// Play button data
	final Rectangle tournamentHitbox = new Rectangle(Application.wid / 2 - btnwid / 2, Application.hei / 2 - btnhei / 2,
			btnwid, btnhei);
	final Color tournamentColor = new Color(255, 127, 191);
	final Color tournamentColorBor = new Color(255, 0, 127);
	final String tournamentText = "Competitive";
	// Quit button data
	final Rectangle quitHitbox = new Rectangle(Application.wid * 3 / 4 - btnwid / 2, Application.hei / 2 - btnhei / 2,
			btnwid, btnhei);
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
		TextFunctions.drawOutlinedText(g, titleText, titlePoint.x, titlePoint.y, Color.WHITE, Color.DARK_GRAY);
		// Draw best rally
		g.setFont(buttonfont);
		TextFunctions.drawCenteredText(g, rallyText + Integer.toString(Application.bestRally), rallyPoint.x,
				rallyPoint.y);
		// Draw casual button
		drawButton(g, casualHitbox, casualText, casualColor, casualColorBor);
		// Draw tournament button
		drawButton(g, tournamentHitbox, tournamentText, tournamentColor, tournamentColorBor);
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
		TextFunctions.drawOutlinedText(g, text, bounds.x + bounds.width / 2, bounds.y + bounds.height / 2, Color.WHITE);
	}

	// Button clicks
	@Override
	public void mouseClicked(MouseEvent e) {
		if (casualHitbox.contains(e.getPoint())) {
			Application.startGame(Game.M_casual);
		}
		if (tournamentHitbox.contains(e.getPoint())) {
			Application.startGame(Game.M_tournament);
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
