package Controller;

import Model.BEAN.GameSessionBEAN;
import Model.BO.GameManager;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@ServerEndpoint("/game/{gameId}")
public class GameEndpoint {

    private static Map<String, Set<Session>> gameRooms = new ConcurrentHashMap<>();
    private GameManager gameManager = GameManager.getInstance();

    @OnOpen
    public void onOpen(Session session, @PathParam("gameId") String gameId) {
        gameRooms.computeIfAbsent(gameId, k -> Collections.synchronizedSet(new HashSet<>())).add(session);
        System.out.println("Client connected to game " + gameId + ": " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session, @PathParam("gameId") String gameId) throws IOException {
        int startX = Integer.parseInt(getValueFromJson(message, "startX"));
        int startY = Integer.parseInt(getValueFromJson(message, "startY"));
        int endX = Integer.parseInt(getValueFromJson(message, "endX"));
        int endY = Integer.parseInt(getValueFromJson(message, "endY"));

        GameSessionBEAN gameSession = gameManager.getGame(gameId);

        if (gameSession != null) {
            boolean moveResult = gameSession.makeMove(startX, startY, endX, endY);
            
            String responseMessage;
            if (moveResult) {
                responseMessage = String.format(
                    "{\"type\":\"MOVE_SUCCESS\", \"startX\":%d, \"startY\":%d, \"endX\":%d, \"endY\":%d, \"nextTurn\":\"%s\"}",
                    startX, startY, endX, endY, gameSession.getCurrentTurn()
                );
            } else {
                responseMessage = "{\"type\":\"MOVE_FAIL\", \"message\":\"Nước đi không hợp lệ!\"}";
            }
            
            broadcast(gameId, responseMessage);
        }
    }

    @OnClose
    public void onClose(Session session, @PathParam("gameId") String gameId) {
        Set<Session> room = gameRooms.get(gameId);
        if (room != null) {
            room.remove(session);
        }
        System.out.println("Client disconnected from game " + gameId + ": " + session.getId());
    }

    @OnError
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    private void broadcast(String gameId, String message) {
        Set<Session> room = gameRooms.get(gameId);
        if (room != null) {
            room.forEach(session -> {
                synchronized (session) {
                    try {
                        session.getBasicRemote().sendText(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
    
    private String getValueFromJson(String json, String key) {
        String searchKey = "\"" + key + "\":";
        int startIndex = json.indexOf(searchKey) + searchKey.length();
        int endIndex = json.indexOf(",", startIndex);
        if (endIndex == -1) {
            endIndex = json.indexOf("}", startIndex);
        }
        return json.substring(startIndex, endIndex).replaceAll("\"", "").trim();
    }
}