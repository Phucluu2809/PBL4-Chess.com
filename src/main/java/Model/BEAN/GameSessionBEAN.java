package Model.BEAN;

import java.util.ArrayList;

public class GameSessionBEAN {
    private int gameId;
    private Board board;
    private String currentTurn;
    private String status;
    private ArrayList<MoveBEAN> movesHistory;
    private int playerRedId;
    private int playerBlackId;

    public GameSessionBEAN(int playerRedId, int playerBlackId) {
        this.board = new Board();
        this.currentTurn = "Red";
        this.status = "ONGOING";
        this.movesHistory = new ArrayList<>();
        this.playerRedId = playerRedId;
        this.playerBlackId = playerBlackId;
    }

    public boolean makeMove(int startX, int startY, int endX, int endY) {
        PieceBEAN pieceToMove = board.getPieceAt(startX, startY);

        if (pieceToMove == null || !pieceToMove.getColor().equals(this.currentTurn)) {
            return false;
        }

        if (board.isMoveValid(startX, startY, endX, endY)) {
            PieceBEAN capturedPiece = board.getPieceAt(endX, endY);
            
            int moveNumber = movesHistory.size() + 1;
            String pieceMovedName = pieceToMove.getClass().getSimpleName();
            String pieceCapturedName = (capturedPiece != null) ? capturedPiece.getClass().getSimpleName() : null;
            MoveBEAN move = new MoveBEAN(moveNumber, this.currentTurn, startX, startY, endX, endY, pieceMovedName, pieceCapturedName);
            this.movesHistory.add(move);
            
            board.executeMove(startX, startY, endX, endY);
            
            this.currentTurn = this.currentTurn.equals("Red") ? "Black" : "Red";
            
            return true;
        }
        
        return false;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public String getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(String currentTurn) {
        this.currentTurn = currentTurn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<MoveBEAN> getMovesHistory() {
        return movesHistory;
    }

    public void setMovesHistory(ArrayList<MoveBEAN> movesHistory) {
        this.movesHistory = movesHistory;
    }

    public int getPlayerRedId() {
        return playerRedId;
    }

    public void setPlayerRedId(int playerRedId) {
        this.playerRedId = playerRedId;
    }

    public int getPlayerBlackId() {
        return playerBlackId;
    }

    public void setPlayerBlackId(int playerBlackId) {
        this.playerBlackId = playerBlackId;
    }
}