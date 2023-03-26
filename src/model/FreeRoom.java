package model;

public class FreeRoom extends Room {
    public FreeRoom() {
        super();
        this.price = 0.0;
    }

    public FreeRoom(String roomNumber, RoomType roomType) {
        super(roomNumber, 0.0, roomType);
    }

    @Override
    public boolean isFree() {
        return true;
    }

    @Override
    public String toString() {
        return "model.FreeRoom: " + roomNumber + ", Price: " + price + ", model.Room type: " + roomType;
    }

}
