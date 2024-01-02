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
	
	public int countBombsAround(int xGrid, int yGrid) {
		int bombCount = 0;
		for (int y=yGrid-1;y<=yGrid+1;y++) {
			for (int x=xGrid-1;x<=xGrid+1;x++) {
				if ((x>=0 & x<levelArray[0].length) &
					(y>=0 & y<levelArray.length)) {
					if (levelArray[x][y].hasBomb()) {
						bombCount+=1;
					}
				}
			}
		}
		return bombCount;
	}
	
	//IO
	public static Block[][] getLevelArray() {
		return levelArray;
	}
	
}
