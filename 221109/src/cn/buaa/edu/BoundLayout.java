package cn.buaa.edu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class BoundLayout extends JFrame implements ActionListener {
    JButton btn1,btn2,btn3;
    JLabel lbl1;
    JTextField usertext;
    JMenuBar jmb;
    JMenu file,edit,exit;
    JMenuItem openfile,closefile,help,itemexit;
    public BoundLayout()
    {
        jmb=new JMenuBar();
        file=new JMenu("File");
        edit=new JMenu("Edit");
        exit=new JMenu("Exit");
        openfile=new JMenuItem("Open");
        closefile=new JMenuItem("Close");
        help=new JMenuItem("Help");
        itemexit=new JMenuItem("Exit",KeyEvent.VK_E);

        jmb.add(file);
        jmb.add(edit);
        jmb.add(exit);
        file.add(openfile);
        file.addSeparator();
        file.add(closefile);
        file.add(help);
        exit.add(itemexit);
        this.setJMenuBar(jmb);
        closefile.addActionListener(this);
        openfile.addActionListener(this);
        help.addActionListener(this);
        itemexit.addActionListener(this);
        help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,ActionEvent.CTRL_MASK));


        lbl1=new JLabel("username",JLabel.RIGHT);
        usertext=new JTextField(10);
        btn1=new JButton("ok");
        this.setLayout(null);
        lbl1.setBounds(10,20,100,30);
        usertext.setBounds(120,20,40,30);
        btn1.setBounds(170,100,50,30);
        this.setSize(500,500);
        this.add(lbl1);
        this.add(usertext);
        this.add(btn1);
        this.setVisible(true);
        this.setDefaultCloseOperation(3);
        this.setTitle("Sokoban Version X Zhao Shirui-25");
    }

    public static void main(String[] args) {
        new BoundLayout();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==closefile)
        {
            System.exit(0);
        }
        if (e.getSource()==openfile)
        {
            System.exit(0);
        }
        if (e.getSource()==help)
        {
            JOptionPane.showMessageDialog(null,"fk\n1.dwad/fwadfaw");
        }
        if(e.getSource()==itemexit)
        {
            System.exit(0);
        }
    }
}
