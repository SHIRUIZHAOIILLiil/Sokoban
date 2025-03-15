import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Stack;

public class MainPanel extends JPanel implements KeyListener {
    //Variables of type int,
    // used to record the number of rows,
    // columns, the number of holes on the map,
    // and the full hole position of the fifth map.
    private int row=10,col=13,hole,x,y;
    //An array of type int is used to receive a
    // two-dimensional array of map data from the ReadMaps class.
    private int [][]rawMap=new int [row][col];
    //An array of type int, used to record the original map style.
    // When players move the warehouse keeper,
    // the system can rely on this array to restore the moved map to its original state.
    private int [][]data;
    //An array of int types,
    // used to record the status of players after moving.
    // After the player operates on the warehouse keeper,
    // the map will change to the shape after the player moves according to this array.
    private int [][]temp;
    //Variables of type int, used to record the x and y coordinates of the warehouse keeper.
    private int WX,WY;
    //The label class is used to display the current level number.
    public JLabel jgate;
    //Button class, click it to recall the previous step.
    public JButton btnUndo;
    //Read the file class, and store the read external map into the array.
    private ReadMaps readmaps;
    //The stack class is used to store the operation number of each key press.
    public Stack<Integer> undo=new Stack<>();
    //String type variable, used to record the path of the map image.
    private String path="221116\\src\\OOPPicture\\";
    //The int type attribute is used to match
    // whether the number of boxes and bits is the same.
    // When the number is different,
    // the default warehouse keeper is standing on a pit.
    private int box=0,pit=0;
    //Variables of type int, used to display the 30level and the number of moves.
    public int gate=1,step=0;
    //Set the Boolean variable judge. When the judge is true, the hole increases by one. That is, a pit is full at the beginning of the game. You must push it again to clear customs.
    private boolean judge=false;
    //Image type array, used to record map pictures.
    Image[] pics={
            new ImageIcon(path+"img1.png").getImage(),//floor0
            new ImageIcon(path+"img2.png").getImage(),//wall1
            new ImageIcon(path+"img6.png").getImage(),//top2
            new ImageIcon(path+"img7.png").getImage(),//down3
            new ImageIcon(path+"img8.png").getImage(),//left4
            new ImageIcon(path+"img9.png").getImage(),//right5
            new ImageIcon(path+"img3.png").getImage(),//hole6
            new ImageIcon(path+"img5.png").getImage(),//full7
            new ImageIcon(path+"img4.png").getImage(),//box8
            new ImageIcon(path+"img5.png").getImage()//full9
    };
    //Constructor to instantiate the declared class
    // and focus on this class when calling it.
    public MainPanel() throws IOException {
        data=new int[row][col];
        temp=new int[row][col];
        //The current level number is displayed at the top of the interface.
        jgate=new JLabel("Gate: "+gate);
        //Initialize the button, and the displayed name is Undo
        btnUndo=new JButton("Undo");
        //Call the init function to
        // initialize the map by passing the data of the current level.
        init(gate);
        //Add a key listener.
        // When the user operates
        // the warehouse administrator on the keyboard from top to bottom,
        // left to right, the system can receive the key parameters and perform the next operation.
        addKeyListener(this);
        //Make the game focus on the mainpanel by default at the beginning.
        requestFocus();
    }
    //The initialization function
    // will assign a value to the level
    // and display the map data read on the main interface.
    public void init(int gate) throws IOException {
        //The parameter is passed to
        // the attribute gate to set the current level.
        this.gate=gate;
        //Each time the init function is called, the initial values of pit and box are 0.
        pit=0;
        box=0;
        //Read the corresponding map information according to
        // the passed level parameters, and put the read data
        // into the passed parameter array.
        readmaps=new ReadMaps(this.gate,rawMap);
        judge=false;
        copyData(rawMap);
        //Execute the paint function again.
        repaint();
    }
    //Transfer the read map information to the other two two-dimensional arrays.
    // One is used to save the original map information,
    // and the other is used to display the map information after the player's operation.
    //If the number of boxes read does not match the number of pits,
    // set the player's position to the pit's position in the array where the map information is saved.
    // If the number read from the transfer data array is 9,
    // the array where the map information is saved will be set to the pit state.
    // If the player does not push the box again, the game cannot be judged as over.
    private void copyData(int[][]rawdata)
    {
        for(int i=0;i<row;i++)
        {
            for (int j=0;j<col;j++)
            {
                //Assign map information to the array that players can operate.
                temp[i][j]=rawdata[i][j];
                //If the number on the map is 6 or 8,
                // the pit or box attribute increases by 1.
                if(rawdata[i][j]==6)
                    pit++;
                if (rawdata[i][j]==8)
                    box++;

                //If the location of the warehouse keeper is read,
                // record its location in WX and WY, and classify
                // the location of the warehouse keeper as the floor
                // in the original array where the map information is saved.
                if(rawdata[i][j]==2||rawdata[i][j]==3||rawdata[i][j]==4||rawdata[i][j]==5)
                {
                    WX=i;
                    WY=j;
                    data[i][j]=0;
                }
               else{
                   //If the box position is read, it is saved as the floor in the data array.
                    if(rawdata[i][j]==8)
                        data[i][j]=0;
                    //In other cases, the data stored in data is consistent with the map information.
                    else
                        data[i][j]=rawdata[i][j];
                }
               //If it is the fifth map,
                // the position of the small full hole
                // in the data array will be pushed again
                // by default to pass,
                // and the current position will be recorded.
               if(rawdata[i][j]==9)
               {
                   data[i][j]=6;
                   judge=true;
                   x=i;
                   y=j;
               }
            }
        }
        //If the number of boxes read does not match the number of pits,
        // the location of the warehouse keeper in the data array is the location of the pits.
        if(pit!=box)
            data[WX][WY]=6;
    }
    //Output the obtained map data in the form of pictures,
    // and display the steps taken
    // by the warehouse keeper on the right side of the map.
    public void paint(Graphics g)
    {
        super.paint(g);
        g.drawString("Steps: "+String.valueOf(step),550,200);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                g.drawImage(pics[temp[i][j]],j*42,i*42,40,40,this);
            }
            System.out.println();
        }
        //If there is no data in the stack,
        // the user are not allowed to click btnundo.
        if (!undo.empty())
            btnUndo.setEnabled(true);
        else
            btnUndo.setEnabled(false);
    }
    //Undo function. The return type is void.
    // The undo function performs rollback
    // according to the number pushed in and displays the result on the main interface.
    public void undoProcess()
    {
        if(undo.empty())
            btnUndo.setEnabled(false);
        int num=undo.pop();
        switch(num)
        {
            //When the pop-up number is 0,
            // it is determined that it has moved to the left,
            // and then it is withdrawn to the right.
            case 0:
                temp[WX][WY+1]=4;
                temp[WX][WY]=data[WX][WY];
                WY++;
                step--;
                break;
            //When the pop-up number is 2,
            // it is determined to move to the left
            // and the box has been transported.
            // At this time, it is withdrawn to the right.
            case 2:
                temp[WX][WY]=8;
                temp[WX][WY+1]=4;
                temp[WX][WY-1]=data[WX][WY-1];
                WY++;
                step--;
                break;
            //When the pop-up number is 4,
            // it is determined that the box
            // that moves to the left
            // and has been pushed into the pit is withdrawn to the right.
            case 4:
                temp[WX][WY]=7;
                temp[WX][WY+1]=4;
                temp[WX][WY-1]=data[WX][WY-1];
                WY++;
                step--;
                break;
            //When the pop-up number is 10,
            // it is determined that it has moved to the top,
            // and then it is withdrawn to the downward.
            case 10:
                temp[WX+1][WY]=2;
                temp[WX][WY]=data[WX][WY];
                WX++;
                step--;
                break;
            //When the pop-up number is 12,
            // it is determined to move to the top
            // and the box has been transported.
            // At this time, it is withdrawn to the downward.
            case 12:
                temp[WX][WY]=8;
                temp[WX+1][WY]=2;
                temp[WX-1][WY]=data[WX-1][WY];
                WX++;
                step--;
                break;
            //When the pop-up number is 14,
            // it is determined that the box
            // that moves to the top
            // and has been pushed into the pit is withdrawn to the downward.
            case 14:
                temp[WX][WY]=7;
                temp[WX+1][WY]=2;
                temp[WX-1][WY]=data[WX-1][WY];
                WX++;
                step--;
                break;
            //When the pop-up number is 20,
            // it is determined that it has moved to the right,
            // and then it is withdrawn to the left.
            case 20:
                temp[WX][WY-1]=5;
                temp[WX][WY]=data[WX][WY];
                WY--;
                step--;
                break;
            //When the pop-up number is 22,
            // it is determined that the box
            // that moves to the right and
            // has been pushed into the pit is withdrawn to the left.
            case 22:
                temp[WX][WY]=8;
                temp[WX][WY-1]=5;
                temp[WX][WY+1]=data[WX][WY+1];
                WY--;
                step--;
                break;
            //When the pop-up number is 24,
            // it is determined that the box
            // that moves to the right
            // and has been pushed into the pit is withdrawn to the left.
            case 24:
                temp[WX][WY]=7;
                temp[WX][WY-1]=5;
                temp[WX][WY+1]=data[WX][WY+1];
                WY--;
                step--;
                break;
            //When the pop-up number is 30,
            // it is determined that it has moved downward,
            // and then it is withdrawn upward.
            case 30:
                temp[WX-1][WY]=3;
                temp[WX][WY]=data[WX][WY];
                WX--;
                step--;
                break;
            //When the pop-up number is 32,
            // it is determined that the box moves downward
            // and has been transported through the pit,
            // and then it is withdrawn upward.
            case 32:
                temp[WX][WY]=8;
                temp[WX-1][WY]=3;
                temp[WX+1][WY]=data[WX+1][WY];
                WX--;
                step--;
                break;
            //When the pop-up number is 34,
            // it is determined that the box
            // that moves to the downward
            // and has been pushed into the pit is withdrawn upward.
            case 34:
                temp[WX][WY]=7;
                temp[WX-1][WY]=3;
                temp[WX+1][WY]=data[WX+1][WY];
                WX--;
                step--;
                break;
        }
        //Execute the paint function again.
        repaint();
    }
    //The goleft function controls the warehouse administrator
    // to move to the left at the allowed position.
    private  void goleft()
    {
        //If the warehouse keeper walks to the left
        // and the floor is in front of him, he can move.
        if(temp[WX][WY-1]==0)
        {
            temp[WX][WY-1]=4;
            temp[WX][WY]=data[WX][WY];
            WY--;
            step++;
            undo.push(0);
        }
        //If the warehouse keeper walks to the left
        // and there is a pit in front of him, he can move.
        else if(temp[WX][WY-1]==6)
        {
            temp[WX][WY-1]=4;
            temp[WX][WY]=data[WX][WY];
            WY--;
            step++;
            undo.push(0);
        }
        //If the warehouse keeper walks to the left,
        // and the box is in front,
        // and the floor is in front of the box,
        // it can be moved.
        else if(temp[WX][WY-1]==8&&temp[WX][WY-2]==0)
        {
            temp[WX][WY-2]=8;
            temp[WX][WY-1]=4;
            temp[WX][WY]=data[WX][WY];
            WY--;
            step++;
            undo.push(2);
        }
        //The warehouse keeper can move
        // if he walks to the left with a box in front and a pit in front.
        else if(temp[WX][WY-1]==8&&temp[WX][WY-2]==6)
        {
            temp[WX][WY-2]=7;
            temp[WX][WY-1]=4;
            temp[WX][WY]=data[WX][WY];
            WY--;
            step++;
            undo.push(2);
        }
        //The warehouse keeper can move the boxes
        // that have been pushed into the pit on the left.
        else if(temp[WX][WY-1]==7&&temp[WX][WY-2]==0)
        {
            temp[WX][WY-2]=8;
            temp[WX][WY-1]=4;
            temp[WX][WY]=data[WX][WY];
            WY--;
            step++;
            undo.push(4);
        }
        //The warehouse keeper can move the boxes
        // that have been pushed into the pit on the left.
        else if(temp[WX][WY-1]==7&&temp[WX][WY-2]==6)
        {
            temp[WX][WY-2]=7;
            temp[WX][WY-1]=4;
            temp[WX][WY]=data[WX][WY];
            WY--;
            step++;
            undo.push(4);
        }
        //The warehouse keeper can move the boxes
        // that have been pushed into the pit on the left.
        else if(temp[WX][WY-1]==9&&temp[WX][WY-2]==0)
        {
            temp[WX][WY-2]=8;
            temp[WX][WY-1]=4;
            temp[WX][WY]=data[WX][WY];
            WY--;
            step++;
            undo.push(4);
        }
        //The warehouse keeper can move the boxes
        // that have been pushed into the pit on the left.
        else if(temp[WX][WY-1]==9&&temp[WX][WY-2]==6)
        {
            temp[WX][WY-2]=7;
            temp[WX][WY-1]=4;
            temp[WX][WY]=data[WX][WY];
            WY--;
            step++;
            undo.push(4);
        }
    }
    //The gotop function controls the warehouse administrator
    // to move to the top at the allowed position.
    private  void gotop()
    {
        //If the warehouse keeper walks to the top
        // and the floor is in front of him, he can move.
        if(temp[WX-1][WY]==0)
        {
            temp[WX-1][WY]=2;
            temp[WX][WY]=data[WX][WY];
            WX--;
            step++;
            undo.push(10);
        }
        //If the warehouse keeper walks to the top
        // and there is a pit in front of him, he can move.
        else if(temp[WX-1][WY]==6)
        {
            temp[WX-1][WY]=2;
            temp[WX][WY]=data[WX][WY];
            WX--;
            step++;
            undo.push(10);
        }
        //If the warehouse keeper walks to the top,
        // and the box is in front,
        // and the floor is in front of the box,
        // it can be moved.
        else if(temp[WX-1][WY]==8&&temp[WX-2][WY]==0)
        {
            temp[WX-2][WY]=8;
            temp[WX-1][WY]=2;
            temp[WX][WY]=data[WX][WY];
            WX--;
            step++;
            undo.push(12);
        }
        //The warehouse keeper can move
        // if he walks to the top with a box in front and a pit in front.
        else if(temp[WX-1][WY]==8&&temp[WX-2][WY]==6)
        {
            temp[WX-2][WY]=7;
            temp[WX-1][WY]=2;
            temp[WX][WY]=data[WX][WY];
            WX--;
            step++;
            undo.push(12);
        }
        //The warehouse keeper can move
        // if he walks to the top with a box in front and a pit in front.
        else if(temp[WX-1][WY]==7&&temp[WX-2][WY]==0)//
        {
            temp[WX-2][WY]=8;
            temp[WX-1][WY]=2;
            temp[WX][WY]=data[WX][WY];
            WX--;
            step++;
            undo.push(14);
        }
        //The warehouse keeper can move
        // if he walks to the top with a box in front and a pit in front.
        else if(temp[WX-1][WY]==7&&temp[WX-2][WY]==6)
        {
            temp[WX-2][WY]=7;
            temp[WX-1][WY]=2;
            temp[WX][WY]=data[WX][WY];
            WX--;
            step++;
            undo.push(14);
        }
        //The warehouse keeper can move
        // if he walks to the top with a box in front and a pit in front.
        else if(temp[WX-1][WY]==9&&temp[WX-2][WY]==0)
        {
            temp[WX-2][WY]=8;
            temp[WX-1][WY]=2;
            temp[WX][WY]=data[WX][WY];
            WX--;
            step++;
            undo.push(14);
        }
        //The warehouse keeper can move
        // if he walks to the top with a box in front and a pit in front.
        else if(temp[WX-1][WY]==9&&temp[WX-2][WY]==6)
        {
            temp[WX-2][WY]=7;
            temp[WX-1][WY]=2;
            temp[WX][WY]=data[WX][WY];
            WX--;
            step++;
            undo.push(14);
        }

    }
    //The goright function controls the warehouse administrator
    // to move to the right at the allowed position.
    private  void goright()
    {
        //If the warehouse keeper walks to the right
        // and the floor is in front of him, he can move.
        if(temp[WX][WY+1]==0)
        {
            temp[WX][WY+1]=5;
            temp[WX][WY]=data[WX][WY];
            WY++;
            step++;
            undo.push(20);
        }
        //If the warehouse keeper walks to the right
        // and there is a pit in front of him, he can move.
        else if(temp[WX][WY+1]==6)
        {
            temp[WX][WY+1]=5;
            temp[WX][WY]=data[WX][WY];
            WY++;
            step++;
            undo.push(20);
        }
        //If the warehouse keeper walks to the right,
        // and the box is in front,
        // and the floor is in front of the box,
        // it can be moved.
        else if(temp[WX][WY+1]==8&&temp[WX][WY+2]==0)
        {
            temp[WX][WY+2]=8;
            temp[WX][WY+1]=5;
            temp[WX][WY]=data[WX][WY];
            WY++;
            step++;
            undo.push(22);
        }
        //The warehouse keeper can move
        // if he walks to the right with a box in front and a pit in front.
        else if(temp[WX][WY+1]==8&&temp[WX][WY+2]==6)
        {
            temp[WX][WY+2]=7;
            temp[WX][WY+1]=5;
            temp[WX][WY]=data[WX][WY];
            WY++;
            step++;
            undo.push(22);
        }
        //The warehouse keeper can move
        // if he walks to the right with a box in front and a pit in front.
        else if(temp[WX][WY+1]==7&&temp[WX][WY+2]==0)
        {
            temp[WX][WY+2]=8;
            temp[WX][WY+1]=5;
            temp[WX][WY]=data[WX][WY];
            WY++;
            step++;
            undo.push(24);
        }
        //The warehouse keeper can move
        // if he walks to the right with a box in front and a pit in front.
        else if(temp[WX][WY+1]==7&&temp[WX][WY+2]==6)
        {
            temp[WX][WY+2]=7;
            temp[WX][WY+1]=5;
            temp[WX][WY]=data[WX][WY];
            WY++;
            step++;
            undo.push(24);
        }
        //The warehouse keeper can move
        // if he walks to the right with a box in front and a pit in front.
        else if(temp[WX][WY+1]==9&&temp[WX][WY+2]==0)
        {
            temp[WX][WY+2]=8;
            temp[WX][WY+1]=5;
            temp[WX][WY]=data[WX][WY];
            WY++;
            step++;
            undo.push(24);
        }
        //The warehouse keeper can move
        // if he walks to the right with a box in front and a pit in front.
        else if(temp[WX][WY+1]==9&&temp[WX][WY+2]==6)
        {
            temp[WX][WY+2]=7;
            temp[WX][WY+1]=5;
            temp[WX][WY]=data[WX][WY];
            WY++;
            step++;
            undo.push(24);
        }
    }
    //The godown function controls the warehouse administrator
    // to move to the down at the allowed position.
    private  void godown()
    {
        //If the warehouse keeper walks to the downward
        // and the floor is in front of him, he can move.
        if(temp[WX+1][WY]==0)
        {
            temp[WX+1][WY]=3;
            temp[WX][WY]=data[WX][WY];
            WX++;
            step++;
            undo.push(30);
        }
        //If the warehouse keeper walks to the downward
        // and there is a pit in front of him, he can move.
        else if(temp[WX+1][WY]==6)
        {
            temp[WX+1][WY]=3;
            temp[WX][WY]=data[WX][WY];
            WX++;
            step++;
            undo.push(31);
        }
        //If the warehouse keeper walks to the downward
        // and the box is in front,
        // and the floor is in front of the box,
        // it can be moved.
        else if(temp[WX+1][WY]==8&&temp[WX+2][WY]==0)
        {
            temp[WX+2][WY]=8;
            temp[WX+1][WY]=3;
            temp[WX][WY]=data[WX][WY];
            WX++;
            step++;
            undo.push(32);
        }
        //The warehouse keeper can move
        // if he walks to the downward with a box in front and a pit in front.
        else if(temp[WX+1][WY]==8&&temp[WX+2][WY]==6)
        {
            temp[WX+2][WY]=7;
            temp[WX+1][WY]=3;
            temp[WX][WY]=data[WX][WY];
            WX++;
            step++;
            undo.push(33);
        }
        //The warehouse keeper can move
        // if he walks to the downward with a box in front and a pit in front.
        else if(temp[WX+1][WY]==7&&temp[WX+2][WY]==0)
        {
            temp[WX+2][WY]=8;
            temp[WX+1][WY]=3;
            temp[WX][WY]=data[WX][WY];
            WX++;
            step++;
            undo.push(34);
        }
        //The warehouse keeper can move
        // if he walks to the downward with a box in front and a pit in front.
        else if(temp[WX+1][WY]==7&&temp[WX+2][WY]==6)
        {
            temp[WX+2][WY]=7;
            temp[WX+1][WY]=3;
            temp[WX][WY]=data[WX][WY];
            WX++;
            step++;
            undo.push(35);
        }
        //The warehouse keeper can move
        // if he walks to the downward with a box in front and a pit in front.
        else if(temp[WX+1][WY]==9&&temp[WX+2][WY]==0)
        {
            temp[WX+2][WY]=8;
            temp[WX+1][WY]=3;
            temp[WX][WY]=data[WX][WY];
            WX++;
            step++;
            undo.push(36);
        }
        //The warehouse keeper can move
        // if he walks to the downward with a box in front and a pit in front.
        else if(temp[WX+1][WY]==9&&temp[WX+2][WY]==6)
        {
            temp[WX+2][WY]=7;
            temp[WX+1][WY]=3;
            temp[WX][WY]=data[WX][WY];
            WX++;
            step++;
            undo.push(37);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    //Press the key to press the monitoring function.
    // when the pressed key is up, down, left
    // and right, the warehouse keeper moves in the corresponding direction.
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode())
        {
            case 37:
                goleft();
                break;
            case 38:
                gotop();
                break;
            case 39:
                goright();
                break;
            case 40:
                godown();
                break;
        }
        repaint();
    }
    //Press the key to lift the monitoring function.
    // When the pressed key is lifted,
    // judge whether there is any box on the map that has been pushed into the pit.
    // If there is, the game will not be ended.
    // If not, the user will be prompted to complete
    // the game and can choose whether to enter the next level.
    @Override
    public void keyReleased(KeyEvent e) {
    //Set the value of the hole to 0.
        hole=0;
        //If it is determined to be true, hole first increases by 1, that is, there are still boxes not pushed into the pit.
        if(judge)
            hole++;
        //If it is determined as true, and the full pit position of the map operated by the player is pushed into a box again, hole will be 0.
        if(judge&&temp[x][y]==7)
            hole=0;
        //Every time the keyboard keys are raised, it is necessary to determine whether there are pits on the map. If there are pits, the game is not over.
        for(int i=0;i<row;i++)
        {
            for (int j=0;j<col;j++)
            {
                if(temp[i][j]==8)
                    hole++;
            }
        }
        //If all the pits on the map are full, it will be judged as clearance and the clearance information will be sent to the player.
        if(hole==0)
        {
            int result=JOptionPane.showInternalConfirmDialog(null,

                    "Do you want to challenge the next level?", "Great!",

                    JOptionPane.YES_NO_CANCEL_OPTION,

                    JOptionPane.INFORMATION_MESSAGE);
                //If the player clicks OK, it will enter the next level. If this level is the last one, you will be prompted that all the games have passed.
            if(result==JOptionPane.OK_OPTION)
            {
                gate++;
                jgate.setText("Gate: "+gate);
                if(gate>5)
                {
                    gate=5;
                    jgate.setText("Gate: "+gate);
                    JOptionPane.showInternalConfirmDialog(null,"You have completed the last level!",
                            "Well Done!",JOptionPane.YES_OPTION, JOptionPane.INFORMATION_MESSAGE);
                }
                try {
                     init(gate);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                step=0;
                undo.clear();
            }//If you click No, this level will be played again. If you click Cancel, nothing will be done.
            else if(result==JOptionPane.NO_OPTION)
            {
                try {
                    init(gate);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                step=0;
                undo.clear();
            }


        }

    }
}
