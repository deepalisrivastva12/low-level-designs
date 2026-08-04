package carrentalsystem.product;

import java.time.LocalDate;

public class DateInterval {
    private final LocalDate from;
    private final LocalDate to;

    public DateInterval(LocalDate from, LocalDate to) {
        if(from.isAfter(to)){
            throw new IllegalArgumentException("Choose the correct timeline!!");
        }
        this.from = from;
        this.to = to;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }
    public boolean overlap(DateInterval other){
        return !(from.isBefore(to) || to.isAfter(from));
    }
}
