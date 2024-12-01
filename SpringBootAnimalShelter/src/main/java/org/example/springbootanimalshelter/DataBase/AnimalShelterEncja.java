package org.example.springbootanimalshelter.DataBase;

import jakarta.persistence.*;

import java.io.Serializable;

import org.example.springbootanimalshelter.Code.AnimalCondition;

@Entity

public class AnimalShelterEncja implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;


    private String name;

    private String species;


    @Enumerated(EnumType.STRING)

    private AnimalCondition condition;


    private int age;

    private double adoptionFee;


    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "shelter_id")

    private ShelterEncja shelter;


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

    public String getSpecies() {

        return species;

    }


    public void setSpecies(String species) {

        this.species = species;

    }


    public AnimalCondition getCondition() {

        return condition;

    }


    public void setCondition(AnimalCondition condition) {

        this.condition = condition;

    }


    public int getAge() {

        return age;

    }


    public void setAge(int age) {

        this.age = age;

    }


    public double getAdoptionFee() {

        return adoptionFee;

    }


    public void setAdoptionFee(double adoptionFee) {

        this.adoptionFee = adoptionFee;

    }

    public ShelterEncja getShelter() {

        return shelter;

    }

    public void setShelter(ShelterEncja shelter) {

        this.shelter = shelter;

    }

}
