import javax.swing.*;
import javax.swing.JFrame;
import java.awt.*;
import java.util.*;

public class window3 {
    JFrame frame = new JFrame();
    JTextArea textArea1 = new JTextArea();
    window3(){
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(1650,800);


        textArea1 = new JTextArea();
        textArea1.setEditable(false);
        textArea1.setSize(1000,1000);
        textArea1.setForeground(Color.BLACK);
        textArea1.setFont(new Font("italic" ,Font.ITALIC, 30));
        frame.add(textArea1);



        showPCI();
    }

    public static String vedorIdConverter(String vendorId){
        String[] vendorNames = {"intel","AMD","Ralink","Realtek Semiconductor", "02 micro", "Nvidia", "Emulex",
                "Fujitsu", "VMware", "Oracle Corp", "Apple"
        };
        String[] vendorIdList = {"0x8086","0x1022","0x1814", "0x10ec", "0x1217","0x10de", "0x19a2", "0x1734", "0x15AD", "0x80EE", "0x106B" };
        for(int i = 0;i < vendorIdList.length;i++){
            if(vendorId.compareTo(vendorIdList[i]) == 0){
                vendorId = vendorNames[i];
            }
        }
        return vendorId;

    }
    // Product Converter changes the Product ID into the name - example: 0x8086 is Intel
    public static String productIdConverter(String productId) {
        String[] productNames = {"Intel 440FX", "Intel 82371SB PIIX3 ISA", "Intel 82371AB/EB/MB PIIX4 IDE Controller",
                "VMware Virtual SVGA II Adapter.", "Intel 82540EM Gigabit Ethernet Controller", "VirtualBox Guest Additions Device",
                " Intel 82801AA AC'97 Audio Controller", "Apple iSight Camera", "Intel 82371AB/EB/MB PIIX4 ACPI.",
                "Intel 82576 Gigabit Ethernet Controller.", "Intel 82579LM Gigabit Network Connection."
        };
        String[] productIdList = {"0x1237", "0x7000", "0x7111", "0x0405", "0x100E", "0xCAFE", "0x2415", "0x003F", "0x7113", "0x265C", "0x2829"};
        for (int i = 0; i < productIdList.length; i++) {
            if (productId.compareTo(productIdList[i]) == 0) {
                productId = productNames[i];
            }
        }
        return productId;
    }

    //PCI Information - Shows Information about the PCI devices on your machine
    private void showPCI() {
        pciInfo pci = new pciInfo();
        pci.read();
        /*String intel = "0x8086";
        String vendor;
        int intelCount = 0;
        int vendorCount = 0;
        ArrayList<String> pciDevices = new ArrayList<>();
        int pciCount = 0;*/

        for (int i = 0; i < pci.busCount(); i++) {
            appendToTextArea("Bus " + i + " has " +
                    pci.deviceCount(i) + " devices\n");}
        appendToTextArea("----------------------------------\n");

        appendToTextArea("BUS NO.\tPCI DEVICE\tVENDOR ID\tVENDOR NAME\tPRODUCT ID\tPRODUCT NAME\n");
        appendToTextArea("-----------------------------------------------------------------------------------------------------------------------------------\n");

        // Iterate through each bus
        for (int i = 0; i < pci.busCount(); i++) {
            System.out.print("Bus " + i + " has " +
                    pci.deviceCount(i) + " devices");

            // Iterate for up to 32 devices.  Not every device slot may be populated
            // so ensure at least one function before printing device information
            for (int j = 0; j < 32; j++) {
                if (pci.functionCount(i, j) > 0) {
                    /*System.out.println("Bus " + i + " device " + j + " has " +
                            pci.functionCount(i, j) + " functions");*/

                    // Iterate through up to 8 functions per device.
                    for (int k = 0; k < 8; k++) {
                        if (pci.functionPresent(i, j, k) > 0) {
                            appendToTextArea(i + "\t" + j +"\t"+String.format("0x%04X", pci.vendorID(i, j, k))+"\t"
                                    + vedorIdConverter(String.format("0x%04X", pci.vendorID(i, j, k)))+ "\t" +
                                    String.format("0x%04X", pci.productID(i, j, k))+"\t"+ productIdConverter(String.format("0x%04X", pci.productID(i, j, k)))+"\n");
                            /*vendor = String.format("0x%04X", pci.vendorID(i, j, k));
                            if (intel.compareTo(vendor) == 0) {
                                intelCount++;
                                vendorCount++;
                            } else {
                                vendorCount++;
                            }
                            pciCount++;
                            //pciDevices.add(vendor);*/
                        }


                    }

                }
            }
        }
        //appendToTextArea("-------------------------------------------------------------------------------\n");
        //Prints out what percentage of the devices in use are intel
        //appendToTextArea("This device is"+(intelCount * 100 / vendorCount)+ "percent intel, Intel device count =" +intelCount+", Vendor count = " +
                //vendorCount);
        //for(int i = 0;i < pciDevices.size(); i++){
        //  System.out.print("\n" + pciDevices.get(i));
        //}
    }





    private void appendToTextArea(String text){
        textArea1.append(text); // Append text to JTextArea
        textArea1.setCaretPosition(textArea1.getDocument().getLength());

    }











}
