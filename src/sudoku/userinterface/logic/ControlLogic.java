package sudoku.userinterface.logic;

import sudoku.constants.GameState;
import sudoku.constants.Messages;
import sudoku.problemdomain.IStorage;
import sudoku.problemdomain.SudokuGame;
import sudoku.userinterface.IUserInterfaceContract;

import java.io.IOException;

// Control Logic class contracted by the IUserInterfaceContract.EventListener interface
// It handles the user input and the actions following certain inputs
public class ControlLogic implements IUserInterfaceContract.EventListener {

    private IStorage storage;
    private IUserInterfaceContract.View view;

    public ControlLogic(IStorage storage, IUserInterfaceContract.View view) {
        this.storage = storage;
        this.view = view;
    }


    @Override
    public void onSudokuInput(int x, int y, int input) {
        // Exception handling
        try{

            // Get game data from the source of truth i.e. the storage
            SudokuGame gameData = storage.getGameData();
            int[][] newGridState = gameData.getCopyOfGridState();

            // Insert input to the grid state
            newGridState[x][y] = input;

            // Create new sudoku game of the current grid state
            gameData = new SudokuGame(
                    GameLogic.checkForCompletion(newGridState),
                    newGridState
            );

            // Update the new game data in storage i.e. source of truth
            storage.updateGameData(gameData);

            // Update just the view just for the changed square
            // for performance
            view.updateSquare(x, y, input);

            //Check for game completion
            if(gameData.getGameState() == GameState.COMPLETE){
                view.showDialog(Messages.GAME_COMPLETE);
            }

        } catch (IOException e){
            // Error handling
            e.printStackTrace();
            view.showError(Messages.ERROR); // show error message
        }
    }

    @Override
    public void onDialogClick() {
        // Exception handling
        try {
            // First update storage
            storage.updateGameData(
                    GameLogic.getNewGame()
            );

            //update view
            view.updateBoard(storage.getGameData());
        } catch(IOException e){
            // Error handling
            e.printStackTrace();
            view.showError(Messages.ERROR); // show error message
        }
    }
}
