package carrentalsystem.reservation;

import java.time.LocalDate;

public class Reservation {
    private final int reservationID;
    private final ReservationType reservationType;
    private final LocalDate reservationDateFrom;
    private final LocalDate reservationDateTo;

    private final int vichleId;
    private final int userId;
    private  ReservationStatus reservationStatus;

    public Reservation(int reservationID, ReservationType reservationType, LocalDate reservationDateFrom, LocalDate reservationDateTo, int vichleId, int userId) {
        this.reservationID = reservationID;
        this.reservationType = reservationType;
        this.reservationDateFrom = reservationDateFrom;
        this.reservationDateTo = reservationDateTo;
        this.vichleId = vichleId;
        this.userId = userId;
        this.reservationStatus=ReservationStatus.SCHEDULED;

    }

    public int getReservationID() {
        return reservationID;
    }

    public ReservationType getReservationType() {
        return reservationType;
    }

    public LocalDate getReservationDateFrom() {
        return reservationDateFrom;
    }

    public int getUserId() {
        return userId;
    }

    public LocalDate getReservationDateTo() {
        return reservationDateTo;
    }

    public int getVichleId() {
        return vichleId;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }
}
