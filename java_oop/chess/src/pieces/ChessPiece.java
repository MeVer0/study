package pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class ChessPiece implements Cloneable{

    public final String color;
    public String symbol;
    public boolean isDead;
    public int positionByX;
    public int positionByY;
    public int[][] directions;

    public ChessPiece(String color, int positionByX, int positionByY){
        this.color = color;
        this.isDead = false;
        this.positionByX = positionByX;
        this.positionByY = positionByY;
    };

    public String getColor() {
        return color;
    }

    public void setDead(boolean isDead) {
        this.isDead = isDead;
    }

    public int getPositionByX() {
        return this.positionByX;
    }

    public int getPositionByY() {
        return this.positionByY;
    }


    public List<int[]> calculatePossibleMoves(ChessPiece[][] gameBoard){
        List<int[]> possibleMoves = new ArrayList<>();


        for (int[] move : this.directions) {
            int NewAxisX = positionByX + ("White".equals(this.color) ? move[0] : move[0] * (-1));
            int NewAxisY = positionByY + ("White".equals(this.color) ? move[1] : move[1] * (-1));

            if (NewAxisX >= 0 && NewAxisX < 8 && NewAxisY >= 0 && NewAxisY < 8) {
                ChessPiece targetPiece = gameBoard[NewAxisX][NewAxisY];

                if ((targetPiece == null) || (!Objects.equals(targetPiece.getColor(), this.getColor()))) {
                    possibleMoves.add(new int[]{NewAxisX, NewAxisY});
                }
            }
        }

        return possibleMoves;
    };


    public void moveTo(int newPositionByX, int newPositionByY){
        this.positionByX = newPositionByX;
        this.positionByY = newPositionByY;
    };

    @Override
    public ChessPiece clone() {
        try {
            ChessPiece clone = (ChessPiece) super.clone();

            if (this.directions != null) {
                clone.directions = new int[this.directions.length][];
                for (int i = 0; i < this.directions.length; i++) {
                    clone.directions[i] = this.directions[i].clone();
                }
            }

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Cloning not supported", e);
        }
    }

}
