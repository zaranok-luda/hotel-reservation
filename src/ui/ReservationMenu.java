package ui;

import api.AdminResource;
import api.HotelResource;
import model.IRoom;

import java.text.SimpleDateFormat;
import java.util.*;

public class ReservationMenu {
    private Scanner scanner;
    private HotelResource hotelResource = new HotelResource();
    private AdminResource adminResource = new AdminResource();

    public ReservationMenu(Scanner scanner) {
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

                System.out.println("Please enter CheckIn (MM/DD/YYYY) and CheckOut (MM/DD/YYYY) dates (space separated) or [0] to Return to main menu:");
                String input = scanner.nextLine();
                String[] userInput = input.split(" ");

                if (input.equalsIgnoreCase("0")) {
                    keepRunning = false;
                    continue;
                }
                if (userInput.length < 2) {
                    System.out.println("Unable to create Reservation. Incorrect input data. Please try again.");
                    continue;
                }

                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                Date checkinDate = formatter.parse(userInput[0]);
                Date checkoutDate = formatter.parse(userInput[1]);

                if (checkinDate.getTime() >= checkoutDate.getTime()) {
                    throw new IllegalArgumentException("CheckIn date must be less than CheckOut date.");
                }

                Collection<IRoom> availableRooms = this.hotelResource.findARoom(checkinDate, checkoutDate);

                System.out.println("Please enter email and room number (space separated) to complete your reservation or [0] to cancel: ");
                String[] reservationInput = scanner.nextLine().split(" ");

                if (reservationInput != null && reservationInput[0].equalsIgnoreCase("0")) {
                    throw new IllegalArgumentException("Not enough data to proceed booking.");
                }

                if (reservationInput.length < 2) {
                    throw new IllegalArgumentException("Not enough data to proceed booking. Email and/or Room number is missing.");
                }
                String customerEmail = reservationInput[0];
                String roomNumber = reservationInput[1];

                boolean isRoomInAvailable = false;
                for (IRoom room: availableRooms) {
                    if (room.getRoomNumber().equalsIgnoreCase(roomNumber)) {
                        isRoomInAvailable = true;
                        break;
                    }
                }

                if (!isRoomInAvailable) {
                    throw new IllegalArgumentException("Entered room number is not from available list for booking. Please enter available room number.");
                }

                this.hotelResource.bookARoom(customerEmail, roomNumber, checkinDate, checkoutDate);

                System.out.println("You successfully booked a room!");

                keepRunning = false;
            } catch (Exception ex) {
                System.out.println("Bad input. Please try again!");
                System.out.println(ex);
            }
        }
    }
}
