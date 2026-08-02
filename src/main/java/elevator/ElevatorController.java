package elevator;

import elevator.enums.ElevationDirection;

import java.net.FileNameMap;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class ElevatorController implements Runnable {
    ElevatorCar elevatorCar;
    PriorityBlockingQueue<Integer> upMinPQ;
    PriorityBlockingQueue<Integer> downMaxPQ;

    private final Object monitor=new Object();

    public ElevatorController(ElevatorCar elevatorCar) {
        this.elevatorCar = elevatorCar;
        this.upMinPQ = new PriorityBlockingQueue<>();
        this.downMaxPQ = new PriorityBlockingQueue<>(10,(a,b)-> b-a);
    }
    public void submitRequest(int dest){
        enqueueRequest(dest);
    }

    private void enqueueRequest(int dest) {
        System.out.println("Request Details: "+dest+" accepted by Elevator: "+elevatorCar.id);
        if(dest==elevatorCar.nextFloor){
            return;
        }
        if(dest>=elevatorCar.nextFloor){
            if(!upMinPQ.contains(dest)){
                upMinPQ.offer(dest);
            }
        }else {
            if(!downMaxPQ.contains(dest)){
                downMaxPQ.offer(dest);
            }
        }
        synchronized (monitor){
            monitor.notifyAll();
        }
    }

    @Override
    public void run() {
        controlElevator();
    }
    public void controlElevator(){
        while (true) {
            synchronized (monitor) {
                while(upMinPQ.isEmpty() && downMaxPQ.isEmpty()){
                    try{
                        System.out.println("elevator"+ elevatorCar.id+" is IDLE!");
                        elevatorCar.currentDirection= ElevationDirection.IDLE;
                        monitor.wait();
                    }catch (InterruptedException e){
                        Thread.currentThread().interrupt();
                    }
                }
            }
            while (!upMinPQ.isEmpty()){
                int floor=upMinPQ.poll();
                System.out.println("Serving floor: " + floor + " by elevator:" +
                        elevatorCar.id + " currentFloor: " + elevatorCar.currentFloor);
                elevatorCar.moveElevator(floor);
            }
            while (!downMaxPQ.isEmpty()){
                int floor=downMaxPQ.poll();
                System.out.println("Serving floor: " + floor + " by elevator:" +
                        elevatorCar.id + " currentFloor: " + elevatorCar.currentFloor);
                elevatorCar.moveElevator(floor);
            }
        }
    }
}
