package org.example2;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Farm {

    ArrayList<Animal> animalArrayList = new ArrayList<>();
    ArrayList<Crop> cropArrayList = new ArrayList<>();

    // Konstruktor
    public Farm() {
        animalArrayList = new ArrayList<>();
        cropArrayList = new ArrayList<>();

        if (readDataFromFile()) {
            System.out.println("Your saved files were successfully loaded");
        } else {
            System.out.println("No saved data was found, I have created some animals and crops for you.");
            CreateAnimalsOnStart();
        }
    }


    public void MainMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("\nChoose an option: ");
            System.out.println("1: View all crops");
            System.out.println("2: Remove crops");
            System.out.println("3: Add crops");
            System.out.println("4: View all animals");
            System.out.println("5: Add an animal to the farm");
            System.out.println("6: Feed an animal");
            System.out.println("7: Remove an animal from the farm");
            System.out.println("8: Save");
            System.out.println("9: Exit");
            String userInput = scanner.nextLine();

            switch (userInput) {
                case "1":
                    ViewCrops();
                    break;

                case "2":
                    RemoveCrop();
                    break;

                case "3":
                    AddCrop();
                    break;

                case "4":
                    ViewAnimals();
                    break;

                case "5":
                    AddAnimal();
                    break;

                case "6":
                    FeedAnimal();
                    break;

                case "7":
                    RemoveAnimal();
                    break;

                case "8":
                    Save();
                    break;

                case "9":
                    running = false;
                    break;

                default:
                    System.out.println("Please choose an option from the list.");
                    break;
            }
        }
    }

    public void ViewCrops() {
        System.out.println("\nAvailable crops on your farm:");
        for (Crop crop : cropArrayList) {
            crop.getDescription();
        }
    }

    public void RemoveCrop() {

        Scanner scanner = new Scanner(System.in);
        ViewCrops();
        System.out.print("\nPlease choose the ID of the crop you wish to remove: ");
        String cropIdInput = scanner.nextLine();

        try {
            int cropIdToRemove = Integer.parseInt(cropIdInput);
            Crop cropToRemove = null;

            for (Crop crop : cropArrayList) {
                if (crop.getId() == cropIdToRemove) {
                    cropToRemove = crop;
                    break;
                }
            }

            if (cropToRemove != null) {
                System.out.print("Enter how much of the " + cropToRemove.getName() + " you wish to remove (in kg): ");
                String quantityInput = scanner.nextLine();

                int quantityToRemove = Integer.parseInt(quantityInput);
                int currentQuantity = cropToRemove.getQuantity();

                if (quantityToRemove <= currentQuantity) {
                    cropToRemove.setQuantity(currentQuantity - quantityToRemove);
                    System.out.println(quantityToRemove + " kg of " + cropToRemove.getName() + " has been removed. " + cropToRemove.getQuantity() + "kg remains.");
                } else {
                    System.out.println("There is not enough " + cropToRemove.getName() + " in stock.");
                }
            } else {
                System.out.println("Please enter a valid ID.");
            }
        } catch (Exception e) {
            System.out.println("Please enter an ID with numbers, not letters or any other symbols.");
        }
    }

    public void AddAnimal() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("What would you like to name the new animal: ");
        String animalName = scanner.nextLine();

        System.out.print("What kind of animal is " + animalName + ": ");
        String animalSpecies = scanner.nextLine();

        Animal newAnimal = new Animal(animalName, animalSpecies);
        animalArrayList.add(newAnimal);

        System.out.println(animalName + " has been added to the farm!");
    }

    public void ViewAnimals() {
        System.out.println("\nAnimals on your farm:");
        for (Animal animal : animalArrayList) {
            animal.getDescription();
        }
    }

    public void FeedAnimal() {
        Scanner scanner = new Scanner(System.in);

        // Visar en lista på alla våra crops
        ViewCrops();

        // Man väljer en crop man vill mata djuret med
        System.out.print("Enter the ID of the crop you want to feed to an animal: ");
        String cropIdInput = scanner.nextLine();

        try {
            int cropIdToFeed = Integer.parseInt(cropIdInput);

            Crop selectedCrop = null;

            // Loopa igenom våra crops för att hitta den crop vi valde
            for (Crop crop : cropArrayList) {
                if (crop.getId() == cropIdToFeed) {
                    selectedCrop = crop;
                    break;
                }
            }

            if (selectedCrop != null && selectedCrop.getQuantity() > 0) {
                // Lista alla våra djur
                ViewAnimals();

                // Låt användaren välja vilket djur vi vill mata
                System.out.print("Enter the ID of the animal you want to feed: ");
                String animalIdInput = scanner.nextLine();
                int animalIdToFeed = Integer.parseInt(animalIdInput);

                Animal selectedAnimal = null;

                // Samma loop som tidigare för att hitta djuret
                for (Animal animal : animalArrayList) {
                    if (animal.getId() == animalIdToFeed) {
                        selectedAnimal = animal;
                        break;
                    }
                }

                if (selectedAnimal != null) {
                    selectedAnimal.feedCrop();

                    selectedCrop.setQuantity(selectedCrop.getQuantity() - 1);
                    System.out.println("You successfully fed " + selectedAnimal.getName() + " with " + selectedCrop.getName() +
                            ". You now have " + selectedCrop.getQuantity() + "kg of " + selectedCrop.getName() + " left.");
                } else {
                    System.out.println("No animal with that ID was found.");
                }
            } else {
                System.out.println("The selected crop is not available or has no quantity left.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter the IDs with numbers only.");
        }
    }

   public void RemoveAnimal(){
       Scanner scanner = new Scanner(System.in);
       ViewAnimals();

       System.out.print("\nEnter the ID of the animal you wish to remove: ");
       String animalIdInput = scanner.nextLine();

       try {
           int animalIdToRemove = Integer.parseInt(animalIdInput);

           Animal foundAnimal = null;
           for (Animal animal : animalArrayList) {
               if (animal.getId() == animalIdToRemove) {
                   foundAnimal = animal;
                   break;
               }
           }

           if (foundAnimal != null) {
               animalArrayList.remove(foundAnimal);
               System.out.println(foundAnimal.getName() + " has been removed from the farm (definitely not slaughtered).");
           } else {
               System.out.println("No animal with the ID '" + animalIdToRemove + "' was found.");
           }
       } catch (NumberFormatException e) {
           System.out.println("Enter an ID with numbers only.");
       }
  }

    public void AddCrop() {
        Scanner scanner = new Scanner(System.in);
        ViewCrops();
        System.out.print("Enter the ID of the crop you want to add: ");
        String cropIdInputStr = scanner.nextLine();

        try {
            int cropIdInput = Integer.parseInt(cropIdInputStr);

            Crop existingCrop = null;

            // Se om det redan finns en Crop med det angivna ID:et
            for (Crop crop : cropArrayList) {
                if (crop.getId() == cropIdInput) {
                    existingCrop = crop;
                    break;
                }
            }

            if (existingCrop != null) {
                // Om Crop redan finns, ber vi användaren att skriva in hur mycket vi vill lägga till
                System.out.print("How much " + existingCrop.getName() + " would you like to add (in kg): ");
                String quantityToAddStr = scanner.nextLine();
                int quantityToAdd = Integer.parseInt(quantityToAddStr);

                existingCrop.setQuantity(existingCrop.getQuantity() + quantityToAdd);
                System.out.println(quantityToAdd + "kg of " + existingCrop.getName() + " has been added. New quantity: " + existingCrop.getQuantity() + " kg");
            } else {
                // Om Crop inte finns, låt användaren ange informationen för en ny Crop
                System.out.print("Enter the name of the new crop: ");
                String cropName = scanner.nextLine();

                System.out.print("In what category does the new crop belong (Meat/Grain/Fruit): ");
                String cropType = scanner.nextLine();

                System.out.print("How much " + cropName + " would you like to add (in kg): ");
                String quantityStr = scanner.nextLine();
                int quantity = Integer.parseInt(quantityStr);

                Crop newCrop = new Crop(cropName, cropType, quantity);
                cropArrayList.add(newCrop);
                System.out.println(quantity + "kg of " + cropName + " has been added to the farm.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter the amount with numbers.");
        }
    }

    File cropFolder = new File("entityFolder");
    File animalsFile = new File("entityFolder/animalsFile.txt");
    File cropsFile = new File("entityFolder/cropsFile.txt");
    String absolutePath = "C:\\Users\\Hannes Premmert\\IdeaProjects\\Inlamningsuppgift2";

    Scanner scanner = new Scanner(System.in);

    private void Save() {

        cropFolder.mkdir();

        try {
            FileWriter fileWriter = new FileWriter(animalsFile);
            BufferedWriter bf = new BufferedWriter(fileWriter);
            bf.write("id,name,species");
            for (Animal animal: animalArrayList) {
                bf.newLine();
                bf.write(animal.getCSV());
            }
            bf.close();
        } catch (IOException e) {
            System.out.println("Something went wrong.");
        }
        try {
            FileWriter fileWriter = new FileWriter(cropsFile);
            BufferedWriter bf = new BufferedWriter(fileWriter);
            bf.write("id,name,cropType,quantity");
            for (Crop crop: cropArrayList) {
                bf.newLine();
                bf.write(crop.getCSV());
            }
            bf.close();
        } catch (IOException e) {
            System.out.println("Something went wrong.");
        }
        System.out.println("Your farm has been saved:");
        ViewAnimals();
        ViewCrops();
    }


    private boolean readDataFromFile() {
        boolean filesExist = false;

        File animalsFile = new File("entityFolder/animalsFile.txt");
        File cropsFile = new File("entityFolder/cropsFile.txt");

        if (animalsFile.exists() && cropsFile.exists()) {
            LoadAnimalsFromSave(animalsFile);
            LoadCropsFromSave(cropsFile);
            filesExist = true;
        }

        return filesExist;
    }

    private void LoadAnimalsFromSave(File file) {
        try {
            FileReader fileReader = new FileReader(animalsFile);
            BufferedReader bf = new BufferedReader(fileReader);

            bf.readLine();
            String line = bf.readLine();

            while (line != null) {
                String[] variables = line.split(",");
                int id = Integer.parseInt(variables[0]);
                String name = variables[1];
                String species = variables[2];
                Animal animal = new Animal(name, species);
                animalArrayList.add(animal);

                line = bf.readLine();
            }

            bf.close();
        } catch (IOException e) {
            System.out.println("Something went wrong.");
        }
    }

    private void LoadCropsFromSave(File file) {
        try {
            FileReader fileReader = new FileReader(cropsFile);
            BufferedReader bf = new BufferedReader(fileReader);

            bf.readLine();
            String line = bf.readLine();

            while (line != null) {
                String[] variables = line.split(",");
                int id = Integer.parseInt(variables[0]);
                String name = variables[1];
                String cropType = variables[2];
                int quantity = Integer.parseInt(variables[3]);
                Crop crop = new Crop(name, cropType, quantity);
                cropArrayList.add(crop);

                line = bf.readLine();
            }

            bf.close();
        } catch (IOException e) {
            System.out.println("Something went wrong.");
        }
    }

    private void CreateAnimalsOnStart() {
        animalArrayList.add(new Animal("Shaun", "Sheep"));
        animalArrayList.add(new Animal("George", "Cow"));
        animalArrayList.add(new Animal("Gunnel", "Pig"));
        animalArrayList.add(new Animal("Lisa", "Goat"));
        cropArrayList.add(new Crop("Hay", "Grain", 30));
        cropArrayList.add(new Crop("Corn", "Grain", 25));
        cropArrayList.add(new Crop("Apples", "Fruit", 10));
        cropArrayList.add(new Crop("Beef", "Meat", 3));
        Save();
    }
}
