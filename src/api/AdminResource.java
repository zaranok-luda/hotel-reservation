package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import model.RoomType;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;

public class AdminResource {
    public final CustomerService customerService = CustomerService.getInstance();
    public final ReservationService reservationService = ReservationService.getInstance();
    public Customer getCustomer(String email){
        return this.customerService.getCustomer(email);
    }
    public void addRoom(String roomNumber, Double price, RoomType roomType) {
        this.reservationService.addRoom(roomNumber, price, roomType);
    }
    public Collection<IRoom> getAllRooms() {
        return this.reservationService.getAllRooms();
    }

    public void displayAllRooms() {
        Collection<IRoom> rooms = this.getAllRooms();
        if (rooms.size() == 0) {
            System.out.println("No rooms available to display.");
            return;
        }

        for (IRoom room: this.getAllRooms()) {
            System.out.println(room);
        }
    }
    public Collection<Customer> getAllCustomers() {
        return this.customerService.getAllCustomers();
    }

    public void displayAllCustomers() {
        Collection<Customer> customers = this.getAllCustomers();
        if (customers.size() == 0) {
            System.out.println("No customers available to display.");
            return;
        }

        for (Customer customer: customers) {
            System.out.println(customer);
        }
    }

    public Collection<Reservation> getAllReservations() {
        return this.reservationService.getAllReservations();
    }

    public void displayAllReservations() {
        Collection<Reservation> reservations = this.getAllReservations();
        if (reservations.size() == 0) {
            System.out.println("No reservations available to display.");
            return;
        }

        for (Reservation reservation: this.getAllReservations()) {
            System.out.println(reservation);
        }
    }

}
