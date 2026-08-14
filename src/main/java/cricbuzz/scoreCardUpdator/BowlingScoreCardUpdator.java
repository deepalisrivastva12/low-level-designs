package cricbuzz.scoreCardUpdator;

import cricbuzz.ballDetails.BallDetails;
import cricbuzz.ballDetails.BallType;
import cricbuzz.ballDetails.RunType;

public class BowlingScoreCardUpdator implements ScoreCardUpdateObserver{
    @Override
    public void update(BallDetails ballDetails) {

        if (ballDetails.ballNumber==6 && ballDetails.ballType==BallType.NORMAL){
            ballDetails.bowler.bowlingScoreCard.totalOverPlayed++;
        }
        if(ballDetails.runType== RunType.ONE){
            ballDetails.bowler.bowlingScoreCard.totalRunsGiven+=1;
        }
        if(ballDetails.runType== RunType.TWO){
            ballDetails.bowler.bowlingScoreCard.totalRunsGiven+=2;

        }
        if(ballDetails.runType== RunType.FOUR){
            ballDetails.bowler.bowlingScoreCard.totalRunsGiven+=4;

        }
        if(ballDetails.runType== RunType.SIX){

            ballDetails.bowler.bowlingScoreCard.totalRunsGiven+=6;
        }
        if(ballDetails.wicket!=null){
            ballDetails.bowler.bowlingScoreCard.totalWicketTaken++;
        }
        if(ballDetails.ballType==BallType.NOBALL){
            ballDetails.bowler.bowlingScoreCard.noBall++;
        }
        if(ballDetails.ballType==BallType.WIDEBALL){
            ballDetails.bowler.bowlingScoreCard.wideBall++;
        }
    }
}
