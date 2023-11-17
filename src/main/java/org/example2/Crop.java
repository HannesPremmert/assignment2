package org.example2;

import java.util.Scanner;

public class Crop extends Entity {
    private String cropType;
    private int quantity;
    private static int nextId = 1;

    // Super-konstruktor
    public Crop(String name, String cropType, int quantity) {
        super(nextId, name);
        nextId++;
        this.cropType = cropType;
        this.quantity = quantity;
    }

    @Override
    public void getDescription() {
        super.getDescription();
        System.out.print(", Crop Type: " + cropType + ", Quantity: " + quantity + " kg\n");
    }

    // Getters and setters
    public String getCropType() {
        return cropType;
    }

    public void setCropType(String cropType) {
        this.cropType = cropType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCSV() {
        return nextId + "," + name + "," + cropType + "," + quantity;
    }
}

