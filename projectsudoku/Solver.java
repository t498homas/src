package projectsudoku;

/**
 * Solver creates solutions for sudokus.
 * It also check if a given sudoku is valid and
 * can be solved.
 * The main part of the code is written by XXX
 * and is to be found at www.XXXXXXXXXxx
 *
 * @author Thomas Jakobsson
 * @version 2012-01-25
 */
public class Solver
{

    private int[][] mValues;
    private int mTimesVisited;
    private int mFirstEmptyRow;
    private int mFirstEmptyCol;
    private boolean mFound;

    /**
     * Creates a default Solver
     */
    public Solver()
    {
        mTimesVisited = 0;
        mFirstEmptyRow = 0;
        mFirstEmptyCol = 0;
        mFound = false;
    }

    public void run(int[][] inValues) throws Exception
    {

        mValues = inValues;

        // find first empty cell---------------------------------------
        for (int r = 0; r < 9; r++)
        {
            for (int c = 0; c < 9; c++)
            {
                if (mValues[r][c] == 0 && !mFound)
                {
                    mFirstEmptyRow = r;
                    mFirstEmptyCol = c;
                    System.out.println("rad " + r + " col " + c);
                    mFound = true;
                }
            }
        }

        try
        {
            // Start to solve the puzzle in the left upper corner
            solve(0, 0);
        } catch (Exception e)
        {
            switch (e.getMessage())
            {
                case "Solution not found":
                    mTimesVisited = 0;
                    System.out.println("ingen lösming hittad");
                    throw new Exception("Solution not found");
                    
                case "Solution found":
                    System.out.println("lösning hittad");
                    
                    break;
                case "number in conflict":
                    mTimesVisited = 0;
                    System.out.println("konflikt");
                    throw new Exception("Numbers in conflict");      
            }
        }
    }

    /**
     * Recursive function to find a valid number for one single cell
     */
    public void solve(int row, int col) throws Exception
    {
        try
        {
            checkNumbers();
        } catch (Exception e)
        {
            throw e;
        }
        // Throw an exception to stop the process if the puzzle is solved
        if (row > 8)
        {
            throw new Exception("Solution found");
        }

        // If the cell is not empty, continue with the next cell
        if (mValues[row][col] != 0)
        {
            next(row, col);
        } else
        {
            // Find a valid number for the empty cell
            for (int num = 1; num < 10; num++)
            {
                if (row == mFirstEmptyRow && col == mFirstEmptyCol)
                {
                    mTimesVisited++;
                }
                if (checkRow(row, num) && checkCol(col, num) && checkBox(row, col, num))
                {
                    mValues[row][col] = num;
                    // Delegate work on the next cell to a recursive call
                    next(row, col);
                }

                // No valid number was found, clean up and return to caller 
            }
            if (mValues[mFirstEmptyRow][mFirstEmptyCol] == mValues[row][col] && mTimesVisited == 9)
            {
                throw new Exception("Solution not found");
            }
            mValues[row][col] = 0;
        }
    }

    /**
     * Calls solve for the next cell
     */
    public void next(int row, int col) throws Exception
    {
        if (col < 8)
        {
            solve(row, col + 1);
        } else
        {
            solve(row + 1, 0);
        }
    }

    /**
     * Checks if num is an acceptable value for the given row
     */
    protected boolean checkRow(int row, int num)
    {
        boolean isGood = true;
        for (int col = 0; col < 9; col++)
        {
            if (mValues[row][col] == num)
            {
                isGood = false;
            }
        }

        return isGood;
    }

    /**
     * Checks if num is an acceptable value for the given row
     */
    protected boolean checkNumRow(int row, int num)
    {
        boolean isGood = true;
        int foundTimes = 0;
        for (int col = 0; col < 9; col++)
        {
            if (mValues[row][col] == num)
            {
                foundTimes++;
            }
        }
        if (foundTimes > 1)
        {
            isGood = false;
        }

        return isGood;
    }

    /**
     * Checks if num is an acceptable value for the given column
     */
    protected boolean checkCol(int col, int num)
    {
        boolean isGood = true;
        for (int row = 0; row < 9; row++)
        {
            if (mValues[row][col] == num)
            {
                isGood = false;
            }
        }
        return isGood;
    }

    /**
     * Checks if num accurs more than once for the given column
     */
    protected boolean checkNumCol(int col, int num)
    {
        boolean isGood = true;
        int foundTimes = 0;
        for (int row = 0; row < 9; row++)
        {
            if (mValues[row][col] == num)
            {
                foundTimes++;
            }
        }
        if (foundTimes > 1)
        {
            isGood = false;
        }
        return isGood;
    }

    /**
     * Checks if num is an acceptable value for the box around row and col
     */
    protected boolean checkBox(int row, int col, int num)
    {
        boolean isGood = true;
        row = (row / 3) * 3;
        col = (col / 3) * 3;

        for (int r = 0; r < 3; r++)
        {
            for (int c = 0; c < 3; c++)
            {
                if (mValues[row + r][col + c] == num)
                {
                    isGood = false;
                }
            }
        }

        return isGood;
    }

    /**
     * Checks if num is an acceptable value for the box around row and col
     */
    protected boolean checkNumBox(int row, int col, int num)
    {
        int foundTimes = 0;
        boolean isGood = true;
        row = (row / 3) * 3;
        col = (col / 3) * 3;

        for (int r = 0; r < 3; r++)
        {
            for (int c = 0; c < 3; c++)
            {
                if (mValues[row + r][col + c] == num)
                {
                    foundTimes++;
                }
            }
        }
        if (foundTimes > 1)
        {
            isGood = false;
        }

        return isGood;
    }

    public int getSolution(int inRow, int inCol)
    {
        return mValues[inRow][inCol];
    }

    public boolean checkNumbers() throws Exception
    {
        boolean isGood = true;
        for (int r = 0; r < 9; r++)
        {
            for (int c = 0; c < 9; c++)
            {
                int num = mValues[r][c];
                //System.out.println(num + "rad" + r + "col" + c);
                if (num != 0)
                {

                    if (!checkNumRow(r, num) || !checkNumCol(c, num) || !checkNumBox(r, c, num))
                    {
                        isGood = false;
                        throw new Exception("number in conflict");
                    }
                }
            }
        }
        return isGood;
    }
}
