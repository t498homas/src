/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projectsudoku;

import java.awt.Graphics;
import java.awt.Image;
import java.io.Serializable;
import javax.swing.JPanel;

/**
 * BackgroundPanel overrides the paintComponent method
 * in Jpanel. It suppleis an easy way to put an Imgage as 
 * background om the Panel.
 * @author Thomas Jakobsson
 * @version 2013-01-18
 */
public class BackgroundPanel extends JPanel implements Serializable
{
    private Image mImg;
    
    /**
     * Sets an Image as backgroung in contentPane
     * @param inImg Image The image to be set as background
     */
    public BackgroundPanel(Image inImg)
    {
        mImg = inImg;
    }
    
   
   
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(mImg, 0, 0, getWidth(), getHeight(), this);
    }
    


}
