package org.example.springbootanimalshelter.DataBase;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelterAnimalRepository extends JpaRepository<AnimalShelterEncja, Long> {

    // Custom queries (jeśli potrzebne)

}
