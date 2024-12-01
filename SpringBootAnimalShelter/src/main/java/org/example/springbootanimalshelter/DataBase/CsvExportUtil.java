package org.example.springbootanimalshelter.DataBase;

import java.io.FileWriter;

import java.io.IOException;

import java.nio.file.Paths;

import java.util.List;

public class CsvExportUtil {

    private static final String DESKTOP_PATH = System.getProperty("user.home") + "/Desktop";

    public static void exportSheltersToDesktopCsv(List<ShelterEncja> shelters) {

        for (ShelterEncja shelter : shelters) {

            String filename = Paths.get(DESKTOP_PATH, shelter.getName().replace(" ", "_") + ".csv").toString();

            exportShelterToCsv(filename, shelter);

        }
    }

    public static void exportShelterToCsv(String filename, ShelterEncja shelter) {

        try (FileWriter writer = new FileWriter(filename)) {

            writer.write("Name,Species,Condition,Age,AdoptionFee\n");

            List<AnimalShelterEncja> animals = shelter.getAnimals();

            if (animals == null || animals.isEmpty()) {

                System.out.println("Schronisko " + shelter.getName() + " nie zawiera zwierząt.");

                return;

            }


            for (AnimalShelterEncja animal : animals) {

                writer.write(String.format("%s,%s,%s,%d,%.2f\n",

                        animal.getName(),

                        animal.getSpecies(),

                        animal.getCondition(),

                        animal.getAge(),

                        animal.getAdoptionFee()));

            }

            System.out.println("Dane schroniska zapisane do pliku CSV: " + filename);

        } catch (IOException e) {

            System.err.println("Błąd podczas zapisywania danych do CSV: " + filename);

            e.printStackTrace();

        }
    }
}
