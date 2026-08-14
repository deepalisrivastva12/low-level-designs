package cricbuzz.wicket;

import cricbuzz.ballDetails.BallDetails;
import cricbuzz.ballDetails.OverDetails;
import cricbuzz.teams.Player;

public class Wicket {
    public WicketType type;
    public Player takenBy;
    public OverDetails overDetails;
    public BallDetails ballDetails;


    public Wicket(WicketType type, Player takenBy, OverDetails overDetails, BallDetails ballDetails) {
        this.type = type;
        this.takenBy = takenBy;
        this.overDetails = overDetails;
        this.ballDetails = ballDetails;
    }
}
