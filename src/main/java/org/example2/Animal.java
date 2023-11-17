package org.example2;

public class Animal extends Entity {

    private String species;
    private static int nextId = 1;

    // Super-konstruktor
    public Animal(String name, String species) {
        super(nextId, name);
        nextId++;
        this.species = species;
    }

    @Override
    public void getDescription() {
        super.getDescription();
        System.out.println(", Species: " + species);
    }

    public void feedCrop() {

    }

    // Getters and setters
    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getCSV() {
        return nextId + "," + name + "," + species;
    }
}
