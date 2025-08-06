import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.GridLayout;
import java.awt.Dimension;

/**
 * GUI Home Screen class for Toguz Korgool
 * User can choose whether to start new game, create a custom game,
 * or load a game from a save file.
 *
 * @author Sarn
 */

public class GUIHomeScreen extends JPanel {

    /**
     * Constructs a GUIHomeScreen JPanel.
     */
    public GUIHomeScreen() {
        setLayout(new GridLayout(1,1));
        createComponents();
    }

    /**
     * Creates components and adds them to the JPanel.
     */
    private void createComponents() {
        JPanel homeScreenPanel = new JPanel();
        homeScreenPanel.setLayout(new GridLayout(3,1));

        JButton homeButton = createButton("Home Button", "Go Back to the Main Menu");
        JButton newGameButton = createButton("New Game", "Launch a New Game");
        JButton customGameButton = createButton("Custom Game", "Create a Custom Game");
        JButton loadGameButton = createButton("Load Game", "Load Game from Save file");

        newGameButton.setName("newGameButton");
        customGameButton.setName("customGameButton");
        loadGameButton.setName("loadGameButton");

        homeButton.addActionListener(e -> replacePanel(new GUIHomeScreen()));
        newGameButton.addActionListener(e -> replacePanel(new NewGameOptionsView(homeButton)));
        customGameButton.addActionListener(e -> replacePanel(new CustomGameOptionsView(homeButton)));
        loadGameButton.addActionListener(e -> replacePanel(new LoadGameOptionsView(homeButton)));

        homeScreenPanel.add(newGameButton);
        homeScreenPanel.add(customGameButton);
        homeScreenPanel.add(loadGameButton);

        JPanel panel = new JPanel();
        panel.add(homeScreenPanel);

        replacePanel(panel);
    }

    /**
     * Creates JButton with a label and tool tip.
     * @param text The text to add to the button.
     * @param toolTipText The text to add to the tool tip.
     * @return The JButton created.
     */
    private JButton createButton(String text, String toolTipText) {
        JButton button = new JButton(text);
        button.setToolTipText(toolTipText);
        button.setPreferredSize(new Dimension(200, 50));
        return button;
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
