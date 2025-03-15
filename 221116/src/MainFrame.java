import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainFrame  extends JFrame implements ActionListener {
    //Declare two panel objects, named pSouth,
    // pNorth respectively,
    // for the two positions of the panel.
    private JPanel pSouth,pNorth;
    //Declare four button keys,
    // which are used to let the user select the first, previous, next and last level.
    private JButton btnFirst, btnPrev, btnNext, btnLast;
    //The mainpanel class is where users play their main games.
    private MainPanel mainPanel;
    //The playmusic class is where users choose music.
    private PlayMusic playMusic;
    //Menubar, which stores three options.
    private JMenuBar jmb;
    //Declare three options for users to choose,
    // and the user can choose help or exit the game.
    private JMenu file,edit,exit;
    //The lower level can select help information.
    private JMenuItem openfile,closefile,help;
    //Declare a variable of type int to control the level of the level.
    private int gate=1;
    //Constructor to initialize the declared variables
    // and add some space to the main page.
    public  MainFrame() throws IOException {
    //Initialize the mainpanel class to display
    // the main game implementation in the center of the main form.
    mainPanel=new MainPanel();
    //Initialize the playlist class to
    // enable the function of playing music to
    // be realized on the left (west) side of the main window.
    playMusic=new PlayMusic();
    /*
    Instantiate the north and south panel and
     initialize them in grid layout.
     The four buttons, menu bar and
     the number of current levels are displayed respectively.
    */
    pSouth=new JPanel(new GridLayout(1,6,5,5));
    pNorth=new JPanel(new GridLayout(1,2,5,5));
    btnFirst=new JButton("First");
    btnPrev=new JButton("Prev");
    btnNext=new JButton("Next");
    btnLast=new JButton("Last");
    //Add four buttons to the action monitoring queue.
    // The system allows them to trigger corresponding actions after being clicked.
    btnFirst.addActionListener(this);
    btnPrev.addActionListener(this);
    btnNext.addActionListener(this);
    btnLast.addActionListener(this);


    jmb=new JMenuBar();
    file=new JMenu("File");
    edit=new JMenu("Edit");
    exit=new JMenu("Exit");
    openfile=new JMenuItem("Open");
    closefile=new JMenuItem("Close");
    help=new JMenuItem("Help");
    //Add menu options to the menu bar.
    jmb.add(file);
    jmb.add(edit);
    jmb.add(exit);
    //Add secondary options under the file option.
    file.add(openfile);
    file.addSeparator();
    file.add(closefile);
    file.add(help);
    //Monitor the actions of sub menu options.
    // When these options are clicked,
    // corresponding events will be triggered respectively.
    closefile.addActionListener(this);
    exit.addActionListener(this);
    help.addActionListener(this);
    //Add the btnUndo button
    // to the playback panel and place it below cbomusic and jplay.
    playMusic.add(mainPanel.btnUndo);
    playMusic.jplay.addActionListener(this);
    //Add event listeners to the cbomusic and btnUndo keys.
    // After clicking or adjusting the options in the drop-down dialog box,
    // the system executes their functions respectively.
    playMusic.cbomusic.addActionListener(this);
    mainPanel.btnUndo.addActionListener(this);
    //Add the required buttons on pSouth panel.
    pSouth.add(btnFirst);
    pSouth.add(btnPrev);
    pSouth.add(btnNext);
    pSouth.add(btnLast);
    //Add the required buttons on pNorth panel.
    pNorth.add(jmb);
    pNorth.add(mainPanel.jgate);

    //Use the border layout layout to
    // place pSouth in the south of the main interface,
    // pNorth in the north of the main interface,
    // the main body of the game in the middle of the page,
    // and music playback and cancellation operations in the west of the interface.
    this.add(pSouth,"South");
    this.add(pNorth,"North");
    this.add(mainPanel,"Center");
    this.add(playMusic,"West");
    //Set the parameters of the main interface.
        // Set the main interface to be visible,
        // and set its width to 700 and height to 550.
        // Set the default closing method to
        // click the upper right corner of the game page to exit.
        // Set the game interface to open in the middle of the screen by default.
        //Set the name of the game window to Sokoban Version 3 Zhao Shirui-25
    this.setVisible(true);
    this.setSize(700,550);
    this.setDefaultCloseOperation(3);
    this.setLocationRelativeTo(null);
    this.setTitle("Sokoban Version 3 Zhao Shirui-25");
    //Set the game to open automatically and focus on the mainpanel.
    mainPanel.requestFocus();
    mainPanel.requestFocus();
}
    //Main function to instantiate a MainFrame object.
    public static void main(String[] args) throws IOException {
        new MainFrame();
    }

    //Listening function, whose return type is void,
    //is used to listen to each key.
    @Override
    public void actionPerformed(ActionEvent e) {
        //When the btnfirst button is pressed,
        // the game jumps to the first level.
        if(e.getSource()==btnFirst)
        {
            //Set the current level to 1
            gate=1;
            //Set the label jgate of the mainpanel to display as 1
            mainPanel.jgate.setText("Gate: "+gate);
            //Reset the pedometer of the mainpanel to start from zero.
            mainPanel.step=0;
            //Clear the stack.
            mainPanel.undo.clear();
            try {
                //Execute the initialization function in mainpanel.
                mainPanel.init(gate);
                //Re focus on the mainpanel.
                mainPanel.requestFocus();
                mainPanel.requestFocus();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        //When the btnPrev button is pressed,
        // the game jumps to the previous level.
        // If the current level is already the first level,
        // it only returns to the first level.
        else if(e.getSource()==btnPrev)
        {
            //Set the current level minus 1
            gate--;
            //Reset the pedometer of the mainpanel to start from zero.
            mainPanel.step=0;
            //Clear the stack.
            mainPanel.undo.clear();
            //If the current level is less than or equal to 1, set the level to 1
            if(gate<=1)
                gate=1;
            //The current level number is displayed at the top of the interface.
            mainPanel.jgate.setText("Gate: "+gate);
            try {
                //Execute the initialization function in mainpanel.
                mainPanel.init(gate);
                //Re focus on the mainpanel.
                mainPanel.requestFocus();
                mainPanel.requestFocus();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        //When the btnNext button is pressed,
        // the game jumps to the next level.
        // If the current level is already the last level,
        // it only returns to the last level.
        else if(e.getSource()==btnNext)
        {
            //Set level number plus 1
            gate++;
            //Reset the pedometer of the mainpanel to start from zero.
            mainPanel.step=0;
            //Clear the stack.
            mainPanel.undo.clear();
            //If the current level is greater than or equal to 5, set the level to 5
            if(gate>=5)
                gate=5;
            //The current level number is displayed at the top of the interface.
            mainPanel.jgate.setText("Gate: "+gate);
            try {
                //Execute the initialization function in mainpanel.
                mainPanel.init(gate);
                //Re focus on the mainpanel.
                mainPanel.requestFocus();
                mainPanel.requestFocus();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        //When the btnLast button is pressed,
        // the game jumps to the last level.
        else if(e.getSource()==btnLast)
        {
            //Set the number of levels to 5
            gate=5;
            //Reset the pedometer of the mainpanel to start from zero.
            mainPanel.step=0;
            //The current level number is displayed at the top of the interface.
            mainPanel.jgate.setText("Gate: "+gate);
            //Clear the stack.
            mainPanel.undo.clear();
            try {
                //Execute the initialization function in mainpanel.
                mainPanel.init(gate);
                //Re focus on the mainpanel.
                mainPanel.requestFocus();
                mainPanel.requestFocus();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        //Click the music selection control and refocus to the mainpanel.
        else if(e.getSource()==playMusic.jplay)
                mainPanel.requestFocus();
       // Click the control music playback selection control and refocus to the mainpanel.
        else if(e.getSource()==playMusic.cbomusic)
                mainPanel.requestFocus();
        //Click Undo to execute the undo function and focus on the mainpanel.
        else if(e.getSource()==mainPanel.btnUndo)
        {
            //Execute the undo function of mainpanel
            mainPanel.undoProcess();
            mainPanel.requestFocus();
        }
        //After clicking these menu controls,
        //close the game. If help is clicked,
        // the help dialog box will pop up.
        if(e.getSource()==closefile)
        {
            System.exit(0);
        }
        if (e.getSource()==exit)
        {
            System.exit(0);
        }
        //If the key clicked is help in the menu bar, a help dialog box will pop up.
        if (e.getSource()==help)
        {
            JOptionPane.showMessageDialog(null,"The up, down, left and right buttons " +"\n"+
                    "on the keyboard can control the warehouse keeper to push the box. " +"\n"+
                    "Click first to return to the first level, click prev to return to the previous level, " +"\n"+
                    "next to the next level, and last to the last level. You can select three pieces of music " +"\n"+
                    "to play or pause. The operation can be rolled back.");
        }
        //Re focus on the mainpanel.
        mainPanel.requestFocus();
    }
}


