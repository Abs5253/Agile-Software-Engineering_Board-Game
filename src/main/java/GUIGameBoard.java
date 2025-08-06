import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.DefaultButtonModel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Font;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * GUIGameBoard class for Toguz Korgool.
 * Creates JPanel which contains a visual representation of the game board.
 *
 * @author Sarn
 */

public class GUIGameBoard extends JPanel {

	private static final Color COLOR_BLACK = new Color(0xBC8667);
	private static final Color COLOR_WHITE = new Color(0xF5D8B7);
	private static final Color COLOR_KORGOOL = Color.BLUE;
	private static final Font fontKorgools = new Font("DIALOG", Font.PLAIN, 18);
	private static final Font fontBoard = new Font("DIALOG", Font.PLAIN, 14);
	private static final String PLAYERONE_TURN = "Your turn";
	private static final String PLAYERTWO_TURN = "Opponent's turn";
	private static final int NUM_HOLES_EACH = 9;
	private static int AI_MOVE_DELAY;

	private JButton[] holesWhite = new JButton[NUM_HOLES_EACH];
	private JButton[] holesBlack = new JButton[NUM_HOLES_EACH];
	private JButton kazanWhite;
	private JButton kazanBlack;
	private JPanel boardPanel;
	private JLabel currentTurnLabel;
	private DefaultButtonModel unclickableModel;

	private Game game;
	private GameBoard board;
	private Side side;
	private Side currentTurn;

	/**
	 * Constructs a GUIGameBoard for a new game.
	 * @param side The Side that the user has chosen.
	 * @param difficulty The difficulty that the user has chosen.
	 * @param homeButton The home button, which takes the user back to the home screen.
	 */
	public GUIGameBoard(Side side, String difficulty, JButton homeButton) {
		this.side = side;
		
		Side opponentSide = (side == Side.WHITE) ? Side.BLACK : Side.WHITE;
		currentTurn = Side.WHITE;
		
		boardPanel = new JPanel(new BorderLayout());
		game = new Game(difficulty, opponentSide);
		board = game.getGameBoard();
		createComponents(homeButton, opponentSide);
	}

	/**
	 * Constructs a GUIGameBoard for a loaded game.
	 * @param board The GameBoard that is loaded into the game.
	 * @param side The Side that the user has chosen.
	 * @param currentTurn The Side of the first turn.
	 * @param difficulty The difficulty that the user has chosen.
	 * @param homeButton The home button, which takes the user back to the home screen.
	 */
	public GUIGameBoard(GameBoard board, Side side, Side currentTurn, String difficulty, JButton homeButton) {
		this.board = board;
		this.side = side;
		this.currentTurn = currentTurn;
		
		Side opponentSide = (side == Side.WHITE) ? Side.BLACK : Side.WHITE;
		
		boardPanel = new JPanel(new BorderLayout());
		game = new Game(board, difficulty, opponentSide);
		createComponents(homeButton, opponentSide);
	}

	/**
	 * Constructs a GUIGameBoard for a custom game.
	 * @param side The Side that the user has chosen.
	 * @param currentTurn The Side of the first turn.
	 * @param difficulty The difficulty that the user has chosen.
	 * @param homeButton The home button, which takes the user back to the home screen.
	 * @param holesWhite The array which holds the number of korgools in each of the white holes.
	 * @param holesBlack The array which holds the number of korgools in each of the black holes.
	 * @param kazanWhite The number of korgools in the white kazan.
	 * @param kazanBlack The number of korgools in the black kazan.
	 */
	public GUIGameBoard(Side side, Side currentTurn, String difficulty, JButton homeButton, int[] holesWhite, int[] holesBlack, int kazanWhite, int kazanBlack) {
		this.side = side;
		this.currentTurn = currentTurn;
		
		Side opponentSide = (side == Side.WHITE) ? Side.BLACK : Side.WHITE;
		
		boardPanel = new JPanel(new BorderLayout());
		game = new Game(difficulty, opponentSide, holesWhite, holesBlack, kazanWhite, kazanBlack);
		board = game.getGameBoard();
		createComponents(homeButton, opponentSide);
	}

