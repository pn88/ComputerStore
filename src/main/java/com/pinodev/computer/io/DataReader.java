package com.pinodev.computer.io;

import com.pinodev.computer.model.Pc;
import com.pinodev.computer.model.ComputerStoreUser;
import com.pinodev.computer.model.Laptop;

import java.util.Scanner;

public class DataReader {
    private Scanner sc = new Scanner(System.in);
    private ConsolePrinter printer;

    public DataReader(ConsolePrinter printer) {
        this.printer = printer;
    }

    public void close() {
        sc.close();
    }

    public int getInt() {
        try {
            return sc.nextInt();
        } finally {
            sc.nextLine();
        }
    }

    public String getString() {
        return sc.nextLine();
    }

    public Pc readAndCreatePc() {
        printer.printLine("Producer: ");
        String producer = sc.nextLine();
        printer.printLine("Model: ");
        String model = sc.nextLine();
        printer.printLine("Hard Disk type: ");
        String hardDiskType = sc.nextLine();
        printer.printLine("Serial number: ");
        String serialNumber = sc.nextLine();
        printer.printLine("RAM(MB): ");
        int memory = getInt();
        printer.printLine("USB ports: ");
        int usbPorts = getInt();
        String publisher = null;
        return new Pc(producer, model, memory, usbPorts, publisher, serialNumber);
    }

    public Laptop readAndCreateLaptop() {
        printer.printLine("Producer: ");
        String producer = sc.nextLine();
        printer.printLine("Model: ");
        String model = sc.nextLine();
        printer.printLine("Hard Disk type: ");
        String diskType = sc.nextLine();
        printer.printLine("Serial number: ");
        int serialNumber = getInt();
        printer.printLine("RAM(MB): ");
        int memoryRam = getInt();
        printer.printLine("USB Slots ");
        int usbSlots = getInt();

        return new Laptop(producer, model, diskType, serialNumber);
    }

    public ComputerStoreUser createComputerStoreUser() {
        printer.printLine("First Name");
        String firstName = sc.nextLine();
        printer.printLine("Last name");
        String lastName = sc.nextLine();
        printer.printLine("Pesel");
        String pesel = sc.nextLine();
        return new ComputerStoreUser(firstName, lastName, pesel);
    }

}