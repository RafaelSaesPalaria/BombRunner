package minesweeper;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseL implements MouseListener {

	//Fields
	private int clickCount = 0;
	
	//Methods
	public void mouseTreatment(MouseEvent e) {
		if (e.getSource().getClass()==Block.class) {
			clickCount+=1;
			Block block = (Block) e.getSource();
			
			if (e.getButton()==MouseEvent.BUTTON1) {
				if (clickCount<=1) {
					block.setBomb(false);
				}
				block.leftClick();
			} else if (e.getButton()==MouseEvent.BUTTON3) {
				block.rightClick();
			}
		}
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

}
