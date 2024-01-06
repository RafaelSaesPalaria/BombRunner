package minesweeper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

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
	private JLabel jlabel = new JLabel();
	
	private Color[] Colors = {
			Color.red, Color.orange,
			Color.yellow, Color.green,
			Color.cyan,Color.BLUE,
			new Color(0,0,139), new Color(128,0,128)};
	
	//Constructor
	public Block(int x, int y) {
		this(x,y,false);
	}
	public Block(int x, int y, boolean hasBomb) {
		this.hasBomb = hasBomb;
		int[] coords = getLocationOnGrid(x, y);
		setLocation(coords[0], coords[1]);
		setSize(blockSize);
		setLayout(null);
		setBackground(Color.green);
		setBorder(BorderFactory.createLineBorder(Color.black, 1));
		
		jlabel.setSize(blockSize);
		jlabel.setHorizontalAlignment(SwingConstants.CENTER);
		jlabel.setFont(new Font("Arial",Font.BOLD,30));
		add(jlabel);
	}
	
	//Methods
	public void leftClick() {
		if (blockType==grass) {
			if (hasBomb) {
				blockType=bomb;
			} else {
				blockType=dirt;
				int[] coords = getGridLocation(this.getX(),this.getY());
				int bombs = Main.getLevel().countBombsAround(coords[0],coords[1]);
				if (bombs>0) {
					jlabel.setText(String.valueOf(bombs));
					jlabel.setForeground(Colors[bombs%Colors.length]);
				} else {
					Main.getLevel().poolZeros(coords[0],coords[1]);
				}
			}
		}
		Main.getLevel().countBombs();
		updateBackground();
		
	}
	
	public void rightClick() {
		if (blockType>=grass) {
			if (blockType<questionMark & Main.getLevel().getFlagsLeft()>0) {
				blockType+=1;
			} else if (blockType<questionMark & Main.getLevel().getFlagsLeft()<=0){ 
				blockType=questionMark;
			} else {
				blockType=grass;
			}
		}
		Main.getLevel().countBombs();
		updateBackground();
	}
	
	public void updateBackground() {
		switch (blockType) {
			case bomb:
				setBackground(Color.black);
				break;
			case dirt:
				setBackground(new Color(205,142,102));
				break;
			case flag:
				setBackground(Color.red);
				break;
			case questionMark:
				jlabel.setText("?");
				setBackground(Color.green);
				break;
			case grass:
			default:
				jlabel.setText("");
				setBackground(Color.green);
				break;
		}
	}
	public int[] getGridLocation(int xGrid, int yGrid) {
		int[] coords = {xGrid/blockSize.width,yGrid/blockSize.height};
		return coords;
	}
	
	public int[] getLocationOnGrid(int x, int y) {
		int[] coords = {x*blockSize.width,y*blockSize.height};
		return coords;
	}
	
	//IO
	public boolean hasBomb() {
		return hasBomb;
	}
	
	public void setBomb(boolean bomb) {
		this.hasBomb = bomb;
	}
	
	public int getBlockType() {
		return blockType;
	}
	
}
