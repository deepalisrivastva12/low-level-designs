package snakeandladder;

import java.util.concurrent.ThreadLocalRandom;

public class Board {

    Cell cells[][];

    public Board(int size,int totalSnake,int totalLadder) {
        initializeCells(size);
        addSnakeAndLadder(totalLadder,totalSnake);
    }

    private void initializeCells(int size) {
        cells=new Cell[size][size];
        for(int i =0;i<size;i++){
            for(int j=0;j<size;j++){
                Cell newCell=new Cell();
                cells[i][j]=newCell;
            }
        }
    }
    public void addSnakeAndLadder(int totalLadder ,int totalSnake){

        while (totalLadder>0){
            int start= ThreadLocalRandom.current().nextInt(1,cells.length*cells.length-1);
            int end=ThreadLocalRandom.current().nextInt(1,cells.length*cells.length-1);
            if(start>=end){
                continue;
            }
            Jump jump=new Jump();
            jump.setStartPoint(start);
            jump.setEndPoint(end);

            Cell cell=getCell(start);
            cell.setJump(jump);

            totalLadder--;
        }

        while (totalSnake>0){
            int start= ThreadLocalRandom.current().nextInt(1,cells.length*cells.length-1);
            int end=ThreadLocalRandom.current().nextInt(1,cells.length*cells.length-1);
            if(end>=start){
                continue;
            }
            Jump jump=new Jump();
            jump.setStartPoint(start);
            jump.setEndPoint(end);

            Cell cell=getCell(start);
            cell.setJump(jump);

            totalSnake--;

        }
    }
    public Cell getCell(int position){
        int row = position/cells.length;
        int col=position%cells.length;
        return cells[row][col];
    }
}
