package sudoku.problemdomain;

import java.util.Objects;


//Class representing coordinates on the sudoku board
public class Coordinates {

    //x & y value of the coordinate as private member vars
    private final int x;
    private final int y;

    //Constructor
    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //Getter for x & y
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    //Override equals() method of class Object to make comparing of coordinates easier
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x &&
                y == that.y;
    }

    //Override hashCode() to make storing easier
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
