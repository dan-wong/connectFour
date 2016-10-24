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

public class Board {
	public static final int ROWS = 7;
	public static final int COLUMNS = 6;
	
	public static Player currentTurn = Player.CIRCLE;
	public static boolean gameOver = false;
	
	public static ConnectFourButton[][] buttonBoard = new ConnectFourButton[ROWS][COLUMNS];
	public static GridBoard gridBoard = new GridBoard();

	private static JFrame frame;
	private JPanel buttonGrid = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					@SuppressWarnings("unused")
					Board window = new Board();
					Board.frame.setVisible(true);
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
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setTitle("Connect Four");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setLayout(new BorderLayout());
		
		buttonGrid = new JPanel(new GridLayout(ROWS, COLUMNS));
		
		for (int i=0; i<ROWS; i++) {
			for (int j=0; j<COLUMNS; j++) {
				ConnectFourButton newButton = new ConnectFourButton(i,j);
				newButton.addActionListener(new ButtonActionListener());
				newButton.setFocusable(false);
				
				buttonBoard[i][j] = newButton;
				buttonGrid.add(newButton);
			}
		}
		
		frame.add(buttonGrid, BorderLayout.CENTER);
		
		JPanel controlButtons = new JPanel();
		JButton resetBtn = new JButton("Reset");
		resetBtn.setFocusable(false);
		resetBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (int i=0; i<ROWS; i++) {
					for (int j=0; j<COLUMNS; j++) {
						ConnectFourButton newButton = buttonBoard[i][j];
						newButton.reset();
					}
				}
				
				gridBoard = new GridBoard();
				gameOver = false;
			}
			
		});
		controlButtons.add(resetBtn);
		
		frame.add(controlButtons, BorderLayout.SOUTH);
	}
	
	public static void toggleTurn() {
		if (currentTurn.equals(Player.CIRCLE)) {
			currentTurn = Player.CROSS;
		} else {
			currentTurn = Player.CIRCLE;
		}
	}
	
	public static void setWin(Player player) {
		JOptionPane.showMessageDialog(frame, player.toString() + " Wins!", "Winner!", JOptionPane.INFORMATION_MESSAGE);
		gameOver = true;
	}
}
