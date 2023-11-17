package org.example2;

public class Entity {
    private int id;
    protected String name;

    // Konstruktor
    public Entity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void getDescription(){
        System.out.print("Id: " + id + ", Name: " + name);
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
