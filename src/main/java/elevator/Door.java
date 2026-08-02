package elevator;

import elevator.enums.DoorState;

public class Door{
    DoorState doorState;

    public Door() {
        this.doorState = DoorState.CLOSE_DOOR;
    }
    public void closeDoor(int id){
        doorState=DoorState.CLOSE_DOOR;
        System.out.println("Door of Elevator "+ id+" is getting closed");
    }
    public void openDoor(int id){
        doorState=DoorState.OPEN_DOOR;
        System.out.println("Door of Elevator "+ id+" is getting open");
    }
}
