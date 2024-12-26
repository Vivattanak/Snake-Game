/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Sreang Vivattanak
 */
//Food Class
package snakegame;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class Food {

    private Point position;
    private final int gridWidth;
    private final int gridHeight;

    //Constructor
    public Food(int gridWidth, int gridHeight, Snake snake) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        generateNewPosition(snake, List.of()); //Simulates an empty board, place food first then the rock
    }
    
    // Method for randomly generating the food in the new position
    public void generateNewPosition(Snake snake, List<Point> rocks) {
        Random rand = new Random();
        int maxAttempts = 100; // Avoid infinite loop if grid is full
        int attempts = 0;

        do {
            position = new Point(rand.nextInt(gridWidth), rand.nextInt(gridHeight));
            attempts++;
        } while ((snake.getHead().equals(position) || rocks.contains(position)) && attempts < maxAttempts);

        if (attempts == maxAttempts) {
            System.out.println("Warning: Unable to place food after 100 attempts");
        }
    }
    
    //Getter for the position of the food
    public Point getPosition() {
        return position;
    }
    
    //For rendering the food
    public void draw(Graphics g, int tileSize) {
        g.setColor(Color.RED);
        g.fillOval(position.x * tileSize, position.y * tileSize, tileSize, tileSize);
        g.setColor(Color.BLACK);
        g.fillRect(position.x * tileSize + 10, position.y * tileSize - 5, tileSize - 18, tileSize - 10);
    }
}
