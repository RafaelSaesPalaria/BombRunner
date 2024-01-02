package minesweeper;

import java.awt.Color;
import java.awt.Container;

public class Level extends Container {

	//Fields
	private static Block[][] levelArray = new Block[8][8];
	private MouseL mouse = new MouseL();
	
	//Constructor
	public Level() {
		setLocation(0,0);
		setSize(Main.getScreen().getContentPane().getSize());
		setLayout(null);
		setBackground(Color.yellow);
		Main.getScreen().add(this);
		createLevelArray();
	}
	
	//Methods
	public void createLevelArray() {
		for (int y=0;y < levelArray.length;y++) {
			for (int x=0;x < levelArray[y].length;x++) {
				levelArray[x][y] = new Block(x,y,Math.random()<0.2);
				levelArray[x][y].addMouseListener(mouse);
				add(levelArray[x][y]);
				repaint();
			}
		}
	}
	
	//IO
	public static Block[][] getLevelArray() {
		return levelArray;
	}
	
}
