package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnect {
	private Connection conn;

    public DBConnect() {
        try {
            connectSQL();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void connectSQL() throws SQLException {
        try {
            String url = "jdbc:mysql://localhost:3307/quanlybaohanh?useSSL=false&serverTimezone=UTC";
            String user = "root";
            String pass = "";

            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, pass);

        } catch (ClassNotFoundException e) {
            throw new SQLException("Khong tim thay Driver MySQL");
        }
    }

    // Truy vấn SELECT
    public ResultSet LoadData(String sql) {
        try {
            Statement stmt = conn.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
            );
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // INSERT, UPDATE, DELETE
    public int UpdateData(String sql) {
        try {
            Statement stmt = conn.createStatement();
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    // Đóng kết nối
    public void close() {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}