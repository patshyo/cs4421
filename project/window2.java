import javax.swing.*;
import javax.swing.JFrame;
import java.awt.*;

public class window2 {


    JFrame frame = new JFrame();
    JTextArea textArea2 = new JTextArea();
    window2(){
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(800,800);

        textArea2 = new JTextArea();
        textArea2.setEditable(false);
        textArea2.setSize(25,25);
        textArea2.setForeground(Color.BLACK);
        textArea2.setFont(new Font("italic" ,Font.ITALIC, 40));
        frame.add(textArea2);


        showMem();
    }

    public void showMem(){
        memInfo mem = new memInfo();
        mem.read();

        //creating rounded total memory in GB variable
        int totalGBM100 = 100 * mem.getTotal() /(1000 * 1000 );
        double totalGB = (double) totalGBM100 / 100;

        //creating rouned used memory in GB variable
        int usedGBM100 = 100 * mem.getUsed() /(1000 * 1000);
        double usedGB = (double) usedGBM100 / 100;
        int memoryPercentageUsed = (int) (100 * (usedGB / totalGB));

        //display info
        appendToTextArea("Memory Info\n--------------------------------------" +
                "-------------------------------------------------------------\n");
        appendToTextArea("Total memory:\t" + totalGB +"Gb\nUsed Memory:\t"+ usedGB +"GB\n"+
                "% Used:\t" + memoryPercentageUsed +"%");

    }

    private void appendToTextArea(String text){
        textArea2.append(text); // Append text to JTextArea
        textArea2.setCaretPosition(textArea2.getDocument().getLength());

    }
}