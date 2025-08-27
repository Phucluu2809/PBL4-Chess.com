package Controller;

import Model.BEAN.GameSessionBEAN;
import Model.BO.GameManager;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/joinGame")
public class JoinGameServlet extends HttpServlet {

    private GameManager gameManager;

    @Override
    public void init() throws ServletException {
        this.gameManager = GameManager.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String gameId = request.getParameter("gameId");

        GameSessionBEAN gameSession = gameManager.getGame(gameId);

        if (gameSession != null) {
            request.setAttribute("gameSession", gameSession);
            request.setAttribute("gameId", gameSession.getGameId());
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/game.jsp");
            dispatcher.forward(request, response);
        } else {
            request.setAttribute("errorMessage", "Lỗi: Mã phòng không hợp lệ hoặc ván cờ đã kết thúc.");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
        }
    }
}