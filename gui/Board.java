package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import grid.GridBoard;
import grid.Player;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class Board {
	public static final int ROWS = 7; //Default 7
	public static final int COLUMNS = 6; //Default 6
	private static final String[] _playerNames = { "Circle", "Cross" };
	
	public static Player _currentTurn = Player.CIRCLE;
	public static boolean _gameOver = false;
	
	public static ConnectFourButton[][] _buttonBoard = new ConnectFourButton[ROWS][COLUMNS];
	public static GridBoard _gridBoard = new GridBoard();

	private static JFrame _frame;
	private JPanel _buttonGrid = new JPanel();
	private static JComboBox<String> _comboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					@SuppressWarnings("unused")
					Board window = new Board();
					Board._frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Board() {
		initialize();
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
					_currentTurn = Player.CIRCLE;
				} else { //Cross
					_currentTurn = Player.CROSS;
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
				
				_gridBoard = new GridBoard();
				_gameOver = false;
			}
			
		});
		
		controlButtons.add(lblStartingPlayer);
		controlButtons.add(_comboBox);
		controlButtons.add(resetBtn);
		
		_frame.getContentPane().add(controlButtons, BorderLayout.SOUTH);
	}
	
	public static void toggleTurn() {
		if (_currentTurn.equals(Player.CIRCLE)) {
			_currentTurn = Player.CROSS;
		} else {
			_currentTurn = Player.CIRCLE;
		}
	}
	
	public static void setWin(Player player) {
		JOptionPane.showMessageDialog(_frame, player.toString() + " Wins!", "Winner!", JOptionPane.INFORMATION_MESSAGE);
		_comboBox.setEnabled(true);
		_gameOver = true;
	}
	
	public static void disableComboBox() {
		if (_comboBox.isEnabled()) {
			_comboBox.setEnabled(false);
		}
	}
}
