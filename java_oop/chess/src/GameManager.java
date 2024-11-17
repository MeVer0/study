import pieces.ChessPiece;
import java.util.List;

public class GameManager {
    private ChessBoard gameBoard;
    private String currentPlayer;
    private boolean gameOver;
    private ChessUI ui;

    public GameManager() {
        this.gameBoard = new ChessBoard();
        this.currentPlayer = "White";
        this.gameOver = false;
        gameBoard.setupNewBoard();
        this.ui = new ChessUI(this, gameBoard);
        ui.setVisible(true);
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void attemptMove(int fromX, int fromY, int toX, int toY) {
        if (gameOver) {ui.showMessage("Game over"); return;};

        ChessPiece selectedPiece = gameBoard.board[fromX][fromY];

        if (selectedPiece == null || !selectedPiece.getColor().equals(currentPlayer)) {
            ui.showMessage("Invalid move. Choose a " + currentPlayer + " piece.");
        }

        assert selectedPiece != null;
        if (isValidMove(selectedPiece, toX, toY)) {
            ChessPiece targetPiece = gameBoard.board[toX][toY];
            if (targetPiece != null && !targetPiece.isDead) {
                if (targetPiece.getColor().equals("White")) {
                    gameBoard.whiteAlivePieces--;
                } else {
                    gameBoard.blackAlivePieces--;
                }
                targetPiece.setDead(true);
            }

            gameBoard.movePiece(selectedPiece, toX, toY);

            if (isCheckMate()) {
                ui.showMessage("Checkmate! " + currentPlayer + " wins!");
                gameOver = true; // Завершаем игру
            } else if (isCheck()) {
                ui.showMessage("Check! " + currentPlayer + "'s king is in danger.");
            }
            switchPlayer();
            ui.updateBoard();
        } else {
            ui.showMessage("Invalid move. Try again.");

        }
    }

    private boolean isCheck() {
        return gameBoard.isKingInCheck(currentPlayer);
    }

    private boolean isCheckMate() {
        if (!isCheck()) return false;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = gameBoard.board[row][col];
                if (piece != null && piece.getColor().equals(currentPlayer)) {
                    List<int[]> possibleMoves = piece.calculatePossibleMoves(gameBoard.getActualBoard());
                    for (int[] move : possibleMoves) {
                        ChessPiece[][] simulatedBoard = gameBoard.cloneBoard();
                        ChessPiece originalPiece = simulatedBoard[row][col];

                        simulatedBoard[row][col] = null;
                        simulatedBoard[move[0]][move[1]] = piece;

                        // Если после хода король все еще под шахом, это не решение
                        if (!gameBoard.isKingInCheck(currentPlayer)) {
                            return false;
                        }

                        // Возвращаем все обратно
                        simulatedBoard[row][col] = piece;
                        simulatedBoard[move[0]][move[1]] = originalPiece;
                    }
                }
            }
        }
        return true;
    }

    protected boolean isValidMove(ChessPiece piece, int toX, int toY) {
        List<int[]> possibleMoves = piece.calculatePossibleMoves(gameBoard.getActualBoard());
        for (int[] move : possibleMoves) {
            if (move[0] == toX && move[1] == toY) {
                ChessPiece originalPiece = gameBoard.board[toX][toY];

                gameBoard.movePiece(piece, toX, toY);
                boolean isInCheck = gameBoard.isKingInCheck(currentPlayer);

                gameBoard.movePiece(piece, piece.getPositionByX(), piece.getPositionByY());
                if (originalPiece != null) originalPiece.setDead(false);
                gameBoard.board[toX][toY] = originalPiece;

                return !isInCheck;
            }
        }
        return false;
    }

    private void switchPlayer() {
        currentPlayer = currentPlayer.equals("White") ? "Black" : "White";
    }

    public void resetGame() {
        this.currentPlayer = "White";
        this.gameOver = false;
        gameBoard.setupNewBoard();
    }

    public static void main(String[] args) {
        new GameManager();
    }
}
