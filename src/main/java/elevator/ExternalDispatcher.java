package elevator;

import elevator.enums.ElevationDirection;

public class ExternalDispatcher {
    private ElevatorScheduler elevatorScheduler;

    public ExternalDispatcher(ElevatorScheduler elevatorScheduler) {
        this.elevatorScheduler = elevatorScheduler;
    }
    public void submitExternalRequest(int floor, ElevationDirection elevationDirection){
        ElevatorController controller= elevatorScheduler.assigningElevator(floor,elevationDirection);
        controller.submitRequest(floor);
    }
}
