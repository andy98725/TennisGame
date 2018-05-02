package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;

import game.Game;

public class Application extends JPanel implements Runnable {
	// Serial ID
	private static final long serialVersionUID = 1L;
	// Application instance in use
	public static Application app;
	// Best rally done
	public static int bestRally = 0;
	// Display size
	public static final int wid = 800, hei = 400;
	// Thread used
	private Thread mainLoop;
	// Framerate
	public static final double frameRate = 120;
	// Delta time
	public static double delta = 0;

	public static void main(String[] args) {
		new Application();
	}

	private Application() {
		//Set application
		app = this;
		// Set thread
		mainLoop = new Thread(this);
		// Create window
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				initWindow();
			}
		});
	}

	// Make container window
	private void initWindow() {
		// Set size
		setPreferredSize(new Dimension(wid, hei));
		// Improves rendering
		setDoubleBuffered(true);
		// Default bgcolor
		setBackground(Color.BLACK);
		// Make window
		JFrame window = new JFrame();
		// Add app
		window.add(this);
		// Add key listener
		window.addKeyListener(new KeyInput());
		// Not resizable
		window.setResizable(false);
		// Window header
		window.setTitle("Tennis");
		// Close app on close
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Pack dimensions
		window.pack();
		// Center
		window.setLocationRelativeTo(null);
		// Display
		window.setVisible(true);
	}

	// On window run, start rendering
	@Override
	public void addNotify() {
		super.addNotify();
		mainLoop.start();
	}

	private static Menu menu;
	private static Game game;
	// Open menu
	public static void openMenu() {
		game = null;
		menu = new Menu();
		app.removeAll();
		app.add(menu);
	}
	// Start game
	public static void startGame() {
		menu = null;
		game = new Game();
		app.removeAll();
	}
	// Render
	@Override
	protected void paintComponent(Graphics graphics) {
		// Graphics2D for increased access to functions
		Graphics2D g = (Graphics2D) graphics;
		// Anti aliasing
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// Draw background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, wid, hei);
		// Draw menu
		if(menu != null) menu.draw(g);
		// Draw game
		else if(game != null) Game.draw(g);
	}

	// Main loop
	@Override
	public void run() {
		// Declare timing variables
		long prevFrame = System.nanoTime(), sleep = 0;
		double deltaH = 0;
		// Start
		openMenu();
		// Start loop
		while (true) {
			// Nanoseconds to seconds
			delta = (System.nanoTime() - prevFrame) / 2000000000.0 + deltaH;
			deltaH = (System.nanoTime() - prevFrame) / 2000000000.0;
			// Update frame timing
			prevFrame = System.nanoTime();
			// Do logic
			if(game != null) Game.logic();
			// Repaint
			repaint();
			// Calculate pause time (in MS)
			sleep = (long) (1000.0 / frameRate) - (System.nanoTime() - prevFrame) / 1000000;
			if (sleep < 0)
				sleep = 0;
			// Delay frame
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				System.out.println("Interrupted: " + e.getMessage());
			}

		}

	}
	public static void pressButton(int button) {
		if(game != null) {
			switch(button) {
			default: break;
			case KeyInput.left:
				Game.pressLeftBumper();
				break;
			case KeyInput.right:
				Game.pressRightBumper();
				break;
			}
		}
	}

}
