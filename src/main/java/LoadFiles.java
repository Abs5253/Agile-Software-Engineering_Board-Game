import java.awt.Component;

import java.io.File;
import java.io.FilenameFilter;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.DefaultListCellRenderer;
import javax.swing.filechooser.FileSystemView;

/**
 * Loads all the files in a set directory, and displays them in a JScrollPane.
 *
 * @author Akshat, Sarn
 */

public class LoadFiles extends JScrollPane {
	private static final String PATH = "./src/main/resources/saved_games";

	private FilenameFilter fileFilter = new FileFilter();
	private JList displayList;

	/**
	 * Constructs LoadFiles.
	 */
	public LoadFiles() {
		displayList = new JList(new File(PATH).listFiles(fileFilter));
		displayList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		displayList.setCellRenderer(new CellRenderer());
		displayList.setLayoutOrientation(javax.swing.JList.HORIZONTAL_WRAP);
		displayList.setName("displayList");
		setViewportView(displayList);
	}

	/**
	 * Gets list of save files found.
	 * @return list of save files found.
	 */
	public JList getList() { return displayList; }

	/**
	 * Private class to render the icon and name of the save file.
	 */
	private static class CellRenderer extends DefaultListCellRenderer{
		@Override
		public Component getListCellRendererComponent(JList list, Object value,
		int index, boolean isSelected, boolean cellHasFocus) {
			if (value instanceof File) {
				File file = (File) value;
				setText(file.getName());
				setIcon(FileSystemView.getFileSystemView().getSystemIcon(file));
				if (isSelected) {
					setBackground(list.getSelectionBackground());
					setForeground(list.getSelectionForeground());
				} else {
					setBackground(list.getBackground());
					setForeground(list.getForeground());
				}
				setEnabled(list.isEnabled());
				setFont(list.getFont());
				setOpaque(true);
			}
			return this;
		}
	}
}
