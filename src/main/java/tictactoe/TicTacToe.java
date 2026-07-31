package tictactoe;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;

public class TicTacToe {
    Deque<Player> players=new LinkedList<>();
    Board board;
    Player winner;
    void initializeGame() {
        Player player1 = new Player("Player1", new PlayingPieceX());
        Player player2 = new Player("Player2", new PlayingPieceO());
        players.add(player1);
        players.add(player2);
        board=new Board(3);
    }
    public GameStatus startGame(){
        boolean noWinner=true;
        while(noWinner){
            Player currentPlayer=players.removeFirst();
            board.printBoard();
            if(!board.hasFreeSpace()){
                noWinner=false;
                continue;
            }
            System.out.print(currentPlayer.name+" Enter the row and column: ");
            Scanner sc =new Scanner(System.in);
            String input=sc.nextLine();
            String[] values=input.split(",");
            int inputRow=Integer.valueOf(values[0]);
            int inputColumn=Integer.valueOf(values[1]);
            boolean validMove=board.addPiece(inputRow,inputColumn,currentPlayer.playingPiece);
            if(!validMove){
                System.out.println("Incorrect Position, Enter again");
                players.addFirst(currentPlayer);
                continue;
            }
            players.addLast(currentPlayer);
            boolean isWinner=checkWinner(inputRow,inputColumn,currentPlayer.playingPiece);
            if(isWinner){
                board.printBoard();
                winner=currentPlayer;
                return GameStatus.WIN;
            }
        }
        return GameStatus.DRAW;
    }

    private boolean checkWinner(int inputRow, int inputColumn, PlayingPiece playingPiece) {
        boolean rowMatch=true;
        boolean colMatch=true;
        boolean diagnolMatch=true;
        boolean reverseDiagonal=true;
        int size=board.size;
        int i = 0;
        int j = inputColumn;
        while(i<size){
            if((board.board[i][j])==null || board.board[i][j].piece!=playingPiece.piece){
                rowMatch=false;
                break;
            }
            i++;
        }
        i=inputRow;
        j=0;
        while(j<size){
            if((board.board[i][j])==null || board.board[i][j].piece!=playingPiece.piece){
                colMatch=false;
                break;
            }
            j++;
        }
        i=0;
        j=0;
        while(i<size && j<size){
            if((board.board[i][j])==null || board.board[i][j].piece!=playingPiece.piece){
                diagnolMatch=false;
                break;
            }
            i++;
            j++;
        }
        i=0;
        j=size-1;
        while(i<size && j>=0){
            if((board.board[i][j])==null || board.board[i][j].piece!=playingPiece.piece){
                reverseDiagonal=false;
                break;
            }
            j--;
            i++;
        }
        return rowMatch || colMatch || diagnolMatch || reverseDiagonal;
    }

}
