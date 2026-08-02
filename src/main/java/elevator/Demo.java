package elevator;

import elevator.enums.ElevationDirection;

import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static void main (String[] args){
        try {
            ElevatorCar elevatorCar1 = new ElevatorCar(1);
            ElevatorCar elevatorCar2 = new ElevatorCar(2);

            ElevatorController controller1 = new ElevatorController(elevatorCar1);
            ElevatorController controller2 = new ElevatorController(elevatorCar2);

            List<ElevatorController> controllerList = new ArrayList<>();
            controllerList.add(controller1);
            controllerList.add(controller2);

            ElevatorScheduler elevatorScheduler = new ElevatorScheduler(controllerList, new NearestElevatorStrategy());
            InternalButton internalButton1 = new InternalButton(controller1);
            InternalButton internalButton2 = new InternalButton(controller2);

            ExternalDispatcher dispatcher = new ExternalDispatcher(elevatorScheduler);
            Building building = new Building(5, dispatcher);

            new Thread(controller1, "Elevator1").start();
            new Thread(controller2, "Elevator2").start();


            building.getFloor(3).pressUpButton();
            Thread.sleep(5);

            building.getFloor(5).pressDownButton();
            Thread.sleep(5);

            internalButton1.pressButton(4); // user inside elevator 1 presses floor 4
            Thread.sleep(5);

            internalButton1.pressButton(5); // user inside elevator 1 presses floor 5
            Thread.sleep(5);

            building.getFloor(1).pressDownButton();
            Thread.sleep(5);

            building.getFloor(2).pressDownButton();
            Thread.sleep(5);

            internalButton2.pressButton(2);

        }catch (Exception e){ e.printStackTrace();

        }
    }
}
