package sample.database;
import sample.model.User;

import java.io.File;
import java.io.InputStream;
import java.sql.*;

public class DBhandllar {

    private Connection connection;
    private PreparedStatement preparedStatement;

    public Connection getConnection() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");
        connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/images","root","root");
        System.out.println("Database Connection Established "+connection.getCatalog());
        return connection;
    }

      public void addDetails(User user, InputStream file) throws SQLException, ClassNotFoundException {

        preparedStatement=getConnection().prepareStatement("INSERT INTO users (username,image,gender) VALUES (?,?,?)");
        preparedStatement.setString(1,user.getName());
        preparedStatement.setBinaryStream(2,file);
        preparedStatement.setString(3,user.getGender());

        preparedStatement.executeUpdate();

      }

      public ResultSet findPhoto() throws SQLException, ClassNotFoundException {
        preparedStatement=getConnection().prepareStatement("SELECT * FROM users WHERE userid=7");

        ResultSet set=null;
        set=preparedStatement.executeQuery();
            return set;
      }
}
