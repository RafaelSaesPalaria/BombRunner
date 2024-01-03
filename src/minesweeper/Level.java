package minesweeper;

import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Level extends Container {

	//Fields
	private static Block[][] levelArray = new Block[8][8];
	private MouseL mouse;
	
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
	
	public void lose() {
		for (int y=0;y < levelArray.length;y++) {
			for (int x=0;x < levelArray[y].length;x++) {
				levelArray[x][y].leftClick();
			}
		}
		JPanel jpanel = new JPanel();
		jpanel.setBounds(2+getSize().width/4, getSize().height/4, getSize().width/2, getSize().height/2);
		jpanel.setBackground(Color.white);
		jpanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		jpanel.setLayout(null);
		
		JLabel jlabel = new JLabel("You Lost!");
		jlabel.setBounds(10, 10, jpanel.getWidth(), 100);
		jlabel.setFont(new Font("Arial",Font.PLAIN,29));
		jlabel.setHorizontalAlignment(SwingConstants.CENTER);
		jpanel.add(jlabel);
		
		JLabel jlabels = new JLabel("Points Time");
		jpanel.add(jlabels);
		
		Button jbutton = new Button("Continue");
		jbutton.setFont(new Font("Arial",Font.PLAIN,29));
		jbutton.setBounds((jpanel.getWidth()/2)-75, 160, 150, 70);
		jbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.getScreen().remove(Main.getLevel());
				Main.setLevel(new Level());
			}
		});
		jpanel.add(jbutton);
		
		
		
		add(jpanel);
		setComponentZOrder(jpanel, 0);
		jpanel.revalidate();
		jpanel.repaint();
		
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
