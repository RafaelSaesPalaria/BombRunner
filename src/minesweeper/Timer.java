package minesweeper;

public class Timer{

	//Fields
	private static int gameTime = -1;
	private static boolean running = true;
	
	//Methods
	public static void reset() {
		gameTime = -1;
		running = true;
	}
	
	public static void stop() {
		running = false;
	}
	
	//Inhred Methods
	public static void start() {
		new Thread() {
			public void run() {
				while (running) {
					Screen.getInstance().updateTitle();
					gameTime+=1;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
	//IO
	public static int getGameTime() {
		return gameTime;
	}

}
