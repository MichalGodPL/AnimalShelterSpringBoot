package org.example.springbootanimalshelter.UserInterface;

import javax.swing.*;

import java.awt.*;

import java.awt.event.*;

import java.util.ArrayList;

import org.example.springbootanimalshelter.Code.Animal;

import org.example.springbootanimalshelter.Code.AnimalCondition;

import org.example.springbootanimalshelter.Code.AnimalShelter;

import org.example.springbootanimalshelter.Code.ShelterManager;


public class AdminPanel {

    private ShelterManager manager;

    private Point mouseClickPoint;


    public AdminPanel() {

        manager = new ShelterManager();

        // Przykładowe dane

        manager.addShelter("Schronisko A", 10);

        manager.addShelter("Schronisko B", 5);

    }

    public void show() {

        JFrame frame = new JFrame();

        frame.setSize(800, 500);

        frame.setUndecorated(true);

        frame.setLocationRelativeTo(null);


        // Umożliwienie przeciągania okna

        frame.addMouseListener(new MouseAdapter() {

            @Override

            public void mousePressed(MouseEvent e) {

                mouseClickPoint = e.getPoint();

            }

        });


        frame.addMouseMotionListener(new MouseMotionAdapter() {

            @Override

            public void mouseDragged(MouseEvent e) {

                Point currentLocation = e.getLocationOnScreen();

                frame.setLocation(currentLocation.x - mouseClickPoint.x, currentLocation.y - mouseClickPoint.y);

            }

        });


        JPanel mainPanel = new JPanel(new BorderLayout());

        mainPanel.setBackground(Color.WHITE);

        frame.add(mainPanel);


        // Nagłówek

        JPanel headerPanel = new JPanel();

        headerPanel.setBackground(Color.WHITE);


        JLabel headerLabel = new JLabel("ADMIN PANEL", SwingConstants.CENTER);

        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));

        headerLabel.setForeground(new Color(45, 85, 255));

        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        headerPanel.add(headerLabel);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Tabela schronisk

        ShelterTableModel shelterTableModel = new ShelterTableModel(new ArrayList<>(manager.getShelters().values()));

        JTable shelterTable = new JTable(shelterTableModel);

        shelterTable.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        shelterTable.setRowHeight(30);

        shelterTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));

        shelterTable.getTableHeader().setBackground(new Color(230, 230, 230));


        ShelterTableModel.enableSorting(shelterTable, shelterTableModel);

        JScrollPane shelterScrollPane = new JScrollPane(shelterTable);

        mainPanel.add(shelterScrollPane, BorderLayout.CENTER);


        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        filterPanel.setBackground(Color.WHITE);


        // Pole tekstowe do filtrowania

        JTextField filterField = new JTextField(15);

        filterField.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        filterField.setToolTipText("Wpisz nazwę schroniska i naciśnij Enter, aby przefiltrować.");

        filterPanel.add(new JLabel("Filtruj nazwę:"));

        filterPanel.add(filterField);


        // Lista rozwijana do filtrowania stanu

        JComboBox<String> stateComboBox = new JComboBox<>(new String[]{"Wszystkie", "Puste", "Pełne"});

        stateComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        stateComboBox.setToolTipText("Wybierz stan schroniska.");

        filterPanel.add(new JLabel("Stan:"));

        filterPanel.add(stateComboBox);

        mainPanel.add(filterPanel, BorderLayout.NORTH);


        // Obsługa pola tekstowego

        filterField.addActionListener(e -> {

            String query = filterField.getText();

            shelterTableModel.filterByName(query); // Filtrowanie tabeli

        });


        // Obsługa listy rozwijanej

        stateComboBox.addActionListener(e -> {

            String selectedState = (String) stateComboBox.getSelectedItem();

            shelterTableModel.filterByState(selectedState); // Filtrowanie tabeli

        });


        // Panel przycisków

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        buttonPanel.setBackground(Color.WHITE);


        RoundedButton addShelterButton = new RoundedButton("Dodaj schronisko");

        RoundedButton removeShelterButton = new RoundedButton("Usuń schronisko");

        RoundedButton viewAnimalsButton = new RoundedButton("Pokaż zwierzęta");

        RoundedButton modifyShelterButton = new RoundedButton("Edytuj schronisko");

        RoundedButton closeButton = new RoundedButton("X");


        styleButton(addShelterButton, new Color(45, 85, 255), Color.WHITE);

        styleButton(removeShelterButton, new Color(100, 200, 150), Color.WHITE);

        styleButton(viewAnimalsButton, new Color(255, 165, 0), Color.WHITE);

        styleButton(closeButton, new Color(255, 0, 0, 180), Color.WHITE);

        styleButton(modifyShelterButton, new Color(188, 125, 200), Color.WHITE);

        closeButton.addActionListener(e -> System.exit(0));


        buttonPanel.add(addShelterButton);

        buttonPanel.add(removeShelterButton);

        buttonPanel.add(viewAnimalsButton);

        buttonPanel.add(modifyShelterButton);

        buttonPanel.add(closeButton);


        mainPanel.add(buttonPanel, BorderLayout.SOUTH);


        // Obsługa przycisków

        addShelterButton.addActionListener(e -> {

            String name = JOptionPane.showInputDialog(frame, "Podaj nazwę schroniska:");

            String capacity = JOptionPane.showInputDialog(frame, "Podaj pojemność schroniska:");

            if (name != null && capacity != null) {

                manager.addShelter(name, Integer.parseInt(capacity));

                shelterTable.setModel(new ShelterTableModel(new ArrayList<>(manager.getShelters().values())));

            }

        });


        removeShelterButton.addActionListener(e -> {

            int selectedRow = shelterTable.getSelectedRow();

            if (selectedRow >= 0) {

                String name = (String) shelterTable.getValueAt(selectedRow, 0);

                manager.removeShelter(name);

                shelterTable.setModel(new ShelterTableModel(new ArrayList<>(manager.getShelters().values())));

            } else {

                JOptionPane.showMessageDialog(frame, "Wybierz schronisko do usunięcia!");

            }

        });


        viewAnimalsButton.addActionListener(e -> {

            int selectedRow = shelterTable.getSelectedRow();

            if (selectedRow >= 0) {

                String shelterName = (String) shelterTable.getValueAt(selectedRow, 0);

                AnimalShelter shelter = manager.getShelter(shelterName);


                // Dodanie przykładowych zwierząt do schroniska A i B

                if (shelter.getShelterName().equals("Schronisko A") && shelter.getAnimals().isEmpty()) {

                    shelter.addAnimal(new Animal("Michaś", "Tajpan Pustynny", AnimalCondition.CHORE, 2, 2137.0));

                    shelter.addAnimal(new Animal("Reksio", "Pies", AnimalCondition.ZDROWE, 13, 1200.0));

                    shelter.addAnimal(new Animal("Raku Chan", "Kot", AnimalCondition.ZDROWE, 10, 500.0));


                } else if (shelter.getShelterName().equals("Schronisko B") && shelter.getAnimals().isEmpty()) {

                    shelter.addAnimal(new Animal("Dogullson", "Pies", AnimalCondition.ZDROWE, 4, 800.0));

                    shelter.addAnimal(new Animal("Pipson", "Gryzoń", AnimalCondition.CHORE, 1, 200.0));

                }

                // Otwieranie panelu UserInterface.AnimalPanel dla wybranego schroniska

                new AnimalPanel(shelter).show();

            } else {

                JOptionPane.showMessageDialog(frame, "Wybierz schronisko, aby zobaczyć zwierzęta!");

            }

        });


        modifyShelterButton.addActionListener(e -> {

            int selectedRow = shelterTable.getSelectedRow();

            if (selectedRow >= 0) {

                String name = (String) shelterTable.getValueAt(selectedRow, 0);

                AnimalShelter selectedShelter = manager.getShelter(name);


                // Pobranie nowych danych od użytkownika

                String newName = JOptionPane.showInputDialog(frame, "Podaj nową nazwę:", selectedShelter.getShelterName());

                String newCapacity = JOptionPane.showInputDialog(frame, "Podaj nową pojemność:", selectedShelter.getMaxCapacity());

                if (newName != null && newCapacity != null) {

                    try {

                        int capacity = Integer.parseInt(newCapacity);

                        // Aktualizacja danych schroniska

                        manager.removeShelter(name); // Usuwamy stare

                        manager.addShelter(newName, capacity); // Dodajemy zaktualizowane

                        // Odświeżenie tabeli

                        shelterTable.setModel(new ShelterTableModel(new ArrayList<>(manager.getShelters().values())));

                    } catch (NumberFormatException ex) {

                        JOptionPane.showMessageDialog(frame, "Pojemność musi być liczbą całkowitą!", "Błąd", JOptionPane.ERROR_MESSAGE);

                    }

                }

            } else {

                JOptionPane.showMessageDialog(frame, "Wybierz schronisko do edycji!");

            }

        });

        frame.setVisible(true);

    }


    // Stylizacja przycisków

    private void styleButton(RoundedButton button, Color bgColor, Color fgColor) {

        button.setFont(new Font("Segoe UI", Font.BOLD, 16));

        button.setForeground(fgColor);

        button.setBackground(bgColor);

        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


        button.addMouseListener(new MouseAdapter() {

            @Override

            public void mouseEntered(MouseEvent e) {

                button.setBackground(bgColor.darker());

            }


            @Override

            public void mouseExited(MouseEvent e) {

                button.setBackground(bgColor);

            }


            @Override

            public void mousePressed(MouseEvent e) {

                button.setBackground(bgColor.brighter());

            }

        });

    }


    // Niestandardowa klasa przycisku z zaokrąglonymi rogami

    static class RoundedButton extends JButton {

        public RoundedButton(String text) {

            super(text);

            setFocusPainted(false);

            setContentAreaFilled(false);

            setBorderPainted(false);

        }


        @Override

        protected void paintComponent(Graphics g) {

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(getBackground());

            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

            g2.setColor(getForeground());

            FontMetrics fm = g2.getFontMetrics();

            int textWidth = fm.stringWidth(getText());

            int textHeight = fm.getAscent();

            g2.drawString(getText(), (getWidth() - textWidth) / 2, (getHeight() + textHeight) / 2 - 3);

            g2.dispose();

        }
    }
}
