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
	public static Dimension blockSize = new Dimension(70,70);
	public static final int bomb = -2;
	public static final int dirt = -1;
	public static final int grass= 0;
	public static final int flag = 1;
	public static final int questionMark = 2;
	
	private boolean hasBomb;
	private int blockType = grass;
	private JLabel jlabel = new JLabel();
	
	private Color[] textColors = {
			Color.red, Color.orange,
			Color.yellow, Color.green,
			Color.cyan,Color.BLUE,
			new Color(0,0,139), new Color(128,0,128)};
	
	private Color[] backgroudColors = {
		Color.black,
		new Color(205,142,102),
		Color.green,
		Color.red,
		Color.green
	};
	
	//Constructor
	public Block(int x, int y) {
		this(x,y,false);
	}
	public Block(int x, int y, boolean hasBomb) {
		this.hasBomb = hasBomb;
		setLocation(getLocationOnGrid(x, y));
		updateBackground();
		defaultBlockSettings();
		defaultJLabelSettings();
	}
	
	//Methods
	public void defaultBlockSettings() {
		setBorder(BorderFactory.createLineBorder(Color.black, 1));
		setSize(blockSize);
		setLayout(null);
	}
	
	public void defaultJLabelSettings() {
		jlabel.setSize(blockSize);
		jlabel.setHorizontalAlignment(SwingConstants.CENTER);
		jlabel.setFont(new Font("Arial",Font.BOLD,30));
		add(jlabel);
	}
	
	public void leftClick(boolean lost) {
		Level level = Level.getInstance();
		if (blockType==grass) {
			if (hasBomb) {
				blockType=bomb;
			} else {
				blockType=dirt;
				if (!lost) {
					int[] coords = getGridLocation(this.getX(),this.getY());
					int bombs = level.countBombsAround(coords[0],coords[1]);
					if (bombs>0) {
						jlabel.setText(String.valueOf(bombs));
						jlabel.setForeground(textColors[bombs%textColors.length]);
					} else {
						level.poolZeros(coords[0],coords[1]);
					}
				}
			}
		}
		level.countBombs();
		updateBackground();
		
	}
	
	public void rightClick() {
		Level level = Level.getInstance();
		if (!wasClicked()) {
			if (blockType<questionMark & level.getFlagsLeft()>0) {
				blockType+=1;
			} else if (blockType<questionMark & level.getFlagsLeft()<=0){ 
				blockType=questionMark;
			} else {
				blockType=grass;
			}
		}
		level.countBombs();
		updateBackground();
	}
	
	public void updateBackground() {
		setBackground(backgroudColors[blockType+2]);
		if (blockType==questionMark) {
			jlabel.setText("?");
		} else if (blockType==grass){
			jlabel.setText("");
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
	//Boolean
	public boolean wasClicked() {
		return !(blockType>=grass);
	}
	
	//IO
	public boolean hasBomb() {
		return hasBomb;
	}
	
	public void setBomb(boolean bomb) {
		this.hasBomb = bomb;
	}
	
	public void setLocation(int[] coords) {
		setLocation(coords[0], coords[1]);
	}
	
	public int getBlockType() {
		return blockType;
	}
	
}
