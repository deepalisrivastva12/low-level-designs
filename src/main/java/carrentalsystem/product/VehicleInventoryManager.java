package carrentalsystem.product;


import carrentalsystem.reservation.Reservation;
import carrentalsystem.reservation.ReservationRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class VehicleInventoryManager {
    //vehicleid-->vehicle
    ConcurrentMap<Integer, Vehicle> vehicleList = new ConcurrentHashMap<>();
    //vehicleid-->reservationlist
    ConcurrentMap<Integer, List<Integer>> vehicleBookingIds = new ConcurrentHashMap<>();
    //vehicleid-->currentLock
    ConcurrentMap<Integer, ReentrantLock> vehicleLocks = new ConcurrentHashMap<>();

    private ReservationRepository reservationRepository;



    public void addVehicle(Vehicle vehicle) {
        vehicleList.put(vehicle.getVehicleID(), vehicle);
    }

    public Vehicle getVehicle(int id) {
        if (vehicleList.get(id) == null) {
            throw new RuntimeException("Vehicle doesn't exits with this id");
        }
        return vehicleList.get(id);
    }

    public void setReservationRepository(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    private ReentrantLock lockTheVehicle(int id) {
        vehicleLocks.putIfAbsent(id, new ReentrantLock());
        return vehicleLocks.get(id);
    }

    /// check if the vehicle is available or not
    public boolean isAvailable(int vehicleId, LocalDate from, LocalDate to) {
        Vehicle vehicle = vehicleList.get(vehicleId);
        if (vehicle == null) return false;
        if (vehicle.getVehicleStatus() == VehicleStatus.MAINTENANCE) return false;

        DateInterval dateInterval = new DateInterval(from, to);
        List<Integer> reservationListofVehicle = vehicleBookingIds.get(vehicleId);
        if (reservationListofVehicle == null || reservationListofVehicle.isEmpty()) {
            return true;
        }
        for (int reservationId : reservationListofVehicle) {
            Reservation reservation = reservationRepository.findById(reservationId).get();
            LocalDate reservationDateFrom = reservation.getReservationDateFrom();
            LocalDate reservationDatesTo = reservation.getReservationDateTo();
            DateInterval requestedReservation = new DateInterval(reservationDateFrom, reservationDatesTo);
            if (dateInterval.overlap(requestedReservation)) {
                return false;
            }
        }
        return true;
    }
        // Atomic Booking means there would be no duplicacy of vehicle id in reservation list for same date as we r using locks
    public boolean reserve(int vehicleId,int reservationId, LocalDate from,LocalDate to){
        ReentrantLock lock=lockTheVehicle(vehicleId);
        lock.lock();
        try {
            if(!(isAvailable(vehicleId,from,to))){
                return false;
            }
            vehicleBookingIds.putIfAbsent(vehicleId,new ArrayList<>());
            vehicleBookingIds.get(vehicleId).add(reservationId);
            return true;
        }finally {
            lock.unlock();
        }
    }

    public void release(int vehicleId,int reservationId){
        ReentrantLock lock=lockTheVehicle(vehicleId);
        lock.lock();
        try {
            List<Integer> reservedVehicleId=vehicleBookingIds.get(vehicleId);
            if(reservedVehicleId!=null){
                reservedVehicleId.remove(Integer.valueOf(reservationId));
            }
            List<Integer> currentlyBooked=vehicleBookingIds.get(vehicleId);
            if(currentlyBooked==null || currentlyBooked.isEmpty()){
                vehicleList.get(vehicleId).setVehicleStatus(VehicleStatus.AVAILABLE);
            }

        }finally {
            lock.unlock();
        }
    }
    public List<Vehicle> getAvailableVehicle(VehicleType type,LocalDate from,LocalDate to){
        return vehicleList.values().stream()
                .filter(i-> i.getVehicleType()==type)
                .filter(i->isAvailable(i.getVehicleID(), from,to))
                .toList();
    }
}
