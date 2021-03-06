
package projectsudoku;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JOptionPane;

/**
 * Class GameSaver bla bla bla
 * @author Thomas Jakobsson
 * @version
 */
public class GameSaver
{
    /**
     * Creates a default GameSaver
     */
    public GameSaver()
    {
        
    }
  
    /**
     * Saves Games to file
     * @param inFolder String path to folder to store game in
     * @param inName String name of game to be saved
     * @param inGame Game Game to be saved
     */
    public void saveGame(String inFolder, String inName, Game inGame)
    {
        if ("".equals(inName)) // if no name is given , set name to date and time
                {
                    // if no name is chosen set date and time as name
                    DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss");
                    Calendar cal = Calendar.getInstance();
                    inName = dateFormat.format(cal.getTime());
                }
        String path = inFolder + inName + ".ser";
        try
        {
            try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(path))) // ctreate the  file to be saved
            {
                os.writeObject(inGame); 
                os.close();
            }
        } catch (IOException ex)
        {
            JOptionPane.showMessageDialog(null, "Det gick inte att skriva filen", "Fel!", JOptionPane.PLAIN_MESSAGE);
        }
    }
}
