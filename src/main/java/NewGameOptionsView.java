import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;

// import java.awt.GridLayout;
// import java.awt.GridBagLayout;
// import java.awt.Dimension;

import java.awt.*;

/**
 * GUI Class for the Loading Screen for the Game
 * User should be able to select which side of the board they want to play on
 * and whether the game will be a 2 player game, or 1 player
 *
 * @author Akshat
 */

public class NewGameOptionsView extends JPanel {
	private final String[] MODE_OPTIONS = {"P1 vs COM", "P1 vs P2"};
	private final String[] COLOR_OPTIONS = {Side.WHITE.name(), Side.BLACK.name()};
	//private final String[] DIFFICULTY_OPTIONS = {"Mancala Calf", "Mancala Elder"};
	private final String[] DIFFICULTY_OPTIONS = {"Easy", "Hard"};	
	private final String TITLE_LABEL_TEXT = "START A NEW GAME";

	public NewGameOptionsView(JButton homeButton) {
        setLayout(new GridLayout(1,1));

		JPanel optionsPanel = new JPanel(new GridLayout(4,1));
		JLabel titleLabel = new JLabel(TITLE_LABEL_TEXT);

		JComboBox modeSelectionBox = new JComboBox(MODE_OPTIONS);
		JComboBox colorBox = new JComboBox(COLOR_OPTIONS);
		JComboBox colorBox1 = new JComboBox(COLOR_OPTIONS);
		JComboBox colorBox2 = new JComboBox(COLOR_OPTIONS);
		JComboBox difficultyBox = new JComboBox(DIFFICULTY_OPTIONS);

		JPanel modePanel = createModePanel(modeSelectionBox);
		JPanel singlePlayerPanel = createSinglePlayerPanel(colorBox, difficultyBox, homeButton);
		JPanel multiPlayerPanel = createMultiPlayerPanel(colorBox1, colorBox2);
		JPanel playerPanel = new JPanel();

		modeSelectionBox.addActionListener(e -> selectionAction(modeSelectionBox, playerPanel, singlePlayerPanel, multiPlayerPanel));

		playerPanel.add(singlePlayerPanel);
		optionsPanel.add(titleLabel);
		optionsPanel.add(homeButton);
		optionsPanel.add(modePanel);
		optionsPanel.add(playerPanel);

		JPanel panel = new JPanel();
        panel.add(optionsPanel);

        replacePanel(panel);
	}

	private JPanel createModePanel(JComboBox modeSelectionBox) {
		JPanel modePanel = new JPanel();
		JLabel modeLabel = new JLabel("Select Game Mode: ");
		modePanel.add(modeLabel);
		modePanel.add(modeSelectionBox);

		return modePanel;
	}

	private JPanel createSinglePlayerPanel(JComboBox colorBox, JComboBox difficultyBox, JButton homeButton) {
		JPanel colorPanel = new JPanel(new GridLayout(1,2));
		JLabel colorLabel = new JLabel("Choose Side");
		colorPanel.add(colorLabel);
		colorPanel.add(colorBox);

		JPanel difficultyPanel = new JPanel(new GridLayout(1,2));
		JLabel difficultyLabel = new JLabel("Choose Difficulty: ");
		difficultyPanel.add(difficultyLabel);
		difficultyPanel.add(difficultyBox);

		JPanel buttonPanel = new JPanel(new GridLayout(1,1));
		JButton startButton = createStartButton();
		startButton.addActionListener(e -> singlePlayerStartAction(colorBox, difficultyBox, homeButton));
		buttonPanel.add(startButton);

		JPanel singlePlayerPanel = new JPanel(new GridLayout(3,1));
		singlePlayerPanel.add(colorPanel);
		singlePlayerPanel.add(difficultyPanel);
		singlePlayerPanel.add(buttonPanel);

		return singlePlayerPanel;
	}

	private JPanel createMultiPlayerPanel(JComboBox colorBox1, JComboBox colorBox2) {
		JPanel colorPanel = new JPanel(new GridLayout(2,2));
		JLabel colorLabel1 = new JLabel("Choose Side (P1): ");
		// JLabel colorLabel2 = new JLabel("Choose Side (P2)");
		colorPanel.add(colorLabel1);
		colorPanel.add(colorBox1);
		// colorPanel.add(colorLabel2);
		// colorPanel.add(colorBox2);

		JPanel buttonPanel = new JPanel(new GridLayout(1,1));
		JButton startButton = createStartButton();
		startButton.setEnabled(false);
		startButton.addActionListener(e -> multiPlayerStartAction());
		buttonPanel.add(startButton);

		JPanel multiPlayerPanel = new JPanel(new GridLayout(3,1));
		multiPlayerPanel.add(colorPanel);
		multiPlayerPanel.add(buttonPanel);

		return multiPlayerPanel;
	}

	private JButton createStartButton() {
		JButton startButton = new JButton("Start");
		startButton.setToolTipText("Start a New Game");
		startButton.setPreferredSize(new Dimension(100, 50));

		startButton.setName("startButton");

		return startButton;
	}

	private void selectionAction(JComboBox modeSelectionBox, JPanel playerPanel, JPanel singlePlayerPanel, JPanel multiPlayerPanel) {
		playerPanel.removeAll();
		if (modeSelectionBox.getSelectedItem().toString().equals("P1 vs COM")) {
			playerPanel.add(singlePlayerPanel);
		} else if (modeSelectionBox.getSelectedItem().toString().equals("P1 vs P2")) {
			playerPanel.add(multiPlayerPanel);
		}
		playerPanel.revalidate();
		playerPanel.repaint();
	}

	private void singlePlayerStartAction(JComboBox colorBox, JComboBox difficultyBox, JButton homeButton) {
		Side side = Side.valueOf(colorBox.getSelectedItem().toString());
		String difficulty = difficultyBox.getSelectedItem().toString();

		getParent().setLayout(new GridLayout(1,1));
		replacePanel(new GUIGameBoard(side, difficulty, homeButton));
	}

	// Should create a game with 2 players
	private void multiPlayerStartAction() {

	}

	private void replacePanel(JPanel panel) {
		removeAll();
		add(panel);
		revalidate();
		repaint();
	}
}
