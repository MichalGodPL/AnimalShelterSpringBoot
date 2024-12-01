package org.example.springbootanimalshelter.DataBase;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelterRepository extends JpaRepository<ShelterEncja, Long> {

    ShelterEncja findByName(String name);

}
