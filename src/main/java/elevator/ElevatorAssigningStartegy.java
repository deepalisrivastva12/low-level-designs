package elevator;

import elevator.enums.ElevationDirection;

import java.util.List;

public interface ElevatorAssigningStartegy {
    public ElevatorController chossingElevator(List<ElevatorController> controllers
            ,int floors, ElevationDirection elevationDirection);
}
