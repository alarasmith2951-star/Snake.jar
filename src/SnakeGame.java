/**
 * SnakeGame is the main class that creates the GUI and controls the game flow.
 * It uses a Timer to repeatedly update the game state, handles user input
 * through buttons, and coordinates between the GameManager and GamePanel
 * @author Aedan Lara-Smith
 * CS251
 */
import javax.swing.*;
import java.awt.*;

public class SnakeGame extends JFrame {
    private GameManager gameManager;
    private GamePanel gamePanel;
    private Timer timer;
    private boolean paused = true;
    private JLabel scoreLabel;
    private JButton startButton;
    private JButton restartButton;
    private boolean gameOver = false;

    public SnakeGame(String[] args) {
        //grid
        gameManager = new GameManager(20, 20);
//load levels if no level loaded it just goes default snake level
        try {
            if (args.length > 0) {
                gameManager.loadLevel(args[0]);
            }
        } catch (Exception e) {
            System.out.println("Could not load level file.");
        }
//initialize game
        gameManager.addSnake();
        gameManager.addFood();

        // UI setup
        gamePanel = new GamePanel(gameManager);

        scoreLabel = new JLabel("Score: 0");
        startButton = new JButton("Start");
        restartButton = new JButton("Restart");

        //button logic
        restartButton.addActionListener(e -> {
            resetGame();
        });

        startButton.addActionListener(e -> {
            if (gameOver) return; // 🚫 can't start if game is over

            paused = !paused;

            if (paused) {
                startButton.setText("Start");
            } else {
                startButton.setText("Pause");
                gamePanel.requestFocusInWindow();
            }
        });

        //layout
        JPanel topPanel = new JPanel();
        topPanel.add(scoreLabel);
        topPanel.add(startButton);
        topPanel.add(restartButton);

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);

        // Timer
        timer = new Timer(150, e -> {
            if (!paused && !gameOver) {
                gameManager.update();
                gamePanel.repaint();

                scoreLabel.setText("Score: " + gameManager.getScore());

                String event = gameManager.getLastEvent();
                if (event.equals("Hit wall") || event.equals("Hit self")) {
                    gameOver = true;
                    paused = true;
                    startButton.setText("Start");

                    JOptionPane.showMessageDialog(this, "Game Over!");
                }
            }
        });

        timer.start();

        setTitle("Snake Game");
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    //reset game stuff
    private void resetGame() {
        gameManager = new GameManager(20, 20);

        gameManager.addSnake();
        gameManager.addFood();

        gameOver = false;
        paused = true;

        startButton.setText("Start");
        scoreLabel.setText("Score: 0");

        gamePanel = new GamePanel(gameManager);

        getContentPane().removeAll();

        JPanel topPanel = new JPanel();
        topPanel.add(scoreLabel);
        topPanel.add(startButton);
        topPanel.add(restartButton);

        add(topPanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);

        revalidate();
        repaint();
        gamePanel.requestFocusInWindow();
    }
}
