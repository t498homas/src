/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projectsudoku;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * SudokuButton represents a field on a sudokuboard
 * When clicked it increases value by one and flips
 * to zero when passing number nine
 * @author Thomas Jakobsson
 * @version
 */
public class SudokuButton extends JButton implements Serializable
{

    private boolean mLocked;
    private int mValue;

    /**
     * Creates a defualt SudokuButton that
     * flips to zero when passing nine
     * @throws IOException
     */
    public SudokuButton() throws IOException
    {
        mValue = 0; // zero equals no number in square
        setIcon(new ImageIcon(ImageIO.read(this.getClass().getResource("/projectsudoku/images/blank.gif"))));
        mLocked = false;
        setRolloverEnabled(false);
        setPreferredSize(new Dimension(10, 10));
        updateActionListener();
    }

    /**
     * Returns if the SudokuButton is locked or not
     * @return boolean true if locked , false if unlocked
     */
    public boolean isLocked()
    {
        return mLocked;
    }

    /**
     * Adds an ActionListener that calls the method
     * to change the buttons value
     */
    public void updateActionListener()
    {
        addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                if (!mLocked) // dont change value if locked
                {
                    changeValue();
                }
            }
        });
        
    }
    
    /**
     * Sets wheter a button is locked or not
     * @param inState boolean 
     */
    public void setLocked(boolean inState)
    {
        mLocked = inState;
    }

    /**
     * Changes the value of the button
     * When called value increases by one or if
     * value is 9 it flips to zero
     */
    public void changeValue()
    {
        if (mValue == 9) // flip to zero if nine
        {
            mValue = 0;
            try
            {
                setIcon(new ImageIcon(ImageIO.read(this.getClass().getResource("/projectsudoku/images/blank.gif"))));
            } catch (IOException ex)
            {
                Logger.getLogger(SudokuButton.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else // increase value by one
        {
            mValue++;
            String img = "/projectsudoku/images/" + mValue + ".gif"; // get the corresponding image
            try
            {
                setIcon(new ImageIcon(ImageIO.read(this.getClass().getResource(img)))); 
            } catch (IOException ex)
            {
                //Logger.getLogger(SudokuButton.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * set value and updates image so it corresponds to
     * locked or unlocked status
     * @param inValue
     */
    public void setValueAndUpdate(int inValue)
    {
        mValue = inValue;
        setImage();
    }

    public void setImage()
    {
       
            String img;
    
            if (mValue == 0)
            {
                img = "/projectsudoku/images/blank.gif";
                System.out.println("en nolla");
            } else 
            {       
                if(mLocked)
                {
                    img = "/projectsudoku/images/" + mValue + "Lock.gif";
                    System.out.println("en låst");
                }
                else
                {
                    img = "/projectsudoku/images/" + mValue + ".gif";
                    System.out.println("en öppen");
                    
            }
            }  
                try
                {
                    ImageIcon image = new ImageIcon(ImageIO.read(this.getClass().getResource(img)));
                    setIcon(image);
                } catch (IOException | IllegalArgumentException ex)
                {
                    
                }
                
        repaint();
    }
    
    

    /**
     * sets value
     * @param inValue int value
     */
    public void setValue(int inValue)
    {
        mValue = inValue;
    }

    /**
     * Returns value
     * @return int value
     */
    public int getValue()
    {
        return mValue;
    }
}
