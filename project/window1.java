import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.border.Border;
import java.awt.*;

public class window1 {
    JFrame frame = new JFrame();
    JTextArea textArea1 = new JTextArea();

    //creating window
    window1() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(1000, 800);
        frame.setResizable(false);

        textArea1 = new JTextArea();
        textArea1.setEditable(false);
        textArea1.setSize(25,25);
        textArea1.setForeground(Color.BLACK);
        textArea1.setFont(new Font("italic" ,Font.ITALIC, 30));
        frame.add(textArea1);

        //calling method
        cpuInfo();
    }

    public void cpuInfo() {

        cpuInfo cpu = new cpuInfo();
        cpu.read(0);
        //Display heading
        appendToTextArea("--------------------------------------------- MY CPU INFO " +
                "---------------------------------------------\n\n");

        // Display CPU model, number of CPUs and cores per CPU
        appendToTextArea("CPU MODEL:\t"+cpu.getModel());
        appendToTextArea("Number of CPUs:\t"+ cpu.socketCount()+"\n");
        appendToTextArea("CORES PER CPU:\t"+cpu.coresPerSocket()+"\n");
        appendToTextArea("-------------------------------------------------------------" +
                "----------------------------------------------------------------------");

        // Show sizes of L1,L2 and L3 cache
        float[] cacheArray = { cpu.l1iCacheSize(), cpu.l2CacheSize(), cpu.l3CacheSize()};
        appendToTextArea("\nCACHE LEVEL\t\tSIZE\n");
        appendToTextArea("----------\t\t----\n");

        //iterate through the different cashe levels and display them in MBs
        for (int counter = 0; counter < cacheArray.length; counter++){
            appendToTextArea((counter+1)+"\t\t"+ Math.round((cacheArray[counter] / 1000000.0)*100.0)/100.0 +"MB\n");
        }
        appendToTextArea("-------------------------------------------------------------" +
                "----------------------------------------------------------------------");

        // get percentage idle for each core
        float Idle1 = cpu.getIdleTime(0);
        float Idle2 = cpu.getIdleTime(1);
        float Idle3 = cpu.getIdleTime(2);
        float Idle4 = cpu.getIdleTime(3);

        //get percentage in user mode for each core
        float user1 = cpu.getUserTime(0);
        float user2 = cpu.getUserTime(1);
        float user3 = cpu.getUserTime(2);
        float user4 = cpu.getUserTime(3);

        //get percentage in kernal mode for each core
        float system1 = cpu.getSystemTime(0);
        float system2 = cpu.getSystemTime(1);
        float system3 = cpu.getSystemTime(2);
        float system4 = cpu.getSystemTime(3);

        //display info to user
        appendToTextArea("\nCORE:\t1\t2\t3\t4\n");
        appendToTextArea("----\t--\t--\t--\t--\t------------\n");
        appendToTextArea("IDLE:\t"+Idle1+"%\t"+Idle2+"%\t"+Idle3+"%\t"+Idle4+"%\n");
        appendToTextArea("USER TIME:\t"+user1+"%\t"+user2+"%\t"+user3+"%\t"+user4+"%\n");
        appendToTextArea("SYSTEM:\t"+system1+"%\t"+system2+"%\t"+system3+"%\t"+system4+"%\n");

    }




    private void appendToTextArea(String text){
        textArea1.append(text); // Append text to JTextArea
        textArea1.setCaretPosition(textArea1.getDocument().getLength());

    }

}