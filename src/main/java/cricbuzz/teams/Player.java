package cricbuzz.teams;

import cricbuzz.teams.scoreCard.BattingScoreCard;
import cricbuzz.teams.scoreCard.BowlingScoreCard;

public class Player {
    public Person person;
    public BowlingScoreCard bowlingScoreCard;
    public BattingScoreCard battingScoreCard;
    public PlayerType type;

    public Player(Person person, PlayerType type) {
        this.person = person;
        this.type = type;
        this.bowlingScoreCard=new BowlingScoreCard();
        this.battingScoreCard=new BattingScoreCard();
    }
    public void printBattingScoreCard(){

        System.out.println("PlayerName: " + person.name + " -- totalRuns: " + battingScoreCard.totalRuns
                + " -- totalBallsPlayed: " + battingScoreCard.totalBallsPlayed + " -- 4s: " + battingScoreCard.totalFours
                + " -- 6s: " + battingScoreCard.totalSixs + " -- outby: " +   ((battingScoreCard.wicket != null) ? battingScoreCard.wicket.takenBy.person.name : "notout"));
    }

    public void printBowlingScoreCard(){
        System.out.println("PlayerName: " + person.name + " -- totalOversThrown: " + bowlingScoreCard.totalOverPlayed
                + " -- totalRunsGiven: " + bowlingScoreCard.totalRunsGiven + " -- WicketsTaken: " + bowlingScoreCard.totalWicketTaken);
    }
}
