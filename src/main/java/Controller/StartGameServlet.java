package Controller;

import Model.BEAN.GameSessionBEAN;
import Model.BO.GameBO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/startGame")
public class StartGameServlet extends HttpServlet {
    private GameBO gameBO;

    @Override
    public void init() throws ServletException {
        this.gameBO = new GameBO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String playerRedId = "1";  
        String playerBlackId = "2";  

        GameSessionBEAN newGame = gameBO.startNewGame(playerRedId, playerBlackId);

        if (newGame != null) {
            request.setAttribute("gameSession", newGame);
            request.setAttribute("gameId", newGame.getGameId());
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/game.jsp");
            dispatcher.forward(request, response);
        } else {
            request.setAttribute("errorMessage", "Lỗi: Không thể tạo ván cờ mới.");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
        }
    }
}