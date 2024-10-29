/*
 *  Example class containing methods to read and display CPU, PCI and USB information
 *
 *  Copyright (c) 2024 Mark Burkley (mark.burkley@ul.ie)
 */
import java.util.*;
public class template 
{
    // Vendor Converter changes the Vendor ID into the name - example: 0x8086 is Intel
    public static String vedorIdConverter(String vendorId){
        String[] vendorNames = {"intel","AMD","Ralink","Realtek Semiconductor", "02 micro", "Nvidia", "Emulex", "Fujitsu",
        };
        String[] vendorIdList = {"0x8086","0x1022","0x1814", "0x10ec", "0x1217","0x10de", "0x19a2", "0x1734" };
        for(int i = 0;i < vendorIdList.length;i++){
            if(vendorId.compareTo(vendorIdList[i]) == 0){
                vendorId = vendorNames[i];
            }
        }
        return vendorId;

    }

    //PCI Information - Shows Information about the PCI devices on your machine
    public static void showPCI() {
        pciInfo pci = new pciInfo();
        pci.read();
        String intel = "0x8086";
        String vendor;
        int intelCount = 0;
        int vendorCount = 0;
        ArrayList<String> pciDevices = new ArrayList<>();
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
                                    " and product " + String.format("0x%04X", pci.productID(i, j, k)));
                            vendor = String.format("0x%04X", pci.vendorID(i, j, k));
                            if (intel.compareTo(vendor) == 0) {
                                intelCount++;
                                vendorCount++;
                            } else {
                                vendorCount++;
                            }
                            pciCount++;
                            pciDevices.add(vendor);
                        }
                    }
                }
            }
        }
        //Prints out what percentage of the devices in use are intel
        System.out.printf("this device is %d percent intel, intelCount = %d, venderCount" +
                " = %d", (intelCount * 100 / vendorCount), intelCount, vendorCount);
        for(int i = 0;i < pciDevices.size(); i++){
            System.out.print("\n" + pciDevices.get(i));
        }
    }

    //ShowUSB is used to get information about any USB buses that may be being used by the computer
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

    //ShowCPU contains all the information about the CPU and the sizes of the L1,L2 and L3 Cache
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

        // Sleep for 1 second and display the idle time percentage for
        // core 1.  This assumes 10Hz so in one second we have 100
        cpu.read(1);
        System.out.println("core 1 idle="+cpu.getIdleTime(1)+"%");
    }

    // ShowSys prints out what the method does
    public static void showSys(){
        sysInfo system = new sysInfo();
        System.out.println("the method intExample(1) does " +system.intExample(1) );
    }

    //ShowDisk contains the total disk storage and how much is being used
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

//ShowMem converts MB to GB and prints how much memory is used
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
        //showPCI();
        //showUSB();
        showDisk();
        //showMem();
        //showSys();
        //e
    }
}

