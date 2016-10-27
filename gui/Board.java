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
import javax.swing.border.EmptyBorder;

import game.ConnectFour;
import game.Statistics;
import game.StatisticsListener;
import grid.Player;
import java.awt.Font;

public class Board implements StatisticsListener {
	public static final int ROWS = 8; 
	public static final int COLUMNS = 8;
	private static final String[] _playerNames = { "Circle", "Cross" };
	
	public static ConnectFourButton[][] _buttonBoard = new ConnectFourButton[ROWS][COLUMNS];

	private static JFrame _frame;
	private JPanel _buttonGrid = new JPanel();
	private static JComboBox<String> _comboBox;
	private JLabel _crossStats;
	private JLabel _circleStats;
	
	private static Statistics _stats = new Statistics();

	/**
	 * Create the application.
	 */
	public Board() {
		initialize();
		
		//Request to be notified of change in statistics
		_stats.addListener(this);
	}
	
	public JFrame getFrame() {
		return _frame;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
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
		_frame.getContentPane().add(controlButtons, BorderLayout.NORTH);
		controlButtons.setBorder(new EmptyBorder(5, 5, 5, 5));
		
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
				
				ConnectFour.resetGame();
			}
			
		});
		controlButtons.setLayout(new BorderLayout(0, 0));
		
		JPanel playerSelectorPane = new JPanel();
		controlButtons.add(playerSelectorPane, BorderLayout.WEST);
		
		JLabel lblStartingPlayer = new JLabel("Starting Player");
		playerSelectorPane.add(lblStartingPlayer);
		
		_comboBox = new JComboBox(_playerNames);
		playerSelectorPane.add(_comboBox);
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
		controlButtons.add(resetBtn, BorderLayout.EAST);
		
		JPanel statPanel = new JPanel();
		statPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		statPanel.setLayout(new BorderLayout());
		_crossStats = new JLabel("Cross: 0 Wins");
		_circleStats = new JLabel("Circle: 0 Wins");

		statPanel.add(_crossStats, BorderLayout.WEST);
		statPanel.add(_circleStats, BorderLayout.EAST);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		bottomPanel.setLayout(new BorderLayout());
		
		JLabel label = new JLabel("Win Statistics");
		label.setFont(new Font("Tahoma", Font.BOLD, 13));
		bottomPanel.add(label);
		bottomPanel.add(statPanel, BorderLayout.SOUTH);
		
		_frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
	}
	
	public static void setWin(Player player) {
		JOptionPane.showMessageDialog(_frame, player.toString() + " Wins!", "Winner!", JOptionPane.INFORMATION_MESSAGE);
		_comboBox.setEnabled(true);
		ConnectFour.gameOver(player);
	}
	
	public static void comboBoxEnabled(boolean value) {
		_comboBox.setEnabled(value);
	}

	@Override
	public void fireUpdates() {
		_crossStats.setText("Cross: " + _stats.getCrossWin() + " Wins");
		_circleStats.setText("Circle: " + _stats.getCircleWin() + " Wins");
	}
}
