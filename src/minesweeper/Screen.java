package minesweeper;

import javax.swing.JFrame;

public class Screen extends JFrame{

	//Constructor
	public Screen() {
		setTitle("BombRunner - [Start Game]");
		setLocation(200, 200);
		setSize(574,600);
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	//Methods
	public void updateTitle() {
		Main.getScreen().setTitle("BombRunner - "+(Main.getLevel().getFlagsLeft())+" Bombs left. "+Timer.getGameTime()+" Seconds");
	}
	
}
