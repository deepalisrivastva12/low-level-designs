package bookingMyShow.model;


import java.util.List;
import java.util.UUID;

public class Booking {
    private final Show show;
    private final List<Integer> seats;
    private final User user;
    private final Payment payment;
    private final UUID bookingId;

    public Booking(Show show, List<Integer> seats, User user, Payment payment) {
        this.show = show;
        this.seats = seats;
        this.user = user;
        this.payment = payment;
        this.bookingId = UUID.randomUUID();
    }

    public Show getShow() {
        return show;
    }

    public List<Integer> getSeats() {
        return seats;
    }

    public User getUser() {
        return user;
    }

    public UUID getBookingId() {
        return bookingId;
    }

    public Payment getPayment() {
        return payment;
    }
}
