package pieces;

import java.util.Objects;

public class Horse extends ChessPiece{

    public Horse(String color, int positionByX, int positionByY) {
        super(color, positionByX, positionByY);
        this.symbol = Objects.equals(color, "White") ? "\u2658" : "\u265E";
        this.directions = new int[][]{
                {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
                {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };
    }

}
