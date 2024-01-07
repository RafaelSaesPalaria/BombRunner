package minesweeper;

import javax.swing.JFrame;

public class Screen extends JFrame{

	//Constructor
	public Screen() {
		updateTitle();
		setLocation(200, 200);
		setSize(574,600);
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	//Methods
	public void updateTitle() {
		if (Timer.getGameTime()<0) {
			setTitle("BombRunner - [Start Game]");
		} else {
			setTitle("BombRunner - "+(Main.getLevel().getFlagsLeft())+" Bombs left. "+Timer.getGameTime()+" Seconds");
		}
	}
	
}
