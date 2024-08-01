package entities;

/**
 * Class of Session's Seat
 */
public class Room extends BaseEntity {
    private int id;
    private String roomName;

    public Room() {
    }

    public Room(int id, String roomName) {
        super(id);
        this.roomName = roomName;
    }

    public Room(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    @Override
    public String toString() {
        return "Room{" + "id=" + id + ", roomName=" + roomName + '}';
    }

}
