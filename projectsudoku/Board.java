
package projectsudoku;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ChangeListener;

/**
 * Board represents a 9 x 9 grid sudokuboard containing 
 * SudokuButtons and a backgoundimage. 
 * Board can also reset buttons and change the buttons state 
 * locked/unlocked.
 *
 * @author Thomas Jakobsson
 * @version 2012-01-12
 */
public class Board extends BackgroundPanel
{

    private SudokuButton[][] mButtons;
//    private ActionListener mAL;
    private ChangeListener mCL;

    
    /**
     * Creates a sudokuboard with blank buttons
     * and the supplied image as background
     * @param inImg Imgage to be set as background
     * @param inAL ActionListener to added to buttons
     * @param inCL ChangeListener to be added to buttons
     */
    public Board(Image inImg, ActionListener inAL, ChangeListener inCL)
    {
        super(inImg); // set the image in BackgroundPanel
        mButtons = new SudokuButton[9][9]; // two-dimensional array for buttons
//        mAL = inAL;
        mCL = inCL;
        setLayout(new GridLayout(9, 9, 0, 0)); // set the 9x9 grid
        setPreferredSize(new Dimension(390, 390));
        try
        {
            setUpBoard();
        } catch (IOException ex)
        {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Method for board setup.
    // Creats and add the SudokuButtons to board
    // It also adds the listeners to SudokuButtons
    private void setUpBoard() throws IOException
    {
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                mButtons[i][j] = new SudokuButton();
                String ac = i + " " + j;
                mButtons[i][j].setActionCommand(ac);
                mButtons[i][j].addChangeListener(mCL);
                add(mButtons[i][j]);
            }
        }
    }

    /**
     * Re-adds updated buttons to board
     */
    public void boardUpdate()
    {
        removeAll(); // make sure the bord is empty
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                // update buttons and add
                mButtons[i][j].updateActionListener();
                mButtons[i][j].addChangeListener(mCL);
                add(mButtons[i][j]);
            }
        }
        repaint(); // repaint board with current buttons
    }

    
    /**
     * Returns SudokuButton[][] contaning the buttons in board
     * @return SudokuButton[][] Current boardButtons
     */
    public SudokuButton[][] getButtons()
    {
        return mButtons;
    }

    /**
     * Sets the provided buttons to board
     * @param inButtons SudokuButton[][] Buttons to be added
     */
    public void setButtons(SudokuButton[][] inButtons)
    {
        mButtons = inButtons;
        upDateButtonImages();
    }

    /**
     * Resets all button in bord to initzial state(sets buttonvalues to zero)
     */
    public void resetAllButtons()
    {
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                mButtons[i][j].setValueAndUpdate(0);
                mButtons[i][j].setImage();
                mButtons[i][j].setLocked(false);
            }
        }
    }
    
    
    /**
     * Resets all unlocked button. Used to clear button during active game
     */
    public void resetUnlockedButtons()
    {
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                if (!mButtons[i][j].isLocked())
                {
                    mButtons[i][j].setValueAndUpdate(0);
                    mButtons[i][j].setImage();
                }
            }
        }
    }
    
    // Updates images on buttons
    private void upDateButtonImages()
    {
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                mButtons[i][j].setIcon(null);
                mButtons[i][j].setImage();
            }
        }
    }
    
    /**
     * Change buttons with value>0 to locked
     */
    public void lockShowingNumbers()
    {
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                if (mButtons[i][j].getValue() > 0)
                {
                    mButtons[i][j].setLocked(true);
                }
            }
        }
    }

    /**
     * Returns SudokuButton at given position
     * @param inRow int Row
     * @param inCol int Column
     * @return SudokuButton at given position
     */
    public SudokuButton getButton(int inRow, int inCol)
    {
        return mButtons[inRow][inCol];
    }
}
