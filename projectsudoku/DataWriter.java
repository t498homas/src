package projectsudoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Represents a writer that creates and modifies textfiles
 *
 * @author Thomas Jakobsson
 * @version 2012-12-12
 */
public class DataWriter
{

    /**
     * Creates a default DataWriter
     */
    public DataWriter()
    {
    }

    /**
     * Creates highscorelist and updates name- and scorelist textfiles
     *
     * @param inNames ArrayList<String> ArrayList containing name of players on
     * highscorelist
     * @param inScores ArrayList<String> ArrayList containing scores of players
     * on highscorelist
     */
    public void saveToHighscore(ArrayList<String> inNames, ArrayList<Integer> inScores) 
    {
        // PrintWriter for each document
        PrintWriter ListWriter = null;
        PrintWriter ScoreWriter = null;
        PrintWriter NameWriter = null;

        try
        {
            File highlist = new File("documents/Highscore.txt"); // create Highscorelist text file
            ListWriter = new PrintWriter(highlist, "ISO-8859-1"); //create a PrintWriter that writes swedish characters
            ScoreWriter = new PrintWriter(new FileOutputStream("documents/scores.txt")); // create PrintWriter for scores
            File namelist = new File("documents/names.txt"); // create file for names
            NameWriter = new PrintWriter(namelist, "ISO-8859-1"); // create PrintWriter for names, swedish characters
        } catch (FileNotFoundException | UnsupportedEncodingException e)
        {
            JOptionPane.showMessageDialog(null, "Filen kunde ej skrivas, försök igen"); // if the file couldnt be written
        }
        // print highscore textfile
        ListWriter.println("--------------------- Hall of fame ----------------------");
        for (int i = 0; i < inScores.size(); ++i)
        {
            // split seconds into hours minutes and seconds
            int hours = inScores.get(i) / 3600;
            int minutes = inScores.get(i) / 60 - (hours * 60);
            int seconds = inScores.get(i) - (hours * 3600) - (minutes * 60);
            ListWriter.println((i + 1) + ". " + inNames.get(i) + "    " + hours + ":" + minutes + ":" + seconds);
            // Print name and score textfiles
            ScoreWriter.println(inScores.get(i));
            NameWriter.println(inNames.get(i));
        }
        ListWriter.close(); // close the stream
        ScoreWriter.close();
        NameWriter.close();
    }

    /**
     * Erases name and score lists
     *
     * @throws FileNotFoundException
     */
    public void clearLists() throws FileNotFoundException
    {
        PrintWriter ScoreWriter = null;
        PrintWriter NameWriter = null;
        try
        {
            ScoreWriter = new PrintWriter(new FileOutputStream("documents/scores.txt", false)); // OutPutStream writes over content in file
            NameWriter = new PrintWriter(new FileOutputStream("documents/names.txt", false));
            ScoreWriter.println("");
            NameWriter.println("");
        } catch (FileNotFoundException e)
        {
            JOptionPane.showMessageDialog(null, "Filen kunde ej skrivas, försök igen"); // if the file couldnt be written
        }
        ScoreWriter.close();
        NameWriter.close();
    }
}
