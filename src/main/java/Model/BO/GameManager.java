package Model.BO;

import Model.BEAN.GameSessionBEAN;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameManager {
    private static GameManager instance = null;
    private final Map<String, GameSessionBEAN> activeGames;

    private GameManager() {
        activeGames = new ConcurrentHashMap<>();
    }

    public static synchronized GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public void addGame(String gameId, GameSessionBEAN gameSession) {
        activeGames.put(gameId, gameSession);
    }

    public GameSessionBEAN getGame(String gameId) {
        return activeGames.get(gameId);
    }

    public void removeGame(String gameId) {
        activeGames.remove(gameId);
    }
}