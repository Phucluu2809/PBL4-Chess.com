package Model.BEAN;

public class MoveBEAN {
    private int moveNumber;
    private String playerColor;
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private String pieceMoved;
    private String pieceCaptured;

    public MoveBEAN(int moveNumber, String playerColor, int startX, int startY, int endX, int endY, String pieceMoved, String pieceCaptured) {
        this.moveNumber = moveNumber;
        this.playerColor = playerColor;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.pieceMoved = pieceMoved;
        this.pieceCaptured = pieceCaptured;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    public void setMoveNumber(int moveNumber) {
        this.moveNumber = moveNumber;
    }

    public String getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(String playerColor) {
        this.playerColor = playerColor;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getEndX() {
        return endX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public int getEndY() {
        return endY;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    public String getPieceMoved() {
        return pieceMoved;
    }

    public void setPieceMoved(String pieceMoved) {
        this.pieceMoved = pieceMoved;
    }

    public String getPieceCaptured() {
        return pieceCaptured;
    }

    public void setPieceCaptured(String pieceCaptured) {
        this.pieceCaptured = pieceCaptured;
    }
}