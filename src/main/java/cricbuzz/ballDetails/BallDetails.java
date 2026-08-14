package cricbuzz.ballDetails;

import cricbuzz.scoreCardUpdator.BattingScoreCardUpdator;
import cricbuzz.scoreCardUpdator.BowlingScoreCardUpdator;
import cricbuzz.scoreCardUpdator.ScoreCardUpdateObserver;
import cricbuzz.teams.Player;
import cricbuzz.teams.PlayerType;
import cricbuzz.teams.Team;
import cricbuzz.teams.scoreCard.BattingScoreCard;
import cricbuzz.wicket.Wicket;
import cricbuzz.wicket.WicketType;

import java.util.ArrayList;
import java.util.List;

public class BallDetails {
    public int ballNumber;
    public BallType ballType;
    public Player battingPlayer;
    public Player bowler;
    public RunType runType;
    public Wicket wicket;
    List<ScoreCardUpdateObserver> scoreCardUpdateObservers=new ArrayList<>();

    public BallDetails(int ballNumber) {
        this.ballNumber = ballNumber;
        this.scoreCardUpdateObservers.add(new BattingScoreCardUpdator());
        this.scoreCardUpdateObservers.add(new BowlingScoreCardUpdator());
    }

    public void startBallDelivery(Team battingTeam, Team bowlingTeam, OverDetails over){
        this.bowler=over.bowler;
        battingPlayer=battingTeam.getStriker();

        ballType =BallType.NORMAL;
        if(isWicketTaken()){
            runType=RunType.ZERO;
           wicket=new Wicket(WicketType.BOLD,bowlingTeam.getCurrentBowler(),over,this);
           battingTeam.setStriker(null);
        }else {
            runType=getRunType();
            if(runType==RunType.ONE || runType==RunType.THREE){
                Player player=battingTeam.getStriker();
                battingTeam.setStriker(battingTeam.getNonStriker());
                battingTeam.setNonStriker(player);
            }
        }
        notifyUpdater(this);
    }

    private void notifyUpdater(BallDetails ballDetails) {
        for(ScoreCardUpdateObserver observer:scoreCardUpdateObservers){
            observer.update(ballDetails);
        }
    }

    private RunType getRunType() {
        double x=Math.random();
        if(x<=0.2){
            return RunType.ONE;
        }else if(x>=0.3 && x<=0.5){
            return RunType.TWO;
        }else if(x>=0.6 && x<=0.8) {
            return RunType.FOUR;
        }else
            return RunType.SIX;

    }

    private boolean isWicketTaken() {
        if(Math.random()<0.2){
            return true;
        }
        return false;
    }


}
