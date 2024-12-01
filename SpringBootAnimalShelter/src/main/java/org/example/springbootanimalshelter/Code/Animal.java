package org.example.springbootanimalshelter.Code;

public class Animal implements Comparable<Animal> {

    private String Imie;

    private String Gatunek;

    private AnimalCondition Kondycja;

    private int Wiek;

    private double Cena;

    private static int idCounter = 1;

    private final int id;


    // Konstruktor

    public Animal(String name, String species, AnimalCondition condition, int age, double price) {

        this.Imie = name;

        this.Gatunek = species;

        this.Kondycja = condition;

        this.Wiek = age;

        this.Cena = price;

        this.id = idCounter++;

    }


    // Metoda print()

    public void print() {

        System.out.println("ID: " + id +

                ", Imie: " + Imie +

                ", Gatunek: " + Gatunek +

                ", Kondycja: " + Kondycja +

                ", Wiek: " + Wiek +

                ", Cena: " + Cena);

    }


    // Implementacja interfejsu Comparable<Code.Animal> – porównanie według imienia, gatunku lub wieku

    @Override

    public int compareTo(Animal other) {

        int nameComparison = this.Imie.compareTo(other.Imie);

        if (nameComparison != 0) {

            return nameComparison;

        }

        int speciesComparison = this.Gatunek.compareTo(other.Gatunek);

        if (speciesComparison != 0) {

            return speciesComparison;

        }

        return Integer.compare(this.Wiek, other.Wiek);

    }


    // Gettery i settery

    public String getName() {

        return Imie;

    }

    public void setName(String name) {

        this.Imie = name;

    }

    public String getSpecies() {

        return Gatunek;

    }

    public void setSpecies(String species) {

        this.Gatunek = species;

    }

    public AnimalCondition getCondition() {

        return Kondycja;

    }

    public void setCondition(AnimalCondition condition) {

        this.Kondycja = condition;

    }

    public int getAge() {

        return Wiek;

    }

    public void setAge(int age) {

        this.Wiek = age;

    }

    public double getPrice() {

        return Cena;

    }

    public void setPrice(double price) {

        this.Cena = price;

    }

}
