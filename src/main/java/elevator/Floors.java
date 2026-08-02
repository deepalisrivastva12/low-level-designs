package elevator;

import elevator.enums.ElevationDirection;

import javax.swing.border.EtchedBorder;

public class Floors {
    int floor;
    ExternalButton upButton;
    ExternalButton downButton;

    Floors(int floor,ExternalDispatcher externalDispatcher){
        this.floor=floor;
        this.upButton=new ExternalButton(externalDispatcher);
        this.downButton=new ExternalButton(externalDispatcher);
    }
    public void pressUpButton(){
        upButton.pressButton(floor, ElevationDirection.UP);
    } public void pressDownButton(){
        downButton.pressButton(floor, ElevationDirection.DOWN);
    }

}
