
package projectsudoku;

import java.awt.Font;
import javax.swing.JLabel;

/**
 * TimePanel extends JLabel and
 * contains a display that shows hours, minutes and seconds.
 * The code is origanated from the Clock-with-display project
 * in BlueJ
 * @author Thomas Jakobsson
 * @version 2013-01-15
 */
public class TimePanel extends JLabel 
{
    private TimerDisplay mDisplay;
    private boolean mRunning;
    private TimerThread mTimeThread;

    /**
     * Creates a TimePanel
     */
    public TimePanel()
    {
        mDisplay = new TimerDisplay();
        mRunning = false;
        mTimeThread = new TimerThread();
        setText(mDisplay.getTime());
        setFont(new Font("Dialog", Font.BOLD, 20)); // change the look
    }
   
    /**
     * Starts the clock
     */
    public void start()
    {

        mRunning = true;
        mTimeThread = new TimerThread();
        mTimeThread.start();
    }
    
    
    /**
     * Stops the clock
     */
    public void stop()
    {
        mRunning = false;
    }
    
    public void newTimeThread()
    {
        mTimeThread = new TimerThread();
    }
    
    /**
     * Resets the clock to zero
     */
    public void reset()
    {
        mDisplay.setTime(0, 0, 0);
        setText(mDisplay.getTime());
    }
    
    /**
     * Returns value of hours
     * @return int value of hours
     */
    public int getHours()
    {
        return mDisplay.getHours();
    }
    
    /**
     * Returns value of minutes
     * @return int value of minutes
     */
    public int getMinutes()
    {
        return mDisplay.getMinutes();
    }
    
    /**
     * Returns value of seconds
     * @return int value of seconds
     */
    public int getSeconds()
    {
        return mDisplay.getSeconds();
    }
    
    /**
     * Ticks the clock one step
     */
    private void step()
    {
        mDisplay.timeTick();
        setText(mDisplay.getTime());
    }
    
    public TimerDisplay getDisplay()
    {
        return mDisplay;
    }

            // inner class for running the clock
            class TimerThread extends Thread
    {
        // while running clock ticks every second
                @Override
        public void run()
        {
            while (mRunning) {
                pause();
                if(mRunning) // if mRunning is set to false while pausing
                {
                step();
                }
            }
        }
        
                // pauses the clock one second(1000ms)
        private void pause()
        {
            try {
                Thread.sleep(1000);   // pause for 1000 milliseconds
            }
            catch (InterruptedException ignore) {
                // do nothing
            }
        }
    }
}
