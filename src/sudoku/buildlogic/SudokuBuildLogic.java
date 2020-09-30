package sudoku.buildlogic;

import sudoku.computationlogic.GameLogic;
import sudoku.problemdomain.IStorage;
import sudoku.problemdomain.SudokuGame;
import sudoku.userinterface.IUserInterfaceContract;
import sudoku.userinterface.logic.ControlLogic;

import java.io.IOException;

// Class concerning building the game
public class SudokuBuildLogic {

    public static void build(IUserInterfaceContract.View userInterface) throws IOException {
        SudokuGame initialState;
        IStorage storage = new LocalStorageImpl();

        try{ // try to get game data from storage
            initialState = storage.getGameData();
        } catch (IOException e){ // if not possible generate new game data
            initialState = GameLogic.getNewGame();
            storage.updateGameData(initialState); // store new game data
        }

        // gluing everything together
        IUserInterfaceContract.EventListener uiLogic = new ControlLogic(storage,userInterface);
        userInterface.setListener(uiLogic);
        userInterface.updateBoard(initialState);
    }

}
