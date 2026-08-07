package bookingMyShow.model;

import bookingMyShow.enums.SeatStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class Show {
    LocalDate date;
    LocalTime time;
    Movie movie;
    private final Map<Integer, SeatStatus> seatStatusMap=new HashMap<>();
    private final Map<Integer, ReentrantLock> lockForSeat=new HashMap<>();

    public Show(LocalDate date,Screen screen, LocalTime time, Movie movie) {
        this.date = date;
        this.time = time;
        this.movie = movie;

        for (Seat seat: screen.getSeats()){
            seatStatusMap.put(seat.getSeatId(),SeatStatus.AVAILABLE);
            lockForSeat.put(seat.getSeatId(),new ReentrantLock());
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public Movie getMovie() {
        return movie;
    }
    public boolean lockSeat(List<Integer> seatsId){
        List<Integer>sorted=new ArrayList<>(seatsId);
        Collections.sort(sorted);
        List<ReentrantLock> acquiredLocks=new ArrayList<>();
        try {
            for (int seatId:sorted){
                ReentrantLock lock=lockForSeat.get(seatId);
                lock.lock();
                acquiredLocks.add(lock);
            }
            for (int seatId:sorted){
                if(seatStatusMap.get(seatId)!=SeatStatus.AVAILABLE){
                    return false;
                }
            }
            for (int seatId:sorted){
                seatStatusMap.put(seatId,SeatStatus.LOCKED);
            }
        }finally {
            for(ReentrantLock lock:acquiredLocks){
                lock.unlock();
            }
        }
        return true;
    }
    public void confirmSeat(List<Integer> seatsId){
        for(int seatid:seatsId) {
            seatStatusMap.put(seatid, SeatStatus.BOOKED);
        }
    }
    public void releaseSeat(List<Integer> seatsId){
        for(int seatid:seatsId) {
            seatStatusMap.put(seatid, SeatStatus.AVAILABLE);
        }
    }
}
