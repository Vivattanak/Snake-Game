/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

/**
 *
 * @author Sreang Vivattanak
 */

//Snake Class
package snakegame;

import java.awt.*;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Snake {
    //Attributes, the body is a list of Points on the grid, which represents where the snake is right now
    private List<Point> body;
    private char direction;
    private static final char[] ALL_DIRECTIONS = {'U', 'D', 'L', 'R'};
    
    
    //Constructor
    public Snake(int startX, int startY) {
        body = new ArrayList<>();
        body.add(new Point(startX, startY));
        body.add(new Point(startX - 1, startY));
        
        //Start in a random direction
        Random rand = new Random();
        int randomIndex = rand.nextInt(ALL_DIRECTIONS.length);
        this.direction = ALL_DIRECTIONS[randomIndex];
    }
    
    //Method for moving the snake
    public void move() {
        Point head = body.get(0);
        Point newHead = switch (direction) {
            case 'U' -> new Point(head.x, head.y - 1);
            case 'D' -> new Point(head.x, head.y + 1);
            case 'L' -> new Point(head.x - 1, head.y);
            case 'R' -> new Point(head.x + 1, head.y);
            default -> head;
        };
        
        //add new head and removing the tail to simulate movement
        body.add(0, newHead);
        body.remove(body.size() - 1);
    }
    
    //Method for growing the snake, copying the final tail
    public void grow() {
        body.add(new Point(body.get(body.size() - 1)));
    }
    
    //Method for checking the collision  
    public boolean checkCollision(int gridWidth, int gridHeight, List<Point> rocks) {
        Point head = body.get(0);
        
        // Boundary collision
        if (head.x < 0 || head.x >= gridWidth || head.y < 0 || head.y >= gridHeight) {
            return true;
        }
        
        // Rock or self collision
        if (rocks.contains(head) || body.subList(1, body.size()).contains(head)) {
            return true;
        }
        return false;
    }
    
    //Method for changing the direction the snake is facing based on keyboard inputs. 
    public void changeDirection(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                if (direction != 'D') direction = 'U';
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                if (direction != 'U') direction = 'D';
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                if (direction != 'R') direction = 'L';
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                if (direction != 'L') direction = 'R';
                break;
        }
    }
    
    //Method for drawing its head and body parts.
    public void draw(Graphics g, int tileSize) {
        g.setColor(Color.GREEN);
        boolean head = true;
        for (Point p : body) {
            g.fillOval(p.x * tileSize, p.y * tileSize, tileSize, tileSize); // Draw each body part
            
            if (head) 
            {
                g.setColor(Color.BLACK);
                g.fillOval(p.x * tileSize + 3, p.y * tileSize + 3, tileSize - 15, tileSize - 15);
                g.fillOval(p.x * tileSize + 10, p.y * tileSize + 3, tileSize - 15, tileSize - 15);
                g.setColor(Color.GREEN);
                head = false;
            }
        }
    }
    
    //Get the position of the snake 
    public Point getHead() {
        return body.get(0);
    }
    
    //Get the full list of body points (including the head and the tail)
    public List<Point> getBody() {
        return body;
    }
}
