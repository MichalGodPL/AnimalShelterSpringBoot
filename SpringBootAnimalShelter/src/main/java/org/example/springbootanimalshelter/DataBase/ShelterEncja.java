package org.example.springbootanimalshelter.DataBase;

import jakarta.persistence.*;

import java.io.Serializable;

import java.util.List;

@Entity

public class ShelterEncja implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;


    private String name;

    private int capacity;


    @OneToMany(mappedBy = "shelter", fetch = FetchType.LAZY, cascade = CascadeType.ALL)

    private List<AnimalShelterEncja> animals;


    // Gettery i settery

    public Long getId() {

        return id;

    }

    public void setId(Long id) {

        this.id = id;

    }


    public String getName() {

        return name;

    }


    public void setName(String name) {

        this.name = name;

    }

    public int getCapacity() {

        return capacity;

    }

    public void setCapacity(int capacity) {

        this.capacity = capacity;

    }

    public List<AnimalShelterEncja> getAnimals() {

        return animals;

    }

    public void setAnimals(List<AnimalShelterEncja> animals) {

        this.animals = animals;

    }
}
