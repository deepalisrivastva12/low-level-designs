package cricbuzz.teams.controllers;

import cricbuzz.teams.Player;

import java.util.*;

public class BowlingPlayerController {
    Deque<Player> bowlers;
    Player currentBowler;
    Map<Player,Integer> bowlersVsOverCount;

    public BowlingPlayerController(List<Player> bowlers) {
        setBowler(bowlers);
    }
    public void setBowler(List<Player> bowlers){
        this.bowlers=new LinkedList<>();
        bowlersVsOverCount=new HashMap<>();
        for(Player player:bowlers){
            this.bowlers.addLast(player);
            this.bowlersVsOverCount.put(player,0);
        }
    }
    public void getNextBowler(int maxOverCountPerBowler){
        Player bowler=bowlers.poll();
        if(bowlersVsOverCount.get(bowler)+1 == maxOverCountPerBowler){
            currentBowler=bowler;
        }else{
            currentBowler=bowler;
            bowlers.addLast(bowler);
            bowlersVsOverCount.put(bowler,bowlersVsOverCount.get(bowler)+1);
        }
    }

    public Player getCurrentBowler() {
        return currentBowler;
    }


}
