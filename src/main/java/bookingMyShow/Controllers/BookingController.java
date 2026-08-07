package bookingMyShow.Controllers;

import bookingMyShow.Services.BookingServices;
import bookingMyShow.model.Booking;
import bookingMyShow.model.Show;
import bookingMyShow.model.User;

import java.util.List;
import java.util.UUID;


public class BookingController {
    private BookingServices bookingServices;

    public BookingController() {
        this.bookingServices = new BookingServices();

    }

    public Booking createBooking(User user, Show show, List<Integer> seats){
        return bookingServices.createBookingService(user,show,seats);
    }
    public Booking getBooking(UUID bookingId){
        return bookingServices.getBookingService(bookingId);
    }
    public List<Booking> getbookingByUser(User user){
        return bookingServices.getBookingByUserService(user);
    }
}
