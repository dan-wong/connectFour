package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonActionListener implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (Board.gameOver) {
			return;
		}
		
		ConnectFourButton button = (ConnectFourButton) ae.getSource();
		
		//Check if the button has already been played
		if (button.played()) {
			return;
		}
		
		int row = button.getRow()+1;
		
		//Check if the buttons below it have been selected before
		if (row < Board.ROWS) {
			ConnectFourButton buttonBelow = Board.buttonBoard[row][button.getColumn()];
			if (!buttonBelow.played()) {
				return;
			}
		}
		
		button.setPlayer(Board.currentTurn);
		Board.gridBoard.set(button.getRow(), button.getColumn(), Board.currentTurn);
		
		Board.toggleTurn();
	}
}
