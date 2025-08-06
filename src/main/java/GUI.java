import javax.swing.UIManager;
import javax.swing.JFrame;

/**
 * Main GUI class for Toguz Korgool.
 * Creates the JFrame used for the game.
 * 
 * @author Sarn
 */
public class GUI extends JFrame {

	/**
	 * Constructs a new GUI frame.
	 * UILookAndFeel set to NimbusLookAndFeel.
	 * JFrame size set to 700x700.
	 */
    public GUI() {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {}

		setTitle("Toguz Korgool: The Ultimate Showdown");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(100, 100);
		setSize(700, 700);
		add(new GUIHomeScreen());
		setVisible(true);
	}

	/**
	 * Main method of Toguz Korgool, launches the GUI. 
	 */
	public static void main(String[] args) {
		new GUI();
	}
}
