import static org.junit.Assert.assertTrue;
import org.junit.Test;

import javax.swing.JButton;
import javax.swing.JLabel;

import com.athaydes.automaton.Swinger;

/**
 * Test class for GUI.
 * 
 * @author Sarn
 */
public class GUITest {
	
	/**
	 * Test to see if move made by user is correct.
	 */
	@Test
	public void testNormalUse() {
		new GUI();
		Swinger swinger = Swinger.forSwingWindow();
		swinger.pause(250)
			   .clickOn("name:newGameButton")
			   .pause(250)
			   .clickOn("name:startButton")
			   .pause(250)
			   .clickOn("name:holeButton0")
			   .pause(250);


		JLabel currentTurnLabel = (JLabel) swinger.getAt("name:currentTurnLabel");
		JButton kazanWhite = (JButton) swinger.getAt("name:kazanWhite");
		JButton kazanBlack = (JButton) swinger.getAt("name:kazanBlack");

		int totalHoles = 18;
		for (int i = 0; i < totalHoles; ++i) {
			String buttonName = "holeButton" + i;
			JButton hole = (JButton) swinger.getAt("name:" + buttonName);
			JLabel korgoolsLabel = (JLabel) hole.getComponent(0);
			int actualKorgools = Integer.parseInt(korgoolsLabel.getText());

			int expectedKorgools = -1;
			if (i == 0) expectedKorgools = 1;
			else if (i > 0 && i < totalHoles/2) expectedKorgools = 10;
			else if (!(i < totalHoles/2) && i < totalHoles) expectedKorgools = 9;

			assertTrue(actualKorgools == expectedKorgools);
		}

		assertTrue(currentTurnLabel.getText().equals("Opponent's turn"));
		assertTrue(Integer.parseInt(kazanWhite.getText()) == 0);
		assertTrue(Integer.parseInt(kazanBlack.getText()) == 0);
	}
}