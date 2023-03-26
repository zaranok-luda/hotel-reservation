package ui;

import api.AdminResource;

import java.util.Scanner;

public class AdminMenu {
    private Scanner scanner;

    private AdminResource resource = new AdminResource();

    public AdminMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void draw() {
        boolean keepRunning = true;

        while (keepRunning) {
            try {
                System.out.println("1. See all Customers");
                System.out.println("2. See all Rooms");
                System.out.println("3. See all Reservations");
                System.out.println("4. Add a Room");
                System.out.println("5. Back to Main Menu");
                int selection = Integer.parseInt(scanner.nextLine());

                switch (selection) {
                    case 1:
                        resource.displayAllCustomers();
                        break;
                    case 2:
                        resource.displayAllRooms();
                        break;
                    case 3:
                        resource.displayAllReservations();
                        break;
                    case 4:
                        new CreateRoomMenu(scanner).draw();
                        break;
                    case 5:
                        keepRunning = false;
                        break;
                    default:
                        System.out.println("Please enter a number between 1 and 5");
                }
            } catch (Exception ex) {
                System.out.println(ex);
                System.out.println("Bad input. Please enter a number between 1 and 5!");
            }
        }
    }
}
