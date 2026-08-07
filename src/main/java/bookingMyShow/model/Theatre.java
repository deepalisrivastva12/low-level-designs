package bookingMyShow.model;

import bookingMyShow.enums.City;

import java.util.List;

public class Theatre {
    private final String theatreName;
    private final City city;
    private final List<Screen> screens;

    public Theatre(String theatreName, City city, List<Screen> screens) {
        this.theatreName = theatreName;
        this.city = city;
        this.screens = screens;
    }

    public String getTheatreName() {
        return theatreName;
    }

    public City getCity() {
        return city;
    }

    public List<Screen> getScreens() {
        return screens;
    }
}
