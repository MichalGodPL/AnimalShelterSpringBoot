package org.example.springbootanimalshelter;

import org.example.springbootanimalshelter.DataBase.ShelterEncja;

import org.example.springbootanimalshelter.DataBase.ShelterRatingEncja;

import org.example.springbootanimalshelter.DataBase.ShelterRepository;

import org.example.springbootanimalshelter.DataBase.ShelterRatingRepository;

import org.example.springbootanimalshelter.DataBase.ShelterAnimalRepository;

import org.example.springbootanimalshelter.DataBase.AnimalShelterEncja;

import org.example.springbootanimalshelter.Code.AnimalCondition;

import org.example.springbootanimalshelter.UserInterface.LoginScreen;

import org.springframework.boot.CommandLineRunner;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;

import java.time.LocalDate;


@SpringBootApplication

public class Main {

    public static void main(String[] args) {

        System.setProperty("java.awt.headless", "false");


        // Uruchomienie Spring Boot

        SpringApplication.run(Main.class, args);


        // Uruchomienie GUI z głównego wątku

        if (!java.awt.GraphicsEnvironment.isHeadless()) {

            javax.swing.SwingUtilities.invokeLater(() -> {

                System.out.println("Uruchamianie GUI z poziomu main...");

                LoginScreen loginScreen = new LoginScreen();

                loginScreen.show();

            });

        } else {

            System.out.println("Tryb headless: GUI nie zostanie uruchomione.");

        }

    }


    @Bean

    public CommandLineRunner runApp(ShelterRepository shelterRepository, ShelterRatingRepository shelterRatingRepository, ShelterAnimalRepository shelterAnimalRepository) {

        return args -> {

            // Przykładowe dane do bazy danych schronisk

            ShelterEncja shelterA = new ShelterEncja();

            shelterA.setName("Schronisko A");

            shelterA.setCapacity(10);

            shelterRepository.save(shelterA);


            ShelterEncja shelterB = new ShelterEncja();

            shelterB.setName("Schronisko B");

            shelterB.setCapacity(5);

            shelterRepository.save(shelterB);


            System.out.println("Dodano przykładowe schroniska do bazy danych.");

            // Dodawanie przykładowych ocen do schronisk

            ShelterRatingEncja rating1 = new ShelterRatingEncja();

            rating1.setShelter(shelterA);

            rating1.setScore(4);

            rating1.setRatingDate(LocalDate.now());

            rating1.setComment("Bardzo dobre schronisko, miła obsługa.");

            shelterRatingRepository.save(rating1);


            ShelterRatingEncja rating2 = new ShelterRatingEncja();

            rating2.setShelter(shelterA);

            rating2.setScore(5);

            rating2.setRatingDate(LocalDate.now());

            rating2.setComment("Znakomite warunki dla zwierząt.");

            shelterRatingRepository.save(rating2);


            ShelterRatingEncja rating3 = new ShelterRatingEncja();

            rating3.setShelter(shelterB);

            rating3.setScore(3);

            rating3.setRatingDate(LocalDate.now());

            rating3.setComment("Schronisko ok, ale mogłoby być czystsze.");

            shelterRatingRepository.save(rating3);


            ShelterRatingEncja rating4 = new ShelterRatingEncja();

            rating4.setShelter(shelterB);

            rating4.setScore(2);

            rating4.setRatingDate(LocalDate.now());

            rating4.setComment("Słaba opieka nad zwierzętami.");

            shelterRatingRepository.save(rating4);


            System.out.println("Dodano przykładowe oceny dla schronisk.");


            // Dodawanie przykładowych zwierząt do bazy danych

            AnimalShelterEncja animal1 = new AnimalShelterEncja();

            animal1.setName("Pipson");

            animal1.setSpecies("Pies");

            animal1.setCondition(AnimalCondition.ZDROWE);  // Zmieniony stan na ZDROWE

            animal1.setAge(3);

            animal1.setAdoptionFee(150.0);

            animal1.setShelter(shelterA); // Przypisanie do Schronisko A

            shelterAnimalRepository.save(animal1);


            AnimalShelterEncja animal2 = new AnimalShelterEncja();

            animal2.setName("Plink");

            animal2.setSpecies("Kot");

            animal2.setCondition(AnimalCondition.CHORE);  // Zmieniony stan na CHORE

            animal2.setAge(2);

            animal2.setAdoptionFee(120.0);

            animal2.setShelter(shelterA); // Przypisanie do Schronisko A

            shelterAnimalRepository.save(animal2);


            AnimalShelterEncja animal3 = new AnimalShelterEncja();

            animal3.setName("Donek");

            animal3.setSpecies("Pies");

            animal3.setCondition(AnimalCondition.KWARANTANNA);  // Zmieniony stan na KWARANTANNA

            animal3.setAge(5);

            animal3.setAdoptionFee(100.0);

            animal3.setShelter(shelterB); // Przypisanie do Schronisko B

            shelterAnimalRepository.save(animal3);


            AnimalShelterEncja animal4 = new AnimalShelterEncja();

            animal4.setName("Plenk");

            animal4.setSpecies("Kot");

            animal4.setCondition(AnimalCondition.W_TRAKCIE_ADOPCJI);  // Zmieniony stan na W_TRAKCIE_ADOPCJI

            animal4.setAge(1);

            animal4.setAdoptionFee(80.0);

            animal4.setShelter(shelterB); // Przypisanie do Schronisko B

            shelterAnimalRepository.save(animal4);


            System.out.println("Dodano przykładowe zwierzęta do schronisk.");

        };

    }

}