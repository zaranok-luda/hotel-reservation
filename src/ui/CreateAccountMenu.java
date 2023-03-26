package ui;

import api.HotelResource;

import java.util.Scanner;

public class CreateAccountMenu {
    private Scanner scanner;
    private HotelResource hotelResource = new HotelResource();

    public CreateAccountMenu(Scanner scanner) {
        this.scanner = scanner;
    }
    public void draw() {
        boolean keepRunning = true;

        while (keepRunning) {
            try {
                System.out.println("Please enter a First name, Last name and email (space separated) or [0] to Return to main menu:");
                String input = scanner.nextLine();
                String[] userInput = input.split(" ");

                if (input.equalsIgnoreCase("0")) {
                    keepRunning = false;
                    continue;
                }
                if (userInput.length < 3) {
                    throw new IllegalArgumentException("Unable to create account. Incorrect input data. Please try again.");
                }

                this.hotelResource.createACustomer(userInput[0], userInput[1], userInput[2]);
                keepRunning = false;
            } catch (Exception ex) {
                System.out.println("Bad input. Please try again!");
                System.out.println(ex);
            }
        }
    }
}
