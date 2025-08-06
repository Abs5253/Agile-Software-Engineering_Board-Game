import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JComboBox;

/**
 * GUI Class for Creating a Custom Game
 *
 * @author Akshat
 */

public class CustomGameOptionsView extends JPanel {
	private static final String TITLE_LABEL_TEXT = "START A CUSTOM GAME";
	private static final int NUM_HOLES_EACH = 9;
	private static final Color COLOR_WHITE = new Color(0xF5D8B7);
	private static final Color COLOR_BLACK = new Color(0xBC8667);

	private JTextField[] holesWhite, holesBlack;
	private JTextField kazanWhite, kazanBlack;
	private JButton homeButton, startButton;

	private int[] whiteHoles, blackHoles;
	private int whiteKazan, blackKazan;

	private final String[] COLOR_OPTIONS = {Side.WHITE.name(), Side.BLACK.name()};
	private final String[] DIFFICULTY_OPTIONS = {"Easy", "Hard"};
	private JComboBox sideBox, turnBox, difficultyBox;

	public CustomGameOptionsView(JButton homeButton) {
        setLayout(new GridLayout(1,1));

		JPanel optionsPanel = new JPanel(new GridLayout(3,1));
		JLabel titleLabel = new JLabel(TITLE_LABEL_TEXT);
		this.homeButton = homeButton;
		startButton = createStartButton();

		sideBox = new JComboBox(COLOR_OPTIONS);
		turnBox = new JComboBox(COLOR_OPTIONS);
		difficultyBox = new JComboBox(DIFFICULTY_OPTIONS);

		kazanWhite = createTextField(COLOR_WHITE);
		kazanBlack = createTextField(COLOR_BLACK);

		holesWhite = new JTextField[NUM_HOLES_EACH];
		holesBlack = new JTextField[NUM_HOLES_EACH];

		whiteHoles = new int[NUM_HOLES_EACH];
		blackHoles = new int[NUM_HOLES_EACH];

		for (int i = 0; i < NUM_HOLES_EACH; i++) {
			holesWhite[i] = createTextField(COLOR_WHITE);
			holesBlack[i] = createTextField(COLOR_BLACK);
			whiteHoles[i] = 0;
			blackHoles[i] = 0;
		}

		whiteKazan = 0;
		blackKazan = 0;

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(startButton);
		buttonPanel.add(homeButton);

		optionsPanel.add(buttonPanel);
		optionsPanel.add(titleLabel);
		createBoardComponents(optionsPanel);

		JPanel panel = new JPanel();
        panel.add(optionsPanel);

        replacePanel(panel);
	}

	private void createBoardComponents(JPanel optionsPanel) {
		JPanel panelHolesPlayerOne = new JPanel(new GridLayout(1, NUM_HOLES_EACH));
		JPanel panelHolesPlayerTwo = new JPanel(new GridLayout(1, NUM_HOLES_EACH));
		JPanel panelKazans = new JPanel(new GridLayout(1, 2));
		JPanel sidePanel = new JPanel(new GridLayout(1,2));
		JPanel turnPanel = new JPanel(new GridLayout(1,2));
		JPanel difficultyPanel = new JPanel(new GridLayout(1,2));
		JPanel boardPanel = new JPanel(new GridLayout(6, 1));
		JLabel sideLabel = new JLabel("Choose Side");
		JLabel turnLabel = new JLabel("Choose Turn");
		JLabel difficultyLabel = new JLabel("Choose Difficulty");

		for (int i = 0; i < NUM_HOLES_EACH; ++i) {
			panelHolesPlayerOne.add(holesWhite[i]);
		}

		for (int i = NUM_HOLES_EACH - 1; !(i < 0); --i) {
			panelHolesPlayerTwo.add(holesBlack[i]);
		}
		panelKazans.add(kazanBlack);
		panelKazans.add(kazanWhite);

		boardPanel.add(panelHolesPlayerTwo);
		boardPanel.add(panelKazans);
		boardPanel.add(panelHolesPlayerOne);

		sidePanel.add(sideLabel);
		sidePanel.add(sideBox);

		turnPanel.add(turnLabel);
		turnPanel.add(turnBox);

		difficultyPanel.add(difficultyLabel);
		difficultyPanel.add(difficultyBox);

		boardPanel.add(sidePanel);
		boardPanel.add(turnPanel);
		boardPanel.add(difficultyBox);

		optionsPanel.add(boardPanel);

	}

