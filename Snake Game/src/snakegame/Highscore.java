/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//Highscore Class
package snakegame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Sreang Vivattanak
 */
public class Highscore {

    private String name;
    private int score;
    
    //Constructor
    public Highscore(String name, int score) {
        this.score = score;
        this.name = name;
        
        register(name, score);
    }
    
    //Method to add the attribute value of the name and score into a table from mysql
    public void register(String name, int score) {
        //Loading the JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        
        //The connection details
        String url = "jdbc:mysql://localhost:3306/leaderboard?";
        String user = "root";
        String password = "password@123";
        
        //Establishing the connection and then inserting it into the scores table
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String insertQuery = "INSERT INTO scores (username, scores) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, name);
                preparedStatement.setInt(2, score);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
