package projectsudoku;

import java.io.Serializable;


/**
 * The ClockDisplay class implements a digital clock display for a
 * European-style 24 hour clock. The clock shows hours and minutes. The 
 * range of the clock is 00:00 (midnight) to 23:59 (one minute before 
 * midnight).
 * 
 * The clock display receives "ticks" (via the timeTick method) every minute
 * and reacts by incrementing the display. This is done in the usual clock
 * fashion: the hour increments when the minutes roll over to zero.
 * 
 * @author Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */
public final class TimerDisplay implements Serializable
{
    private NumberDisplay mHours;
    private NumberDisplay mMinutes;
    private NumberDisplay mSeconds;
    private String displayString;    // simulates the actual display
    
    /**
     * Constructor for ClockDisplay objects. This constructor 
     * creates a new clock set at 00:00.
     */
    public TimerDisplay()
    {
        mHours = new NumberDisplay(24);
        mMinutes = new NumberDisplay(60);
        mSeconds = new NumberDisplay(60);
        updateDisplay();
    }

    /**
     * This method should get called once every minute - it makes
     * the clock display go one minute forward.
     */
    public void timeTick()
    {
        mSeconds.increment();
        if(mSeconds.getValue() == 0) {  // it just rolled over!
            mMinutes.increment();
            if(mMinutes.getValue() == 0)
            {
                mHours.increment();
            }
        }
        updateDisplay();
    }

    /**
     * Set the time of the display to the specified hour and
     * minute.
     */
    public void setTime(int inHour, int inMinute, int inSecond)
    {
        mHours.setValue(inHour);
        mMinutes.setValue(inMinute);
        mSeconds.setValue(inSecond);
        updateDisplay();
    }

    /**
     * Return the current time of this display in the format HH:MM.
     */
    public String getTime()
    {
        return displayString;
    }
    
    public int getHours()
    {
        return mHours.getValue();
    }
    
    public int getMinutes()
    {
        return mMinutes.getValue();
    }
    
    public int getSeconds()
    {
        return mSeconds.getValue();
    }
    
    /**
     * Update the internal string that represents the display.
     */
    private void updateDisplay()
    {
        displayString = mHours.getDisplayValue() + ":" + 
                        mMinutes.getDisplayValue() + ":" +
                        mSeconds.getDisplayValue();
    }
}
