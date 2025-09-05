package Model.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/pbl4_chess?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Tạo kết nối mới
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            
        } catch (ClassNotFoundException e) {
            // Ném ra lỗi rõ ràng nếu không tìm thấy file JAR
            throw new RuntimeException("Lỗi: Không tìm thấy MySQL JDBC Driver! Hãy kiểm tra thư mục WEB-INF/lib và Deployment Assembly.", e);
        } catch (SQLException e) {
            // Ném ra lỗi rõ ràng nếu kết nối CSDL thất bại
            throw new RuntimeException("Lỗi: Không thể kết nối tới cơ sở dữ liệu! Hãy kiểm tra XAMPP và thông tin kết nối.", e);
        }
        return conn;
    }
}