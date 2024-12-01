package org.example.springbootanimalshelter.Code;

import java.util.ArrayList;

import java.util.Collections;

import java.util.Comparator;

import java.util.List;


public class AnimalShelter {

    private String shelterName;

    private List<Animal> animalList;

    private int maxCapacity;


    public AnimalShelter(String shelterName, int maxCapacity) {

        this.shelterName = shelterName;

        this.maxCapacity = maxCapacity;

        this.animalList = new ArrayList<>();

    }

    // Dodawanie zwierzęcia

    public void addAnimal(Animal animal) {

        if (animalList.size() >= maxCapacity) {

            System.err.println("Schronisko jest Pełne!");

            return;

        }

        for (Animal a : animalList) {

            if (a.getName().equals(animal.getName()) &&

                    a.getSpecies().equals(animal.getSpecies()) &&

                    a.getAge() == animal.getAge()) {

                System.out.println("Zwierzę już Istnieje w Schronisku.");

                return;

            }

        }

        animalList.add(animal);

    }


    // Usuwanie zwierzęcia

    public void removeAnimal(Animal animal) {

        animalList.remove(animal);

    }



    // Adopcja zwierzęcia

    public void getAnimal(Animal animal) {

        animal.setCondition(AnimalCondition.W_TRAKCIE_ADOPCJI);

        animalList.remove(animal);

    }

    public List<Animal> getAnimals() {

        return new ArrayList<>(animalList); // Zwraca kopię listy, aby zabezpieczyć oryginalną listę

    }


    // Zmiana stanu zwierzęcia

    public void changeCondition(Animal animal, AnimalCondition condition) {

        animal.setCondition(condition);

    }

    // Zmiana wieku zwierzęcia

    public void changeAge(Animal animal, int age) {

        animal.setAge(age);

    }

    // Zliczanie zwierząt według stanu

    public long countByCondition(AnimalCondition condition) {

        return animalList.stream()

                .filter(a -> a.getCondition() == condition)

                .count();

    }

    // Sortowanie według imienia

    public List<Animal> sortByName() {

        List<Animal> sortedList = new ArrayList<>(animalList);

        Collections.sort(sortedList);

        return sortedList;

    }

    // Sortowanie według ceny

    public List<Animal> sortByPrice() {

        List<Animal> sortedList = new ArrayList<>(animalList);

        sortedList.sort(Comparator.comparingDouble(Animal::getPrice));

        return sortedList;

    }

    // Wyszukiwanie według imienia

    private static final Comparator<Animal> NAME_COMPARATOR = Comparator.comparing(Animal::getName, String.CASE_INSENSITIVE_ORDER);

    public Animal search(String name) {

        Animal dummyAnimal = new Animal(name, "", AnimalCondition.ZDROWE, 0, 0.0);


        for (Animal animal : animalList) {

            if (NAME_COMPARATOR.compare(animal, dummyAnimal) == 0) {

                return animal;

            }

        }

        return null;

    }

    // Wyszukiwanie po fragmencie imienia lub gatunku

    public List<Animal> searchPartial(String query) {

        List<Animal> result = new ArrayList<>();

        for (Animal animal : animalList) {

            if (animal.getName().toLowerCase().contains(query) || animal.getSpecies().toLowerCase().contains(query)) {

                result.add(animal);

            }

        }

        return result;

    }

    // Podsumowanie

    public void summary() {

        animalList.forEach(Animal::print);

    }

    // Maksymalne zwierzę według Collections.max (domyślnie według Comparable, czyli po imieniu)

    public Animal max() {

        return Collections.max(animalList);

    }

    public int getCurrentCapacity() {

        return animalList.size();

    }

    public int getMaxCapacity() {

        return maxCapacity;

    }

    public String getShelterName() {

        return shelterName;

    }

}

