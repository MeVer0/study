package pieces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Pawn extends ChessPiece {

    private final int startPositionByX;

    public Pawn(String color, int positionByX, int positionByY) {
        super(color, positionByX, positionByY);
        this.startPositionByX = positionByX;
        this.symbol = Objects.equals(color, "White") ? "\u2659" : "\u265F";
        this.directions = new int[][]{
                {1, 0}, {2, 0},
                {1, 1}, {1, -1}

        };
    }

    public List<int[]> calculatePossibleMoves(ChessPiece[][] gameBoard){
        List<int[]> possibleMoves = new ArrayList<>();

        for (int[] direction : this.directions) {
            // handle the possibility of moving 2 squares in the first move
            if (this.positionByX != startPositionByX && direction[0] == 2){continue;}

            int NewAxisX = positionByX + ("White".equals(this.color) ? direction[0] : direction[0] * (-1));
            int NewAxisY = positionByY + ("White".equals(this.color) ? direction[1] : direction[1] * (-1));

            if (NewAxisX >= 0 && NewAxisX < 8 && NewAxisY >= 0 && NewAxisY < 8) {
                ChessPiece targetPiece = gameBoard[NewAxisX][NewAxisY];

                if (Arrays.equals(direction, new int[]{1, 1}) || Arrays.equals(direction, new int[]{1, -1})){
                    if (targetPiece != null && !Objects.equals(targetPiece.getColor(), this.getColor()) && !targetPiece.isDead){
                        possibleMoves.add(new int[]{NewAxisX, NewAxisY});
                    }
                    continue;
                }

                if ((targetPiece == null) || (!targetPiece.getColor().equals(this.getColor()))) {
                    possibleMoves.add(new int[]{NewAxisX, NewAxisY});
                }
            };
        }
        return possibleMoves;
        }
};
