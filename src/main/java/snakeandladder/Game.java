package snakeandladder;

import java.util.Deque;
import java.util.LinkedList;

public class Game {
    Board board;
    Dice dice;
    Deque<Player> players=new LinkedList<>();
    Player winner;

    public Game(){
        initializeGame();
    }
    public void initializeGame(){
        board=new Board(5,3,4);
        dice=new Dice(1);
        winner=null;
        addPlayers();
    }
    public void addPlayers(){
        Player player1=new Player("Deepali",0);
        Player player2=new Player("Vaishnavi",0);
        players.add(player1);
        players.add(player2);

    }

    public void startGame(){
        while(winner==null){
            Player playingPlayer =playerTurn();
            System.out.println("Player name: "+playingPlayer.name+" Current Position: "+playingPlayer.currentPosition);
            int number = dice.rollDice();
            int playerNewPosition = playingPlayer.currentPosition+number;
            playerNewPosition=jumpCheck(playerNewPosition);
            playingPlayer.currentPosition=playerNewPosition;
            System.out.println("Player name: "+playingPlayer.name+" New Position: "+playingPlayer.currentPosition);

            if(playerNewPosition>=board.cells.length*board.cells.length-1){
                winner=playingPlayer;
            }
        }
        System.out.println("Winner is: "+winner.name);
    }
    public Player playerTurn(){
        Player player=players.removeFirst();
        players.addLast(player);
        return player;
    }
    public int jumpCheck(int playerPosition){
        if(playerPosition>board.cells.length*board.cells.length-1){
            return playerPosition;
        }
        Cell cell= board.getCell(playerPosition);
        if(cell.jump!=null && cell.jump.startPoint==playerPosition){
            String jumpBy=cell.jump.startPoint<cell.jump.endPoint ? "Ladder" :"Snake";
            System.out.println("There is a "+jumpBy+" for "+cell.jump.endPoint);
            return jumpCheck(cell.jump.endPoint);
        }
        return playerPosition;
    }
}
