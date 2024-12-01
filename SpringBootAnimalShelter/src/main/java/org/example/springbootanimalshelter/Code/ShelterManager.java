package org.example.springbootanimalshelter.Code;

import java.util.HashMap;

import java.util.List;

import java.util.Map;

import java.util.stream.Collectors;


public class ShelterManager {

    private Map<String, AnimalShelter> shelters;


    public ShelterManager() {

        shelters = new HashMap<>();

    }


    // Dodawanie nowego schroniska

    public void addShelter(String name, int capacity) {

        shelters.put(name, new AnimalShelter(name, capacity));

    }


    // Usuwanie schroniska

    public void removeShelter(String name) {

        shelters.remove(name);

    }


    // Wyszukiwanie pustych schronisk

    public List<AnimalShelter> findEmpty() {

        return shelters.values().stream()

                .filter(shelter -> shelter.getCurrentCapacity() == 0)

                .collect(Collectors.toList());

    }

    public AnimalShelter getShelter(String name) {

        return shelters.get(name);

    }

    public Map<String, AnimalShelter> getShelters() {

        return shelters;

    }

    // Podsumowanie z informacją o zapełnieniu

    public void summary() {

        shelters.forEach((name, shelter) -> {

            double occupancy = (double) shelter.getCurrentCapacity() / shelter.getMaxCapacity() * 100;

            System.out.printf("Schronisko: %s, Zapełnienie: %.2f%%\n", name, occupancy);

        });

    }

}
