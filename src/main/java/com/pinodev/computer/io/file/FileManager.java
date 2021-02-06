package com.pinodev.computer.io.file;

import com.pinodev.computer.model.ComputerStore;

public interface FileManager {
    ComputerStore importData();
    void exportData(ComputerStore computerStore);
}