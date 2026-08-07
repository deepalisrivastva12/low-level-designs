package bookingMyShow;

import bookingMyShow.Controllers.BookingController;
import bookingMyShow.Controllers.TheatreController;
import bookingMyShow.enums.Category;
import bookingMyShow.enums.City;
import bookingMyShow.model.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BookMyShow {
    TheatreController theatreController;
    BookingController bookingController;
    public static void main(String[] args){
        BookMyShow bookMyShow=new BookMyShow();
        bookMyShow.initializeApp();
        bookMyShow.startTheFlow();

    }


    private List<Seat> createSeat() {
        List<Seat>seats=new ArrayList<>();
        for (int i =1;i<10;i++){
            seats.add(new Seat(i, Category.SILVER));
        }
        for (int i =11;i<21;i++){
            seats.add(new Seat(i, Category.GOLD));
        }
        for (int i =22;i<31;i++){
            seats.add(new Seat(i, Category.PLATINUM));
        }
        return seats;
    }

    private void initializeApp() {
        theatreController=new TheatreController();
        bookingController=new BookingController();

        Movie spiderman=new Movie("Spider Man: A Brand New Day");
        Movie alpha=new Movie("Alpha");

        Screen inoxScreen1=new Screen(1,createSeat());
        Theatre inoxTheatre=new Theatre("INOX", City.BANGALORE,List.of(inoxScreen1));

        Screen pvrScreen1=new Screen(1,createSeat());
        Theatre pvrTheatre =new Theatre("PVR",City.LUCKNOW,List.of(pvrScreen1));

        theatreController.addTheatre(inoxTheatre);
        theatreController.addTheatre(pvrTheatre);
        LocalDate date=LocalDate.now();
        Show inoxMorningShowScreen1=new Show(date,inoxScreen1,LocalTime.of(9,0),spiderman);

        Show inoxeveningShowScreen1=new Show(date,inoxScreen1,LocalTime.of(6,0),spiderman);

        Show inoxnightShowScreen1=new Show(date,inoxScreen1,LocalTime.of(21,0),spiderman);

        Show pvrAfternoonShow=new Show(date,pvrScreen1,LocalTime.of(15,0),alpha);

        inoxScreen1.addShow(inoxeveningShowScreen1);
        inoxScreen1.addShow(inoxMorningShowScreen1);
        inoxScreen1.addShow(inoxnightShowScreen1);

        pvrScreen1.addShow(pvrAfternoonShow);

    }

    private void startTheFlow() {
        User user1=new User("Tom Holland","U1");
        System.out.println("Username "+user1.getUserName()+" logged in");

        LocalDate selectedDate =LocalDate.now();
        System.out.println("Selected  Date: "+selectedDate);

        City selectedCity =City.BANGALORE;
        System.out.println("Selected City: "+selectedCity);

        Set<Movie> movies=theatreController.getMovies(selectedDate,selectedCity);
        System.out.println("Available Movies are ");
        movies.forEach(movie -> System.out.println("-> "+movie.getName()+" "));

        Movie selectedMovie=movies.iterator().next();
        System.out.println("Selected Movie: "+selectedMovie.getName());

        List<Theatre> theatres=theatreController.getTheatre(selectedCity,selectedMovie,selectedDate);
        System.out.println("Available Theatres are ");
        theatres.forEach(theatre -> System.out.println( "-> "+theatre.getTheatreName()));

        Theatre selectedTheatre=theatres.get(0);
        System.out.println("Selected Theatre: "+selectedTheatre.getTheatreName());
        List<Show> shows=theatreController.getShows(selectedMovie,selectedDate,selectedTheatre);

        System.out.println("Available Shows are ");
        shows.forEach(show -> System.out.println("-> "+show.getTime()));

        Show selectedShow=shows.get(0);
        System.out.println("Selected Show: "+selectedShow.getTime());

        List<Integer> selectedSeats=List.of(1,2,3);
        System.out.print("Selected Seats are: ");
        selectedSeats.forEach(i-> System.out.print(i+" "));

        Booking booking=bookingController.createBooking(user1,selectedShow,selectedSeats);
        System.out.println("\nBooking SUCCESSFUL");
        System.out.println("Booking id is "+booking.getBookingId());
    }
}
