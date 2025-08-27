package Model.DAO;

import Model.BEAN.MoveBEAN;
import java.sql.*;
import java.util.ArrayList;

public class GameDAO {
    private final Connection conn;

    public GameDAO() {
        this.conn = DBConnection.getConnection();
    }

    public int createNewGame(String playerRedId, String playerBlackId) throws SQLException {
        String sql = "INSERT INTO games (player_red_id, player_black_id, status) VALUES (?, ?, 'ONGOING')";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, playerRedId);
            ps.setString(2, playerBlackId);
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Tạo game thất bại, không lấy được ID.");
                }
            }
        }
    }

    public void updateGameResult(int gameId, String status, String winnerId) throws SQLException {
        String sql = "UPDATE games SET status = ?, winner_id = ?, end_time = NOW() WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            if (winnerId != null) {
                ps.setString(2, winnerId);
            } else {
                ps.setNull(2, Types.VARCHAR);
            }
            ps.setInt(3, gameId);
            ps.executeUpdate();
        }
    }

    public void saveMovesHistory(int gameId, ArrayList<MoveBEAN> moves) throws SQLException {
        String sql = "INSERT INTO moves (game_id, move_number, player_color, start_pos, end_pos, piece_moved, piece_captured) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (MoveBEAN move : moves) {
                ps.setInt(1, gameId);
                ps.setInt(2, move.getMoveNumber());
                ps.setString(3, move.getPlayerColor());
                ps.setString(4, move.getStartX() + "," + move.getStartY());
                ps.setString(5, move.getEndX() + "," + move.getEndY());
                ps.setString(6, move.getPieceMoved());
                ps.setString(7, move.getPieceCaptured());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
}