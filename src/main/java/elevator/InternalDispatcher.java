package elevator;

public class InternalDispatcher {
    public void submitInternalRequest(int dest,ElevatorController controller)
    {
        controller.submitRequest(dest);
    }
}
