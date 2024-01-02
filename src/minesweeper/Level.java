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
				if (validPosition(x, y)) {
					if (levelArray[x][y].hasBomb()) {
						bombCount+=1;
					}
				}
			}
		}
		return bombCount;
	}
	
	public void poolZeros(int xGrid, int yGrid) {
		checkZero(xGrid, yGrid-1);
		checkZero(xGrid, yGrid+1);
		checkZero(xGrid-1, yGrid);
		checkZero(xGrid+1, yGrid);
	}
	
	public void checkZero(int xGrid, int yGrid) {
		if (validPosition(xGrid, yGrid)) {
			if (validPosition(xGrid, yGrid) &
				levelArray[xGrid][yGrid].getBlockType()==Block.grass &
				countBombsAround(xGrid, yGrid)==0) {
				
				levelArray[xGrid][yGrid].leftClick();
				poolZeros(xGrid,yGrid);
			}
		}
	}
	
	//IO
	public boolean validPosition(int xGrid, int yGrid) {
		return ((xGrid>=0 & xGrid<levelArray[0].length) &
				(yGrid>=0 & yGrid<levelArray.length));
	}
	
	public static Block[][] getLevelArray() {
		return levelArray;
	}
	
}
