package sudoku.computationlogic;

import sudoku.constants.GameState;
import sudoku.constants.Rows;
import sudoku.problemdomain.SudokuGame;

import java.util.*;

// Class regarding for checking the sudoku rules
// and other game logic related functionalities
public class GameLogic {

    // returns a new game which can be solved but is incomplete
    public static SudokuGame getNewGame(){
        return new SudokuGame(
                GameState.NEW,
                GameGenerator.getNewGameGrid()
        );
    }


    // Checks whether the game is complete i.e.
    // game is valid and all tiles are filled
    public static GameState checkForCompletion(int[][] grid){
        if(sudokuIsInvalid(grid)) return GameState.ACTIVE;
        if(tilesAreNotFilled(grid)) return GameState.ACTIVE;
        return GameState.COMPLETE;
    }

    // Checks whether the sudoku is invalid
    // by checking if the rows, columns or squares of a game are invalid
    public static boolean sudokuIsInvalid(int[][] grid) {
        if(rowsAreInvalid(grid)) return true;
        if(columnsAreInvalid(grid)) return true;
        return squaresAreInvalid(grid);
    }

    // Checks wheter there is any invalid row in the game
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

    // Checks wheter there is any invalid column in the game
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

    // Checks whether there is a invalid square in the game
    // By checking each row of squares (top, middle,bottom) independently
    private static boolean squaresAreInvalid(int[][] grid) {

        if(rowOfSquaresIsInvalid(Rows.TOP, grid)) return true;

        if(rowOfSquaresIsInvalid(Rows.MIDDLE, grid)) return true;

        return rowOfSquaresIsInvalid(Rows.BOTTOM, grid);
    }

    // Checks whether one row of square invalid
    // by checking each square in the certain row
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

    // error: reverse y and x
    // Checks whether a square is valid
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

    //error: other impl
    // Checks whether a collection has duplicates
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

    // Checks whether all tiles of the game are filled
    public static boolean tilesAreNotFilled(int[][] grid) {
        for(int xIndex = 0; xIndex < SudokuGame.GRID_BOUNDARY; xIndex++){
            for(int yIndex = 0; yIndex < SudokuGame.GRID_BOUNDARY; yIndex++){
                if(grid[xIndex][yIndex] == 0) return true;
            }
        }
        return false;
    }
}
