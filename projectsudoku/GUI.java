package projectsudoku;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.event.ChangeListener;

/**
 * This class represents the GUI of the sudokugame It contains the board and
 * timepanel
 *
 * @author Thomas Jakobsson
 * @version 2012-01-15
 */
public class GUI extends JFrame
{

    private Board mBoard;
    private TimePanel mTimePanel;
    private ActionListener mAL;

    /**
     * Creates a default GUI
     *
     * @param inAL ActionListner to be added to controlbuttons
     * @param inCL ChangeListener to be added to SudokuButtons
     * @throws IOException
     */
    public GUI(ActionListener inAL, ChangeListener inCL) throws IOException
    {

        //----Set up board ------------------------------
        mBoard = new Board(GUI.getImage("center.gif"), mAL, inCL);
        mTimePanel = new TimePanel();
        mAL = inAL;
        //---------North-----------------------------------
        BackgroundPanel north = new BackgroundPanel(GUI.getImage("north.gif"));
        JLabel logo = new JLabel();
        logo.setIcon(new ImageIcon(GUI.getImage("sudoku.gif")));
        north.add(logo);
        add(north, BorderLayout.NORTH);
        //------Center--------------------------------------
        add(mBoard, BorderLayout.CENTER);
        //------ WestPanel ----------------------------------
        BackgroundPanel west = new BackgroundPanel(GUI.getImage("west.gif"));
        west.setLayout(new GridLayout(7, 0, 10, 10));
        west.setPreferredSize(new Dimension(130, 400));
        west.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15)); // set some space between buttons
        west.add(makeButton("newGame", "new.gif"));
        west.add(makeButton("addGame", "add.gif"));
        west.add(makeButton("clear", "clear.gif"));
        west.add(makeButton("solve", "solve.gif"));
        west.add(makeButton("load", "load.gif"));
        west.add(makeButton("save", "save.gif"));
        west.add(makeButton("high", "high.gif"));
        add(west, BorderLayout.WEST);
        //----East---------------------------------------------
        BackgroundPanel east = new BackgroundPanel(GUI.getImage("east.gif"));
        east.setLayout(new GridLayout(7, 0, 10, 10));
        east.setPreferredSize(new Dimension(130, 400));
        east.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15)); // set some space between components
        JLabel timehead = new JLabel("Tidtagning: ");
        timehead.setFont(new Font("Dialog", Font.BOLD, 16)); // nicer font
        east.add(timehead);
        east.add(mTimePanel);
        east.add(makeButton("restart", "restart.gif"));
        east.add(makeButton("abort", "abort.gif"));
        add(east, BorderLayout.EAST);
        //---------South---------------------------------------
        BackgroundPanel south = new BackgroundPanel(GUI.getImage("south.gif"));
        south.setLayout(new FlowLayout(WIDTH, 25, 5));
        south.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 15));
        south.add(makeButton("help", "help.gif"));
        south.add(makeButton("quit", "quit.gif"));
        add(south, BorderLayout.SOUTH);

        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/projectsudoku/images/logo.gif"))); // sets an icon in main window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // makes sure the application closes properly
        setResizable(false); // prevents the user from changing size of the window
        pack();
        setLocationRelativeTo(null); // makes the window open in the centre of the screen
        setVisible(true);
    }

    // method for creating SudokuButtons
    private JButton makeButton(String inName, String inPath) throws IOException
    {
        JButton button = new JButton();
        button.setName(inName);
        button.setBorder(BorderFactory.createEmptyBorder());
        String path = "/projectsudoku/images/" + inPath;
        button.setIcon(new ImageIcon(ImageIO.read(this.getClass().getResource(path))));
        button.addActionListener(mAL);
        return button;
    }

    /**
     * Returns the Board in GUI
     * @return Board Board in GUI
     */
    public Board getBoard()
    {
        return mBoard;
    }

    /**
     * Returns the TimePanel in GUI
     * @return TimePanel TimePanel in GUI
     */
    public TimePanel getTimePanel()
    {
        return mTimePanel;
    }

    /**
     * Static method for getting Images
     * @param inName String name of image
     * @return Image 
     * @throws IOException
     */
    public static Image getImage(String inName) throws IOException
    {
        return ImageIO.read(GUI.class.getResource("/projectsudoku/images/" + inName));
    }
}
