//diskinfo window
import javax.swing.*;
import javax.swing.JFrame;
import java.awt.*;

public class window4 {
    JFrame frame = new JFrame();
    JTextArea textArea4 = new JTextArea();
    
    window4(){
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(1650,800);

        textArea4 = new JTextArea();
        textArea4.setEditable(false);
        textArea4.setSize(25,25);
        textArea4.setForeground(Color.BLACK);
        textArea4.setFont(new Font("italic" ,Font.ITALIC, 40));
        frame.add(textArea4);
        
        showDisk();
    }
    public void showDisk()
    {
        diskInfo disk = new diskInfo();
        disk.read();

        // Iterate through all of the disks
        for (int i = 0; i < disk.diskCount(); i++) {
            if (i == 0){
                //header
                appendToTextArea("disk Name\ttotal Blocks\tUsed Blocks\tUnused Blocks\tpercentage used\n");
                appendToTextArea("--------------------------------------------------------------------" +
                        "-----------------------------------------------------------------\n");
            }
            //find percentage used of disk
            double percentageUsed = ((double) disk.getUsed(i) / disk.getTotal(i)) * 100;

            //display info to user
            appendToTextArea(disk.getName(i) +"\t"+ disk.getTotal(i)+"\t"+ disk.getUsed(i)+
                    "\t"+disk.getAvailable(i) +"\t"+ percentageUsed +"%\n");
        }
    }




    private void appendToTextArea(String text){
        textArea4.append(text); // Append text to JTextArea
        textArea4.setCaretPosition(textArea4.getDocument().getLength());

    }
    
}
