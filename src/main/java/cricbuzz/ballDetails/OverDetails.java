package cricbuzz.ballDetails;

import cricbuzz.teams.Player;
import cricbuzz.teams.Team;

import java.util.ArrayList;
import java.util.List;

public class OverDetails {
    List<BallDetails> balls;
    Player bowler;
    int overNumber;
    int extraBall;

    public OverDetails(int overNumber,Player bowledBy) {
        this.overNumber = overNumber;
        balls=new ArrayList<>();
        this.bowler= bowledBy;
    }
    public boolean startOver(Team battingTeam, Team bowlingTeam,int runsToWins) throws  Exception{
        int ballCount=1;
        while(ballCount<=6){
            BallDetails ball=new BallDetails(ballCount);
            ball.startBallDelivery(battingTeam,bowlingTeam,this);
            if(ball.ballType==BallType.NORMAL){
                balls.add(ball);
                ballCount++;
                if(ball.wicket!=null){
                    battingTeam.recordWicket();
                    if (battingTeam.isAllOut()) {
                        return true;   // innings ends — all out
                    }
                    battingTeam.chooseNextBatsMan();
                }
                if(runsToWins!=-1 && battingTeam.totalRuns()>=runsToWins){
                    battingTeam.setWinner(true);
                    return true;
                }
            }else {
                extraBall++;
            }
        }
        return false;
    }
}
