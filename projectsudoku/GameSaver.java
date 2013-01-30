/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projectsudoku;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.swing.JOptionPane;

/**
 *
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
            //Logger.getLogger(GameSaver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
