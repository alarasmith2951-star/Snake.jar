/**
 * The Main class serves as the entry point of the program.
 * t uses SwingUtilities.invokeLater to ensure that the GUI is created
 *  It then initializes the SnakeGame class, which sets up and runs the game
 */


import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SnakeGame(args);
        });
    }
}