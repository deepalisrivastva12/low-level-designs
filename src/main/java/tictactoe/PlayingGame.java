package tictactoe;

public class PlayingGame {
    public static void main(String[] args){
        System.out.println("\n Tic Tac Toe Game \n");
        TicTacToe game = new TicTacToe();
        game.initializeGame();
        GameStatus status=game.startGame();
        switch (status){
            case WIN:
                System.out.println(game.winner.name+" is the winner !!");
                break;
            case DRAW:
                System.out.println("It's a Draw");
                break;
            default:
                System.out.println("Game end!!");
                break;
        }
    }
}
