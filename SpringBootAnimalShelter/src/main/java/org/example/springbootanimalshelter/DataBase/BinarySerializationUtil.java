package org.example.springbootanimalshelter.DataBase;

import java.io.*;

import java.nio.file.Paths;

import java.util.List;

public class BinarySerializationUtil {

    private static final String DESKTOP_PATH = System.getProperty("user.home") + "/Desktop";

    private static final String DEFAULT_BINARY_FILENAME = Paths.get(DESKTOP_PATH, "shelters.bin").toString();


    public static void saveSheltersToDesktopBinary(List<ShelterEncja> shelters) {

        saveToBinaryFile(DEFAULT_BINARY_FILENAME, shelters);

    }

    public static List<ShelterEncja> loadSheltersFromDesktopBinary() {

        return readFromBinaryFile(DEFAULT_BINARY_FILENAME);

    }

    public static void saveToBinaryFile(String filename, List<?> objects) {

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {

            oos.writeObject(objects);

            System.out.println("Dane zapisane do pliku binarnego: " + filename);

        } catch (IOException e) {

            System.err.println("Błąd podczas zapisywania do pliku binarnego: " + filename);

            e.printStackTrace();

        }

    }

    public static <T> List<T> readFromBinaryFile(String filename) {

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {

            return (List<T>) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {

            System.err.println("Błąd podczas odczytu z pliku binarnego: " + filename);

            e.printStackTrace();

            return null;

        }
    }
}
