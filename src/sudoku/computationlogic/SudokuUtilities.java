package sudoku.computationlogic;

import sudoku.problemdomain.SudokuGame;

public class SudokuUtilities {

    // Method for copying the content of an array (oldArray)
    // to a new array (newArray). Both arrays did exist before
    public static void copySudokuArrayValues(int[][] oldArray, int[][] newArray){
        for(int xIndex = 0; xIndex < SudokuGame.GRID_BOUNDARY; xIndex++){
            for(int yIndex = 0; yIndex < SudokuGame.GRID_BOUNDARY; yIndex++){
                newArray[xIndex][yIndex] = oldArray[xIndex][yIndex];
            }
        }
    }

    // Method for copying the content of an array (oldArray)
    // to a new array (newArray). the new array is generated in this method
    public static int[][] copyToNewArray(int[][] oldArray){
        int[][] newArray = new int[SudokuGame.GRID_BOUNDARY][SudokuGame.GRID_BOUNDARY];

        for(int xIndex = 0; xIndex < SudokuGame.GRID_BOUNDARY; xIndex++){
            for(int yIndex = 0; yIndex < SudokuGame.GRID_BOUNDARY; yIndex++){
                newArray[xIndex][yIndex] = oldArray[xIndex][yIndex];
            }
        }

        return newArray;
    }
}
