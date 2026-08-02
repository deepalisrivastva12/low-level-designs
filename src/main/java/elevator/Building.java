package elevator;

import java.util.ArrayList;
import java.util.List;

public class Building {
    List<Floors> floors=new ArrayList<>();
    public Building(int totalfloors,ExternalDispatcher externalDispatcher){
        for(int i=1;i<=totalfloors;i++){
            floors.add(new Floors(i,externalDispatcher));
        }
    }

    public Floors getFloor(int floor) {
        return floors.get(floor-1);
    }
}
