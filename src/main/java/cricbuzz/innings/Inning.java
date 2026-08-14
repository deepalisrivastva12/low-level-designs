package cricbuzz.innings;

import cricbuzz.ballDetails.OverDetails;
import cricbuzz.match.MatchType;
import cricbuzz.teams.Player;
import cricbuzz.teams.Team;

import java.util.ArrayList;
import java.util.List;

public class Inning {

    MatchType matchType;
    Team bowlingTeam;
    Team battingTeam;
    List<OverDetails> overs;

    public Inning(MatchType matchType, Team bowlingTeam, Team battingTeam) {
        this.matchType = matchType;
        this.bowlingTeam = bowlingTeam;
        this.battingTeam = battingTeam;
        this.overs = new ArrayList<>();
    }

    public void startGame(int runsToWin){
        try {
            battingTeam.chooseNextBatsMan();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        int noOfOvers= matchType.noOfOvers();
        for(int over=1;over<=noOfOvers;over++) {
            bowlingTeam.chooseNextBowler(matchType.oversPerBowler());
            OverDetails ongoingOver = new OverDetails(over, bowlingTeam.getCurrentBowler());
            overs.add(ongoingOver);
            try {
                boolean winner = ongoingOver.startOver(battingTeam, bowlingTeam, runsToWin);
                if (winner) {
                    break;
                }
            } catch (Exception e) {
               e.printStackTrace();
            }
            Player player = battingTeam.getStriker();
            battingTeam.setStriker(battingTeam.getNonStriker());
            battingTeam.setNonStriker(player);
        }
    }
    public int getTotalRuns(){
        return battingTeam.totalRuns();
    }
}