	/**
	 * Creates components for the screen, including the board, labels, buttons.
	 * @param homeButton The home button, which takes the user back to the home screen.
	 * @param opponentSide The side of the opponent.
	 */
	private void createComponents(JButton homeButton, Side opponentSide) {
		setLayout(new GridLayout(1,1));
		setAIDelayOn();

		unclickableModel = new DefaultButtonModel() {
			public void setPressed(boolean pressed) {}
			public void setRollover(boolean rollover) {}
		};

		currentTurnLabel = (currentTurn == side) ? new JLabel(PLAYERONE_TURN) : new JLabel(PLAYERTWO_TURN);
		currentTurnLabel.setName("currentTurnLabel");
		currentTurnLabel.setFont(fontBoard);

		createBoardComponents();
		setUpGameBoard(homeButton);

		currentTurn = (currentTurn == Side.WHITE) ? Side.BLACK : Side.WHITE;
		boolean isGameOver = gameOver();
		currentTurn = (currentTurn == Side.WHITE) ? Side.BLACK : Side.WHITE;

		if (!isGameOver && opponentSide == currentTurn) makeAIMove(side);
	}

	/**
	 * Creates a JPanel which contains the "Home" button and the "Save" button.
	 * The Home button takes the user to the home screen.
	 * The Save button opens a dialog to let the user save the game.
	 * @param homeButton The home button, which takes the user back to the home screen.
	 * @return The JPanel which contains the Home and Save buttons.
	 */
	private JPanel createMenuPanel(JButton homeButton) {
		JPanel menuPanel = new JPanel();
		menuPanel.setLayout(new GridLayout(1,2));

		JButton saveButton = new JButton("Save Game");
		saveButton.addActionListener(e -> {
			JDialog d = new SaveGameDialog(null, board);
			d.setLocationRelativeTo(null);
			d.setVisible(true);
		});

		menuPanel.add(homeButton);
		menuPanel.add(saveButton);

		return menuPanel;
	}

	/**
	 * Displays the GUIGameBoard, showing the state of the game.
	 * @param homeButton The home button, which takes the user back to the home screen.
	 */
	private void setUpGameBoard(JButton homeButton) {
		JPanel panelHolesPlayerOne = new JPanel(new GridLayout(1, NUM_HOLES_EACH));
		JPanel panelHolesPlayerTwo = new JPanel(new GridLayout(1, NUM_HOLES_EACH));
		JPanel panelKazans = new JPanel(new GridLayout(1,2));
		if (side == Side.WHITE) {
			for (int i = 0; i < NUM_HOLES_EACH; ++i) {
				panelHolesPlayerOne.add(holesWhite[i]);
			}
			for (int i = NUM_HOLES_EACH - 1; !(i < 0); --i) {
				panelHolesPlayerTwo.add(holesBlack[i]);
			}
			panelKazans.add(kazanBlack);
			panelKazans.add(kazanWhite);
		} else {
			for (int i = 0; i < NUM_HOLES_EACH; ++i) {
				panelHolesPlayerOne.add(holesBlack[i]);
			}
			for (int i = NUM_HOLES_EACH - 1; !(i < 0); --i) {
				panelHolesPlayerTwo.add(holesWhite[i]);
			}
			panelKazans.add(kazanWhite);
			panelKazans.add(kazanBlack);
		}

		JPanel gameBoard = new JPanel(new GridLayout(3,1));
		gameBoard.add(panelHolesPlayerTwo);
		gameBoard.add(panelKazans);
		gameBoard.add(panelHolesPlayerOne);

		boardPanel.add(createMenuPanel(homeButton), BorderLayout.NORTH);
		boardPanel.add(gameBoard, BorderLayout.CENTER);
		boardPanel.add(currentTurnLabel, BorderLayout.SOUTH);

		replacePanel(boardPanel);
		updateGameBoard();
	}

	/**
	 * Updates the GUIGameBoard, showing the new state of the game.
	 */
	private void updateGameBoard() {
		ArrayList<Hole> boardHoles = board.getHoles();

		for (int i = 0; i < NUM_HOLES_EACH; ++i) {
			updateHoleButton(holesWhite[i], boardHoles.get(i));
			updateHoleButton(holesBlack[i], boardHoles.get(i + NUM_HOLES_EACH));
		}

		kazanWhite.setText("" + board.getWhiteScore());
		kazanBlack.setText("" + board.getBlackScore());

		replacePanel(boardPanel);
	}

	/**
	 * Updates a holeButton to the new state.
	 * Displays the hole number, and the number of korgools in the hole.
	 * @param holeButton The hole button to update.
	 * @param hole The hole containing the information used to update the hole button.
	 */
	private void updateHoleButton(JButton holeButton, Hole hole) {
		JLabel labelKorgools = new JLabel("", JLabel.CENTER);
		JLabel labelHoleNumber = new JLabel("" + hole.getNumber(), JLabel.CENTER);

		labelKorgools.setForeground(COLOR_KORGOOL);
		labelKorgools.setFont(fontKorgools);
		labelHoleNumber.setFont(fontBoard);

		if (hole.isTuz()) labelKorgools.setText("X");
		else labelKorgools.setText("" + hole.getKorgools());

		holeButton.removeAll();
		holeButton.add(labelKorgools, BorderLayout.CENTER);
		if (side == hole.getSide()) holeButton.add(labelHoleNumber, BorderLayout.SOUTH);
		else holeButton.add(labelHoleNumber, BorderLayout.NORTH);
	}

