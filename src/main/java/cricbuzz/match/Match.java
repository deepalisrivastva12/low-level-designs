package cricbuzz.match;

import cricbuzz.innings.Inning;
import cricbuzz.teams.Team;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

public class Match {
    Team teamA;
    Team teamB;
    MatchType type;
    Date matchDate;
    String venue;
    Inning[] innings;
    Team tossWinner;

    public Match(Team teamA, Team teamB, MatchType type, Date matchDate, String venue) {
        this.teamA = teamA;
        this.teamB = teamB;
        this.type = type;
        this.matchDate = matchDate;
        this.venue = venue;
        this.innings = new Inning[2];
    }

    public void startMatch(){
        tossWinner = toss(teamA, teamB);
        for (int inning=1;inning<=2;inning++){
            Inning inningDetails;
            Team battingTeam;
            Team bowlingTeam;

            //assuming here that tossWinner batFirst
            boolean isChasing = false;
            if(inning==1){
                battingTeam=tossWinner;
                bowlingTeam=tossWinner.getTeamName().equals(teamA.getTeamName()) ? teamB:teamA;
                inningDetails =new Inning(type,bowlingTeam,battingTeam);
                inningDetails.startGame(-1);
            }else {
                bowlingTeam=tossWinner;
                battingTeam=tossWinner.getTeamName().equals(teamA.getTeamName()) ? teamB:teamA;
                inningDetails =new Inning(type,bowlingTeam,battingTeam);
                inningDetails.startGame(innings[0].getTotalRuns());
                if(bowlingTeam.getTotalRuns() > battingTeam.getTotalRuns()){
                    bowlingTeam.setWinner(true);
                }
            }
            innings[inning-1] = inningDetails;

            //print inning details
            System.out.println();
            System.out.println("INNING " + inning + " -- total Run: " + battingTeam.getTotalRuns());
            System.out.println("---Batting ScoreCard : " + battingTeam.getTeamName() + "---");

            battingTeam.printBattingScoreCard();

            System.out.println();
            System.out.println("---Bowling ScoreCard : " + bowlingTeam.getTeamName() + "---");
            bowlingTeam.printBowlingScoreCard();

        }
        System.out.println();
        if(teamA.isWinner()){
            System.out.println("---WINNER---" + teamA.getTeamName());

        }else if(teamB.isWinner()){
            System.out.println("---WINNER---" + teamB.getTeamName());

        }else {
            System.out.println("---MATCH TIED---");
        }
    }
    private Team toss(Team teamA, Team teamB){
        //random function return value between 0 and 1
        if(Math.random() < 0.5) {
            return teamA;
        } else {
            return teamB;
        }
    }

}
