package sudoku.persistence;

import sudoku.problemdomain.IStorage;
import sudoku.problemdomain.SudokuGame;

import java.io.IOException;

public class LocalStorageImpl implements IStorage {
    @Override
    public void updateGameData(SudokuGame game) throws IOException {

    }

    @Override
    public SudokuGame getGameData() throws IOException {
        return null;
    }
}
