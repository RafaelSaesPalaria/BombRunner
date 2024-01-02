package minesweeper;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class Block extends JPanel {

	//Fields
	public static final Dimension blockSize = new Dimension(70,70);
	
	//Constructor
	public Block(int x, int y, Color color) {
		int[] coords = getGridLocation(x, y);
		setLocation(coords[0], coords[1]);
		setSize(blockSize);
		setLayout(null);
		setBackground(color);
		setBorder(BorderFactory.createLineBorder(Color.black, 1));
		System.out.println(this);
	}
	
	public int[] getGridLocation(int x, int y) {
		int[] coords = {x*blockSize.width,y*blockSize.height};
		return coords;
	}
	
}
