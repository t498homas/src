package projectsudoku;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Control manages the sudokusolver and contains all logic
 *
 * @author Thomas Jakobsson
 * @date 2013-01-10
 */
public class Control
{

    private GUI mGUI; // The GUI the user see
    private GameLoader mLoader; // gameLOader for saved games
    private GameSaver mSaver; // for saving active games
    private DataReader mReader; // For readind text-files
    private DataWriter mWriter; // for writing text-files
    private Solver mSolver; // contains the algorithm for solving the sudoku
    private ActionListener mAL; // actionlistener for buttons
    private ChangeListener mCL; // actionlistener for SudokuButtons
    private int[][] mCurrentValues; // the values on the board atm
    private int[][] mSolutionValues; // the valuues of the solution provided by solver
    private boolean mGameInAction; // true when game is running
    private boolean mSolved; // true if sudoku is solved
    private ArrayList<Integer> mScores; // scores for highscorelist
    private ArrayList<String> mNames; // names for highscorelist

    /**
     * Creates a default control
     *
     */
    public Control() 
    {
        mAL = new Action(); // to be added to all JButtons on the GUI
        mCL = new ChangeListener() // to be added to all SudukoButtons
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {   // check if sudoku is solved every time a button is clicked
                checkIfCorrect();
            }
        };
        try
        {
            mGUI = new GUI(mAL, mCL);
        } catch (IOException | IllegalArgumentException ex)
        {
            JOptionPane.showMessageDialog(null, "Bilderna till spelet kan inte hittas!");
        }
        mLoader = new GameLoader();
        mSaver = new GameSaver();
        mLoader = new GameLoader();
        mReader = new DataReader();
        mWriter = new DataWriter();
        mSolver = new Solver();
        mGameInAction = false;
        mSolved = false;
        mCurrentValues = new int[9][9];
        mSolutionValues = new int[9][9];
        mScores = new ArrayList<>();
        mNames = new ArrayList<>();
        loadHighscore(); // fill arrays with highscoredata
    }

    // start a new game, called by "Nytt spel"" button
    private void runNewGame()
    {
        if (!mGameInAction) // can only be done if no game is active
        {
            mGUI.getTimePanel().stop();
            mGUI.getTimePanel().reset();
            clearBoard(); // just to be sure nothing remains
            Game game = mLoader.loadGame("newgames"); // set the folder from where new game is retrieved
            if (game != null)
            {
                mGUI.getBoard().setButtons(game.getButtons()); // set the buttons
                startGame();
                mLoader.nullGame();
            }
        }
    }

    // save a new unplayed game called by "Lägg till nytt spel button"
    private void saveNewGame()
    {
        if (!mGameInAction) // can only be done if no game is active
        {
            extractValuesFromBoard();
            if (mSolver.checkNewGame(mCurrentValues))
            {
                mGUI.getBoard().lockShowingNumbers(); // set button showing number to locked
                String newName = JOptionPane.showInputDialog("Vad vill du döpa spelet till?"); // get name of the game from user
                if (!"".equals(newName) && newName != null) // dont do anything unless user names game or if press cancel 
                {   // save the game
                    mSaver.saveGame("newgames/", newName, new Game(mGUI.getBoard().getButtons(), mGUI.getTimePanel().getHours(), mGUI.getTimePanel().getMinutes(), mGUI.getTimePanel().getSeconds()));
                    clearBoard(); // clear the board
                }
                else if(newName.equals(""))
                {
                    JOptionPane.showMessageDialog(null, "Du måste namnge det nya spelet!", "Namnge spelet!", JOptionPane.PLAIN_MESSAGE);
                }
            } else
            {
                JOptionPane.showMessageDialog(null, "Du har försökt spara ett olösligt sudoku!", "HOPPSAN!!!", JOptionPane.PLAIN_MESSAGE);
            }
        }

    }

    // method to set all button to unlocked and value to zero
    // and resets mCurrentValues to zero
    private void clearBoard()
    {
        if (!mGameInAction)  // can only be done if no game is active
        {
            mGUI.getBoard().resetAllButtons();
        }

    }

    // method to get the solution, called by the "Läs in och lös" button
    private void solveGame()
    {
        if (!mGameInAction)  // can only be done if no game is active, no cheating!
        {
            mGUI.getBoard().lockShowingNumbers();
            extractValuesFromBoard();
            try
            {
                mSolver.run(mCurrentValues);
            } catch (Exception ex)
            {
                String info = ex.getMessage();
                JOptionPane.showMessageDialog(null, info, "HOPPSAN!!!", JOptionPane.PLAIN_MESSAGE);
            }
            updateBoardButton();
        }
    }

    // method to load and start a saved game, called by the "Ladda spel" button"
    private void runSavedGame()
    {
        if (!mGameInAction) // can only be done if no game is active
        {
            abortGame();
            Game game = mLoader.loadGame("savedgames"); // set the folder where the game is to be stored
            if (game != null)
            {
                mGUI.getBoard().setButtons(game.getButtons());
                mGUI.getTimePanel().getDisplay().setTime(game.getHours(), game.getMinutes(), game.getSeconds());
                mSolved = false;
                mGUI.getBoard().boardUpdate();
                extractLockedValues();  // important only to read buttons from original game
                try
                {
                    mSolver.run(mCurrentValues);
                } catch (Exception ex)
                {
                    // No need to do anything, game is already checked
                }
                getSolution();
                extractValuesFromBoard(); // now get all values to get current board 
                mGUI.getTimePanel().start();
                mGameInAction = true;
                mLoader.nullGame();
            }
        }
    }

    // method to save a game in action, time stops when the ok to save is pressed, called by "Spara spel" button
    private void saveGame()
    {
        if (mGameInAction) // can only be done if there is a game to save
        {
            String savedname = JOptionPane.showInputDialog("<html>Vad vill du döpa spelet till?<br>Tomt fält ger datum och tid som namn</html>"); // get the name from user
            if (savedname != null) // if user dont press cancel
            {
                //save the game in folder savedgames
                mSaver.saveGame("savedgames/", savedname, new Game(mGUI.getBoard().getButtons(), mGUI.getTimePanel().getHours(), mGUI.getTimePanel().getMinutes(), mGUI.getTimePanel().getSeconds()));
                // stop clock and reset game
                mGUI.getTimePanel().stop();
                mGameInAction = false;
                clearBoard();
                mGUI.getTimePanel().reset();
            }
        }
    }

    // create the top ten list, called by the "Highsore" button
    private void setHighscoreList()
    {
        mWriter.saveToHighscore(mNames, mScores);
        JOptionPane.showMessageDialog(null, mReader.getText("documents/Highscore.txt"), "Highscorelista", JOptionPane.PLAIN_MESSAGE);

    }

    // resets all unlocked buttos during a game, called by "Börja om" button
    private void restartGame()
    {
        mGUI.getBoard().resetUnlockedButtons();
    }

    // abort active game and reset board, called by "Avbryt spelet" button
    private void abortGame()
    {
        if (mGameInAction)
        {
            mGameInAction = false;
            mSolved = false;
            
            clearBoard();
            mGUI.getTimePanel().stop();
            mGUI.getTimePanel().reset();
        }
    }

    // starts the game
    private void startGame()
    {
        mSolved = false;
        mGameInAction = true;
        mGUI.getBoard().boardUpdate();
        extractValuesFromBoard(); // update mCurrentValues
        try
        {
            mSolver.run(mCurrentValues);
        } catch (Exception ex)
        {
            //Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        getSolution(); // update mSolutionValues
        extractLockedValues(); // update mCurrentValues
        mGUI.getTimePanel().start();

    }

    // gets current values from buttons
    private void extractValuesFromBoard()
    {
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                mCurrentValues[i][j] = mGUI.getBoard().getButton(i, j).getValue();
            }
        }
    }

    // gets current values from  locked buttons, other value set to zero
    private void extractLockedValues()
    {
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                if (mGUI.getBoard().getButton(i, j).isLocked())
                {
                    mCurrentValues[i][j] = mGUI.getBoard().getButton(i, j).getValue();
                } else
                {
                    mCurrentValues[i][j] = 0;
                }
            }
        }
    }

    // helpmethod to solveGame()
    // used bwhen solution is to be shown to user
    private void updateBoardButton()
    {
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                mGUI.getBoard().getButton(i, j).setValueAndUpdate(mCurrentValues[i][j]);
            }
        }
    }

    // helpmethod to startGame
    // gets the solution for comparison with current board
    private void getSolution()
    {
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                mSolutionValues[i][j] = mSolver.getSolution(i, j);
            }
        }

    }

    // check everytime a sudokubutton is pressed
    private void checkIfCorrect()
    {
        extractValuesFromBoard(); // get current values
        // if solution is correct AND not solved yet AND games is in action
        if (Arrays.deepEquals(mSolutionValues, mCurrentValues) && !mSolved && mGameInAction)
        {
            mSolved = true;
            mGUI.getTimePanel().stop();
            JOptionPane.showMessageDialog(null, "Bra jobbat ! Du har löst sudokut !", "GRATTIS!!!", JOptionPane.PLAIN_MESSAGE);
            checkHighscore();
            abortGame();
        }
    }

    // check if the user gets to be on highscorelist
    private void checkHighscore()
    {
        // get time and convert to seconds
        int score = mGUI.getTimePanel().getHours() * 3600 + mGUI.getTimePanel().getMinutes() * 60 + mGUI.getTimePanel().getSeconds();
        if (score < mScores.get(mScores.size() - 1)) // check if user made it to the top ten list
        {
            String name = JOptionPane.showInputDialog(null, "Skriv in ditt namn", "Grattis du kom in på Highscorelistan!", JOptionPane.PLAIN_MESSAGE);
            if (name == null || name.equals(""))
            {  // if the user leave name blank, name him
                name = "Unknown sudokumaster";
            }
            // find the position for the score
            int i = 0;
            while (score > mScores.get(i))
            {
                ++i;
            }
            mScores.remove(9); // remove the last index
            mNames.remove(9);
            mScores.add(i, score); // add the user to position
            mNames.add(i, name);
            // make new list
            mWriter.clearLists(); // clear lists just in case
            setHighscoreList();
        }
    }

    // creates the highscorelist
    private void loadHighscore()
    {
        mReader.highscoreToArray(mScores, mNames);
    }

    // shows the helptext for the user, called by the "Hjälp" button
    private void help()
    {
        // create a JTextArea
        JTextArea textArea = new JTextArea(30, 70);
        textArea.setText(mReader.getText("documents/help.txt"));
        Border border = BorderFactory.createLineBorder(Color.BLACK, 3, true);
        textArea.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        textArea.setEditable(false);
        // wrap a scrollpane around it
        JScrollPane scrollPane = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(null, scrollPane, "Om SudokuSolver", JOptionPane.PLAIN_MESSAGE);
    }

    // exit application and confirm that user really want to quit
    private void close()
    {    // make sure the user wants to quit
        int reply = JOptionPane.showConfirmDialog(null, "<html>Är du säker på att du vill avsluta?<br> Pågående spel sparas ej</html>", "Sluta nu?", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION)
        {
            System.exit(0);
        }
    }
    
    private class Action implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            // check which JButton that was pressed and take action
                JButton pressed = (JButton) e.getSource();
                switch (pressed.getName())
                {
                    case "newGame":
                        runNewGame();
                        break;
                    case "addGame":
                        saveNewGame();
                        break;
                    case "clear":
                        clearBoard();
                        break;
                    case "solve":
                        solveGame();
                        break;
                    case "load":
                        runSavedGame();
                        break;
                    case "save":
                        saveGame();
                        break;
                    case "high":
                        setHighscoreList();
                        break;
                    case "restart":
                        restartGame();
                        break;
                    case "abort":
                        abortGame();
                        break;
                    case "help":
                        help();
                        break;
                    case "quit":
                        close();
                        break;
        }
        
    }
}
}
