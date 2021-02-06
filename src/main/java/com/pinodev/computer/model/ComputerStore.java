package com.pinodev.computer.model;

import com.pinodev.computer.exception.PublicationAlreadyExistsException;
import com.pinodev.computer.exception.UserAlreadyExistsException;

import java.io.Serializable;
import java.util.*;

public class ComputerStore implements Serializable {

    private Map<String, LaptopFactory> publications = new HashMap<>();
    private Map<String, ComputerStoreUser> users = new HashMap<>();

    public Map<String, LaptopFactory> getPublications() {
        return publications;
    }

    public Collection<LaptopFactory> getSortedPublications(Comparator<LaptopFactory> comparator) {
        ArrayList<LaptopFactory> list = new ArrayList<>(this.publications.values());
        list.sort(comparator);
        return list;
    }

    public Map<String, ComputerStoreUser> getUsers() {
        return users;
    }

    public Collection<ComputerStoreUser> getSortedUsers(Comparator<ComputerStoreUser> comparator) {
        ArrayList<ComputerStoreUser> list = new ArrayList<>(this.users.values());
        list.sort(comparator);
        return list;
    }

    public void addUser(ComputerStoreUser user) {
        if(users.containsKey(user.getPesel()))
            throw new UserAlreadyExistsException(
                    "The user with the specified user ID already exists " + user.getPesel()
            );
        users.put(user.getPesel(), user);
    }

    public void addLaptop(LaptopFactory laptopFactory) {
        if(publications.containsKey(laptopFactory.getTitle()))
            throw new PublicationAlreadyExistsException(
                    "A laptop already exists " + laptopFactory.getTitle()
            );
        publications.put(laptopFactory.getTitle(), laptopFactory);
    }

public Optional<LaptopFactory> findLaptopByTitle(String title) {
    return Optional.ofNullable(publications.get(title));
}

    public boolean removeLaptop(LaptopFactory laptopFactory) {
        if(publications.containsValue(laptopFactory)) {
            publications.remove(laptopFactory.getTitle());
            return true;
        } else {
            return false;
        }
    }
}