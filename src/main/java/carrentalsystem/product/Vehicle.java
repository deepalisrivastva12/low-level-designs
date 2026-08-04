package carrentalsystem.product;


public class Vehicle {
    private final  VehicleType vehicleType;
    private  VehicleStatus vehicleStatus;
    private final String vehicleNumber;
    private final int vehicleID;
    private double dailyRentalprice;

    public Vehicle(int vehicleID, VehicleType vehicleType, String vehicleNumber) {
        this.vehicleID = vehicleID;
        this.vehicleType = vehicleType;
        this.vehicleStatus = VehicleStatus.AVAILABLE;
        this.vehicleNumber = vehicleNumber;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public VehicleStatus getVehicleStatus() {
        return vehicleStatus;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public int getVehicleID() {
        return vehicleID;
    }

    public double getDailyRentalprice() {
        return dailyRentalprice;
    }


    public void setDailyRentalprice(double dailyRentalprice) {
        this.dailyRentalprice = dailyRentalprice;
    }

    public void setVehicleStatus(VehicleStatus vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }
}
