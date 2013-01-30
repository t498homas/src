package projectsudoku;

import java.io.Serializable;

/**
 * Represents a game containing SudokuButtons and variables for setting the
 * TimePanel. Used when saving games
 *
 * @author Thomas Jakobsson
 * @version 2013-01-20
 */
public class Game implements Serializable
{

    private SudokuButton[][] mButtons;
    private int mHours;
    private int mMinutes;
    private int mSeconds;

    /**
     * Creates a Game to be saved
     *
     * @param inButtons SudokuButton[][] SudokuButtons to be saved
     * @param inHours int value in hours
     * @param inMinutes int value in minutes
     * @param inSeconds int value in seconds
     */
    public Game(SudokuButton[][] inButtons, int inHours, int inMinutes, int inSeconds)
    {
        mButtons = inButtons;
        mHours = inHours;
        mMinutes = inMinutes;
        mSeconds = inSeconds;
    }

    public SudokuButton[][] getButtons()
    {
        return mButtons;
    }

    public int getHours()
    {
        return mHours;
    }

    public int getMinutes()
    {
        return mMinutes;
    }

    public int getSeconds()
    {
        return mSeconds;
    }
}
