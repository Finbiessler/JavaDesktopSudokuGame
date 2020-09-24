package sudoku.problemdomain;

import sudoku.constants.GameState;

import java.io.Serializable;


// Class representing the sudoku game itself
public class SudokuGame implements Serializable { // implements Serializable to make saving easier
    private final GameState gameState;  // State of the game complete/active/new
    private final int[][] gridState;    // state of the grid i.e. which numbers are where

    public static final int GRID_BOUNDARY = 9; //9X9 is a sudoku board

    //Constructor
    public SudokuGame(GameState gameState, int[][] gridState) {
        this.gameState = gameState;
        this.gridState = gridState;
    }

    //Getters
    public GameState getGameState() {
        return gameState;
    }

    public int[][] getCopyOfGridState() {
        return SudokuUtilities.copyToNewArray(gridState);
    }

}
