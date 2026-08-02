package elevator;

import elevator.enums.ElevationDirection;

public class ExternalButton {
    private ExternalDispatcher externalDispatcher;

    public ExternalButton(ExternalDispatcher externalDispatcher) {
        this.externalDispatcher = externalDispatcher;
    }
    public void pressButton(int floor, ElevationDirection elevationDirection){
        externalDispatcher.submitExternalRequest(floor,elevationDirection);
    }
}
