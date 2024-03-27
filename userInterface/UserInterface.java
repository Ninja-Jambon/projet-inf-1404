package userInterface;

import universe.*;

public class UserInterface extends Thread {
	private Window window;

	public UserInterface(Universe universe) {
		window = new Window(universe);
	}

	public void run() {
	}
}