/*
 *  Example class containing methods to read and display CPU, PCI and USB information
 *
 *  Copyright (c) 2024 Mark Burkley (mark.burkley@ul.ie)
 */
import java.util.*;
public class template 
{
    public static String vedorIdConverter(String vendorId){
        String[] vendorNames = {"intel","AMD","Ralink","Realtek Semiconductor", "02 micro", "Nvidia", "Emulex",
                "Fujitsu", "VMware", "Oracle Corporation", "Apple"
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
    public static String productIdConverter(String productId){
        String[] productNames = {"Intel 440FX", "Intel 82371SB PIIX3 ISA", "Intel 82371AB/EB/MB PIIX4 IDE Controller",
                "VMware Virtual SVGA II Adapter.", "Intel 82540EM Gigabit Ethernet Controller", "VirtualBox Guest Additions Device",
                " Intel 82801AA AC'97 Audio Controller", "Apple iSight Camera", "Intel 82371AB/EB/MB PIIX4 ACPI.",
                "Intel 82576 Gigabit Ethernet Controller.", "Intel 82579LM Gigabit Network Connection."
        };
        String[] productIdList = {"0x1237", "0x7000", "0x7111", "0x0405 ", "0x0405", "0x100E", "0xCAFE", "0x2415", "0x003F", "0x7113", "0x265C", "0x2829"};
        for(int i = 0;i < productIdList.length;i++){
            if(productId.compareTo(productIdList[i]) == 0){
                productId = productNames[i];
            }
        }
        return productId;

    }
    public static void showPCI() {
        pciInfo pci = new pciInfo();
        pci.read();
        String intel = "0x8086";
        String vendorId;
        String vendorName;
        String productId;
        String productName;
        int intelCount = 0;
        int vendorCount = 0;
        ArrayList<String> vendorIdList = new ArrayList<>();
        ArrayList<String> vendorNameList = new ArrayList<>();
        ArrayList<String> productIdList = new ArrayList<>();
        ArrayList<String> productNameList = new ArrayList<>();


        int pciCount = 0;

        System.out.println("\nThis machine has " +
                pci.busCount() + " PCI buses ");

        // Iterate through each bus
        for (int i = 0; i < pci.busCount(); i++) {
            System.out.println("Bus " + i + " has " +
                    pci.deviceCount(i) + " devices");

            // Iterate for up to 32 devices.  Not every device slot may be populated
            // so ensure at least one function before printing device information
            for (int j = 0; j < 32; j++) {
                if (pci.functionCount(i, j) > 0) {
                    System.out.println("Bus " + i + " device " + j + " has " +
                            pci.functionCount(i, j) + " functions");

                    // Iterate through up to 8 functions per device.
                    for (int k = 0; k < 8; k++) {
                        if (pci.functionPresent(i, j, k) > 0) {
                            System.out.println("Bus " + i + " device " + j + " function " + k +
                                    " has vendor " + vedorIdConverter(String.format("0x%04X", pci.vendorID(i, j, k))) +
                                    " and product " + productIdConverter(String.format("0x%04X", pci.productID(i, j, k))));
                            vendorId = String.format("0x%04X", pci.vendorID(i, j, k));
                            vendorIdList.add(vendorId);
                            vendorName = vedorIdConverter(vendorId);
                            vendorNameList.add(vendorName);
                            productId = String.format("0x%04X", pci.productID(i, j, k));
                            productIdList.add(productId);
                            productName = productIdConverter(productId);
                            productNameList.add(productName);



                            if (intel.compareTo(vendorId) == 0) {
                                intelCount++;
                                vendorCount++;
                            } else {
                                vendorCount++;
                            }
                            pciCount++;

                        }
                    }
                }
            }
        }
        System.out.printf("this device is %d percent intel, intelCount = %d, venderCount" +
                " = %d", (intelCount * 100 / vendorCount), intelCount, vendorCount);
        for(int i = 0;i < vendorIdList.size(); i++){
            System.out.print("\n" + vendorIdList.get(i));
        }
    }
    public static void showUSB()
    {
        usbInfo usb = new usbInfo();
        usb.read();
        System.out.println("\nThis machine has "+
            usb.busCount()+" USB buses ");

        // Iterate through all of the USB buses
        for (int i = 1; i <= usb.busCount(); i++) {
            System.out.println("Bus "+i+" has "+
                usb.deviceCount(i)+" devices");
            //set counter for unusedUSBCount to 0
            int unusedUSBCount = 0;
//If statement to check there are no bus devices
            if (usb.busCount() == 0) {
                unusedUSBCount++;     //increment unusedUSBCount if bus is not in use
            }
            System.out.println("In this Device there are" + unusedUSBCount + "Usb buses not in use!");

            // Iterate through all of the USB devices on the bus
            for (int j = 1; j <= usb.deviceCount(i); j++) {
                System.out.println("Bus "+i+" device "+j+
                    " has vendor "+ String.format("0x%04X", usb.vendorID(i,j))+
                    " and product "+String.format("0x%04X", usb.productID(i,j)));
            }
        }
    }

    public static void showCPU()
    {
        cpuInfo cpu = new cpuInfo();
        cpu.read(0);

        // Show CPU model, CPU sockets and cores per socket
        System.out.println("CPU " + cpu.getModel() + " has "+
            cpu.socketCount() + " sockets each with "+
            cpu.coresPerSocket() + " cores");

        // Show sizes of L1,L2 and L3 cache
        System.out.println("l1d="+cpu.l1dCacheSize()+
            ", l1i="+cpu.l1iCacheSize()+
            ", l2="+cpu.l2CacheSize()+
            ", l3="+cpu.l3CacheSize());

        double totall1cache = cpu.l1dCacheSize() + cpu.l1iCacheSize();
        if ((totall1cache > cpu.l2CacheSize()) && totall1cache > cpu.l3CacheSize()){
            System.out.println("L1 has the largest Cache Size");
        } else if (totall1cache < cpu.l2CacheSize() && cpu.l2CacheSize() > cpu.l3CacheSize()){
            System.out.println("L2 has the largest Cache size");
        } else if (totall1cache < cpu.l3CacheSize() && cpu.l2CacheSize() < cpu.l3CacheSize()) {
            System.out.println("L3 has the largest Cache Size");
        } else {
            System.out.println("The cache sizes are all the same");
        }




        // Sleep for 1 second and display the idle time percentage for
        // core 1.  This assumes 10Hz so in one second we have 100
        cpu.read(1);
        System.out.println("core 1 idle="+cpu.getIdleTime(1)+"%");

    }
    public static void showSys(){
        sysInfo system = new sysInfo();
        System.out.println("the method intExample(1) does " +system.intExample(1) );
    }
    public static void showDisk()
    {
        diskInfo disk = new diskInfo();
        disk.read();

        // Iterate through all of the disks
        for (int i = 0; i < disk.diskCount(); i++) {
            System.out.println ("disk "+disk.getName(i)+" has "+
                disk.getTotal(i)+" blocks, of which "+
                disk.getUsed(i)+" are used");
            //prints out unused amount of disk by taking away Used disk from Tota Disk

            System.out.println("disk unused = " + (disk.getTotal(i) - disk.getUsed(i)));
            //assigns double to percentageUsed and then divides Used disk by total disk and multiplies by 100


            double percentageUsed = ((double) disk.getUsed(i) / disk.getTotal(i)) * 100;

//prints out the percentage of disk used and prints out percent sign as well(%)


            System.out.println("percentage of disk used = " + percentageUsed + "%");


        }
    }
    public static void testShowDisk()
    {
        diskInfo disk = new diskInfo();
        disk.read();

        // Iterate through all of the disks
        for (int i = 0; i < disk.diskCount(); i++) {
            //System.out.println ("disk "+disk.getName(i)+" has "+
                    //disk.getTotal(i)+" blocks, of which "+
                    //disk.getUsed(i)+" are used");
            //prints out unused amount of disk by taking away Used disk from Tota Disk

            System.out.println("disk unused = " + (disk.getTotal(i) - disk.getUsed(i)));
            //assigns double to percentageUsed and then divides Used disk by total disk and multiplies by 100


            double percentageUsed = ((double) disk.getUsed(i) / disk.getTotal(i)) * 100;

//prints out the percentage of disk used and prints out percent sign as well(%)


            System.out.println("percentage of disk used = " + percentageUsed + "%");
            //initialized percentage unused as a double and the equal to percentage used taken away from 100 to get the remainder of percentage unused

            double percentageUnUsed = 100 - percentageUsed;

            System.out.println("percentage of disk unused = " + percentageUnUsed + "%");


        }

    }

    public static void showMem()
    {
        memInfo mem = new memInfo();
        mem.read();

        double totalGB = (double) mem.getTotal() /(1000 * 1000 );
        double usedGB =(double) mem.getUsed() /(Math.pow(2,20));


        System.out.println ("There is "+mem.getTotal()+" memory of which "+
            mem.getUsed()+" is used");
        System.out.println("there is " + totalGB +"Gb of memory,of which"+ usedGB +"GB is used");
        //prints out unused memory in kilobytes.
        System.out.println("memory unused in kilobytes =" + (mem.getTotal()- mem.getUsed()));
        //prints out unused memory in GB
        System.out.println("memory unused in GB =" +(totalGB - usedGB));
        //Displays the Percentage of used memory
        double memorypercentageUsed = ((double)mem.getUsed()/ mem.getTotal()) * 100;
        System.out.println("percentage of memory used =" + memorypercentageUsed + "%");
//displays percentage of unused memory
//makes memorypercentageUsed100 as what is unused memory percentage by taking it away from 100.
        double memorypercentageUsed100 = 100 - memorypercentageUsed;
        System.out.println("percentage of unused memory =" + memorypercentageUsed100 + "%" );


    }






    public static void main(String[] args)
    {
        System.loadLibrary("sysinfo");
        sysInfo info = new sysInfo();
        cpuInfo cpu = new cpuInfo();
        cpu.read(0);

        //showCPU();
        showPCI();
        //showUSB();
        //showDisk();
        //showMem();
        //showSys();

    }
}

