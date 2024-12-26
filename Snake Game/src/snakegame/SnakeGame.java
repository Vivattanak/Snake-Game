package snakegame;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Sreang Vivattanak
 */
import javax.swing.*;

public class SnakeGame {
    private JFrame frame;
    private SnakeGUI panel;

    //Constructor
    public SnakeGame() {
        frame = new JFrame("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // Create the game panel
        panel = new SnakeGUI();
        frame.add(panel);

        // Create menu
        createMenu();

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    //In order to create the menu bar
    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Options");

        JMenuItem restart = new JMenuItem("Restart");
        restart.addActionListener(e -> panel.restartGame());
        menu.add(restart);

        JMenuItem highScores = new JMenuItem("High Scores");
        highScores.addActionListener(e -> panel.showHighScores());
        menu.add(highScores);

        menuBar.add(menu);
        frame.setJMenuBar(menuBar);
    }
    
    public static void main(String[] args) {
        SnakeGame sg = new SnakeGame();
    }

}



