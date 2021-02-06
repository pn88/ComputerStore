package com.pinodev.computer.io;

import com.pinodev.computer.model.*;

import java.util.Collection;

public class ConsolePrinter {
    public void printPcs(Collection<LaptopFactory> laptopFactories) {
        long count = laptopFactories.stream()
                .filter(p -> p instanceof Pc)
                .map(LaptopFactory::toString)
                .peek(this::printLine)
                .count();
        if (count == 0)
            printLine("No pc's in the com.pinodev.library");
    }

    public void printLaptops(Collection<LaptopFactory> laptopFactories) {
        long count = laptopFactories.stream()
                .filter(p -> p instanceof Laptop)
                .map(LaptopFactory::toString)
                .peek(this::printLine)
                .count();
        if (count == 0)
            printLine("No laptops in the com.pinodev.library");
    }

    public void printUsers(Collection<ComputerStoreUser> users) {
        users.stream()
                .map(User::toString)
                .forEach(this::printLine);
    }

    public void printLine(String text) {
        System.out.println(text);
    }
}