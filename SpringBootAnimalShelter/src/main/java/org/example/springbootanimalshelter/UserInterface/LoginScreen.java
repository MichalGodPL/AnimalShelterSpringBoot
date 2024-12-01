package org.example.springbootanimalshelter.UserInterface;

import javax.swing.*;

import java.awt.*;

import java.awt.event.*;


public class LoginScreen {

    private JFrame frame;

    private Point mouseClickPoint;


    public void show() {

        System.out.println("Uruchamiam LoginScreen...");

        frame = new JFrame("Login");

        frame.setSize(420, 480);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLocationRelativeTo(null);

        frame.setUndecorated(true);


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


        JPanel mainPanel = new JPanel();

        mainPanel.setLayout(null);

        mainPanel.setBackground(Color.WHITE);

        frame.add(mainPanel);


        placeComponents(mainPanel);

        frame.setVisible(true);

    }


    private void placeComponents(JPanel panel) {

        // Nagłówek "Welcome!"

        JLabel welcomeLabel = new JLabel("Welcome!");

        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));

        welcomeLabel.setForeground(new Color(45, 85, 255));

        welcomeLabel.setBounds(140, 40, 200, 40);

        panel.add(welcomeLabel);


        // Pole tekstowe - Login

        JTextField userText = new JTextField();

        styleTextField(userText);

        userText.setBounds(50, 120, 320, 50);

        panel.add(userText);


        JLabel userLabel = new JLabel("Your name:");

        styleLabel(userLabel);

        userLabel.setBounds(50, 95, 200, 20);

        panel.add(userLabel);


        // Pole tekstowe - Hasło

        JPasswordField passwordText = new JPasswordField();

        styleTextField(passwordText);

        passwordText.setBounds(50, 220, 320, 50);

        panel.add(passwordText);


        JLabel passwordLabel = new JLabel("Password:");

        styleLabel(passwordLabel);

        passwordLabel.setBounds(50, 195, 200, 20);

        panel.add(passwordLabel);


        // Przycisk "Log as Admin"

        RoundedButton adminButton = new RoundedButton("Log as Admin");

        styleButton(adminButton, new Color(45, 85, 255), Color.WHITE);

        adminButton.setBounds(50, 300, 320, 55);

        panel.add(adminButton);


        // Przycisk "Log as User"

        RoundedButton userButton = new RoundedButton("Log as User");

        styleButton(userButton, new Color(100, 200, 150), Color.WHITE);

        userButton.setBounds(50, 380, 320, 55);

        panel.add(userButton);


        // Przycisk zamknięcia

        RoundedButton closeButton = new RoundedButton("X");

        styleButton(closeButton, new Color(255, 0, 0, 180), Color.WHITE);

        closeButton.setBounds(370, 10, 40, 40);

        closeButton.addActionListener(e -> System.exit(0));

        panel.add(closeButton);


        // Obsługa przycisków

        adminButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(panel, "Logged in as Admin");

            frame.dispose();

            new AdminPanel().show();

        });


        userButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(panel, "Logged in as User");

            frame.dispose();

            new UserPanel().show();

        });

    }

    // Metoda do stylizacji pól tekstowych

    private void styleTextField(JTextField textField) {

        textField.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        textField.setForeground(Color.BLACK);

        textField.setBackground(new Color(240, 240, 240));

        textField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    }


    // Metoda do stylizacji etykiet

    private void styleLabel(JLabel label) {

        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        label.setForeground(new Color(100, 100, 100));

    }


    // Metoda do stylizacji przycisków z animacjami

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

            super.paintComponent(g2);

            g2.dispose();

        }

    }


    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new LoginScreen().show());

    }

}
