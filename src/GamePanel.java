/**
 * GamePanel is responsible for displaying the game and handling keyboard input.
 It listens for arrow keys to change the snake’s direction and uses
 paintComponent to draw the snake and food based on the current state
 stored in GameManager.
 * @author Aedan Lara-Smith
 * CS251
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel {
    private GameManager gameManager;
    private int cellSize = 25;


    public GamePanel(GameManager gm) {
        this.gameManager = gm;

        setPreferredSize(new Dimension(500, 500));
        setBackground(Color.BLACK);

        setFocusable(true);
        requestFocusInWindow();

//keyboard inputs
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP ->
                            gameManager.setDirection(0, -1);
                    case KeyEvent.VK_DOWN ->
                            gameManager.setDirection(0, 1);
                    case KeyEvent.VK_LEFT ->
                            gameManager.setDirection(-1, 0);
                    case KeyEvent.VK_RIGHT ->
                            gameManager.setDirection(1, 0);
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // draw snake
        g.setColor(Color.GREEN);
        for (int[] s : gameManager.getSnake()) {
            g.fillRect(s[0] * cellSize, s[1] * cellSize, cellSize, cellSize);
        }

        // draw food
        int[] f = gameManager.getFood();
        if (f != null) {
            g.setColor(Color.RED);
            g.fillOval(f[0] * cellSize, f[1] * cellSize, cellSize, cellSize);
        }
    }
}
