package bookingMyShow.model;

import java.time.LocalDate;
import java.util.*;

public class Screen {

    private final int screenId;
    private final List<Seat> seats;
    private final Map<LocalDate,List<Show>> showsListByDate=new HashMap<>();

    public Screen(int screenId, List<Seat> seats) {
        this.screenId = screenId;
        this.seats = seats;
    }

    public List<Seat> getSeats(){
        return seats;
    }
    public void addShow(Show show){
        showsListByDate
                .computeIfAbsent(show.getDate(),d-> new ArrayList<>())
                .add(show);
    }
    public List<Show> getShows(LocalDate date){
        return showsListByDate.getOrDefault(date,new ArrayList<>());
    }
}
