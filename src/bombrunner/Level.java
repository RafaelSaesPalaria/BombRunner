package bombrunner;

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
	private static Level level;
	private static Block[][] levelArray;
	private static MouseL mouse;
	private int bombLeft;
	private int bombWrong;
	private int totalBombs;
	private int totalFlags;
	
	//Constructor
	public Level(MouseL mouse) {
		Level.mouse = mouse;
		levelArray = new Block[8][8];
		createLevelArray();
	}
	
	//Methods
	public void createLevelArray() {
		addMouseListener(mouse);
		for (int y1=0;y1 < levelArray.length;y1++) {
			final int y = y1;
			new Thread() {
				public void run() {
					for (int x=0;x < levelArray[y].length;x++) {
						levelArray[x][y] = new Block(x,y);
						add(levelArray[x][y]);
						repaint();
					}
					putBombs(2,y);
				}
			}.start();
		}
	}
	
	public void putBombs(int amount,int y) {
		if (amount>levelArray.length) {
			amount = levelArray.length;
		}
		for (int i = 0; i < amount; i++) {
			int x = (int) (Math.random()*levelArray[0].length);
			//int y = (int) (Math.random()*levelArray.length);
			if (levelArray[x][y].hasBomb() || levelArray[x][y].getBlockType()!=Block.grass) {
				i-=1;
			}
			levelArray[x][y].setBomb(true);
		}
		bombLeft+=amount;
		totalBombs+=amount;
	}
	
	public int countBombsAround(int xGrid, int yGrid) {
		int bombCount = 0;
		for (int y=yGrid-1;y<=yGrid+1;y++) {
			for (int x=xGrid-1;x<=xGrid+1;x++) {
				if (isValidPosition(x, y)) {
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
		checkZero(xGrid-1, yGrid-1);
		checkZero(xGrid-1, yGrid+1);
		checkZero(xGrid+1, yGrid-1);
		checkZero(xGrid+1, yGrid+1);
	}
	
	public void checkZero(int xGrid, int yGrid) {
		if (isValidPosition(xGrid, yGrid)) {
			levelArray[xGrid][yGrid].leftClick(false);
			if (isValidPosition(xGrid, yGrid) &
				levelArray[xGrid][yGrid].getBlockType()==Block.grass &
				countBombsAround(xGrid, yGrid)==0) {
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
				levelArray[x][y].leftClick(true);
				
				levelArray[x][y].repaint();
				levelArray[x][y].revalidate();
			}
		}

		endPanel("You Lost!",bombLeft+" Bombs left",Timer.getGameTime()+" Seconds");
	}
	
	public void win() {
		endPanel("You Won!",Timer.getGameTime()+" Seconds");
	}
	
	public void endPanel(String... messages) {
		removeMouseListener(mouse);
		Timer.stop();
		
		JPanel jpanel = new JPanel();
		int x = 2+getSize().width/4;
		int y = getSize().height/4;
		int w = getSize().width/2;
		int h = getSize().height/2;
		jpanel.setBounds(x, y, w, h);
		jpanel.setBackground(Color.white);
		jpanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		jpanel.setLayout(null);
		
		int distance = 150/messages.length;
		
		for (int i=0; i < messages.length; i++) {
			JLabel jlabel = new JLabel(messages[i]);
			jlabel.setBounds(10, 10+(i*distance), jpanel.getWidth(), 50);
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
				Timer.reset();
				Screen.getInstance().updateTitle();
			}
		});
		jpanel.add(jbutton);
		
		
		
		add(jpanel);
		setComponentZOrder(jpanel, 0);
		Screen.getInstance().repaint();
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
		Screen.getInstance().updateTitle();
	}
	
	public static void reset() {
		level = new Level(mouse);
	}
	
	//IO
	public static boolean isValidPosition(int xGrid, int yGrid) {
		return ((xGrid>=0 & xGrid<levelArray[0].length) &
				(yGrid>=0 & yGrid<levelArray.length));
	}
	
	public static synchronized Level getInstance() {
		if (level == null) {
			reset();
		}
		return level;
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
	
	public int getTotalBombs() {
		return totalBombs;
	}
	
	public void setMouse(MouseL mouse) {
		Level.mouse = mouse;
		addMouseListener(mouse);
	}
	
}
