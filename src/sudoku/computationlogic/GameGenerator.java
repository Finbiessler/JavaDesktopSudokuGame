package sudoku.computationlogic;

import sudoku.problemdomain.Coordinates;
import sudoku.problemdomain.SudokuGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameGenerator {
    public static int[][] getNewGameGrid(){
        return unsolveGame(getSolvedGame());
    }

    // Takes a solved game and returns a unsolved game
    // the unsolved game which is returned is still solvable
    private static int[][] unsolveGame(int[][] solvedGame) {

        // Generate random seed for random numbers
        Random random = new Random(System.currentTimeMillis());

        // Init needed variables
        boolean solveable = false;
        int[][] solveableArray = new int[SudokuGame.GRID_BOUNDARY][SudokuGame.GRID_BOUNDARY];

        // Brute force alg to generate a unsolved sudoku game from a solved game
        // the generated unsolved game is still solvable
        while (!solveable){
            SudokuUtilities.copySudokuArrayValues(solvedGame, solveableArray);

            int index = 0;

            // Delete 40 random values from the sudoku game
            while(index < 40){

                int xCoordinate = random.nextInt(SudokuGame.GRID_BOUNDARY);
                int yCoordinate = random.nextInt(SudokuGame.GRID_BOUNDARY);

                if(solveableArray[xCoordinate][yCoordinate] != 0){
                    solveableArray[xCoordinate][yCoordinate] = 0;
                    index++;
                }
            }

            // Check wheteher the uncomplete game is still solvable
            int[][] toBeSolved = new int[SudokuGame.GRID_BOUNDARY][SudokuGame.GRID_BOUNDARY];
            SudokuUtilities.copySudokuArrayValues(solveableArray, toBeSolved);

            solveable = SudokuSolver.puzzleIsSolvable(toBeSolved);

            //return solvable array if solvable == true
            // otherwise try whole process again
        }

        return solveableArray;
    }


    // Function that generates a solved sudoku game
    public static int[][] getSolvedGame(){

        // Generate random seed for random numbers
        Random random = new Random(System.currentTimeMillis());

        // init needed variables
        int[][] newGrid = new int[SudokuGame.GRID_BOUNDARY][SudokuGame.GRID_BOUNDARY];

        // iterate through all 9 possible values for one tile
        for(int value = 1; value <= SudokuGame.GRID_BOUNDARY; value++){
            int allocations = 0;
            int interrupt = 0;

            List<Coordinates> allocTracker = new ArrayList<>();

            int attempts = 0;


            while(allocations < SudokuGame.GRID_BOUNDARY){

                // when to many wrong numbers where inserted in a row
                // backtrack and delete all these values from the game
                if(interrupt > 200){
                    allocTracker.forEach(coord -> {
                        newGrid[coord.getX()][coord.getY()] = 0; // delete values
                    });

                    //reset trackers and increment the value of attempts
                    interrupt = 0;
                    allocations = 0;
                    allocTracker.clear();
                    attempts++;

                    // when you attempted this process to often (>500 times)
                    // clear the whole game and start from the beginning
                    if(attempts > 500){
                        clearArray(newGrid);
                        //restarting
                        attempts = 0;
                        value = 1;
                    }
                }

                // pick a random tile of the sudoku game
                int xCoordinate = random.nextInt(SudokuGame.GRID_BOUNDARY);
                int yCoordinate = random.nextInt(SudokuGame.GRID_BOUNDARY);

                // when this tile is empty set it to the current value
                if(newGrid[xCoordinate][yCoordinate] == 0){
                    newGrid[xCoordinate][yCoordinate] = value;

                    // when this results in an invalid sudoku delete the
                    // content of the give tile
                    // also increment interrupt counter
                    if(GameLogic.sudokuIsInvalid(newGrid)){
                        newGrid[xCoordinate][yCoordinate] = 0;
                        interrupt++;
                    } else {
                        // when the input results in a valid sudoku game
                        // add the tile to the allocation tracker and
                        // increment the allocations tracker
                        allocTracker.add(new Coordinates(xCoordinate, yCoordinate));
                        allocations++;
                    }
                }
            }
        }

        // solvable sudoku game is returned
        return newGrid;
    }

    // helper function for clearing the whole sudoku game
    private static void clearArray(int[][] newGrid){
        for(int xIndex = 0; xIndex < SudokuGame.GRID_BOUNDARY; xIndex++){
            for(int yIndex = 0; yIndex < SudokuGame.GRID_BOUNDARY; yIndex++){
                newGrid[xIndex][yIndex] = 0;
            }
        }
    }
}
