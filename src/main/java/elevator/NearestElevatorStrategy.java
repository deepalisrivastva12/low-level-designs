package elevator;

import elevator.enums.ElevationDirection;

import java.util.List;
import java.util.Map;

public class NearestElevatorStrategy implements ElevatorAssigningStartegy{
    ElevatorCar elevatorCar;
    @Override
    public ElevatorController chossingElevator(List<ElevatorController> controllers, int requestFloors, ElevationDirection direction) {
        ElevatorController bestChoice=null;
        int minDistance = Integer.MAX_VALUE;

        for(ElevatorController controller:controllers){
            int nextFloorStep=controller.elevatorCar.nextFloor;
            boolean isSameDirection = controller.elevatorCar.currentDirection==direction && (
                    ((direction == ElevationDirection.UP && nextFloorStep<=requestFloors) ||
                            (direction == ElevationDirection.DOWN && nextFloorStep >= requestFloors)));
            int dis= Math.abs(nextFloorStep-requestFloors);
            if(isSameDirection && dis<minDistance){
                minDistance=dis;
                bestChoice=controller;
            }
        }
        if(bestChoice==null){
            for(ElevatorController controller:controllers){
                if(controller.elevatorCar.currentDirection==ElevationDirection.IDLE){
                    bestChoice=controller;
                    break;
                }
            }
        }
        if(bestChoice==null) {
            bestChoice=controllers.get(0);
        }
        return bestChoice;

    }
}
