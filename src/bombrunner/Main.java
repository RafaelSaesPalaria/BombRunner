package bombrunner;

public class Main {

	//Fields
	private static MouseL mouse;
	private static Screen screen;
	private static Level level;
	
	//Constructor
	public static void main(String[] args) {
		start();
	}
	
	//Methods
	public static void start() {
		screen = Screen.getInstance();
		level = Level.getInstance();
		mouse = new MouseL();
		level.setMouse(mouse);
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
	public static void setLevel(Level level) {
		Main.level = level;
	}
}
