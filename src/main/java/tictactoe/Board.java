package tictactoe;

public class Board {
    int size;
    public PlayingPiece[][] board;

    public Board(int size) {
        this.size = size;
        board=new PlayingPiece[size][size];
    }

    boolean addPiece(int row, int col, PlayingPiece piece){
        if(board[row][col]==null) {
            board[row][col] = piece;
            return true;
        }
        return false;
    }
    boolean hasFreeSpace(){
        for(int i =0;i<size;i++){
            for(int j =0;j<size;j++){
                if(board[i][j]==null)
                    return true;
            }
        }
        return false;
    }
    void printBoard(){
        for(int i =0;i<size;i++){
            for(int j =0;j<size;j++){
                if(board[i][j]!=null) {
                    System.out.print(board[i][j].piece);
                }else {
                    System.out.print(" ");
                }
                System.out.print("|");
            }
            System.out.println();
        }
    }
}
