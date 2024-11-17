import pieces.*;
import java.util.List;

public class ChessBoard {

    public ChessPiece[][] board;
    public int whiteAlivePieces;
    public int blackAlivePieces;

    public void setupNewBoard(){
        this.board = new ChessPiece[8][8];
        this.whiteAlivePieces = 16;
        this.blackAlivePieces = 16;

        this.board[0][0] = new Rook("White", 0,0);
        this.board[0][1] = new Horse("White", 0,1);
        this.board[0][2] = new Bishop("White", 0,2);
        this.board[0][3] = new Queen("White", 0,3);
        this.board[0][4] = new King("White", 0,4);
        this.board[0][5] = new Bishop("White", 0,5);
        this.board[0][6] = new Horse("White", 0,6);
        this.board[0][7] = new Rook("White", 0,7);

        for (int i = 0; i < 8; i++) {
            this.board[1][i] = new Pawn("White", 1, i);
        };


        this.board[7][0] = new Rook("Black", 7,0);
        this.board[7][1] = new Horse("Black", 7,1);
        this.board[7][2] = new Bishop("Black", 7,2);
        this.board[7][3] = new Queen("Black", 7,3);
        this.board[7][4] = new King("Black", 7,4);
        this.board[7][5] = new Bishop("Black", 7,5);
        this.board[7][6] = new Horse("Black", 7,6);
        this.board[7][7] = new Rook("Black", 7,7);

        for (int i = 0; i < 8; i++) {
            this.board[6][i] = new Pawn("Black", 6, i);
        }
    };

    public ChessPiece[][] getActualBoard(){
        ChessPiece[][] actualBoard = new ChessPiece[8][8];

        for (ChessPiece[] row : this.board) {
            for (int i = 0; i < row.length; i++) {
                if (row[i] == null) {continue;}
                int positionByRow = row[i].getPositionByX();
                int positionByColumn = row[i].getPositionByY();

                actualBoard[positionByRow][positionByColumn] = row[i].isDead ? null : row[i];
            }
        }
        return actualBoard;
    };

    public void movePiece(ChessPiece piece, int newPositionByX, int newPositionByY){
        ChessPiece targetPiece = this.board[newPositionByX][newPositionByY];

        if (targetPiece != null && !targetPiece.color.equals(piece.color)) {
            targetPiece.setDead(true);
        }
        this.board[piece.positionByX][piece.positionByY] = null;
        this.board[newPositionByX][newPositionByY] = piece;
        piece.moveTo(newPositionByX, newPositionByY);

    };

    public String getPieceSymbolByPosition(int PositionByX, int PositionByY){
      return this.board[PositionByX][PositionByY] != null ? this.board[PositionByX][PositionByY].symbol : "";
    };

    public boolean isKingInCheck(String color) {
        ChessPiece king = findKing(color);
        if (king == null) {
            return false;
        }

        int kingX = king.getPositionByX();
        int kingY = king.getPositionByY();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = this.board[row][col];
                if (piece != null && !piece.getColor().equals(color)) {
                    List<int[]> moves = piece.calculatePossibleMoves(this.getActualBoard());
                    for (int[] move : moves) {
                        if (move[0] == kingX && move[1] == kingY) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isCheckmate(String color) {
        if (!isKingInCheck(color)) {
            return false;
        }

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = this.board[row][col];
                if (piece != null && piece.getColor().equals(color)) {
                    List<int[]> possibleMoves = piece.calculatePossibleMoves(this.getActualBoard());
                    for (int[] move : possibleMoves) {
                        ChessPiece[][] simulatedBoard = this.cloneBoard();
                        ChessPiece originalPiece = simulatedBoard[move[0]][move[1]];

                        simulatedBoard[row][col] = null;
                        simulatedBoard[move[0]][move[1]] = piece;

                        if (!isKingInCheck(color)) {
                            return false;
                        }

                        simulatedBoard[row][col] = piece;
                        simulatedBoard[move[0]][move[1]] = originalPiece;
                    }
                }
            }
        }
        return true;
    }

    private ChessPiece findKing(String color) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = this.board[row][col];
                if (piece != null && piece.getColor().equals(color) && piece instanceof King) {
                    return piece;
                }
            }
        }
        return null;
    }

    public ChessPiece[][] cloneBoard() {
        ChessPiece[][] newBoard = new ChessPiece[8][8];

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = board[row][col];

                if (piece != null) {
                    newBoard[row][col] = piece.clone();
                } else {
                    newBoard[row][col] = null;
                }
            }
        }
        return newBoard;
    }
}

