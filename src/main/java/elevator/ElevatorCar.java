package elevator;

import elevator.enums.DoorState;
import elevator.enums.ElevationDirection;

public class ElevatorCar {
    int id;
    int currentFloor;
    int nextFloor;
    ElevationDirection currentDirection;
    Door door;

    public ElevatorCar(int id) {
        this.currentFloor = 0;
        this.currentDirection = ElevationDirection.IDLE;
        door=new Door();
        this.id=id;
    }
    public void display(){
        System.out.println("Elevator"+id+" on current floor "+currentFloor+" moving towards "+nextFloor);
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public void moveElevator(int dest){
        this.nextFloor=dest;
        if(this.currentFloor==nextFloor){
            door.openDoor(id);
            return;
        }
        int stFloor=currentFloor;
        if(nextFloor >= stFloor){
            currentDirection=ElevationDirection.UP;
            display();
            for(int i =stFloor;i<=nextFloor;i++){
               try{
                   Thread.sleep(5);
               }catch (Exception e){

               }
               this.currentFloor=i;
               display();
            }
        }else{
            currentDirection=ElevationDirection.DOWN;
            display();
            for (int i=stFloor-1;i>=nextFloor;i--){
                try {
                    Thread.sleep(5);
                }catch (Exception e){

                }
                this.currentFloor=i;
                display();
            }
            door.closeDoor(id);
        }

    }
}
