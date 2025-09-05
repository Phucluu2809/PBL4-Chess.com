package Model.BO;

import Model.BEAN.GameSessionBEAN;
import Model.DAO.GameDAO;

public class GameBO {
    private final GameDAO gameDAO;
    private final GameManager gameManager;

    public GameBO() {
        this.gameDAO = new GameDAO();
        this.gameManager = GameManager.getInstance();
    }

    public GameSessionBEAN startNewGame(String playerRedId, String playerBlackId) {
        try {
            int gameId = gameDAO.createNewGame(playerRedId, playerBlackId);
            
            GameSessionBEAN session = new GameSessionBEAN(Integer.parseInt(playerRedId), Integer.parseInt(playerBlackId));
            session.setGameId(gameId);
            
            gameManager.addGame(String.valueOf(gameId), session);
            
            return session;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void endGame(String gameId, String finalStatus, String winnerId) {
        GameSessionBEAN session = gameManager.getGame(gameId);
        if (session == null) {
            return;
        }
        
        try {
            gameDAO.updateGameResult(session.getGameId(), finalStatus, winnerId);
            
            if (!session.getMovesHistory().isEmpty()) {
                gameDAO.saveMovesHistory(session.getGameId(), session.getMovesHistory());
            }
            
            gameManager.removeGame(gameId);
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}