import java.io.File;
import java.io.FilenameFilter;

/**
 * Filters the files based on their extension
 *
 * @author Akshat
 */

public class FileFilter implements FilenameFilter {

  private String[] extensionArray = {"txt", "ser"}; // More extensions can be added

  @Override
  public boolean accept(File dir, String name) {
    for(String ext : extensionArray) {
      if (name != null && name.endsWith(ext))
        return true;
    }
    return false;
  }
}
