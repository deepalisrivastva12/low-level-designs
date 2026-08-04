package carrentalsystem.reservation;

import javax.swing.plaf.OptionPaneUI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ReservationRepository {
    ConcurrentMap<Integer,Reservation> reservationList;

    public ReservationRepository() {
        this.reservationList=new ConcurrentHashMap<>();
    }

    public Optional<Reservation> findById(int reservationId){
        return Optional.ofNullable(reservationList.get(reservationId));
    }
    public void save(Reservation reservation){
        reservationList.put(reservation.getReservationID(),reservation);
    }
    public void remove(int reservationID){
        reservationList.remove(reservationID);
    }
    public Map<Integer,Reservation> getAll(){
        return reservationList;
    }

}
