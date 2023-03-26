package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {
    private final CustomerService customerService = CustomerService.getInstance();
    private final ReservationService reservationService = ReservationService.getInstance();

    public Customer getCustomer(String email) {
        return this.customerService.getCustomer(email);
    }

    public void createACustomer(String firstName, String lastName, String email) {
        Customer customer = this.getCustomer(email);
        if (customer != null) {
            throw new IllegalArgumentException("Unable to create a new account. Customer with this email already exists!");
        }
        Customer newCustomer = new Customer(firstName, lastName, email);
        this.customerService.addCustomer(newCustomer);
    }
    public IRoom getRoom(String roomNumber) {
        return reservationService.getARoom(roomNumber);
    }

    public Reservation bookARoom(String customerEmail, String roomNumber, Date checkInDate, Date checkOutDate) {
        Customer customer = this.getCustomer(customerEmail);
        if (customer == null) {
            throw new IllegalArgumentException("No such customer found by given email!");
        }
        IRoom room = reservationService.getARoom(roomNumber);
        if (room == null) {
            throw new IllegalArgumentException("No such room found by given room number!");
        }

        return this.reservationService.reserveARoom(customer, room, checkInDate, checkOutDate);
    }

    public Collection<Reservation> getCustomersReservations(String customerEmail) {
        return this.reservationService.getCustomersReservation(this.getCustomer(customerEmail));
    }

    public void printCustomersReservations(String customerEmail) {
        Collection<Customer> customers = this.customerService.getAllCustomers();
        if (customers == null || customers.size() == 0) {
            throw new IllegalArgumentException("Please create an Account first to see reservations");
        }
        Customer customer = this.getCustomer(customerEmail);

        if (customer == null) {
            throw new IllegalArgumentException("Unable to find customer with given email!");
        }

        Collection<Reservation> reservations = this.getCustomersReservations(customerEmail);
        if (reservations.size() == 0) {
            System.out.println("No reservations available to display.");
            return;
        }

        for (Reservation reservation: reservations) {
            System.out.println(reservation);
        }
    }

    public Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
        Collection<IRoom> availableRooms = this.reservationService.findRooms(checkIn, checkOut);
        this.printRooms("Available rooms: ", availableRooms);

        if (availableRooms.size() == 0) {
            System.out.println("No available rooms found for provided dates.");
            System.out.println("New search will be performed for the recommended dates...");

            Collection<IRoom> recommendedRooms = this.reservationService.findRecommendedRooms(checkIn, checkOut);
            this.printRooms("Recommended rooms: ", recommendedRooms);

            if (recommendedRooms.size() == 0) {
                System.out.println("No rooms found for recommended dates");
                throw new IllegalArgumentException("No ANY available rooms found. Please try a new search.");
            }
            availableRooms = recommendedRooms;
        }

        return availableRooms;
    }

    public void printRooms(String label, Collection<IRoom> rooms) {
        System.out.println(label);
        for (IRoom room: rooms) {
            System.out.println(room);
        }
    }

}
