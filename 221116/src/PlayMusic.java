import javafx.scene.media.AudioClip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

class PlayMusic extends JPanel implements ActionListener, ItemListener {

    //Button control, used to control the playing state of music.
    public JButton jplay;
    //Music playing class, used to play music.
    private AudioClip ac;
    //String type variable, used to control the path of music playing.
    private String path="221116\\src\\music\\";
    //String type variable array, used to control the name of music.
    private String[] musicFile={"clock.MP3","fog.mp3","lazy.mp3"};
    //String type variable array, used to control which music is played.
    private String[] musicDisplay={"First","Second","Third"};
    //The label control array is used to occupy space in the grid layout.
    private JLabel []jlabel=new JLabel[3];
    //The combobox class is used to select music.
    public JComboBox cbomusic;
    //Constructor, which is used to instantiate the previously declared class.
    public PlayMusic()
    {
        this.setLayout(new GridLayout(10,1,5,10));
        cbomusic=new JComboBox(musicDisplay);
        jplay=new JButton("play");
        for(int i=0;i<3;i++)
        {
            jlabel[i]=new JLabel();
            this.add(jlabel[i]);
        }

        this.add(cbomusic);
        this.add(jplay);

        jplay.addActionListener(this);
        cbomusic.addItemListener(this);
        ac=new AudioClip(new File(path+"\\clock.MP3").toURI().toString());
        ac.stop();
    }
    @Override
    //Monitor function. When the current music is playing,
    // jplay displays stop. When the current music is paused,
    // jplay displays play.
    public void actionPerformed(ActionEvent e) {
        if(jplay.getText()=="play")
        {
            ac.play();
            jplay.setText("stop");
        }
        else {
            ac.stop();
            jplay.setText("play");
        }
    }
    //The function that controls
    // the change of the content displayed on the combobox.
    // When switching music, the music will play automatically.
    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getSource()==cbomusic)
        {
            //If the contents of the combobox change, read the corresponding serial number of the combobox first.
            String str=musicFile[cbomusic.getSelectedIndex()];
            //Pause the currently playing music.
            ac.stop();
            //Switch the content of jplay to play.
            jplay.setText("play");
            //Retrieve files for the selected music.
            ac=new AudioClip(new File(path+str).toURI().toString());
            //play the music
            ac.play();
            //Switch the content of jplay to stop.
            jplay.setText("stop");
        }
    }
}