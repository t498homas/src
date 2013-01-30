
package projectsudoku;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 * Represents a reader that reads textfiles
 * It also reads from files and adds content to arraylists
 *
 * @author Thomas Jakobsson
 * @version 2012-12-12
 */
public class DataReader
{
    /**
     * Creates a default DataReader
     */
    public DataReader()
    {
    }

    
    /**
     * Returns a text as a string from a textfile
     * @param inFile String Path to the textfile
     * @return String content in File as a string
     * @throws FileNotFoundException
     * @throws IllegalStateException
     * @throws IllegalArgumentException
     */
    public String getText(String inFile) throws FileNotFoundException, IllegalStateException, IllegalArgumentException
    {
        Scanner inputStream = null;
        try
        {
            inputStream = new Scanner(new FileInputStream(inFile),"ISO-8859-1"); // create a scanner that reads swedish characters
        } catch (FileNotFoundException | IllegalArgumentException ex)
        {
            JOptionPane.showMessageDialog(null, "Något gick fel, försök igen"); // show is something went wrong
            
        }
        String info = "";
        try
        {
            while (inputStream.hasNext() == true) // continue until document is finished
            {
                info += inputStream.nextLine() + "\n"; // read next line

            }
        } catch (IllegalStateException ex)
        {
            JOptionPane.showMessageDialog(null, "det gick inte att läsa filen"); // show is something went wrong

        }
        inputStream.close(); // be sure to close the stream
        return info;

    }
    
    
    /**
     * Initiates arraylists with names and score 
     * from saved textfile at start of games
     * 
     * @param inScores ArrayList<Integer> ArrayList to be updated with scores
     * @param inNames ArrayList<Integer> ArrayList to be updated with names
     */
    public void highscoreToArray(ArrayList<Integer> inScores, ArrayList<String> inNames)
    {
        Scanner inputNames = null;
        Scanner inputScores = null;
        try
        {
            inputScores = new Scanner(new FileInputStream("documents/scores.txt"));
            inputNames = new Scanner(new FileInputStream("documents/names.txt"), "ISO-8859-1"); // create a scanner that reads swedish characters
            
        } catch (FileNotFoundException | IllegalArgumentException ex)
        {
            JOptionPane.showMessageDialog(null, "Något gick fel, försök igen"); // show is something went wrong
            
        }
        
        try
        {
            while (inputScores.hasNext() == true) // continue until document is finished
            {
                inScores.add(Integer.parseInt(inputScores.nextLine())); // add score

            }
            while (inputNames.hasNext() == true)
            {
                inNames.add(inputNames.nextLine());  // add name
            }
        } catch (IllegalStateException ex)
        {
            JOptionPane.showMessageDialog(null, "det gick inte att läsa filen"); // show is something went wrong

        }
        inputScores.close(); // be sure to close the stream
    }

}
