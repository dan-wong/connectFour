package gui;

import javax.swing.JButton;

import game.ConnectFour;
import grid.Player;

public class ConnectFourButton extends JButton {
	private static final long serialVersionUID = 1L;
	
	private Player _player = null;
	private final int _row;
	private final int _column;

	public ConnectFourButton(int row, int column) {
		super();
		
		_row = row;
		_column = column;
	}
	
	public void setPlayer(Player player) {
		if (_player == null) {
			_player = player;
			
			setText(ConnectFour.getCurrentTurn().getSymbol());
		}
	}
	
	public void reset() {
		_player = null;
		setText("");
	}
	
	public int getRow() {
		return _row;
	}

	public int getColumn() {
		return _column;
	}
	
	public Player getPlayer() {
		return _player;
	}
	
	public boolean played() {
		return (_player != null);
	}
}
