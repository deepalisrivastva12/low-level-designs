package cricbuzz.teams.controllers;

import cricbuzz.teams.Player;
import cricbuzz.teams.PlayerType;

import java.util.*;

public class BattingPlayerController {
    Queue<Player> yetToPlay;
    Player striker;
    Player nonStriker;

    public BattingPlayerController(Queue<Player> yetToPlay) {
        this.yetToPlay=new LinkedList<>();
        this.yetToPlay.addAll(yetToPlay);
    }
    public void getNextPlayer() throws Exception{
        if(yetToPlay==null){
            throw new Exception("All players have been played!!");
        }
        if(striker==null){
            striker=yetToPlay.poll();
        }
        if (nonStriker==null){
            nonStriker=yetToPlay.poll();
        }
    }
    public Player getStriker() {
        return striker;
    }

    public void setStriker(Player striker) {
        this.striker = striker;
    }

    public Player getNonStriker() {
        return nonStriker;
    }

    public void setNonStriker(Player nonStriker) {
        this.nonStriker = nonStriker;
    }
}
