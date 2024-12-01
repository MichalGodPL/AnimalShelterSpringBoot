package org.example.springbootanimalshelter.UserInterface;

import javax.swing.table.AbstractTableModel;

import javax.swing.JTable;

import java.util.ArrayList;

import java.util.Comparator;

import java.util.List;

import org.example.springbootanimalshelter.Code.Animal;


public class AnimalTableModel extends AbstractTableModel {

    private final String[] baseColumnNames = {"Imię", "Gatunek", "Kondycja", "Wiek", "Cena"};

    private String[] columnNames;

    private List<Animal> animals;

    private final List<Animal> allAnimals; // Wszystkie zwierzęta, na wypadek filtrowania

    private int lastSortedColumn = -1; // Indeks ostatnio sortowanej kolumny

    private boolean ascending = true; // Kierunek sortowania


    public AnimalTableModel(List<Animal> animals) {

        this.animals = new ArrayList<>(animals);

        this.allAnimals = new ArrayList<>(animals); // Kopia wszystkich zwierząt

        this.columnNames = baseColumnNames.clone(); // Nazwy kolumn z możliwością aktualizacji

    }


    @Override

    public int getRowCount() {

        return animals.size();

    }

    @Override

    public int getColumnCount() {

        return columnNames.length;

    }


    @Override

    public Object getValueAt(int rowIndex, int columnIndex) {

        Animal animal = animals.get(rowIndex);

        return switch (columnIndex) {

            case 0 -> animal.getName();

            case 1 -> animal.getSpecies();

            case 2 -> animal.getCondition();

            case 3 -> animal.getAge();

            case 4 -> animal.getPrice();

            default -> null;

        };
    }

    @Override

    public String getColumnName(int column) {

        return columnNames[column];

    }

    public void sortByColumn(int columnIndex) {

        // Jeśli kliknięto tę samą kolumnę, odwróć kierunek sortowania

        if (lastSortedColumn == columnIndex) {

            ascending = !ascending;

        } else {

            ascending = true; // Nowa kolumna -> zawsze zaczynamy od sortowania rosnącego

        }

        lastSortedColumn = columnIndex;

        Comparator<Animal> comparator = switch (columnIndex) {

            case 0 -> Comparator.comparing(Animal::getName);

            case 1 -> Comparator.comparing(Animal::getSpecies);

            case 2 -> Comparator.comparing(Animal::getCondition);

            case 3 -> Comparator.comparingInt(Animal::getAge);

            case 4 -> Comparator.comparingDouble(Animal::getPrice);

            default -> throw new IllegalArgumentException("Nieznana kolumna: " + columnIndex);

        };

        if (!ascending) {

            comparator = comparator.reversed();

        }

        animals.sort(comparator);

        updateColumnHeaders();

        fireTableDataChanged();

    }


    private void updateColumnHeaders() {

        // Przywrócenie nazw kolumn do domyślnych

        System.arraycopy(baseColumnNames, 0, columnNames, 0, baseColumnNames.length);

        // Dodanie strzałki wskazującej kierunek sortowania

        if (lastSortedColumn != -1) {

            columnNames[lastSortedColumn] = baseColumnNames[lastSortedColumn] +

                    (ascending ? " ↑" : " ↓");

        }

        fireTableStructureChanged();

    }


    public static void enableSorting(JTable table, AnimalTableModel model) {

        table.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {

            @Override

            public void mouseClicked(java.awt.event.MouseEvent e) {

                int column = table.columnAtPoint(e.getPoint());

                model.sortByColumn(column);

            }

        });

    }

    public void updateData(List<Animal> updatedAnimals) {

        this.animals = new ArrayList<>(updatedAnimals);

        fireTableDataChanged();

    }

    public void filterByName(String query) {

        if (query == null || query.isEmpty()) {

            animals = new ArrayList<>(allAnimals);

        } else {

            animals = allAnimals.stream()

                    .filter(animal -> animal.getName().toLowerCase().contains(query.toLowerCase()))

                    .toList();

        }

        fireTableDataChanged();

    }

    public Animal getAnimalAt(int rowIndex) {

        return animals.get(rowIndex);

    }

}


