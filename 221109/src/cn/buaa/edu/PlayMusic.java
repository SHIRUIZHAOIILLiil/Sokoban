package cn.buaa.edu;

import javax.swing.*;
import javafx.scene.media.AudioClip;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.time.Clock;

public class PlayMusic extends JFrame implements ActionListener, ItemListener {
    JPanel jp;
    JButton jplay;
    AudioClip ac;
    String path="D:\\java_idea\\2022930\\221109\\src\\cn\\buaa\\edu\\music\\";
    String[] musicFile={"a.mp3","B.mp3","C.mp3"};
    String[] musicDisplay={"First","第二首","第三首"};
    private JComboBox cbomusic;
    public PlayMusic()
    {
        cbomusic=new JComboBox(musicDisplay);

        this.setVisible(true);
        this.setSize(400,400);
        jp=new JPanel();
        jp.add(cbomusic);
        //jp.setBackground(Color.cyan);
        jplay=new JButton("Undo");
        jp.add(jplay);
        jplay.addActionListener(this);
        cbomusic.addItemListener(this);

        this.add(jp,"North");
        ac=new AudioClip(new File(path+"\\a.mp3").toURI().toString());
        ac.stop();


    }

    public static void main(String[] args) {
        new PlayMusic();
    }

    @Override
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

    @Override
    public void itemStateChanged(ItemEvent e) {

        if(e.getSource()==cbomusic)
        {
            String str=musicFile[cbomusic.getSelectedIndex()];
            ac.stop();
            jplay.setText("play");
            ac=new AudioClip(new File(path+str).toURI().toString());
            ac.play();
            jplay.setText("stop");
        }
    }
}
