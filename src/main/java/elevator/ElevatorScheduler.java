package elevator;

import elevator.enums.ElevationDirection;

import java.util.List;

public class ElevatorScheduler {
    List<ElevatorController> controllers;
    private ElevatorAssigningStartegy startegy;

    public ElevatorScheduler(List<ElevatorController> controllers, ElevatorAssigningStartegy startegy) {
        this.controllers = controllers;
        this.startegy = startegy;
    }

    public void setStartegy(ElevatorAssigningStartegy startegy) {
        this.startegy = startegy;
    }
    public ElevatorController assigningElevator(int floor, ElevationDirection elevationDirection){
      return  startegy.chossingElevator(controllers,floor,elevationDirection);
    }
}
