package inventoryManagementSystem.model;

public class Address {
    private String area,city,state;
    private String pincode;

    public Address(String area, String city, String state, String pincode) {
        this.area = area;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}
