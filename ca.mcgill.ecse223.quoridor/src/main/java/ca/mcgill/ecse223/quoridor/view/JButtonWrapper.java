package ca.mcgill.ecse223.quoridor.view;

import javax.swing.JButton;

public class JButtonWrapper {
	private int row;
	private int col;
	private JButton button;
	
	public JButtonWrapper(int _row, int _col, JButton _button) {
		this.row = row;
		this.col = _col;
		this.button = _button;
	}
	
	public int getColumn() {
		return this.col;
	}
	public int getRow() {
		return this.row;
	}
	public JButton  getButton() {
		return this.button;
	}
}
