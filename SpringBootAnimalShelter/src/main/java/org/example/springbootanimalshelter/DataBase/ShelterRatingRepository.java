package org.example.springbootanimalshelter.DataBase;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelterRatingRepository extends JpaRepository<ShelterRatingEncja, Long> {

    // Możesz dodać zapytania grupujące tutaj

}
