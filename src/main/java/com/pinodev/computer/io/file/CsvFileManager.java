package com.pinodev.computer.io.file;

import com.pinodev.computer.exception.DataExportException;
import com.pinodev.computer.exception.DataImportException;
import com.pinodev.computer.exception.InvalidDataException;
import com.pinodev.computer.model.*;

import java.io.*;
import java.util.Collection;

public class CsvFileManager implements FileManager {
    private static final String LAPTOPS_FILE_NAME = "ComputerStore.csv";
    private static final String USERS_FILE_NAME = "ComputerStore_users.csv";

    @Override
    public void exportData(ComputerStore computerStore) {
        exportPublications(computerStore);
        exportUsers(computerStore);
    }

    @Override
    public ComputerStore importData() {
        ComputerStore computerStore = new ComputerStore();
        importLaptops(computerStore);
        importUsers(computerStore);
        return computerStore;
    }

    private void exportPublications(ComputerStore computerStore) {
        Collection<LaptopFactory> laptopFactories = computerStore.getPublications().values();
        exportToCsv(laptopFactories, LAPTOPS_FILE_NAME);
    }

    private void exportUsers(ComputerStore computerStore) {
        Collection<ComputerStoreUser> users = computerStore.getUsers().values();
        exportToCsv(users, USERS_FILE_NAME);
    }

    private <T extends CsvConvertible> void exportToCsv(Collection<T> collection, String fileName) {
        try (FileWriter fileWriter = new FileWriter(fileName);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            for (T element : collection) {
                bufferedWriter.write(element.toCsv());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new DataExportException("Error writing data to file " + fileName);
        }
    }

    private void importLaptops(ComputerStore computerStore) {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(LAPTOPS_FILE_NAME))) {
            bufferedReader.lines()
                    .map(this::createObjectFromString)
                    .forEach(computerStore::addLaptop);
        } catch (FileNotFoundException e) {
            throw new DataImportException("No file " + LAPTOPS_FILE_NAME);
        } catch (IOException e) {
            throw new DataImportException("Error reading file " + LAPTOPS_FILE_NAME);
        }
    }

    private LaptopFactory createObjectFromString(String csvText) {
        String[] split = csvText.split(";");
        String type = split[0];
        if(Pc.TYPE.equals(type)) {
            return createPc(split);
        } else if(Laptop.TYPE.equals(type)) {
            return createLaptop(split);
        }
        throw new InvalidDataException("Unknown laptop: " + type);
    }

    private Pc createPc(String[] data) {
        String producer = data[1];
        String x = data[2];
        int memoryRam = Integer.valueOf(data[3]);
        String model = data[4];
        int serialNumber = Integer.valueOf(data[5]);
        String y = data[6];
        return new Pc(producer, model, memoryRam, serialNumber, x, y);
    }

    private Laptop createLaptop(String[] data) {
        String producer = data[1];
        String model = data[2];
        int memoryRam = Integer.valueOf(data[3]);
        int x = Integer.valueOf(data[4]);
        int y = Integer.valueOf(data[5]);
        String memoryType = data[6];
        return new Laptop(producer, model, memoryType, memoryRam);
    }

    private void importUsers(ComputerStore computerStore) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(USERS_FILE_NAME))) {
            bufferedReader.lines()
                    .map(this::createUserFromString)
                    .forEach(computerStore::addUser);
        } catch (FileNotFoundException e) {
            throw new DataImportException("No file " + USERS_FILE_NAME);
        } catch (IOException e) {
            throw new DataImportException("Error reading file " + USERS_FILE_NAME);
        }

    }

    private ComputerStoreUser createUserFromString(String csvText) {
        String[] split = csvText.split(";");
        String firstName = split[0];
        String lastName = split[1];
        String pesel = split[2];
        return new ComputerStoreUser(firstName, lastName, pesel);
    }
}