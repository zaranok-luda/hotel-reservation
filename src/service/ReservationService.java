package service;

import model.*;

import java.util.*;

public class ReservationService {
    private final Set<IRoom> rooms = new HashSet<>();
    private final List<Reservation> reservations = new ArrayList<>();

    private static ReservationService instance;

    private ReservationService() {
    }

    public static ReservationService getInstance() {
        if (ReservationService.instance == null) {
            ReservationService.instance = new ReservationService();
        }

        return ReservationService.instance;
    }
    public void addRoom(String roomNumber, Double price, RoomType roomType) {
        IRoom newRoom = new Room(roomNumber, price, roomType);
        if (this.rooms.contains(newRoom)) {
            throw new IllegalArgumentException("Incorrect input data: Current room number already exists!");
        }
        this.rooms.add(newRoom);
    }

    public IRoom getARoom(String roomId) {
        Iterator<IRoom> iterator = this.rooms.iterator();
        while(iterator.hasNext()){
            IRoom room = iterator.next();
            if (room.getRoomNumber().equals(roomId)) {
                return room;
            }
        }

        return null;
    }

    public Collection<IRoom> getAllRooms(){
        return new ArrayList<>(this.rooms);
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        if (customer == null) {
            throw new IllegalArgumentException("Incorrect input data: Current user is empty");
        }
        if (room == null) {
            throw new IllegalArgumentException("Incorrect input data: Current room is empty");
        }
        if (checkInDate == null || checkOutDate == null) {
            throw new IllegalArgumentException("Incorrect input data: Entered date range is invalid");
        }

        addCheckinCheckoutTime(checkInDate, checkOutDate);

        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(reservation);

        return reservation;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate){
        List<IRoom> rooms = new ArrayList<>();
        for (IRoom room: this.getAllRooms()) {
            if (this.isRoomAvailable(room, new Date(checkInDate.getTime()), new Date(checkOutDate.getTime()))) {
                rooms.add(room);
            }
        }

        return rooms;
    }

    public Collection<IRoom> findRecommendedRooms(Date checkIn, Date checkOut) {
        final int DAYS_7_MS = 7 * 24 * 60 * 60 * 1000;
        checkIn.setTime(checkIn.getTime() + DAYS_7_MS);
        checkOut.setTime(checkOut.getTime() + DAYS_7_MS);

        Date checkInPrint = new Date(checkIn.getTime());
        Date checkOutPrint = new Date(checkOut.getTime());
        this.addCheckinCheckoutTime(checkInPrint, checkOutPrint);

        System.out.println("Recommended checkIn date: " +  checkInPrint);
        System.out.println("Recommended checkOut date: " + checkOutPrint);

        return this.findRooms(checkIn, checkOut);
    }

    public Collection<Reservation> getCustomersReservation(Customer customer) {
        List<Reservation> reservations = new ArrayList<>();
        for (Reservation reservation: this.reservations) {
            if (reservation.getCustomer().getEmail().equalsIgnoreCase(customer.getEmail())) {
                reservations.add(reservation);
            }
        }

        return reservations;
    }

    public Collection<Reservation> getAllReservations() {
        return this.reservations;
    }

    public void addCheckinCheckoutTime(Date checkInDate, Date checkOutDate) {
        final int CHECK_IN_TIME = 16;
        final int CHECK_OUT_TIME = 10;
        checkInDate.setTime(checkInDate.getTime() + CHECK_IN_TIME * 60 * 60 * 1000);
        checkOutDate.setTime(checkOutDate.getTime() + CHECK_OUT_TIME * 60 * 60 * 1000);
    }

    private boolean isRoomAvailable(IRoom room, Date checkInDate, Date checkOutDate) {
        addCheckinCheckoutTime(checkInDate, checkOutDate);
        for (Reservation reservation: this.reservations) {
            if (room.getRoomNumber().equalsIgnoreCase(reservation.getRoom().getRoomNumber())
            && !roomIsVacant(reservation, checkInDate, checkOutDate)) {
                return false;
            }
        }

        return true;
    }

    private boolean roomIsVacant(Reservation reservation, Date checkInDate, Date checkOutDate) {
        long reservationCheckInDate = reservation.getCheckInDate().getTime();
        long reservationCheckOutDate = reservation.getCheckOutDate().getTime();
        long customerCheckInDate = checkInDate.getTime();
        long customerCheckOutDate = checkOutDate.getTime();

        return (customerCheckInDate < reservationCheckInDate && customerCheckOutDate <= reservationCheckInDate)
                || (customerCheckInDate >= reservationCheckOutDate && customerCheckOutDate > reservationCheckOutDate);
    }

}
