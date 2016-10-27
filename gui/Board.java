package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import game.ConnectFour;
import grid.Player;

public class Board {
	public static final int ROWS = 8; 
	public static final int COLUMNS = 8;
	private static final String[] _playerNames = { "Circle", "Cross" };
	
	public static ConnectFourButton[][] _buttonBoard = new ConnectFourButton[ROWS][COLUMNS];

	private static JFrame _frame;
	private JPanel _buttonGrid = new JPanel();
	private static JComboBox<String> _comboBox;

	/**
	 * Create the application.
	 */
	public Board() {
		initialize();
	}
	
	public JFrame getFrame() {
		return _frame;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		_frame = new JFrame();
		_frame.setBounds(100, 100, 450, 300);
		_frame.setTitle("Connect Four");
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		_frame.getContentPane().setLayout(new BorderLayout());
		
		_buttonGrid = new JPanel(new GridLayout(ROWS, COLUMNS));
		
		for (int i=0; i<ROWS; i++) {
			for (int j=0; j<COLUMNS; j++) {
				ConnectFourButton newButton = new ConnectFourButton(i,j);
				newButton.addActionListener(new ButtonActionListener());
				newButton.setFocusable(false);
				
				_buttonBoard[i][j] = newButton;
				_buttonGrid.add(newButton);
			}
		}
		
		_frame.getContentPane().add(_buttonGrid, BorderLayout.CENTER);
		
		JPanel controlButtons = new JPanel();
		
		JLabel lblStartingPlayer = new JLabel("Starting Player");
		
		_comboBox = new JComboBox<String>(_playerNames);
		_comboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String playerType = (String) _comboBox.getSelectedItem();
				
				//Circle
				if (playerType.equals(_playerNames[0])) {
					ConnectFour.setCurrentTurn(Player.CIRCLE);
				} else { //Cross
					ConnectFour.setCurrentTurn(Player.CROSS);
				}
			}
			
		});
		
		JButton resetBtn = new JButton("Reset");
		resetBtn.setFocusable(false);
		resetBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (int i=0; i<ROWS; i++) {
					for (int j=0; j<COLUMNS; j++) {
						ConnectFourButton newButton = _buttonBoard[i][j];
						newButton.reset();
					}
				}
				
				ConnectFour.startGame();
			}
			
		});
		
		controlButtons.add(lblStartingPlayer);
		controlButtons.add(_comboBox);
		controlButtons.add(resetBtn);
		
		_frame.getContentPane().add(controlButtons, BorderLayout.SOUTH);
	}
	
	public static void setWin(Player player) {
		JOptionPane.showMessageDialog(_frame, player.toString() + " Wins!", "Winner!", JOptionPane.INFORMATION_MESSAGE);
		_comboBox.setEnabled(true);
		ConnectFour.gameOver();
	}
	
	public static void disableComboBox() {
		if (_comboBox.isEnabled()) {
			_comboBox.setEnabled(false);
		}
	}
}
