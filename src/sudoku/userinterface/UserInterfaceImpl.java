package sudoku.userinterface;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sudoku.constants.GameState;
import sudoku.problemdomain.Coordinates;
import sudoku.problemdomain.SudokuGame;

import java.awt.*;
import java.util.HashMap;

public class UserInterfaceImpl implements IUserInterfaceContract.View,
        EventHandler<KeyEvent> {

    // Stage ad root group
    private final Stage stage;
    private final Group root;

    // Hashmap for connecting certain coordinates
    // to the respective SudokuTextField
    private HashMap<Coordinates, SudokuTextField> textFieldCoordinates;

    //EventListener
    private IUserInterfaceContract.EventListener listener;

    //Constants for the UserInterface

    //SIZES
    public static final double WINDOW_Y = 732;
    public static final double WINDOW_X = 668;
    public static final double BOARD_PADDING = 50;
    public static final double BOARD_X_AND_Y = 576;

    //COLORS & TITLE
    private static final Color WINDOW_BACKGROUND_COLOR = Color.rgb(0, 150, 136);
    private static final Color BOARD_BACKGROUND_COLOR = Color.rgb(224, 242,  241);
    public static final String SUDOKU_TITLE = "Sudoku";


    //Constructor
    //  inits member variables
    //  calls helper function initializing the userinterface
    public UserInterfaceImpl(Stage stage, Group root) {
        this.stage = stage;
        this.root = new Group();
        this.textFieldCoordinates = new HashMap<>();
        initializeUserInterface();
    }

    // User interface initialization helper function
    // calls respective initializer function for all UI elements
    // and shows the elements on the stage
    private void initializeUserInterface() {
        drawBackground(root);
        drawTitle(root);
        drawSudokuboard(root);
        drawTextFields(root);
        drawGridlLines(root);
        stage.show();   //putting all elements to show
    }

    // Initializer function for the grid lines
    private void drawGridlLines(Group root) {
        int xAndY = 114;
        int index = 0;

        // Loop through all 8 line pairs (horizontal and vertical)
        while(index < 8){
            int thickness;
            // Displaying the grid lines
            // separating the nine sudoku squares
            // a bit thicker than the others
            if(index == 2 || index == 5){
                thickness = 3;
            } else{
                thickness = 2;
            }

            // Create the respective (vertical-) line
            Rectangle verticalLine = getLine(
              xAndY + 64 * index,
              BOARD_PADDING,
              BOARD_X_AND_Y,
              thickness
            );

            // Create the respective (horizontal-) line
            Rectangle horizontalLine = getLine(
                    BOARD_PADDING,
                    xAndY + 64 * index,
                    thickness,
                    (int) BOARD_X_AND_Y
            );

            //adding the lines to the root group
            root.getChildren().addAll(verticalLine, horizontalLine);

            //moving one line pair further
            index ++;
        }
    }

    // Helper function which creates the rectangle line objects for
    // the drawGridLines() function
    private Rectangle getLine(double x, double y, double height, int width){
        Rectangle line = new Rectangle();
        line.setX(x);
        line.setY(y);
        line.setHeight(height);
        line.setWidth(width);

        line.setFill(Color.BLACK);

        return line;
    }

    // Initializer function for the text fields
    private void drawTextFields(Group root) {
        // First x and y (starting coordinate)
        final int xOrigin = 50;
        final int yOrigin = 50;

        // Change of x and y per tile
        final int xAndYDelta = 64;

        // looping through all tiles in a nested for loop
        // Think of the first loop looping through all horizontal tile positions
        // and the second loop through all vertical tile positions
        for(int xIndex = 0; xIndex < 9; xIndex++){
            for(int yIndex = 0; yIndex < 9; yIndex++){

                // Set respective x and y coordinates
                int x = xOrigin + xIndex * xAndYDelta;
                int y = yOrigin + yIndex * xAndYDelta;

                // Create new tile
                SudokuTextField tile = new SudokuTextField(xIndex, yIndex);

                // Call helper function for styling the tile
                styleSudokuTile(tile, x, y);

                // Handling the actual user input
                // by passing the setOnKeyPressed() method of tile
                // this (EventHandler since UserInterfaceImpl implements EventHandler)
                tile.setOnKeyPressed(this);

                //put the tile in the hashmap for connecting tile and respective coordinate
                textFieldCoordinates.put(new Coordinates(xIndex, yIndex), tile);

                // add tile to root group
                root.getChildren().add(tile);
            }
        }
    }

    // Helper function for styling the sudoku tiles
    private void styleSudokuTile(SudokuTextField tile, double x, double y){
        Font numberFont = new Font(32);

        // Set font and alignment
        tile.setFont(numberFont);
        tile.setAlignment(Pos.CENTER);

        // Set position and size
        tile.setLayoutX(x);
        tile.setLayoutY(y);
        tile.setPrefHeight(64);
        tile.setPrefWidth(64);

        // Set background to be empty such that
        // the sudoku board handles the background
        tile.setBackground(Background.EMPTY);
    }

    // Initializer function for the sudoku board
    private void drawSudokuboard(Group root) {
        //Set up Rectangle object as background
        Rectangle boardBackground = new Rectangle();
        boardBackground.setX(BOARD_PADDING);
        boardBackground.setY(BOARD_PADDING);

        boardBackground.setWidth(BOARD_X_AND_Y);
        boardBackground.setHeight(BOARD_X_AND_Y);

        // Set background color
        boardBackground.setFill(BOARD_BACKGROUND_COLOR);

        // add background to root group
        root.getChildren().addAll(boardBackground);
    }

    // Initializer function for the title
    private void drawTitle(Group root) {
        // Set title text + color
        Text title = new Text(235, 690, SUDOKU_TITLE);
        title.setFill(Color.WHITE);

        // Set title font
        Font titleFont = new Font(43);
        title.setFont(titleFont);

        // add title to root group
        root.getChildren().add(title);
    }

    // Initializer function for the background
    private void drawBackground(Group root) {
        // Prepare scene (size, color)
        Scene scene = new Scene(root, WINDOW_X, WINDOW_Y);
        scene.setFill(WINDOW_BACKGROUND_COLOR);

        // set the scene of the stage
        // to the scene just created
        stage.setScene(scene);
    }

    @Override
    public void setListener(IUserInterfaceContract.EventListener listener) {
        this.listener = listener;
    }

    // Updates one tile of the game
    // When input is 0 the tile is left blank
    @Override
    public void updateSquare(int x, int y, int input) {
        SudokuTextField tile = textFieldCoordinates.get(new Coordinates(x, y));

        String value = Integer.toString(input);

        if(value.equals("0")) value = "";

        tile.setText(value);
    }

    // Updates the whole board based on some SudokuGame object
    @Override
    public void updateBoard(SudokuGame game) {
        for(int xIndex = 0; xIndex < 9; xIndex++){
            for(int yIndex = 0; yIndex < 9; yIndex++){

                TextField tile = textFieldCoordinates.get(new Coordinates(xIndex, yIndex));
                String value = Integer.toString(game.getCopyOfGridState()[xIndex][yIndex]);
                if(value.equals("0")) value = "";
                tile.setText(value);

                if(game.getGameState() == GameState.NEW){
                    if(value.equals("")){
                        tile.setStyle("-fx-opacity: 1");
                        tile.setDisable(false);
                    } else{
                        tile.setStyle("-fx-opacity: 0.8");
                        tile.setDisable(true);
                    }
                }
            }
        }
    }

    // Show a certain dialog on screen
    @Override
    public void showDialog(String message) {
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK);
        dialog.showAndWait();

        if(dialog.getResult() == ButtonType.OK) listener.onDialogClick();
    }

    // Show an error message on the screen
    @Override
    public void showError(String message) {
        Alert dialog = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        dialog.showAndWait();

    }

    // Handling user input with javafx
    @Override
    public void handle(KeyEvent event) {
        if(event.getEventType() == KeyEvent.KEY_PRESSED){

            if(event.getText().matches("[0-9]")){

                int value = Integer.parseInt(event.getText());

                // calling helper function for handling user input
                handleInput(value, event.getSource());

            } else if(event.getCode() == KeyCode.BACK_SPACE){

                // calling helper function for handling user input
                handleInput(0, event.getSource());

            } else{

                ((TextField) event.getSource()).setText("");

            }

        }

        event.consume();
    }

    // Helper function for handling user input
    private void handleInput(int value, Object source) {

        //Passing the input to the event listener contracted in IUserInterfaceContract
        listener.onSudokuInput(
                ((SudokuTextField) source).getX(),
                ((SudokuTextField) source).getY(),
                value
        );
    }
}
