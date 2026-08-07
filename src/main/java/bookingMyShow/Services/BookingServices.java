package bookingMyShow.Services;

import bookingMyShow.enums.PaymentStatus;
import bookingMyShow.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BookingServices {
    private final Map<UUID,Booking> bookingById=new HashMap<>();

    public Booking createBookingService(User user, Show show, List<Integer> seats) {
        if(!show.lockSeat(seats)){
            throw new RuntimeException( "Seats are not Available!!");
        }
        Payment payment=new Payment(PaymentStatus.SUCCESS);
        if(payment.getStatus()==PaymentStatus.SUCCESS){
        show.confirmSeat(seats);
        Booking booking=new Booking(show,seats,user,payment);
        bookingById.put(booking.getBookingId(),booking);
        return booking;
        }
        else show.releaseSeat(seats);
        throw new RuntimeException("Payment Failed!!");
    }

    public Booking getBookingService(UUID bookingId) {
        return bookingById.get(bookingId);
    }

    public List<Booking> getBookingByUserService(User user) {
        return bookingById.values().stream()
                .filter(booking -> booking.getUser().equals(user))
                .toList();
    }
}
