package minesweeper;

import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Level extends Container {

	//Fields
	private static Block[][] levelArray;
	private MouseL mouse;
	private int bombLeft;
	private int bombWrong;
	private int totalBombs;
	private int totalFlags;
	
	//Constructor
	public Level() {
		setLocation(0,0);
		setSize(Main.getScreen().getContentPane().getSize());
		setLayout(null);
		Main.getScreen().add(this);
		start();
	}
	
	//Methods
	public void start() {
		levelArray = new Block[8][8];
		mouse = new MouseL();
		createLevelArray();
	}
	public void createLevelArray() {
		for (int yi=0;yi < levelArray.length;yi++) {
			final int y = yi;
			new Thread() {
				public void run() {
					for (int x=0;x < levelArray[y].length;x++) {
						Boolean bomb = false;
						if (Math.random()<0.2) {
							bombLeft+=1;
							totalBombs+=1;
							bomb = true;
						}
						levelArray[x][y] = new Block(x,y,bomb);
						levelArray[x][y].addMouseListener(mouse);
						add(levelArray[x][y]);
						repaint();
					}
					Main.getScreen().setTitle("Minesweeper - "+(totalBombs)+" Bombs left.");
				}
			}.start();
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
	
	public void lose() {
		bombLeft = 0;
		for (int y=0;y < levelArray.length;y++) {
			for (int x=0;x < levelArray[y].length;x++) {
				if (levelArray[x][y].hasBomb() & levelArray[x][y].getBlockType()!=Block.flag) {
					bombLeft+=1;
				}
				levelArray[x][y].leftClick();
				levelArray[x][y].removeMouseListener(mouse);
				levelArray[x][y].repaint();
			}
		}

		endPanel("You Lost!",bombLeft+" Bombs left");
	}
	
	public void win() {
		endPanel("You Won!");
	}
	
	public void endPanel(String... messages) {
		JPanel jpanel = new JPanel();
		int x = 2+getSize().width/4;
		int y = getSize().height/4;
		int w = getSize().width/2;
		int h = getSize().height/2;
		jpanel.setBounds(x, y, w, h);
		jpanel.setBackground(Color.white);
		jpanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		jpanel.setLayout(null);
		
		for (int i=0; i < messages.length; i++) {
			JLabel jlabel = new JLabel(messages[i]);
			jlabel.setBounds(10, 10+(i*50), jpanel.getWidth(), 50);
			jlabel.setFont(new Font("Arial",Font.PLAIN,29));
			jlabel.setHorizontalAlignment(SwingConstants.CENTER);
			jpanel.add(jlabel);
		}
		
		Button jbutton = new Button("Continue");
		jbutton.setFont(new Font("Arial",Font.PLAIN,29));
		jbutton.setBounds((jpanel.getWidth()/2)-75, 160, 150, 70);
		jbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.restart();
			}
		});
		jpanel.add(jbutton);
		
		
		
		add(jpanel);
		setComponentZOrder(jpanel, 0);
		jpanel.revalidate();
		jpanel.repaint();
	}
	
	public void countBombs() {
		bombLeft=0;
		bombWrong=0;
		totalFlags = 0;
		for (int y=0;y < levelArray.length;y++) {
			for (int x=0;x < levelArray[y].length;x++) {
				if (levelArray[x][y].hasBomb() & levelArray[x][y].getBlockType()!=Block.flag) {
					bombLeft+=1;
				} else if (!(levelArray[x][y].hasBomb()) & (levelArray[x][y].getBlockType()>Block.grass)) {
					bombWrong+=1;
				}
				if (levelArray[x][y].getBlockType()==Block.flag) {
					totalFlags+=1;
				}
			}
		}
		if (bombLeft==0 & bombWrong==0) {win();}
		Main.getScreen().setTitle("Minesweeper - "+(totalBombs-totalFlags)+" Bombs left.");
	}
	
	//IO
	public boolean validPosition(int xGrid, int yGrid) {
		return ((xGrid>=0 & xGrid<levelArray[0].length) &
				(yGrid>=0 & yGrid<levelArray.length));
	}
	
	public static Block[][] getLevelArray() {
		return levelArray;
	}
	
	public int getBombLeft() {
		return bombLeft;
	}
	
	public void setBombLeft(int bombLeft) {
		this.bombLeft = bombLeft;
	}
	
	public int getFlagsLeft() {
		return totalBombs-totalFlags;
	}
	
}
