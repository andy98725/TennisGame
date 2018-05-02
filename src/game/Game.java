package game;

import java.awt.Graphics2D;

import main.Application;

public class Game {
	public static Bracket leftBracket, rightBracket;
	public static Ball ball;
	public static GUI display;
	private static int bounces;
	public static int score[];
	public Game() {
		// Initiate game
		display = new GUI("A","L");
		ball = new Ball();
		leftBracket = new Bracket(Bracket.B_LEFT);
		rightBracket = new Bracket(Bracket.B_RIGHT);
		score = new int[2];
		score[0] = 0;
		score[1] = 0;
		bounces = 0;
	}

	public static void logic() {
		// Move ball
		ball.logic();
		// Cooldown brackets
		leftBracket.logic();
		rightBracket.logic();
		// Cooldown display
		display.logic();
	}

	public static void draw(Graphics2D g) {
		// Draw brackets
		leftBracket.draw(g);
		rightBracket.draw(g);
		// Draw ball
		ball.draw(g);
		// Draw GUI
		display.draw(g);
		
	}
	public static void pressLeftBumper() {
		leftBracket.doBounce();
		// Update GUI
		display.pressed(false);
	}
	public static void pressRightBumper() {
		rightBracket.doBounce();
		// Update GUI
		display.pressed(true);
	}
	public static void updateBumpers() {
		leftBracket.updateBrackets();
		rightBracket.updateBrackets();
	}
	public static void declareWinner(boolean right) {
		// Allocate winnings
		if(right) {
			score[0] += bounces;
		}
		else {
			score[1] += bounces;
		}
		// Reset count
		bounces = 0;
		// Update bumpers
		updateBumpers();
	}
	public static void incBounces() {
		bounces++;
		if(bounces > Application.bestRally)
			Application.bestRally = bounces;
	}
	public static int getBounces() {
		return bounces;
	}
}
