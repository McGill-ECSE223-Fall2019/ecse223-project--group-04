package ca.mcgill.ecse223.quoridor.view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ButtonActionListener implements MouseListener{
	public int row;
	public int col;
	public ButtonActionListener(int row_, int col_) {
		this.row = row_;
		this.col = col_;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		//MainGameWindow.tileListener(row, col);
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
