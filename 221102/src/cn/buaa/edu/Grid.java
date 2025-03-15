package cn.buaa.edu;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

public class Grid extends JFrame {
    private JButton btn1,btn2,btn3,btn4,btn5;
    private JPanel p1,p2,p3,p4;
    private JLabel lbl1,lbl2,lbl3,lbl4;
    private DrawPanel dp;
    private String str1="227006951";
    private String str2="Sokoban Game";
    private String str3="ZSR";
    private String str4="welcome to buaa";

    public Grid()
    {
        p1=new JPanel();
        p2=new JPanel();
        p3=new JPanel();
        p4=new JPanel();
        dp=new DrawPanel();
        p1.setLayout(new GridLayout(5,1,5,10));
        p1.setBorder(BorderFactory.createLineBorder(Color.blue,2));
        p2.setLayout(new FlowLayout());
        p2.setBorder(BorderFactory.createLineBorder(Color.cyan,2));
        p3.setLayout(new FlowLayout());
        p4.setLayout(new BorderLayout());
        btn1=new JButton("Btn1");
        btn2=new JButton("Btn2");
        btn3=new JButton("Btn3");
        btn4=new JButton("Btn4");
        btn5=new JButton("Btn5");

        lbl1=new JLabel(str1);
        lbl2=new JLabel(str2);
        lbl3=new JLabel(str3);
        //lbl4=new JLabel("step:"+dp.count);

        p1.add(btn1);
        p1.add(btn2);
        p1.add(btn3);
        p1.add(btn4);
        p1.add(btn5);
        p2.add(lbl2,BorderLayout.CENTER);
        p3.add(lbl1,"CENTER");
        p3.add(lbl3);
       // p4.add(lbl4,BorderLayout.SOUTH);

        //this.add(p1,"East");
        this.add(p2,"North");
        this.add(p3,"South");
        this.add(dp,"Center");
        //this.add(p4,"East");


        this.setVisible(true);
        this.setSize(300,200);
        this.setDefaultCloseOperation(3);
        dp.requestFocus();
    }

    public static void main(String[] args) {
        new Grid();
    }
}

class DrawPanel extends JPanel implements KeyListener
{
    private Image image;
    private int x,y,maxX=300,maxY=200;
    public int count=0;
    private JPanel p;
    private JLabel lbl;
    public DrawPanel()
    {
        x=0;y=0;
        addKeyListener(this);
        p= new JPanel();
        p.setLayout(new BorderLayout());
        lbl=new JLabel("step: "+count);
        p.add(lbl,BorderLayout.EAST);
        this.add(p, BorderLayout.SOUTH);
        requestFocus();
    }
    public void paint(Graphics g)
    {
        g.setColor(Color.cyan);
        BasicStroke stoke=new BasicStroke(5f);
        Graphics2D g2=(Graphics2D) g;
        g2.setStroke(stoke);

        /*g.drawLine(10,10,20,20);
        g2.drawRect(10,60,50,20);
        g.drawRoundRect(5,90,50,20,20,30);*/
        super.paint(g);
        try
        {
            image= ImageIO.read(new File("E:\\webfile\\Sales\\Sall\\img\\s1.jpg"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        g.drawImage(image,x,y,50,80,this);
       // g.drawString("steps: "+count,x+50,y+10);
        lbl.setText("step: "+count);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
        switch (e.getKeyCode())
        {
            case 37:
                if(x<=0)break;
                x-=10;
                count++;
                break;
            case 38:
                if(y<=0)break;
                y-=10;
                count++;
                break;
            case 39:
                if(x>=maxX)
                {
                    x=maxX;
                    break;
                }
                x+=10;
                count++;
                break;
            case 40:
                if(y>=maxY)
                {
                    y=maxY;
                    break;
                }
                y+=10;
                count++;
                break;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
