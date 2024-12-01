package org.example.springbootanimalshelter.DataBase;

import org.springframework.http.HttpStatus;

import java.nio.file.Files;

import java.nio.file.Paths;

import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import java.util.Map;

import java.util.stream.Collectors;


@Controller

public class ShelterController {

    private final ShelterRepository shelterRepository;

    private final ShelterRatingCriteria ratingCriteria;


    public ShelterController(ShelterRepository shelterRepository, ShelterRatingCriteria ratingCriteria) {

        this.shelterRepository = shelterRepository;

        this.ratingCriteria = ratingCriteria;


        // Inicjalizacja danych podczas startu aplikacji

        initializeData();

    }


    public void initializeData() {

        try {

            List<ShelterEncja> shelters = shelterRepository.findAll();

            if (shelters == null || shelters.isEmpty()) {

                System.out.println("Brak schronisk do inicjalizacji danych.");

                return;

            }


            String desktopPath = System.getProperty("user.home") + "/Desktop";


            if (!Files.exists(Paths.get(desktopPath))) {

                System.err.println("Ścieżka do pulpitu nie istnieje: " + desktopPath);

                return;

            }


            // Tworzenie pliku binarnego

            String binaryFilename = Paths.get(desktopPath, "shelters.bin").toString();

            BinarySerializationUtil.saveToBinaryFile(binaryFilename, shelters);

            System.out.println("Dane schronisk zapisano do pliku binarnego: " + binaryFilename);


            // Tworzenie plików CSV

            for (ShelterEncja shelter : shelters) {

                String csvFilename = Paths.get(desktopPath, shelter.getName().replace(" ", "_") + ".csv").toString();

                CsvExportUtil.exportShelterToCsv(csvFilename, shelter);

            }

        } catch (Exception e) {

            System.err.println("Wystąpił problem podczas inicjalizacji danych.");

            e.printStackTrace();

        }
    }


    @GetMapping("/shelters/ratings/average")

    public ResponseEntity<?> getAverageRatings() {

        try {

            List<Object[]> averageRatings = ratingCriteria.getAverageRatingPerShelter();

            if (averageRatings.isEmpty()) {

                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Brak ocen dla schronisk.");

            }

            List<Map<String, Object>> results = averageRatings.stream().map(row -> Map.of(

                    "shelter", row[0],

                    "averageRating", row[1]

            )).collect(Collectors.toList());


            return ResponseEntity.ok(results);

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Błąd podczas pobierania ocen.");

        }

    }

    @GetMapping("/shelters/export/csv")

    public ResponseEntity<String> exportSheltersToCsv() {

        try {

            List<ShelterEncja> shelters = shelterRepository.findAll();

            if (shelters.isEmpty()) {

                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Brak schronisk do eksportu.");

            }

            CsvExportUtil.exportSheltersToDesktopCsv(shelters);

            return ResponseEntity.ok("Dane wyeksportowano do CSV.");

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Błąd podczas eksportu do CSV.");

        }
    }

    @GetMapping("/shelters/export/bin")

    public ResponseEntity<String> saveSheltersAsBinary() {

        try {

            List<ShelterEncja> shelters = shelterRepository.findAll();

            if (shelters.isEmpty()) {

                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Brak danych do zapisania.");

            }

            BinarySerializationUtil.saveSheltersToDesktopBinary(shelters);

            return ResponseEntity.ok("Dane zapisano do pliku binarnego.");

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Błąd podczas zapisu do binarki.");

        }

    }

    @GetMapping("/shelters/import/bin")

    public ResponseEntity<?> loadSheltersFromBinary() {

        try {

            List<ShelterEncja> shelters = BinarySerializationUtil.loadSheltersFromDesktopBinary();

            if (shelters == null || shelters.isEmpty()) {

                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Brak danych w pliku binarnym.");

            }

            return ResponseEntity.ok(shelters);

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Błąd podczas odczytu danych.");

        }

    }

}
