/*
 *  Example class containing methods to read and display CPU, PCI and USB information
 *
 *  Copyright (c) 2024 Mark Burkley (mark.burkley@ul.ie)
 */
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class template extends JFrame implements ActionListener {


    JButton button;
    JButton button2;
    JButton button3;
    JButton button4;

    template(){
        ImageIcon icon = new ImageIcon("/media/sf_project/cs4421/pictures/CPUInfo.jpg");
        ImageIcon icon2 = new ImageIcon("/media/sf_project/cs4421/pictures/MemoryInfo.jpg");
        ImageIcon icon3 = new ImageIcon("/media/sf_project/cs4421/pictures/PCI-info.jpg");
        ImageIcon icon4 = new ImageIcon("/media/sf_project/cs4421/pictures/DiskInfo.jpg");


        Border border1 = BorderFactory.createLineBorder(Color.GREEN, 5);
        Border border2 = BorderFactory.createLineBorder(Color.BLUE, 5);
        Border border3 = BorderFactory.createLineBorder(Color.RED, 5);
        Border border4 = BorderFactory.createLineBorder(Color.ORANGE, 5);

        button = new JButton();
        button.addActionListener(this);
        button.setBounds(0, 0, 400, 400);
        button.setIcon(icon);
        button.setBorder(border1);


        button2 = new JButton();
        button2.addActionListener(this);
        button2.setBounds(400, 0, 400, 400);
        button2.setIcon(icon2);
        button2.setBorder(border2);

        button3 = new JButton();
        button3.addActionListener(this);
        button3.setBounds(400, 400, 400, 400);
        button3.setIcon(icon3);
        button3.setBorder(border3);

        button4 = new JButton();
        button4.addActionListener(this);
        button4.setBounds(0, 400, 400, 400);
        button4.setIcon(icon4);
        button4.setBorder(border4);

        // Set up frame properties
        this.setTitle("ISE Group 4 project.");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(800, 800);
        this.setLayout(null);// Use absolute positioning for button to take effect
        this.setResizable(false);
        this.setLayout(new GridLayout(2, 2, 5, 5));

        // Set icon image
        ImageIcon image = new ImageIcon("Logo.png");
        this.setIconImage(image.getImage());

        // Background color
        this.getContentPane().setBackground(new Color(144, 132, 250));

        // Add button to frame
        this.add(button);
        this.add(button2);
        this.add(button3);
        this.add(button4);
        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            window1 window1 = new window1();
        }
        if (e.getSource()== button2){
            window2 window2 = new window2();
        }
        if (e.getSource() == button3){
            window3 window3 = new window3();
        }
        if (e.getSource() == button4){
            window4 window4 = new window4();
        }
    }


    public static void main(String[] args)
    {
        System.loadLibrary("sysinfo");
        new template();

    }
}