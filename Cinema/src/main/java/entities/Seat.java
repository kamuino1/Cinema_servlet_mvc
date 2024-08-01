package entities;

/**
 * Class of Session's Seat
 */
public class Seat extends BaseEntity {
    /**
     * Number of row
     */
    private int rowNumber;
    /**
     * Number of place
     */
    private int placeNumber;
    private Room room;

    public Seat() {
    }

    public Seat(int id, int rowNumber, int placeNumber, Room room) {
        super(id);
        this.rowNumber = rowNumber;
        this.placeNumber = placeNumber;
        this.room = room;
    }

    public Seat(int rowNumber, int placeNumber) {
        this.rowNumber = rowNumber;
        this.placeNumber = placeNumber;
    }
    
    public Seat(int id, int rowNumber, int placeNumber) {
        super(id);
        this.rowNumber = rowNumber;
        this.placeNumber = placeNumber;
    }
    
    public int getRowNumber() {
        return rowNumber;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getPlaceNumber() {
        return placeNumber;
    }

    public void setPlaceNumber(int placeNumber) {
        this.placeNumber = placeNumber;
    }


    @Override
    public String toString() {
        return "Seat{" +
                "rowNumber=" + rowNumber +
                ", placeNumber=" + placeNumber +
                '}';
    }
}
