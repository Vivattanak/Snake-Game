/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Sreang Vivattanak
 */
//SnakeGUI Class
package snakegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SnakeGUI extends JPanel implements ActionListener {

    private static final int TILE_SIZE = 20;
    private static final int GRID_WIDTH = 40;
    private static final int GRID_HEIGHT = 40;
    private static final int DELAY = 100;

    private Timer timer;
    private Snake snake;
    private Food food;
    private RockManager rocks;
    private boolean running;
    private int score;
    private boolean gameOver = false;
    private long startTime;
    private boolean keyPressed;

    //Constructor
    public SnakeGUI() {
        setPreferredSize(new Dimension(GRID_WIDTH * TILE_SIZE, GRID_HEIGHT * TILE_SIZE));
        setBackground(Color.YELLOW);
        setFocusable(true);
        initGame();
        addKeyListener(new SnakeKeyAdapter());
    }

    //To initialize the game
    private void initGame() {
        snake = new Snake(GRID_WIDTH / 2, GRID_HEIGHT / 2);
        food = new Food(GRID_WIDTH, GRID_HEIGHT, snake);
        rocks = new RockManager(10, GRID_WIDTH, GRID_HEIGHT, snake, food);

        running = true;
        score = 0;
        startTime = System.currentTimeMillis();

        timer = new Timer(DELAY, this);
        timer.start();
        gameOver = false;
    }

    //To restart the game when you click in the menu bar
    public void restartGame() {
        timer.stop();
        initGame();
        repaint();
    }

    //Method when we click the leaderboard option in the menu bar it will show the leaderboard
    //by accessing the database
    public void showHighScores() {
        try {
            String url = "jdbc:mysql://localhost:3306/leaderboard?";
            String user = "root";
            String password = "password@123";

            Connection connection = DriverManager.getConnection(url, user, password);
            String query = "SELECT * FROM scores ORDER BY scores DESC LIMIT 10";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            StringBuilder topScores = new StringBuilder();
            int i = 0;
            while (resultSet.next()) {
                i++;
                String playerName = resultSet.getString("username");
                int score = resultSet.getInt("scores");
                topScores.append(" Rank: ").append(i)
                        .append(", Player: ").append(playerName)
                        .append(", Score: ").append(score)
                        .append("\n");
            }

            JOptionPane.showMessageDialog(this, topScores.toString(), "Top 10 Scores", JOptionPane.INFORMATION_MESSAGE);

            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching top scores", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Method to draw the components after any key is pressed, including the time and score.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (keyPressed) {  // If the any key is pressed, display the game
            if (running) {
                food.draw(g, TILE_SIZE);
                rocks.draw(g, TILE_SIZE);
                snake.draw(g, TILE_SIZE);

                g.setColor(Color.BLACK);
                g.drawString("Score: " + score, 10, 10);
                g.drawString("Time: " + getElapsedTime(), 10, 30);
            } else {
                showGameOver(g);
            }
        } else {  // If the game has not started, display the "Press any key to start" message
            showStartMessage(g);
        }
    }

    //In order to show the game over screen, with the time and the score
    private void showGameOver(Graphics g) {
        String scoreMessage = "Game Over! Your Score: " + score;
        String timeMessage = "Your time is: " + getElapsedTime();
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 25));
        g.drawString(scoreMessage, getWidth() / 2 - 90, getHeight() / 2);
        g.drawString(timeMessage, getWidth() / 2 - 90, getHeight() / 2 + 50);

        if (!gameOver) {
            gameOver = true;
            // Use SwingUtilities.invokeLater to show the input dialog with a delay (to fix some bugs)
            // To record the score
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    String playerName = getUserInput();
                    new Highscore(playerName, score);
                }
            });
        }
    }

    //Method in order to show the input box
    public String getUserInput() {
        String nameInput = JOptionPane.showInputDialog(this, "Enter your name:");
        if (nameInput == null || nameInput.trim().isEmpty()) {
            nameInput = "Anonymous";
        }
        return nameInput.trim();
    }
    
    //Method to return the time
    private String getElapsedTime() {
        long elapsedMillis = System.currentTimeMillis() - startTime;
        long seconds = (elapsedMillis / 1000) % 60;
        long minutes = (elapsedMillis / (1000 * 60)) % 60;
        return String.format("%02d:%02d", minutes, seconds); 
    }

    //Method to show the start screen prompting the user to press any key to start
    private void showStartMessage(Graphics g) {
        String message = "Press any key to start!";
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 25));
        g.drawString(message, getWidth() / 2 - 130, getHeight() / 2);
    }

    //The event triggered is the timer, and it basically moves the snake, and then checking collision or if
    //the snake has eaten any food, after that updating the board   
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running && keyPressed) {
            snake.move();
            if (snake.checkCollision(GRID_WIDTH, GRID_HEIGHT, rocks.getRocks())) {
                running = false;
                timer.stop();
            }
            if (snake.getHead().equals(food.getPosition())) {
                snake.grow();
                score++;
                food.generateNewPosition(snake, rocks.getRocks());
            }
            repaint();
        }
    }
    
    // Reads the keyboard pressed in order to start the game or change the direction of the snake
    // KeyAdapter is similar to KeyListener but only for the methods you need
    private class SnakeKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (!keyPressed) {
                keyPressed = true;
                timer.start();
            } else {
                snake.changeDirection(e.getKeyCode());
            }
        }
    }
}
