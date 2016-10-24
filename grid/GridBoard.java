package grid;

import gui.Board;
import gui.ConnectFourButton;

public class GridBoard {
	private Player[][] gridBoard = new Player[Board.ROWS][Board.COLUMNS];

	public GridBoard() {

	}

	public void set(int row, int column, Player player) {
		gridBoard[row][column] = player;

		boolean win = checkForWin(row, column, player);

		if (win) {
			Board.setWin(player);
		}
	}

	private boolean checkForWin(int row, int column, Player player) {
		ConnectFourButton currButton = Board.buttonBoard[row][column];
		
		if (rowCheck(currButton) || downTurtle(currButton) || nwseDiag(currButton) || nesw(currButton)) {
			return true;
		}

		return false;
	}

	private boolean rowCheck(ConnectFourButton currButton) {
		int counter = 0;
		for (int i=0; i<Board.COLUMNS; i++) {
			ConnectFourButton newButton = Board.buttonBoard[currButton.getRow()][i];
			
			if (currButton.getPlayer().equals(newButton.getPlayer())) {
				counter++;
			} else {
				counter = 0;
			}
			
			if (counter == 4) {
				return true;
			}
		}
		
		return false;
	}

	private boolean downTurtle(ConnectFourButton currButton) {
		int counter = 0;
		if (currButton.getRow() > (Board.ROWS-3)) {
			return false;
		}
		
		for (int i=0; i<Board.ROWS; i++) {
			ConnectFourButton newButton = Board.buttonBoard[i][currButton.getColumn()];
			
			if (currButton.getPlayer().equals(newButton.getPlayer())) {
				counter++;
			} else {
				counter = 0;
			}
			
			if (counter == 4) {
				return true;
			}
		}
		
		return false;
	}

	/**
	 * North West to South East
	 * 
	 * @param currButton
	 * @param depth
	 * @return
	 */
	private boolean nwseDiag(ConnectFourButton currButton) {
		int counter = 0;
		
		int row = currButton.getRow();
		int column = currButton.getColumn();
		
		for (int i=column; i>0 && row>0; i--) {
			row--;
		}
		
		for (int i=0; i<Board.COLUMNS && row<Board.ROWS; i++) {
			ConnectFourButton newButton = Board.buttonBoard[row][i];
			
			if (currButton.getPlayer().equals(newButton.getPlayer())) {
				counter++;
			} else {
				counter = 0;
			}
			
			if (counter == 4) {
				return true;
			}
			
			row++;
		}
		
		return false;
	}
	
	/**
	 * North East to South West
	 * @param currButton
	 * @param depth
	 * @return
	 */
	private boolean nesw(ConnectFourButton currButton) {
		int counter = 0;
		
		int row = currButton.getRow();
		int column = currButton.getColumn();
		
		for (int i=column; i<Board.COLUMNS-1 && row>0; i++) {
			row--;
		}

		for (int i=Board.COLUMNS-1; i>=0 && row<Board.ROWS; i--) {
			ConnectFourButton newButton = Board.buttonBoard[row][i];
			
			if (currButton.getPlayer().equals(newButton.getPlayer())) {
				counter++;
			} else {
				counter = 0;
			}
			
			if (counter == 4) {
				return true;
			}
			
			row++;
		}
		
		return false;
	}
}
