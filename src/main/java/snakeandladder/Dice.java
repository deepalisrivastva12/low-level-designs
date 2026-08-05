package snakeandladder;

import java.util.concurrent.ThreadLocalRandom;

public class Dice {

    int totalDice;
    int min=1;
    int max=6;

    public Dice(int totalDice) {
        this.totalDice = totalDice;
    }
    public int rollDice(){
        int diceUsed=0;
        int totalNumber=0;
        while(diceUsed<totalDice){
            totalNumber += ThreadLocalRandom.current().nextInt(min,max);
            diceUsed++;
        }
        return totalNumber;
    }
}
