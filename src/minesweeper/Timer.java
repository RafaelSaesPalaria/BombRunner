package minesweeper;

public class Timer{

	//Fields
	private static int gameTime;
	
	//Methods
	public static void resetGameTime() {
		gameTime = 0;
	}
	
	//Inhred Methods
	public static void start() {
		new Thread() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Main.getScreen().updateTitle();
					gameTime+=1;
				}
			}
		}.start();
	}
	
	//IO
	public static int getGameTime() {
		return gameTime;
	}

}
