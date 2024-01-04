package minesweeper;

import java.awt.Rectangle;

public class Main {

	//Fields
	private static Screen screen;
	private static Level level;
	
	//Constructor
	public static void main(String[] args) {
		start();
	}
	
	//Methods
	public static void start() {
		screen = new Screen();
		level = new Level();
		screen.add(level);
	}
	
	public static void restart() {
		Rectangle bounds = screen.getBounds();
		screen.dispose();
		
		screen = new Screen();
		screen.setBounds(bounds);
		level = new Level();
		screen.add(level);
	}
	
	//IO
	public static Level getLevel() {
		return level;
	}
	
	public static Screen getScreen() {
		return screen;
	}
	
	public static void setLevel(Level level) {
		Main.level = level;
	}
}
