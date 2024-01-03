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
	
	
	//Constructor
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
		jlabel.setFont(new Font("Arial",Font.PLAIN,19));
		add(jlabel);
	}
	
	//Methods
	public void leftClick() {
		if (blockType==grass) {
			if (hasBomb) {
				blockType=bomb;
				Main.getLevel().lose();
			} else {
				blockType=dirt;
				int[] coords = getGridLocation(this.getX(),this.getY());
				int bombs = Main.getLevel().countBombsAround(coords[0],coords[1]);
				if (bombs>0) {
					jlabel.setText(String.valueOf(bombs));
				} else {
					Main.getLevel().poolZeros(coords[0],coords[1]);
				}
			}
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
