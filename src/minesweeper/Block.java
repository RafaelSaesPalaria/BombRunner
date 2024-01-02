package minesweeper;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class Block extends JPanel {

	//Fields
	public static final Dimension blockSize = new Dimension(70,70);
	public static final int bomb = -2;
	public static final int dirt = -1;
	public static final int grass= 0;
	public static final int flag = 1;
	public static final int questionMark = 2;
	
	private boolean hasBomb;
	private int blockType = grass;
	
	
	//Constructor
	public Block(int x, int y, boolean hasBomb) {
		this.hasBomb = hasBomb;
		int[] coords = getGridLocation(x, y);
		setLocation(coords[0], coords[1]);
		setSize(blockSize);
		setLayout(null);
		setBackground(Color.green);
		setBorder(BorderFactory.createLineBorder(Color.black, 1));
		System.out.println(this);
	}
	
	//Methods
	public void leftClick() {
		if (hasBomb) {
			blockType=bomb;
		} else {
			blockType=dirt;
		}
		updateBackground();
	}
	
	public void rightClick() {
		if (blockType>=grass) {
			if (blockType<questionMark) {
				blockType+=1;
			} else {
				blockType=grass;
			}
		}
		updateBackground();
	}
	
	public void updateBackground() {
		switch (blockType) {
			case bomb:
				setBackground(Color.black);
				break;
			case dirt:
				setBackground(new Color(165,42,42));
				break;
			case flag:
				setBackground(Color.red);
				break;
			case questionMark:
				setBackground(Color.magenta);
				break;
			case grass:
			default:
				setBackground(Color.green);
				break;
		}
	}
	
	public int[] getGridLocation(int x, int y) {
		int[] coords = {x*blockSize.width,y*blockSize.height};
		return coords;
	}
	
}
