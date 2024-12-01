package org.example.springbootanimalshelter.DataBase;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity

@Table(name = "ratings")

public class ShelterRatingEncja {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;


    @Column(nullable = false)

    private int score; // Skala 0-5


    @ManyToOne

    @JoinColumn(name = "shelter_id", nullable = false)

    private ShelterEncja shelter;


    @Column(nullable = false)

    private LocalDate ratingDate;


    @Column(nullable = false)

    private String comment;

    // Gettery i settery

    public Long getId() {

        return id;

    }


    public void setId(Long id) {

        this.id = id;

    }


    public int getScore() {

        return score;

    }

    public void setScore(int score) {

        this.score = score;

    }


    public ShelterEncja getShelter() {

        return shelter;

    }


    public void setShelter(ShelterEncja shelter) {

        this.shelter = shelter;

    }


    public LocalDate getRatingDate() {

        return ratingDate;

    }


    public void setRatingDate(LocalDate ratingDate) {

        this.ratingDate = ratingDate;

    }


    public String getComment() {

        return comment;

    }


    public void setComment(String comment) {

        this.comment = comment;

    }

}
