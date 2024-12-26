/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Sreang Vivattanak
 */
//RockManagerClass
package snakegame;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RockManager {

    private List<Rock> rocks;
    private final int gridWidth;
    private final int gridHeight;

    //Constructor
    public RockManager(int numRocks, int gridWidth, int gridHeight, Snake snake, Food food) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        rocks = new ArrayList<>();
        generateRocks(numRocks, snake, food);
    }
    
    //A method to generate and place the rocks so that it doesn't overlap with anything
    private void generateRocks(int numRocks, Snake snake, Food food) {
        Random rand = new Random();

        // List of positions where rocks should not appear in order for it to be solvable
        Set<Point> restrictedPositions = new HashSet<>(Arrays.asList(
                new Point(0, 1), new Point(1, 0), new Point(1, gridHeight - 1),
                new Point(0, gridHeight - 2), new Point(gridWidth - 2, 0),
                new Point(gridWidth - 1, 1), new Point(gridWidth - 1, gridHeight - 2),
                new Point(gridWidth - 2, gridHeight - 1)
        ));

        while (rocks.size() < numRocks) {
            int x = rand.nextInt(gridWidth);
            int y = rand.nextInt(gridHeight);
            Point newRockPosition = new Point(x, y);

            // Check that the new position does not conflict with the snake, food, 
            // already existing rocks, or the restricted positions
            if (!snake.getHead().equals(newRockPosition)
                    && !snake.getBody().contains(newRockPosition)
                    && !food.getPosition().equals(newRockPosition)
                    && rocks.stream().noneMatch(rock -> rock.getPosition().equals(newRockPosition))
                    && !restrictedPositions.contains(newRockPosition)) {
                rocks.add(new Rock(x, y));
            }
        }
    }
    
    //Returns the position of all the rocks
    public List<Point> getRocks() {
        List<Point> rockPositions = new ArrayList<>();
        for (Rock rock : rocks) {
            rockPositions.add(rock.getPosition());
        }
        return rockPositions;
    }
    
    //Draw all the rocks on the board
    public void draw(Graphics g, int tileSize) {
        for (Rock rock : rocks) {
            rock.draw(g, tileSize);
        }
    }
}
