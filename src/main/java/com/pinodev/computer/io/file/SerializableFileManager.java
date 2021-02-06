package com.pinodev.computer.io.file;

import com.pinodev.computer.exception.DataExportException;
import com.pinodev.computer.exception.DataImportException;
import com.pinodev.computer.model.ComputerStore;

import java.io.*;

public class SerializableFileManager implements FileManager {
    private static final String FILE_NAME = "ComputerStore.o";

    @Override
    public void exportData(ComputerStore computerStore) {
        try (FileOutputStream fos = new FileOutputStream(FILE_NAME);
             ObjectOutputStream oos = new ObjectOutputStream(fos);
             ){
            oos.writeObject(computerStore);
        } catch (FileNotFoundException e) {
            throw new DataExportException("No file " + FILE_NAME);
        } catch (IOException e) {
            throw new DataExportException("Error writing data to file " + FILE_NAME);
        }
    }

    @Override
    public ComputerStore importData() {
        try (FileInputStream fis = new FileInputStream(FILE_NAME);
             ObjectInputStream ois = new ObjectInputStream(fis);
             ) {
            return (ComputerStore) ois.readObject();
        } catch (FileNotFoundException e) {
            throw new DataImportException("No file " + FILE_NAME);
        } catch (IOException e) {
            throw new DataImportException("Error reading file " + FILE_NAME);
        } catch (ClassNotFoundException e) {
            throw new DataImportException("Inconsistent data type in file " + FILE_NAME);
        }
    }
}