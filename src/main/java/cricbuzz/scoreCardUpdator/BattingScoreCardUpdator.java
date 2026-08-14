package cricbuzz.scoreCardUpdator;

import cricbuzz.ballDetails.BallDetails;
import cricbuzz.ballDetails.RunType;

public class BattingScoreCardUpdator implements ScoreCardUpdateObserver{
    @Override
    public void update(BallDetails ballDetails) {
        int run=0;
        if(ballDetails.runType== RunType.ONE){
            run=1;
        }
        if(ballDetails.runType== RunType.TWO){
            run=2;
        }
        if(ballDetails.runType== RunType.FOUR){
            run=4;
            ballDetails.battingPlayer.battingScoreCard.totalFours++;
        }
        if(ballDetails.runType== RunType.SIX){
            run=6;
            ballDetails.battingPlayer.battingScoreCard.totalSixs++;
        }
        ballDetails.battingPlayer.battingScoreCard.totalRuns+=run;
        ballDetails.battingPlayer.battingScoreCard.totalBallsPlayed++;
        if (ballDetails.wicket!=null){
            ballDetails.battingPlayer.battingScoreCard.wicket=ballDetails.wicket;
        }
    }
}
