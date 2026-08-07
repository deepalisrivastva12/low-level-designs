package bookingMyShow.Services;

import bookingMyShow.enums.City;
import bookingMyShow.model.*;

import java.time.LocalDate;
import java.util.*;

public class TheatreServices {
    private final Map<City,List<Theatre>> theatreListByCity=new HashMap<>();

    public void addTheatreService(Theatre theatre) {
        theatreListByCity.computeIfAbsent(theatre.getCity(),c->new ArrayList<>())
                .add(theatre);
    }

    public Set<Movie> getMoviesService(LocalDate date, City city) {
        Set<Movie> movies=new HashSet<>();
        List<Theatre> theatres=theatreListByCity.getOrDefault(city,List.of());
        for(Theatre theatre:theatres){
            for (Screen screen:theatre.getScreens()){
                for (Show show:screen.getShows(date)){
                    movies.add(show.getMovie());
                }
            }
        }
        return movies;
    }

    public List<Theatre> getTheatreService(City city, Movie movie, LocalDate date) {
        List<Theatre> theatres=theatreListByCity.getOrDefault(city,List.of());

        return theatres.stream().
                filter(theatre->theatre.getScreens().stream()
                        .anyMatch(screen -> screen.getShows(date).stream().
                                anyMatch(show -> show.getMovie().equals(movie))))
                .toList();
    }

    public List<Show> getShowsService(Movie movie, LocalDate date, Theatre theatre) {
        return theatre.getScreens().stream()
                .flatMap(screen -> screen.getShows(date).stream()
                        .filter(show -> show.getMovie().equals(movie)))
                .toList();
    }
}
