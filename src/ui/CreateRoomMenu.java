package ui;

import api.AdminResource;
import model.RoomType;

import java.util.Scanner;

public class CreateRoomMenu {
    private Scanner scanner;
    private AdminResource resource = new AdminResource();

    public CreateRoomMenu(Scanner scanner) {
        this.scanner = scanner;
    }
    public void draw() {
        boolean keepRunning = true;

        while (keepRunning) {
            try {
                System.out.println("Please enter a Room number, Price and Type (space separated) or [0] to Return to main menu:");
                String input = scanner.nextLine();
                String[] userInput = input.split(" ");

                if (input.equalsIgnoreCase("0")) {
                    keepRunning = false;
                    continue;
                }
                if (userInput.length < 3) {
                    System.out.println("Unable to create Room. Incorrect input data. Please try again.");
                    continue;
                }

                String parsedRoomNumber;
                Double parsedPrice;
                RoomType parsedRoomType;
                try {
                    parsedRoomNumber = String.valueOf(Integer.parseInt(userInput[0]));
                } catch (Exception ex) {
                    throw new IllegalArgumentException("Wrong Room number entered!");
                }
                try {
                    parsedPrice = Double.parseDouble(userInput[1]);
                } catch (Exception ex) {
                    throw new IllegalArgumentException("Wrong Price entered!");
                }
                try {
                    parsedRoomType = RoomType.valueOf(userInput[2].toUpperCase());
                } catch (Exception ex) {
                    throw new IllegalArgumentException("Wrong Room type entered!");
                }

                resource.addRoom(parsedRoomNumber, parsedPrice, parsedRoomType);
                System.out.println("Room created successfully: " + userInput[0] + ", " + userInput[1] + ", " + userInput[2]);
                keepRunning = false;
            } catch (Exception ex) {
                System.out.println("Bad input. Please try again!");
                System.out.println(ex);
            }
        }
    }
}
