package game;

import java.awt.Dimension;
import java.awt.EventQueue;

import grid.GridBoard;
import grid.Player;
import gui.Board;
import gui.ConnectFourButton;

public class ConnectFour {
	private static Board _window;
	private static Player _currentTurn = Player.CIRCLE;
	private static boolean _gameOver = false;
	
	private static Statistics _stats = new Statistics();
	private static GridBoard _gridBoard = new GridBoard();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					_window = new Board();
					_window.getFrame().setVisible(true);
					_window.getFrame().setSize(new Dimension(450,450));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static Player getCurrentTurn() {
		return _currentTurn;
	}
	
	public static void setCurrentTurn(Player player) {
		_currentTurn = player;
	}
	
	public static void toggleTurn() {
		if (_currentTurn.equals(Player.CIRCLE)) {
			_currentTurn = Player.CROSS;
		} else {
			_currentTurn = Player.CIRCLE;
		}
	}
	
	public static void gameOver(Player player) {
		_gameOver = true;
		_stats.addWin(player);
		Board.comboBoxEnabled(true);
	}
	
	public static void resetGame() {
		_gameOver = false;
		_gridBoard = new GridBoard();
		Board.comboBoxEnabled(true);
	}
	
	public static boolean getGameStatus() {
		return _gameOver;
	}
	
	public static void playTurn(ConnectFourButton button) {
		if (ConnectFour.getGameStatus()) {
			return;
		} 
		
		//Check if the button has already been played
		if (button.played()) {
			return;
		}
		
		int row = button.getRow()+1;
		
		//Check if the buttons below it have been selected before
		if (row < Board.ROWS) {
			ConnectFourButton buttonBelow = Board._buttonBoard[row][button.getColumn()];
			if (!buttonBelow.played()) {
				return;
			}
		}
		
		//Ensure the combo box is disabled if game is in progress
		//And only if a valid button has been pressed i.e. game not started, incorrect row clicked
		Board.comboBoxEnabled(false);
		
		button.setPlayer(ConnectFour.getCurrentTurn());
		_gridBoard.set(button.getRow(), button.getColumn(), ConnectFour.getCurrentTurn());
		
		ConnectFour.toggleTurn();
	}
}
