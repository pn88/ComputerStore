package com.pinodev.computer.app;

public class ComputerStoreApp {
    private static final String APP_NAME = "ComputerStore v1";

    public static void main(String[] args) {
        System.out.println(APP_NAME);
        ComputerStoreControl compControl = new ComputerStoreControl();
        compControl.controlLoop();
    }
}