package org.example.lab7.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDao {
    public Connection getConnection() throws SQLException{
        try{
            Class.forName(("com.mysql.cj.jdbc.Driver"));
        }catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }
        String url = "jdbc:mysql://localhost:3306/hr";
        String username = "root";
        String password = "rootroot";

        return DriverManager.getConnection(url,username,password);
    }
}
