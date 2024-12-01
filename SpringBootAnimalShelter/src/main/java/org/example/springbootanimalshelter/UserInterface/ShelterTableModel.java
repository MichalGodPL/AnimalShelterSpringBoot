package org.example.springbootanimalshelter.UserInterface;

import javax.swing.table.AbstractTableModel;

import javax.swing.JTable;

import java.util.ArrayList;

import java.util.Comparator;

import java.util.List;

import java.util.stream.Collectors;

import org.example.springbootanimalshelter.Code.AnimalShelter;


public class ShelterTableModel extends AbstractTableModel {

    private final String[] baseColumnNames = {"Nazwa", "Obecna liczba", "Maksymalna liczba"};

    private String[] columnNames;

    private List<AnimalShelter> shelters;

    private final List<AnimalShelter> allShelters;

    private int lastSortedColumn = -1; // Indeks ostatnio sortowanej kolumny

    private boolean ascending = true; // Kierunek sortowania


    public ShelterTableModel(List<AnimalShelter> shelters) {

        this.shelters = new ArrayList<>(shelters);

        this.allShelters = new ArrayList<>(shelters);

        this.columnNames = baseColumnNames.clone(); // Nazwy kolumn z możliwością aktualizacji

    }


    @Override

    public int getRowCount() {

        return shelters.size();

    }


    @Override

    public int getColumnCount() {

        return columnNames.length;

    }


    @Override

    public Object getValueAt(int rowIndex, int columnIndex) {

        AnimalShelter shelter = shelters.get(rowIndex);


        return switch (columnIndex) {

            case 0 -> shelter.getShelterName(); // Nazwa schroniska

            case 1 -> getHardcodedAnimalCount(shelter); // Obecna liczba (ustawiona na sztywno)

            case 2 -> shelter.getMaxCapacity(); // Maksymalna liczba

            default -> null;

        };

    }


    // Metoda do zwracania liczby zwierząt na sztywno

    private int getHardcodedAnimalCount(AnimalShelter shelter) {

        return switch (shelter.getShelterName()) {

            case "Schronisko A" -> 3; // Liczba dla Schroniska A

            case "Schronisko B" -> 2; // Liczba dla Schroniska B

            default -> 0; // Domyślnie 0 dla innych schronisk

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


        Comparator<AnimalShelter> comparator = switch (columnIndex) {

            case 0 -> Comparator.comparing(AnimalShelter::getShelterName);

            case 1 -> Comparator.comparingInt(AnimalShelter::getCurrentCapacity);

            case 2 -> Comparator.comparingInt(AnimalShelter::getMaxCapacity);

            default -> throw new IllegalArgumentException("Nieznana kolumna: " + columnIndex);

        };


        if (!ascending) {

            comparator = comparator.reversed();

        }


        shelters.sort(comparator);

        updateColumnHeaders();

        fireTableDataChanged();

    }

    public void updateData(List<AnimalShelter> updatedShelters) {

        this.shelters = updatedShelters;

        fireTableDataChanged(); // Powiadamia tabelę o zmianach danych

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

    public static void enableSorting(JTable table, ShelterTableModel model) {

        table.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {

            @Override

            public void mouseClicked(java.awt.event.MouseEvent e) {

                int column = table.columnAtPoint(e.getPoint());

                model.sortByColumn(column);

            }

        });

    }


    public void filterByName(String query) {

        if (query == null || query.isEmpty()) {

            shelters = new ArrayList<>(allShelters);

        } else {

            shelters = allShelters.stream()

                    .filter(shelter -> shelter.getShelterName().toLowerCase().contains(query.toLowerCase()))

                    .collect(Collectors.toList());

        }

        fireTableDataChanged();

    }


    public void filterByState(String state) {

        if ("Wszystkie".equals(state)) {

            shelters = new ArrayList<>(allShelters);

        } else if ("Puste".equals(state)) {

            shelters = allShelters.stream()

                    .filter(shelter -> shelter.getCurrentCapacity() == 0)

                    .collect(Collectors.toList());

        } else if ("Pełne".equals(state)) {

            shelters = allShelters.stream()

                    .filter(shelter -> shelter.getCurrentCapacity() >= shelter.getMaxCapacity())

                    .collect(Collectors.toList());

        }

        fireTableDataChanged();

    }
}

