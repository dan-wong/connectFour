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
		ConnectFourButton currButton = Board._buttonBoard[row][column];

		if (rowCheck(currButton) || downTurtle(currButton) || nwseDiag(currButton) || nesw(currButton)) {
			return true;
		}

		return false;
	}

	private boolean rowCheck(ConnectFourButton currButton) {
		int counter = 0;

		// Get the limits from four to the left and four to the right
		int start = currButton.getColumn() - 4;
		start = start < 0 ? 0 : start; // Ensure it is not less than zero

		int end = currButton.getColumn() + 4;
		//Ensure it is not greater than the number of columns
		end = end >= Board.COLUMNS ? Board.COLUMNS - 1 : end;
		
		// Check if the square to the left is played and is of the same type, if it
		// isn't start search from the current grid
		if (currButton.getColumn() != 0) {
			ConnectFourButton leftButton = Board._buttonBoard[currButton.getRow()][currButton.getColumn()-1];
			if (leftButton.played() && !leftButton.getPlayer().equals(currButton.getPlayer())) {
				start = currButton.getColumn();
			}
		}
		
		// Check if the square to the right is played and is of the same type, if it
				// isn't end the search at the current grid
		if (currButton.getColumn() != Board.COLUMNS-1) {
			ConnectFourButton rightButton = Board._buttonBoard[currButton.getRow()][currButton.getColumn()+1];
			if (rightButton.played() && !rightButton.getPlayer().equals(currButton.getPlayer())) {
				end = currButton.getColumn();
			}
		}
		
		for (int i = start; i <= end; i++) {
			ConnectFourButton newButton = Board._buttonBoard[currButton.getRow()][i];

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
	 * Check for a connect four along the column
	 * 
	 * @param currButton
	 * @return
	 */
	private boolean downTurtle(ConnectFourButton currButton) {
		int counter = 0;

		// Get the limits from four up and four down
		int start = currButton.getRow() - 4;
		start = start < 0 ? 0 : start; // Ensure it is not less than zero

		int end = currButton.getRow() + 4;
		// Ensure it is not greater than the number of columns
		end = end >= Board.COLUMNS ? Board.COLUMNS - 1 : end; 

		// If the current row is three from the bottom, automatically return
		// false
		if (currButton.getRow() > (Board.ROWS - 3)) {
			return false;
		}

		// Check if the square above is played and is of the same type, if it
		// isn't start search from the current grid
		if (currButton.getRow() != 0) {
			ConnectFourButton upButton = Board._buttonBoard[currButton.getRow() - 1][currButton.getColumn()];
			if (upButton.played() && !upButton.getPlayer().equals(currButton.getPlayer())) {
				start = currButton.getRow();
			}
		}

		// Check if the square below is played and is of the same type, if it
		// isn't end the search at the current grid
		if (currButton.getRow() != Board.ROWS-1) {
			ConnectFourButton downButton = Board._buttonBoard[currButton.getRow() + 1][currButton.getColumn()];
			if (downButton.played() && !downButton.getPlayer().equals(currButton.getPlayer())) {
				end = currButton.getRow();
			}
		}

		// Check for the connect four along the column
		for (int i = start; i <= end; i++) {
			ConnectFourButton newButton = Board._buttonBoard[i][currButton.getColumn()];

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
	 * Trace back to the originating diagonal, then check along the diagonal
	 * starting from there
	 * 
	 * @param currButton
	 * @param depth
	 * @return
	 */
	private boolean nwseDiag(ConnectFourButton currButton) {
		int counter = 0;

		int row = currButton.getRow();
		int column = currButton.getColumn();

		// Trace back four squares (i.e. the maximum place a connect four could
		// originate from)
		for (int i = column, shift = 0; i > 0 && row > 0 && shift <= 4; i--, shift++) {
			row--;
		}

		// Make sure starting column is valid
		column = (column - 4) < 0 ? 0 : column - 4;

		for (int i = column; i < Board.COLUMNS && row < Board.ROWS; i++) {
			ConnectFourButton newButton = Board._buttonBoard[row][i];

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
	 * 
	 * @param currButton
	 * @param depth
	 * @return
	 */
	private boolean nesw(ConnectFourButton currButton) {
		int counter = 0;

		int row = currButton.getRow();
		int column = currButton.getColumn();

		// Trace back four squares (i.e. the maximum place a connect four could
		// originate from)
		for (int i = column, shift = 0; i < Board.COLUMNS - 1 && row > 0 && shift <= 4; i++, shift++) {
			row--;
		}

		// Make sure starting column is valid
		column = (column + 4) > (Board.COLUMNS - 1) ? (Board.COLUMNS - 1) : column + 4;

		for (int i = column; i >= 0 && row < Board.ROWS; i--) {
			ConnectFourButton newButton = Board._buttonBoard[row][i];

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
