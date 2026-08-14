package cricbuzz.scoreCardUpdator;

import cricbuzz.ballDetails.BallDetails;

public interface ScoreCardUpdateObserver {
    public void update(BallDetails bAlLDetails);
}
