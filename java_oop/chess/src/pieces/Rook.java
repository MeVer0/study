package pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Rook extends ChessPiece {

    private final int startPositionByX;

    public Rook(String color, int positionByX, int positionByY) {
        super(color, positionByX, positionByY);
        this.startPositionByX = positionByX;
        this.symbol = Objects.equals(color, "White") ? "\u2656" : "\u265C";
        this.directions = new int[][]{
                {1, 0}, {0, 1}, {-1, 0}, {0, -1}, {0, 4}
        };
    }

    public List<int[]> calculatePossibleMoves(ChessPiece[][] gameBoard) {
        List<int[]> possibleMoves = new ArrayList<>();

        for (int[] direction : this.directions) {
            for (int multiplier = 1; multiplier < 8; multiplier++) {
                int newAxisX = this.positionByX + direction[0] * multiplier;
                int newAxisY = this.positionByY + direction[1] * multiplier;

                if (newAxisX >= 0 && newAxisX < 8 && newAxisY >= 0 && newAxisY < 8) {
                    ChessPiece targetPiece = gameBoard[newAxisX][newAxisY];

                    if (targetPiece == null) {
                        possibleMoves.add(new int[]{newAxisX, newAxisY});
                    } else if (!Objects.equals(targetPiece.getColor(), this.getColor())) {
                        possibleMoves.add(new int[]{newAxisX, newAxisY});
                        break;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
        }

        return possibleMoves;
    };

    public boolean moved() {
        return this.startPositionByX == positionByX;
    }
}