	private JTextField createTextField(Color color) {
		JTextField jTextField = new JTextField(3);
		jTextField.setBackground(color);
		jTextField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) { warn(); }
			public void removeUpdate(DocumentEvent e) { warn(); }
			public void insertUpdate(DocumentEvent e) { warn(); }
		});
		return jTextField;
	}

	private JButton createStartButton() {
		JButton startButton = new JButton("Start");
		startButton.setToolTipText("Start a New Game");
		startButton.setPreferredSize(new Dimension(200, 50));
		startButton.setEnabled(false);
		startButton.addActionListener(e ->
			startButtonAction(homeButton, whiteHoles, blackHoles, whiteKazan, blackKazan)
		);

		return startButton;
	}


	private void startButtonAction(JButton homeButton, int[] holesWhite, int[] holesBlack, int kazanWhite, int kazanBlack) {
		Side side = Side.valueOf(sideBox.getSelectedItem().toString());
		Side turn = Side.valueOf(turnBox.getSelectedItem().toString());
		String difficulty = difficultyBox.getSelectedItem().toString();

		replacePanel(new GUIGameBoard(side, turn, difficulty, homeButton, holesWhite, holesBlack, kazanWhite, kazanBlack));
	}

	private void warn() {
		int sum = 0, whiteTuzNumber = 0, blackTuzNumber = 0, kazanWhiteValue = 0, kazanBlackValue = 0;
		String whiteInput = "", blackInput = "", kazanWhiteInput = "", kazanBlackInput = "";

		for (int i = 0; i < NUM_HOLES_EACH; ++i) {
			whiteInput = holesWhite[i].getText().toString();
			blackInput = holesBlack[i].getText().toString();
			kazanWhiteInput = kazanWhite.getText().toString();
			kazanBlackInput = kazanBlack.getText().toString();

			// Checks inputs for all the Holes on the White Side
			if (whiteInput.matches("\\d+")) {
				whiteHoles[i] = Integer.parseInt(whiteInput);
				sum += Integer.parseInt(whiteInput);
			} else if (whiteInput.toLowerCase().equals("x") && whiteTuzNumber == 0) {
				whiteHoles[i] = -1;
				whiteTuzNumber = i + 1;
			} else if (whiteInput.isEmpty()) {
				whiteHoles[i] = 0;
			}

			// Checks input for all the Holes on the Black Side
			if (blackInput.matches("\\d+")) {
				blackHoles[i] = Integer.parseInt(blackInput);
				sum += Integer.parseInt(blackInput);
			} else if (blackInput.toLowerCase().equals("x") && blackTuzNumber == 0) {
				blackHoles[i] = -1;
				blackTuzNumber = i + 1;
			} else if (blackInput.isEmpty()) {
				blackHoles[i] = 0;
			}

		}

		if (kazanWhiteInput.matches("\\d+")) {
			kazanWhiteValue = Integer.parseInt(kazanWhiteInput);
			whiteKazan = kazanWhiteValue;
			sum += kazanWhiteValue;
		}

		if (kazanBlackInput.matches("\\d+")) {
			kazanBlackValue = Integer.parseInt(kazanBlackInput);
			blackKazan = kazanBlackValue;
			sum += kazanBlackValue;
		}

		boolean isValid = (sum == 162 &&  whiteTuzNumber != 9 && blackTuzNumber != 9 && kazanWhiteValue < 81 && kazanBlackValue < 81)
			&& ((whiteTuzNumber != blackTuzNumber) || (whiteTuzNumber == 0 && blackTuzNumber == 0));

		if (isValid) {
				startButton.setEnabled(true);
		} else {
			startButton.setEnabled(false);
		}
	}

	private void replacePanel(JPanel panel) {
		removeAll();
		add(panel);
		revalidate();
		repaint();
	}
}
