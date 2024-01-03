package minesweeper;

public class Main {

	//Fields
	private static Screen screen;
	private static Level level;
	
	//Constructor
	public static void main(String[] args) {
		screen = new Screen();
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
