package minesweeper;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JPanel;

public class Level extends Container {

	//Fields
	private static Block[][] levelArray = new Block[10][10];
	
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
				int[] coords = setCoordinatesOnGrid(x, y);
				levelArray[x][y] = new Block(x,y,Color.red);
				add(levelArray[x][y]);
				repaint();
			}
		}
	}
	
	public int[] setCoordinatesOnGrid(int xGrid, int yGrid) {
		int[] blockSize = getBlockSize();
		int[] coordinates = {blockSize[0]*xGrid,blockSize[1]*yGrid};
		return coordinates;
	}
	
	public int[] getBlockSize() {
		int x = (int) getSize().getWidth()/Main.getLevel().getLevelArray()[0].length;
		int y = (int) getSize().getHeight()/Main.getLevel().getLevelArray()[1].length;
		int[] coordinates = {x,y};
		return coordinates;
	}
	
	//IO
	public static Block[][] getLevelArray() {
		return levelArray;
	}
	
}
