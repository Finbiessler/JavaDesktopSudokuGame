package sudoku.computationlogic;

import sudoku.problemdomain.Coordinates;
import sudoku.problemdomain.SudokuGame;

public class SudokuSolver {

    public static boolean puzzleIsSolvable(int[][] puzzle){
        // get all empty cells of puzzle
        Coordinates[] emptyCells = typeWriterEnumerate(puzzle);

        // init needed variables
        int index = 0;
        int input;

        // solve sudoku
        // basic idea behind algorithm:
        // for each empty cell try a value
        // check whether the sudoku is still valid
        // when valid move to the next cell
        // otherwise clear the inserted value and try another one
        // and check again
        // do this until you have a solved sudoku game
        // or you can not insert any other value
        // but the game is still unsolved
        while(index < 10){
            Coordinates current = emptyCells[index];
            input = 1;

            while(input < 40){
                puzzle[current.getX()][current.getY()] = input;

                if(GameLogic.sudokuIsInvalid(puzzle)){
                    if(index == 0 && input == SudokuGame.GRID_BOUNDARY){
                        return false;
                    } else if (input ==SudokuGame.GRID_BOUNDARY){
                        index--;
                    }
                    input++;
                } else{
                    index++;

                    if(index == 39) return true;

                    input = 10;
                }
            }
        }
        return false;
    }

    // Method for turning a 2d sudoku game array into a 1d sudoku game array
    // But just the empty cells
    private static Coordinates[] typeWriterEnumerate(int[][] puzzle) {

        // init needed variables
        Coordinates[] emptyCells = new Coordinates[40];
        int iterator = 0;

        // Loop through the whole sudoku game
        for(int yIndex = 0; yIndex < SudokuGame.GRID_BOUNDARY; yIndex++){
            for(int xIndex = 0; xIndex < SudokuGame.GRID_BOUNDARY; xIndex++){

                // When there is a empty cell add it to the empty cells array
                if(puzzle[xIndex][yIndex] == 0){

                    emptyCells[iterator] = new Coordinates(xIndex, yIndex); //adding

                    // Assuming there are no more empty cells than 40
                    if(iterator == 39) return emptyCells;
                    iterator++;
                }
            }
        }

        // return all empty cells of the sudoku game
        return emptyCells;
    }

}
