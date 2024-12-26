/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Sreang Vivattanak
 */

//Rock Class
package snakegame;
import java.awt.*;

public class Rock {
    
    private Point position;
    
    //Constructor
    public Rock(int x, int y) {
        position = new Point(x, y);
    }
    
    //Getter for the position of rock
    public Point getPosition() {
        return position;
    }

    //Rendering/Drawing method for representing the rock
    public void draw(Graphics g, int tileSize) {
        g.setColor(Color.GRAY);
        g.fillRect(position.x * tileSize, position.y * tileSize, tileSize, tileSize);
    }
}

