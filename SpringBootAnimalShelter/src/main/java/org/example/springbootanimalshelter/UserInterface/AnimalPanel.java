package org.example.springbootanimalshelter.UserInterface;

import javax.swing.*;

import java.awt.*;

import javax.swing.table.TableRowSorter;

import javax.swing.JTable;

import org.example.springbootanimalshelter.Code.Animal;

import org.example.springbootanimalshelter.Code.AnimalCondition;

import org.example.springbootanimalshelter.Code.AnimalShelter;


public class AnimalPanel {

    private AnimalShelter shelter;

    private final String adminPassword = "2137"; // Hasło administratora

    public AnimalPanel(AnimalShelter shelter) {

        this.shelter = shelter;

    }


    public void show() {

        JFrame frame = new JFrame("Zwierzęta w schronisku: " + shelter.getShelterName());

        frame.setSize(800, 600);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        frame.add(mainPanel);


        // Tabela ze zwierzętami

        AnimalTableModel tableModel = new AnimalTableModel(shelter.getAnimals());

        JTable animalTable = new JTable(tableModel);

        animalTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        animalTable.setRowHeight(25);

        animalTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));


        JScrollPane animalScrollPane = new JScrollPane(animalTable);

        mainPanel.add(animalScrollPane, BorderLayout.CENTER);


        // Dodanie sorterów do tabeli

        TableRowSorter<AnimalTableModel> sorter = new TableRowSorter<>(tableModel);

        animalTable.setRowSorter(sorter);


        // Panel przycisków

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton addButton = new JButton("Dodaj zwierzę");

        JButton removeButton = new JButton("Usuń zwierzę");

        JButton modifyButton = new JButton("Edytuj zwierzę");

        JButton closeButton = new JButton("Zamknij");


        buttonPanel.add(addButton);

        buttonPanel.add(removeButton);

        buttonPanel.add(modifyButton);

        buttonPanel.add(closeButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);


        // Obsługa przycisku "Dodaj zwierzę"

        addButton.addActionListener(e -> {

            if (!authenticate()) {

                JOptionPane.showMessageDialog(frame, "Nieprawidłowe hasło!", "Błąd", JOptionPane.ERROR_MESSAGE);

                return;

            }


            String name = JOptionPane.showInputDialog(frame, "Podaj nazwę zwierzęcia:");

            String species = JOptionPane.showInputDialog(frame, "Podaj gatunek:");

            String ageString = JOptionPane.showInputDialog(frame, "Podaj wiek:");

            String priceString = JOptionPane.showInputDialog(frame, "Podaj cenę adopcji:");


            String[] conditions = {"ZDROWE", "CHORE", "W TRAKCIE ADOPCJI", "KWARANTANNA"};

            String condition = (String) JOptionPane.showInputDialog(

                    frame,

                    "Wybierz kondycję zwierzęcia:",

                    "Kondycja",

                    JOptionPane.QUESTION_MESSAGE,

                    null,

                    conditions,

                    conditions[0]

            );


            try {

                int age = Integer.parseInt(ageString);

                double price = Double.parseDouble(priceString);

                AnimalCondition animalCondition = AnimalCondition.valueOf(condition);

                shelter.addAnimal(new Animal(name, species, animalCondition, age, price));

                tableModel.updateData(shelter.getAnimals());

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(frame, "Podano nieprawidłowe dane!", "Błąd", JOptionPane.ERROR_MESSAGE);

            }

        });


        // Obsługa przycisku "Usuń zwierzę"

        removeButton.addActionListener(e -> {

            if (!authenticate()) {

                JOptionPane.showMessageDialog(frame, "Nieprawidłowe hasło!", "Błąd", JOptionPane.ERROR_MESSAGE);

                return;

            }


            int selectedRow = animalTable.getSelectedRow();

            if (selectedRow >= 0) {

                Animal selectedAnimal = tableModel.getAnimalAt(selectedRow);

                shelter.removeAnimal(selectedAnimal);

                tableModel.updateData(shelter.getAnimals());

            } else {

                JOptionPane.showMessageDialog(frame, "Wybierz zwierzę do usunięcia!");

            }

        });


        // Obsługa przycisku "Edytuj zwierzę"

        modifyButton.addActionListener(e -> {

            if (!authenticate()) {

                JOptionPane.showMessageDialog(frame, "Nieprawidłowe hasło!", "Błąd", JOptionPane.ERROR_MESSAGE);

                return;

            }


            int selectedRow = animalTable.getSelectedRow();

            if (selectedRow >= 0) {

                Animal selectedAnimal = tableModel.getAnimalAt(selectedRow);


                String newName = JOptionPane.showInputDialog(frame, "Podaj nową nazwę:", selectedAnimal.getName());

                String newSpecies = JOptionPane.showInputDialog(frame, "Podaj nowy gatunek:", selectedAnimal.getSpecies());

                String newAgeString = JOptionPane.showInputDialog(frame, "Podaj nowy wiek:", selectedAnimal.getAge());

                String newPriceString = JOptionPane.showInputDialog(frame, "Podaj nową cenę adopcji:", selectedAnimal.getPrice());


                String[] conditions = {"ZDROWE", "CHORE"};

                String newCondition = (String) JOptionPane.showInputDialog(

                        frame,

                        "Wybierz nową kondycję zwierzęcia:",

                        "Kondycja",

                        JOptionPane.QUESTION_MESSAGE,

                        null,

                        conditions,

                        selectedAnimal.getCondition().name()

                );


                try {

                    int newAge = Integer.parseInt(newAgeString);

                    double newPrice = Double.parseDouble(newPriceString);

                    AnimalCondition animalCondition = AnimalCondition.valueOf(newCondition);


                    selectedAnimal.setName(newName);

                    selectedAnimal.setSpecies(newSpecies);

                    selectedAnimal.setAge(newAge);

                    selectedAnimal.setPrice(newPrice);

                    selectedAnimal.setCondition(animalCondition);

                    tableModel.updateData(shelter.getAnimals());

                } catch (NumberFormatException ex) {

                    JOptionPane.showMessageDialog(frame, "Podano nieprawidłowe dane!", "Błąd", JOptionPane.ERROR_MESSAGE);

                }

            } else {

                JOptionPane.showMessageDialog(frame, "Wybierz zwierzę do edycji!");

            }

        });


        // Obsługa przycisku "Zamknij"

        closeButton.addActionListener(e -> frame.dispose());

        frame.setVisible(true);

    }

    private boolean authenticate() {

        String inputPassword = JOptionPane.showInputDialog(null, "Wprowadź hasło administratora:");

        return adminPassword.equals(inputPassword);

    }

}
