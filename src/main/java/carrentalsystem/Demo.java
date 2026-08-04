package carrentalsystem;

import carrentalsystem.BillSystem.Bill;
import carrentalsystem.BillSystem.DailyBillStrategy;
import carrentalsystem.payment.Payment;
import carrentalsystem.payment.UPIPaymentStrategy;
import carrentalsystem.product.Vehicle;
import carrentalsystem.product.VehicleStatus;
import carrentalsystem.product.VehicleType;
import carrentalsystem.reservation.Reservation;
import carrentalsystem.reservation.ReservationType;

import java.time.LocalDate;
import java.util.List;

public class Demo {
    public static void main(String[] args){
        System.out.println("-----Vehile Rental Store-----");

        VehicleRentalStores rentalStores=new VehicleRentalStores();
        //-----Creating a random location-----
        Location location=new Location(12,"Ghanta Ghar","Bareilly","Uttar Pradesh","India");
        //-----Creating store-----
        Store store1=new Store(101,location);
        rentalStores.addStore(store1);

        //-----Creating User-----
        User user1=new User(1,"Deepali","DR00123");
        User user2=new User(2,"Vaishnavi","DR00890");
        rentalStores.addUser(user1);
        rentalStores.addUser(user2);

        //-----Adding vehicle to vehicleManager-----
        Vehicle vehicle1=new Vehicle(1, VehicleType.FOUR_WHEELAR,"DLUP123");
        Vehicle vehicle2=new Vehicle(2, VehicleType.TWO_WHEELAR,"DLUP890");
        vehicle1.setDailyRentalprice(1000);
        vehicle2.setDailyRentalprice(500);

        store1.getInventoryManager().addVehicle(vehicle1);
        store1.getInventoryManager().addVehicle(vehicle2);

        //-----Checking the all available four wheelar vehicle-----
        LocalDate from =LocalDate.of(2026,04,14);
        LocalDate to =LocalDate.of(2026,05,14);
        List<Vehicle> availableVehicle=store1.getInventoryManager().getAvailableVehicle(VehicleType.FOUR_WHEELAR,from,to);
        System.out.println("Available Vehicle from "+from+" to "+to+" :- ");
        for (Vehicle v:availableVehicle){
            System.out.print(v.getVehicleID()+":"+v.getVehicleType());
        }

        //-----Create reservation for availble vehicle-----
        System.out.println("\nCreating Reservation: ");
        Reservation reservation= store1.createReservation(1,user1,from,to, ReservationType.DAILY);
        System.out.println("Reservation Created with ReservationId: "+reservation.getReservationID());

        //-----User Started The trip-----
        store1.startTrip(reservation.getReservationID());

        //-----User has submitted the vehicle-----
        store1.submitVehicle(reservation.getReservationID());

        //-----Generate bill for the reservation-----
        Bill bill = store1.generateBill(reservation.getReservationID(),new DailyBillStrategy(store1.getInventoryManager()));
        System.out.println("Bill is generated with BillId: "+bill.getBillId());
        System.out.println("Total amount of bill to be paid: "+bill.getTotalAmount());

        //-----Make the payment for the generated bill----
        Payment payment=store1.makePayment(new UPIPaymentStrategy(),bill,bill.getTotalAmount());
        System.out.println("Payment Receipt");
        System.out.println("PaymentId:"+payment.getPaymentId());
        System.out.println("Payment Amount:"+payment.getPaymentAmount());
        System.out.println("Payment Mode:"+payment.getPaymentMode());
        System.out.println("Payment has successfully done on date "+payment.getPaymentDate());
    }
}
