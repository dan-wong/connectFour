package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import game.ConnectFour;

public class ButtonActionListener implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent ae) {
		ConnectFourButton button = (ConnectFourButton) ae.getSource();
		ConnectFour.playTurn(button);
	}
}
