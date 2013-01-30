package projectsudoku;

import java.awt.HeadlessException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * GameLoader provides methods to load new and saved games
 *
 * @author Thomas Jakobsson
 * @version 2013-01-18
 */
public class GameLoader
{
    private JFileChooser mFileChooser;
    private Game mGame;

    /**
     * Creates a defualt GameLoader
     */
    public GameLoader()
    {
        mFileChooser = null;
        mGame = null;
    }

    /**
     * Returns choosen Game
     *
     * @param inPath String Path to directory of new games
     * @return Game Game chosen by user
     */
    public Game loadGame(String inPath) 
    {
        mFileChooser = new JFileChooser(inPath);
        Object game = null;
        ObjectInputStream os = null;
        try
        {
            os = new ObjectInputStream(new FileInputStream(getFile()));
        } catch (IOException | NullPointerException ex)
        {
            //Logger.getLogger(GameLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        try
        {
            game = os.readObject();
        } catch (IOException | ClassNotFoundException | NullPointerException ex)
        {
           // Logger.getLogger(GameLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        mGame = (Game) game;
        return mGame;
    }

    // gets file chosen by user
    private File getFile()
    {
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".ser", "ser"); // set a filter to FileChooser, only show specifed types of files
        mFileChooser.setFileFilter(filter);
        File file = null;
        int returnVal = mFileChooser.showOpenDialog(null); // open the filechooserwindow
        try
        {
            if (returnVal == JFileChooser.APPROVE_OPTION)// if chosen file is ok 
            {
                file = mFileChooser.getSelectedFile();
            }
        } catch (HeadlessException ex)
        {
            JOptionPane.showMessageDialog(null, "problem med att öppna filer"); // show is something went wrong
        }
        return file;
    }

    public void nullGame()
    {
        mGame = null;
    }
}
