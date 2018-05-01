package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {
	// Input bindings
	public static final int left = KeyEvent.VK_A, right = KeyEvent.VK_L;
	@Override
	public void keyPressed(KeyEvent arg0) {
		switch(arg0.getExtendedKeyCode()) {
		default: // Press button in application
			Application.pressButton(arg0.getExtendedKeyCode());
			break;
			// Escape opens menu
		case KeyEvent.VK_ESCAPE:
			Application.openMenu();
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
