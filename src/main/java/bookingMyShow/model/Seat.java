package bookingMyShow.model;

import bookingMyShow.enums.Category;


public class Seat {
    private final int seatId;
    private final Category category;

    public Seat(int seatId,Category category) {
        this.category = category;
        this.seatId=seatId;
    }

    public int getSeatId() {
        return seatId;
    }
}
