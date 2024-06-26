package bombrunner;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseL implements MouseListener {

	//Fields
	private int clickCount = 0;
	private long lastClick = 0;
	private int clickTime = 150;
	
	//Methods
	public void mouseTreatment(MouseEvent e) {
		int x = e.getX()/Block.blockSize.width;
		int y = e.getY()/Block.blockSize.height;
		
		if (Level.isValidPosition(x, y)) {
			clickCount+=1;
			Block block = Level.getInstance().getLevelArray()[x][y];
			
			if (e.getButton()==MouseEvent.BUTTON1) {
				
				if (clickCount<=1) {
					Timer.start();
					block.setBomb(false);
				}
				block.leftClick(false);
				
				if (block.hasBomb()) {
					Level.getInstance().lose();
				}
			} else if (e.getButton()==MouseEvent.BUTTON3 & e.getWhen()-lastClick>clickTime) {
				block.rightClick();
			}
		}
		lastClick = e.getWhen();
	}
	
	//Inhered Methods
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseTreatment(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseTreatment(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	//IO
	public int getClickCount() {
		return clickCount;
	}

}
