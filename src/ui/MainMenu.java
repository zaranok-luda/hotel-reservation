package ui;

import api.HotelResource;

import java.util.Scanner;

public class MainMenu {
    private HotelResource hotelResource = new HotelResource();

    public void draw() {
        boolean keepRunning = true;
        try (Scanner scanner = new Scanner(System.in)) {

            while (keepRunning) {
                try {
                    System.out.println("1. Find and reserve a room");
                    System.out.println("2. See my reservations");
                    System.out.println("3. Create an account");
                    System.out.println("4. Admin");
                    System.out.println("5. Exit");
                    System.out.println("Please enter your selection?");
                    int selection = Integer.parseInt(scanner.nextLine());

                    switch (selection) {
                        case 1:
                            new ReservationMenu(scanner).draw();
                            break;
                        case 2:
                            new PrintCustomerReservationsMenu(scanner).draw();
                            break;
                        case 3:
                            new CreateAccountMenu(scanner).draw();
                            break;
                        case 4:
                            new AdminMenu(scanner).draw();
                            break;
                        case 5:
                            keepRunning = false;
                            break;
                        default:
                            System.out.println("Please enter a number between 1 and 5");
                    }
                } catch (Exception ex) {
                    System.out.println("Bad input. Please try again!");
                    System.out.println(ex);
                }
            }
        }
    }
}
