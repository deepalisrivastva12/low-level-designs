package elevator;

public class InternalButton {
    private final ElevatorController controller;
    private InternalDispatcher internalDispatcher=new InternalDispatcher();
    public InternalButton(ElevatorController controller) {
        this.controller = controller;
    }
    public void pressButton(int dest){
        internalDispatcher.submitInternalRequest(dest,controller);
    }
}
