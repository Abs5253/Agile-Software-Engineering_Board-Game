import javax.swing.UIManager;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.GridLayout;
import java.awt.Dimension;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * GUI Class for the Loading Screen for the Game
 *
 * @author Akshat, Sarn
 */

public class LoadGameOptionsView extends JPanel {
	private static final String TITLE_LABEL_TEXT = "LOAD A GAME FROM A SAVE FILE";
	private final String[] COLOR_OPTIONS = {Side.WHITE.name(), Side.BLACK.name()};
	private final String[] DIFFICULTY_OPTIONS = {"Easy", "Hard"};

	private LoadFiles loadFiles;
	private JButton homeButton;
	private JComboBox sideBox, turnBox, difficultyBox;

	public LoadGameOptionsView(JButton homeButton) {
		this.homeButton = homeButton;
		setLayout(new GridLayout(1,1));
		loadFiles = new LoadFiles();
		createComponents();
	}

	private void createComponents() {
		JPanel optionsPanel = new JPanel(new GridLayout(4,1));

		JPanel buttonsPanel = new JPanel(new GridLayout(1,2));
		JButton loadGameButton = new JButton("Load a Game");
		JLabel titleLabel = new JLabel(TITLE_LABEL_TEXT);

		JPanel settingsPanel = new JPanel(new GridLayout(3,1));
		JPanel sidePanel = new JPanel(new GridLayout(1,2));
		JPanel turnPanel = new JPanel(new GridLayout(1,2));
		JPanel difficultyPanel = new JPanel(new GridLayout(1,2));
		JLabel sideLabel = new JLabel("Choose Side");
		JLabel turnLabel = new JLabel("Choose Turn");
		JLabel difficultyLabel = new JLabel("Choose Difficulty");
		sideBox = new JComboBox(COLOR_OPTIONS);
		turnBox = new JComboBox(COLOR_OPTIONS);
		difficultyBox = new JComboBox(DIFFICULTY_OPTIONS);

		sidePanel.add(sideLabel);
		sidePanel.add(sideBox);
		turnPanel.add(turnLabel);
		turnPanel.add(turnBox);
		difficultyPanel.add(difficultyLabel);
		difficultyPanel.add(difficultyBox);

		settingsPanel.add(sidePanel);
		settingsPanel.add(turnPanel);
		settingsPanel.add(difficultyPanel);

		
		loadGameButton.setToolTipText("Load a Game from Save Files");
		loadGameButton.setPreferredSize(new Dimension(100, 50));
		loadGameButton.addActionListener(e -> loadGame());

		loadFiles.getList().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) loadGame();
			}
		});

		buttonsPanel.add(homeButton);
		buttonsPanel.add(loadGameButton);
		optionsPanel.add(buttonsPanel);
		optionsPanel.add(settingsPanel);
		optionsPanel.add(titleLabel);
		optionsPanel.add(loadFiles);
		
		JPanel panel = new JPanel();
        panel.add(optionsPanel);

        replacePanel(panel);
	}

	private void loadGame() {
		if (loadFiles.getList().getSelectedValue() != null) {
			try {
				String fileName = loadFiles.getList().getSelectedValue().toString();
				FileInputStream file = new FileInputStream(new File(fileName));
				ObjectInputStream in = new ObjectInputStream(file);

				GameBoard game = (GameBoard) in.readObject();
				in.close();
				file.close();

				Side side = Side.valueOf(sideBox.getSelectedItem().toString());
				Side turn = Side.valueOf(turnBox.getSelectedItem().toString());
				String difficulty = difficultyBox.getSelectedItem().toString();

				getParent().setLayout(new GridLayout(1,1));
				GUIGameBoard gamePanel = new GUIGameBoard(game, side, turn, difficulty, homeButton);
				replacePanel(gamePanel);
			} catch (Exception e) {}
		}
		
	}

	private void replacePanel(JPanel panel) {
		removeAll();
		add(panel);
		revalidate();
		repaint();
	}
}
