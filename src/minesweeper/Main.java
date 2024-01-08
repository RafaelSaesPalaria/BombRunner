package minesweeper;

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
		level = Level.getInstance();
		screen.add(level);
	}
	
	public static void restart() {
		screen.remove(level);
		Level.reset();
		level = Level.getInstance();
		screen.add(level);
		screen.repaint();
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
