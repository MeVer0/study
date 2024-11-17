package pieces;

import java.util.Objects;

public class King extends ChessPiece {

    private final int startPositionByX;

    public King(String color, int positionByX, int positionByY) {
        super(color, positionByX, positionByY);
        this.startPositionByX = positionByX;
        this.symbol = Objects.equals(color, "White") ? "\u2655" : "\u265A";
        this.directions = new int[][]{
                {1, 0}, {0, 1}, {-1, 0}, {0, -1},
                {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
        };
    }

    public boolean moved() {
        return this.startPositionByX == positionByX;
    }
}