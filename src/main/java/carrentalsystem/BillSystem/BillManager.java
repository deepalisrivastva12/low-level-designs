package carrentalsystem.BillSystem;

import carrentalsystem.reservation.Reservation;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class BillManager {

    private BillStrategy billStrategy;
    ConcurrentMap<Integer,Bill> bills=new ConcurrentHashMap<>();

    public void setBillStrategy(BillStrategy billStrategy) {
        this.billStrategy = billStrategy;
    }

    public BillManager(BillStrategy billStrategy) {
        this.billStrategy = billStrategy;
    }

    public void setBills(ConcurrentMap<Integer, Bill> bills) {
        this.bills = bills;
    }
    public Bill generateBill(Reservation reservation){
        Bill bill=billStrategy.generateBill(reservation);
        bills.put(bill.getBillId(),bill);
        return bill;
    }
    public Map<Integer,Bill> getAllBills(){
        return bills;
    }
    public Optional<Bill> getBillById(int billid){
        return Optional.ofNullable(bills.get(billid));
    }
}
