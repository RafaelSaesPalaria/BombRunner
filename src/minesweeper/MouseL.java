package minesweeper;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseL implements MouseListener {

	//Methods
	public void mouseTreatment(MouseEvent e) {
		if (e.getButton()==MouseEvent.BUTTON1) {
			((Block)e.getSource()).leftClick();
		} else if (e.getButton()==MouseEvent.BUTTON3) {
			((Block)e.getSource()).rightClick();
		}
		System.out.println(e);
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
