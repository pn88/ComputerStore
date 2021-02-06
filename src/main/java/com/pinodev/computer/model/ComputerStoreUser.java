package com.pinodev.computer.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ComputerStoreUser extends User {
    private List<LaptopFactory> laptopFactoryHistory = new ArrayList<>();
    private List<LaptopFactory> borrowedLaptopFactories = new ArrayList<>();

      public ComputerStoreUser(String firstName, String lastName, String pesel) {
        super(firstName, lastName, pesel);
    }

    @Override
    public String toCsv() {
        return getFirstName() + ";" + getLastName() + ";" + getPesel();
    }

    private void addLaptopToHistory(LaptopFactory pub) {
        laptopFactoryHistory.add(pub);
    }

    public boolean returnLaptop(LaptopFactory pub) {
        boolean result = false;
        if (borrowedLaptopFactories.remove(pub)) {
            result = true;
            addLaptopToHistory(pub);
        }
        return result;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ComputerStoreUser that = (ComputerStoreUser) o;
        return Objects.equals(laptopFactoryHistory, that.laptopFactoryHistory) &&
                Objects.equals(borrowedLaptopFactories, that.borrowedLaptopFactories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), laptopFactoryHistory, borrowedLaptopFactories);
    }
}