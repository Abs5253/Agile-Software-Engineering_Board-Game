/**
 * Custom JDialog that has the logic for saving the current game and allows
 * the user to name the save.
 *
 * @author David
 */

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.util.ArrayList;
import java.io.*;
import java.util.*;
import java.nio.file.*;

class SaveGameDialog extends JDialog implements ActionListener, PropertyChangeListener {

    private String userInput;
    private JTextField textField;
    private ArrayList<String> listOfSaves;
    private JOptionPane optionPane;
    private String btnText1 = "Save";
    private String btnText2 = "Cancel";
    private GameBoard game;

    /**
     * Constructor for the custom SaveDialog.
     * @param - frame the parent frame
     * @param - game The game that is to be saved
     */
    public SaveGameDialog(Frame frame, GameBoard game) {
        super(frame, true);
        this.game = game;
        this.listOfSaves = new ArrayList<String>();

        // Retrive a list of all currently saved games
        File saves = new File("./src/main/resources/saved_games");
        File[] listOfFiles = saves.listFiles();
        // make a list of all saves
        for(File file : listOfFiles) {
            String s = file.toString();
            // remove the path name
            int i = s.lastIndexOf("/") + 1;
            // remove the .ser from the end
            s = s.substring(i, s.length() - 4);
            listOfSaves.add(s);
        }

        setTitle("Save Game");
        textField = new JTextField(10);

        // Array of the text and components to be displayed
        String msgString1 = "Enter a name for the new save";
        String msgString2 = "(can not be blank)";
        Object[] array = {msgString1, msgString2, textField};

        // Array specifying the number of dialog buttons and their text
        Object[] options = {btnText1, btnText2};

        //Create the JOptionPane.
        optionPane = new JOptionPane(array, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION,
                null, options, options[0]);

        // Display the save option pane
        setContentPane(optionPane);

        // Clode the window correctly
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Make the text field the default focus
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent ce) {
                textField.requestFocusInWindow();
            }
        });

        textField.addActionListener(this);
        // event handler to validate input on state change (submit)
        optionPane.addPropertyChangeListener(this);
        pack();
    }

    // Event handler for the text input field
    @Override
    public void actionPerformed(ActionEvent e) {
        optionPane.setValue(btnText1);
    }

    // Method to check for state changes, ie input validation on submit
    @Override
    public void propertyChange(PropertyChangeEvent e) {
        String prop = e.getPropertyName();

        if (isVisible() && (e.getSource() == optionPane) && (JOptionPane.VALUE_PROPERTY.equals(prop)
                || JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))) {

            Object value = optionPane.getValue();

            if (value == JOptionPane.UNINITIALIZED_VALUE) {
                //ignore reset
                return;
            }

            // reset optionPane's value
            optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);

            if (btnText1.equals(value)) {
                userInput = textField.getText();
                String save = userInput;
                // remove whitespace
                save.trim();
                save = save.replaceAll("\\s+", "_");
                // check its not blank or wovverwiting another save
                if ((save.length() > 0) && !(listOfSaves.contains(save))) {
                    if(makeSave(save)){
                        JOptionPane.showMessageDialog(this, "Game \"" + save + "\" saved");
                    } else {
                        JOptionPane.showMessageDialog(this, "Game:\"" + save + "\" could not be saved",
                            "Save Error", JOptionPane.ERROR_MESSAGE);
                    }
                    exit();
                } else {
                    // invalid input
                    textField.selectAll();
                    JOptionPane.showMessageDialog(this, "A save with this name already exisits\n" +
                        "please enter a new name\n" +
                        "(name for save cannot be blank)", "Invalid input", JOptionPane.ERROR_MESSAGE);
                    // reset input
                    userInput = null;
                    textField.requestFocusInWindow();
                }
            } else {
                //user closed dialog or clicked cancel
                JOptionPane.showMessageDialog(this, " Save cancelled ");
                userInput = null;
                exit();
            }
        }
    }

    public boolean makeSave(String name) {
        try
        {
            //Saving game to a file
            FileOutputStream file = new FileOutputStream(new File("./src/main/resources/saved_games/" + name + ".ser"));
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of game
            out.writeObject(game);
            out.close();
            file.close();
            // successful
            return true;
        }
        catch(IOException ex)
        {
            // save failed
            System.out.println("\nIOException caught");
            return false;
        }
    }
    // reset after use
    public void exit() {
        dispose();
    }
}
