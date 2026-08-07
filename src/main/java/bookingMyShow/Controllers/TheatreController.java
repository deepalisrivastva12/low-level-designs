package bookingMyShow.Controllers;

import bookingMyShow.Services.TheatreServices;
import bookingMyShow.enums.City;
import bookingMyShow.model.Movie;
import bookingMyShow.model.Show;
import bookingMyShow.model.Theatre;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class TheatreController {
    private TheatreServices theatreServices;

    public TheatreController() {
        this.theatreServices = new TheatreServices();

    }


    public void addTheatre(Theatre theatre){
        theatreServices.addTheatreService(theatre);
    }
    public Set<Movie> getMovies(LocalDate date, City city){
       return theatreServices.getMoviesService(date,city);
    }
    public List<Theatre> getTheatre(City city,Movie movie,LocalDate date){
        return theatreServices.getTheatreService(city,movie,date);
    }
    public List<Show> getShows(Movie movie,LocalDate date,Theatre theatre){
        return theatreServices.getShowsService(movie,date,theatre);
    }
}
