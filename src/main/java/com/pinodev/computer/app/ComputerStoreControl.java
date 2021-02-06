package com.pinodev.computer.app;

import com.pinodev.computer.exception.*;
import com.pinodev.computer.io.ConsolePrinter;
import com.pinodev.computer.io.DataReader;
import com.pinodev.computer.io.file.FileManager;
import com.pinodev.computer.io.file.FileManagerBuilder;
import com.pinodev.computer.model.*;

import java.util.Comparator;
import java.util.InputMismatchException;

class ComputerStoreControl {
    private ConsolePrinter printer = new ConsolePrinter();
    private DataReader dataReader = new DataReader(printer);
    private FileManager fileManager;

    private ComputerStore computerStore;

    ComputerStoreControl() {
        fileManager = new FileManagerBuilder(printer, dataReader).build();
        try {
            computerStore = fileManager.importData();
            printer.printLine("Imported data from a file");
        } catch (DataImportException | InvalidDataException e) {
            printer.printLine(e.getMessage());
            printer.printLine("New base initiated");
            computerStore = new ComputerStore();
        }
    }

    void controlLoop() {
        Option option;

        do {
            printOptions();
            option = getOption();
            switch (option) {
                case ADD_PC:
                    addPc();
                    break;
                case ADD_LAPTOP:
                    addLaptop();
                    break;
                case PRINT_PCS:
                    printPcs();
                    break;
                case PRINT_LAPTOPS:
                    printLaptops();
                    break;
                case DELETE_PC:
                    deletePc();
                    break;
                case DELETE_LAPTOP:
                    deleteLaptop();
                    break;
                case ADD_USER:
                    addUser();
                    break;
                case PRINT_USERS:
                    printUsers();
                    break;
                case FIND_PC:
                    findPc();
                    break;
                case EXIT:
                    exit();
                    break;
                default:
                    printer.printLine("There is no such option, please re-enter: ");
            }
        } while (option != Option.EXIT);
    }

    private Option getOption() {
        boolean optionOk = false;
        Option option = null;
        while (!optionOk) {
            try {
                option = Option.createFromInt(dataReader.getInt());
                optionOk = true;
            } catch (NoSuchOptionException e) {
                printer.printLine(e.getMessage() + ", re-enter:");
            } catch (InputMismatchException ignored) {
                printer.printLine("You entered a value that is not a number, please enter again: ");
            }
        }

        return option;
    }

    private void printOptions() {
        printer.printLine("Choose an option: ");
        for (Option option : Option.values()) {
            printer.printLine(option.toString());
        }
    }

    private void addPc() {
        try {
            Pc pc = dataReader.readAndCreatePc();
            computerStore.addLaptop(pc);
        } catch (InputMismatchException e) {
            printer.printLine("Pc creation failed, invalid data");
        } catch (ArrayIndexOutOfBoundsException e) {
            printer.printLine("Capacity is reached, no more pc can be added");
        }
    }

    private void addLaptop() {
        try {
            Laptop laptop = dataReader.readAndCreateLaptop();
            computerStore.addLaptop(laptop);
        } catch (InputMismatchException e) {
            printer.printLine("Laptop could not be created, invalid data");
        } catch (ArrayIndexOutOfBoundsException e) {
            printer.printLine("The capacity limit has been reached, you cannot add more storage");
        }
    }

    private void addUser() {
        ComputerStoreUser computerStoreUser = dataReader.createComputerStoreUser();
        try {
            computerStore.addUser(computerStoreUser);
        } catch (UserAlreadyExistsException e) {
            printer.printLine(e.getMessage());
        }
    }

    private void printPcs() {
        printer.printPcs(computerStore.getSortedPublications(
                Comparator.comparing(LaptopFactory::getTitle, String.CASE_INSENSITIVE_ORDER))
        );
    }

    private void printLaptops() {
        printer.printLaptops(computerStore.getSortedPublications(
              Comparator.comparing(LaptopFactory::getTitle, String.CASE_INSENSITIVE_ORDER)
        ));
    }

    private void printUsers() {
        printer.printUsers(computerStore.getSortedUsers(
                Comparator.comparing(User::getLastName, String.CASE_INSENSITIVE_ORDER)
        ));
    }

    private void findPc() {
        printer.printLine("Enter the title of the laptop:");
        String title = dataReader.getString();
        String notFoundMessage = "There are no laptop with this title";
        computerStore.findLaptopByTitle(title)
                .map(LaptopFactory::toString)
                .ifPresentOrElse(System.out::println, () -> System.out.println(notFoundMessage));
    }

    private void deleteLaptop() {
        try {
            Laptop laptop = dataReader.readAndCreateLaptop();
            if (computerStore.removeLaptop(laptop))
                printer.printLine("Removed laptop.");
            else
                printer.printLine("No laptop indicated.");
        } catch (InputMismatchException e) {
            printer.printLine("Laptop could not be created, invalid data");
        }
    }

    private void deletePc() {
        try {
            Pc pc = dataReader.readAndCreatePc();
            if (computerStore.removeLaptop(pc))
                printer.printLine("Pc removed.");
            else
                printer.printLine("No pc indicated.");
        } catch (InputMismatchException e) {
            printer.printLine("Failed to create pc, invalid data");
        }
    }
    private void exit() {
        try {
            fileManager.exportData(computerStore);
            printer.printLine("Export of data to file successfully");
        } catch (DataExportException e) {
            printer.printLine(e.getMessage());
        }
        dataReader.close();
        printer.printLine("End of show");
    }

    private enum Option {
        EXIT(0, "Exit"),
        ADD_PC(1, "Add a pc"),
        ADD_LAPTOP(2, "Add a laptop"),
        PRINT_PCS(3, "View available pcs"),
        PRINT_LAPTOPS(4, "View available laptops"),
        DELETE_PC(5, "Delete a pc"),
        DELETE_LAPTOP(6, "Delete laptop"),
        ADD_USER(7, "Add user"),
        PRINT_USERS(8, "View users"),
        FIND_PC(9, "Search for a laptop");

        private int value;
        private String description;

        Option(int value, String desc) {
            this.value = value;
            this.description = desc;
        }

        @Override
        public String toString() {
            return value + " - " + description;
        }

        static Option createFromInt(int option) throws NoSuchOptionException {
            try {
                return Option.values()[option];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new NoSuchOptionException("No option on id " + option);
            }
        }
    }
}