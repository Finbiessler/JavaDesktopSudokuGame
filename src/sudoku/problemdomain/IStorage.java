package sudoku.problemdomain;

import java.io.IOException;


//Interface for retrieving and updating the game data
public interface IStorage {

    //Method for updating the Sudoku game
    void updateGameData(SudokuGame game) throws IOException;

    //Method for retrieving the game data
    SudokuGame getGameData() throws  IOException;
}
