import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import pieces.ChessPiece;

public class ChessUI extends JFrame {
    private ChessBoard chessBoard;
    private JButton[][] squares;
    private ChessPiece selectedPiece = null;
    private List<int[]> possibleMoves = null;
    private GameManager gameManager;

    private JLabel currentPlayerLabel;
    private JLabel whitePiecesLabel;
    private JLabel blackPiecesLabel;

    public ChessUI(GameManager gameManager, ChessBoard board) {
        this.gameManager = gameManager;
        this.chessBoard = board;
        this.squares = new JButton[8][8];

        setTitle("Chess Game");
        setSize(600, 700); // Увеличиваем высоту окна для информационной панели и кнопки рестарта
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(8, 8));
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton button = new JButton();
                button.setBackground((row + col) % 2 == 0 ? Color.WHITE : Color.GRAY);
                button.setText(this.chessBoard.getPieceSymbolByPosition(row, col));
                button.setFont(new Font("Serif", Font.BOLD, 36));
                boardPanel.add(button);
                squares[row][col] = button;

                final int currentRow = row;
                final int currentCol = col;
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handleSquareClick(currentRow, currentCol);
                    }
                });
            }
        }

        // Панель информации с метками и кнопкой рестарта
        JPanel infoPanel = new JPanel(new BorderLayout());

        // Создаем метки для отображения текущего игрока и количества фигур
        JPanel labelsPanel = new JPanel(new GridLayout(3, 1));
        currentPlayerLabel = new JLabel("Current Player: " + gameManager.getCurrentPlayer());
        whitePiecesLabel = new JLabel("White Pieces Alive: " + chessBoard.whiteAlivePieces);
        blackPiecesLabel = new JLabel("Black Pieces Alive: " + chessBoard.blackAlivePieces);

        currentPlayerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        whitePiecesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        blackPiecesLabel.setHorizontalAlignment(SwingConstants.CENTER);

        labelsPanel.add(currentPlayerLabel);
        labelsPanel.add(whitePiecesLabel);
        labelsPanel.add(blackPiecesLabel);

        // Создаем кнопку рестарта
        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(e -> restartGame());
        infoPanel.add(labelsPanel, BorderLayout.CENTER);
        infoPanel.add(restartButton, BorderLayout.EAST);

        // Добавляем панели в основное окно
        add(infoPanel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);

        updateBoard();
    }

    public void updateBoard() {
        ChessPiece[][] actualBoard = chessBoard.getActualBoard();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = actualBoard[row][col];
                squares[row][col].setText(piece != null && !piece.isDead ? piece.symbol : "");
            }
        }

        // Обновляем метки на панели
        currentPlayerLabel.setText("Current Player: " + gameManager.getCurrentPlayer());
        whitePiecesLabel.setText("White Pieces Alive: " + chessBoard.whiteAlivePieces);
        blackPiecesLabel.setText("Black Pieces Alive: " + chessBoard.blackAlivePieces);
    }

    private void handleSquareClick(int x, int y) {
        if (selectedPiece == null) {
            ChessPiece piece = chessBoard.board[x][y];
            if (piece != null && piece.getColor().equals(gameManager.getCurrentPlayer())) {
                selectedPiece = piece;
                possibleMoves = piece.calculatePossibleMoves(chessBoard.board);
                highlightMoves(possibleMoves);
            }
        } else {
            if (isMoveValid(x, y)) {
                gameManager.attemptMove(selectedPiece.getPositionByX(), selectedPiece.getPositionByY(), x, y);
                clearHighlights();
                selectedPiece = null;
                possibleMoves = null;
            } else {
                clearHighlights();
                selectedPiece = null;
                possibleMoves = null;
            }
        }
    }

    private boolean isMoveValid(int x, int y) {
        for (int[] move : possibleMoves) {
            if (move[0] == x && move[1] == y) {
                return true;
            }
        }
        return false;
    }

    private void highlightMoves(List<int[]> moves) {
        for (int[] move : moves) {
            int x = move[0];
            int y = move[1];
            squares[x][y].setBackground(Color.YELLOW);
        }
    }

    private void clearHighlights() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                squares[row][col].setBackground((row + col) % 2 == 0 ? Color.WHITE : Color.GRAY);
            }
        }
    }

    private void restartGame() {
        int response = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to restart the game?",
                "Confirm Restart",
                JOptionPane.YES_NO_OPTION
        );

        if (response == JOptionPane.YES_OPTION) {
            chessBoard.setupNewBoard();
            gameManager.resetGame();
            updateBoard();
        }
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
