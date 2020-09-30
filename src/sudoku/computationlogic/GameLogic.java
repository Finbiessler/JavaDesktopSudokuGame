package sudoku.computationlogic;

import sudoku.constants.GameState;
import sudoku.constants.Rows;
import sudoku.problemdomain.SudokuGame;

import java.util.*;

public class GameLogic {
    public static SudokuGame getNewGame(){
        return new SudokuGame(
                GameState.NEW,
                GameGenerator.getNewGameGrid()
        );
    }

    public static GameState checkForCompletion(int[][] grid){
        if(sudokuIsInvalid(grid)) return GameState.ACTIVE;
        if(tilesAreNotFilled(grid)) return GameState.ACTIVE;
        return GameState.COMPLETE;
    }

    public static boolean sudokuIsInvalid(int[][] grid) {
        if(rowsAreInvalid(grid)) return true;
        if(columnsAreInvalid(grid)) return true;
        return squaresAreInvalid(grid);
    }

    private static boolean rowsAreInvalid(int[][] grid) {
        for(int yIndex = 0; yIndex < SudokuGame.GRID_BOUNDARY; yIndex++){
            List<Integer> row = new ArrayList<>();

            for(int xIndex = 0; xIndex < SudokuGame.GRID_BOUNDARY; xIndex++){
                row.add(grid[xIndex][yIndex]);
            }

            if(collectionHasRepeats(row)) return true;
        }
        return false;
    }


    private static boolean columnsAreInvalid(int[][] grid) {
        for(int xIndex = 0; xIndex < SudokuGame.GRID_BOUNDARY; xIndex++){
            List<Integer> column = new ArrayList<>();

            for(int yIndex = 0; yIndex < SudokuGame.GRID_BOUNDARY; yIndex++){
                column.add(grid[xIndex][yIndex]);
            }

            if(collectionHasRepeats(column)) return true;
        }
        return false;
    }

    private static boolean squaresAreInvalid(int[][] grid) {

        if(rowOfSquaresIsInvalid(Rows.TOP, grid)) return true;

        if(rowOfSquaresIsInvalid(Rows.TOP, grid)) return true;

        return rowOfSquaresIsInvalid(Rows.TOP, grid);
    }

    private static boolean rowOfSquaresIsInvalid(Rows value, int[][] grid) {
        switch(value){
            case TOP:
                if(squareIsInvalid(0, 0, grid)) return true;
                if(squareIsInvalid(0, 3, grid)) return true;
                return squareIsInvalid(0, 6, grid);
            case MIDDLE:
                if(squareIsInvalid(3, 0, grid)) return true;
                if(squareIsInvalid(3, 3, grid)) return true;
                return squareIsInvalid(3, 6, grid);
            case BOTTOM:
                if(squareIsInvalid(6, 0, grid)) return true;
                if(squareIsInvalid(6, 3, grid)) return true;
                return squareIsInvalid(6, 6, grid);
            default:
                return false;
        }
    }

    //reverse y and x
    private static boolean squareIsInvalid(int yIndex, int xIndex, int[][] grid) {
        int xIndexEnd = xIndex + 3;
        int yIndexEnd = yIndex + 3;

        List<Integer> square = new ArrayList<>();

        while(yIndex < yIndexEnd){
            while(xIndex < xIndexEnd){
                square.add(grid[xIndex][yIndex]);
                xIndex++;
            }
            xIndex -= 3;
            yIndex++;
        }
        return collectionHasRepeats(square);
    }

    public static boolean collectionHasRepeats(List<Integer> square) {
        Set<Integer> duplicates = new LinkedHashSet<>();
        Set<Integer> uniques = new HashSet<>();

        // See for errors
        for(Integer tile : square){
            if(!uniques.add(tile)){
                if(tile != 0) duplicates.add(tile);
            }
        }

        return duplicates.size() != 0;
    }

    public static boolean tilesAreNotFilled(int[][] grid) {
        for(int xIndex = 0; xIndex < SudokuGame.GRID_BOUNDARY; xIndex++){
            for(int yIndex = 0; yIndex < SudokuGame.GRID_BOUNDARY; yIndex++){
                if(grid[xIndex][yIndex] == 0) return true;
            }
        }
        return false;
    }
}