	/**
	 * Creates the buttons for the game board components.
	 * The white holes, black holes, white kazan and black kazan.
	 */
	private void createBoardComponents() {
		for (int i = 0; i < NUM_HOLES_EACH; ++i) {
			holesWhite[i] = createHoleButton(COLOR_WHITE, i);
			holesBlack[i] = createHoleButton(COLOR_BLACK, i + NUM_HOLES_EACH);
		}

		kazanWhite = createKazan(COLOR_WHITE, -1);
		kazanBlack = createKazan(COLOR_BLACK, -1);

		kazanWhite.setName("kazanWhite");
		kazanBlack.setName("kazanBlack");
	}

	/**
	 * Creates a Hole button. User clicks the button to make a move.
	 * @param color The color of the hole (white or black).
	 * @param index The index of the hole, from 0 to 17.
	 * @return The JButton created for the hole.
	 */
	private JButton createHoleButton(Color color, int index) {
		JButton holeButton = new JButton();
		holeButton.setName("holeButton" + index);

		holeButton.setModel(new DefaultButtonModel() {
			public void setRollover(boolean rollover) {}
		});

		holeButton.addActionListener(e -> {
			Side sidePressed = (color == COLOR_WHITE) ? Side.WHITE : Side.BLACK;

			if (sidePressed == side && side == currentTurn) {
				if (board.getHole(index).getKorgools() != 0) {
					currentTurn = (currentTurn == Side.WHITE) ? Side.BLACK : Side.WHITE;
					currentTurnLabel.setText(PLAYERTWO_TURN);

					if (side == Side.WHITE) game.move(index + 1, sidePressed);
					else game.move(index + 1 - NUM_HOLES_EACH, sidePressed);

					updateGameBoard();
					if(!gameOver()) {
						makeAIMove(currentTurn);
						gameOver();
					}
				}
			}
		});

		holeButton.setLayout(new BorderLayout());
		holeButton.setFocusPainted(false);
		holeButton.setBackground(color);

		return holeButton;
	}

	/**
	 * Creates a kazan unclickable JButton.
	 * @param color The color of the kazan (white or black).
	 * @param korgools The number of korgools in the kazan.
	 * @return The unclickable JButton created for the kazan.
	 */
	private JButton createKazan(Color color, int korgools) {
		JButton kazan = new JButton();

		kazan.setModel(unclickableModel);
		kazan.setFocusPainted(false);
		kazan.setBackground(color);
		kazan.setFont(fontKorgools);

		return kazan;
	}

	/**
	 * Tells the game to make an AI move after a delay.
	 * @param opponentSide The Side of the opponent.
	 */
	private void makeAIMove(Side opponentSide) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				game.moveAutomatedOpponent();
				currentTurnLabel.setText(PLAYERONE_TURN);
				currentTurn = side;
				updateGameBoard();
				gameOver();
			}
		}, AI_MOVE_DELAY);
	}

	/**
	 * Set delay of AI move to be 3 seconds.
	 * This simulates the AI thinking.
	 * Would later be the given time for AI to find a move.
	 */
	private static void setAIDelayOn() { AI_MOVE_DELAY = 3000; }

	/**
	 * Set delay of AI move to 0 seconds.
	 * Used to speed up testing.
	 */
	private static void setAIDelayOff() { AI_MOVE_DELAY = 0; }

	/**
	 * Check if the game has ended.
	 * If the game has ended, show the winner and the scores.
	 * @return true if the game is over.
	 */
	public boolean gameOver() {
		if(game.isEndOfGame(currentTurn)) {
			String winner = game.getWinner(side);
			String whiteScore = "White score: " + board.getWhiteScore();
			String blackScore = "Black score: " + board.getBlackScore();
			String message = winner + "\n" + whiteScore + "\n" + blackScore;

			JOptionPane.showMessageDialog(this, message, "Game over", JOptionPane.INFORMATION_MESSAGE, null);

			for (int i = 0; i < NUM_HOLES_EACH; ++i) {
				holesWhite[i].setModel(unclickableModel);
				holesBlack[i].setModel(unclickableModel);
			}

			return true;
		}
		return false;
	}

	/**
	 * Replace panel shown on the screen.
	 * @param panel The panel to add.
	 */
	private void replacePanel(JPanel panel) {
		removeAll();
		add(panel);
		revalidate();
		repaint();
	}
}
