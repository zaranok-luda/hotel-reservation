package ui;

import api.AdminResource;
import api.HotelResource;

import java.util.Scanner;

public class PrintCustomerReservationsMenu {
    private Scanner scanner;
    private HotelResource hotelResource = new HotelResource();
    private AdminResource adminResource = new AdminResource();

    public PrintCustomerReservationsMenu(Scanner scanner) {
        this.scanner = scanner;
    }
    public void draw() {
        boolean keepRunning = true;

        while (keepRunning) {
            try {
                if (this.adminResource.getAllCustomers().size() == 0) {
                    System.out.println("Please create an Account first to reserve a room");
                    break;
                }

                System.out.println("Please customer email or [0] to Return to main menu:");
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("0")) {
                    keepRunning = false;
                    continue;
                }

                this.hotelResource.printCustomersReservations(input);
                keepRunning = false;
            } catch (Exception ex) {
                System.out.println("Bad input. Please try again!");
                System.out.println(ex);
            }
        }
    }
}
