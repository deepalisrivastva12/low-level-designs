package cricbuzz.teams;

import cricbuzz.teams.controllers.BattingPlayerController;
import cricbuzz.teams.controllers.BowlingPlayerController;

import java.util.Deque;
import java.util.List;
import java.util.Queue;

public class Team {
    String teamName;
    Queue<Player> players;
    List<Player> playersOnBench;
    BowlingPlayerController bowlingController;
    BattingPlayerController battingController;
    boolean isWinner;

    public Team(String teamName, Queue<Player> players, List<Player> playersOnBench, List<Player> bowlers) {
        this.teamName = teamName;
        this.players = players;
        this.playersOnBench = playersOnBench;
        bowlingController=new BowlingPlayerController(bowlers);
        battingController=new BattingPlayerController(players);
    }

    public String getTeamName() {
        return teamName;
    }

    int wicketsFallen = 0;

    public void recordWicket() {
        wicketsFallen++;
    }

    public boolean isAllOut() {
        return wicketsFallen >= 10; // adjust if your XI size differs
    }
    public void chooseNextBatsMan()throws  Exception{
        if(isAllOut()){
            throw new Exception("All wickests are out!!");
        }
        battingController.getNextPlayer();
    }
    public void chooseNextBowler(int maxOverCountPerBowler){
        bowlingController.getNextBowler(maxOverCountPerBowler);
    }
    public Player getCurrentBowler(){
       return bowlingController.getCurrentBowler();
    }
    public void printBattingScoreCard(){
        for (Player player:players){
            player.printBattingScoreCard();
        }
    }
    public void printBowlingScoreCard(){
        for (Player player:players){
            if(player.bowlingScoreCard.totalOverPlayed>0) {
                player.printBowlingScoreCard();
            }
        }
    }
    public Player getStriker(){
        return battingController.getStriker();
    }
    public Player getNonStriker(){
        return battingController.getNonStriker();
    }
    public void setStriker(Player player){
        battingController.setStriker(player);
    }
    public void setNonStriker(Player player){
        battingController.setNonStriker(player);
    }
    public int totalRuns(){
        int runs=0;
        for (Player player:players){
            runs+=player.battingScoreCard.totalRuns;
        }
        return runs;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public void setWinner(boolean winner) {
        isWinner = winner;
    }

    public int getTotalRuns() {
        int totalRun = 0;
        for (Player player : players) {

            totalRun += player.battingScoreCard.totalRuns;
        }
        return totalRun;
    }
}
