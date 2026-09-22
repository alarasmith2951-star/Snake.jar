/**
 * Class that will implement the GameManager aspect of Snake as well as several
 * aspects use to ensure an easy way to test(i.e. SetSnakePosition etc.)
 * main notes are adding the snake, the board(boundary etc.), the food,
 * and basic movements.
 * @author Aedan Lara-Smith
 * CS251
 */
import java.util.*;
import java.io.*;


//Initializes the game with a given width and height for the grid.
public class GameManager {
    private int width, height;

    private List<int[]> walls = new ArrayList<>();
    private LinkedList<int[]> snake = new LinkedList<>();
    private int[] food;

    private Random rand = new Random(1); // fixed seed

    // direction
    private int dx = 1, dy = 0;

    // last event used this in testing
    private String lastEvent = "";

    public GameManager(int w, int h) {
        width = w;
        height = h;
    }
//Reads a file to set the grid size and load wall positions.
    public void loadLevel(String file) throws Exception {
        Scanner sc = new Scanner(new File(file));

        width = sc.nextInt();
        height = sc.nextInt();

        while (sc.hasNextInt()) {
            walls.add(new int[]{
                    sc.nextInt(), sc.nextInt(),
                    sc.nextInt(), sc.nextInt()
            });
        }
    }
//Resets the snake and places it at the starting position (0,0).
    public void addSnake() {
        snake.clear();
        snake.add(new int[]{0, 0});
    }

    //Clears the snake and places it at a specific coordinate. I used this for
    //testing to avoid walls
    public void setSnakePosition(int x, int y) {
        snake.clear();
        snake.add(new int[]{x, y});
    }

//Returns the current position of the snake’s head.
    public int[] getHead() {
        return snake.getFirst();
    }
    public List<int[]> getSnake() {
        return snake;
    }

    public int[] getFood() {
        return food;
    }

    private int score = 0;

    public int getScore() {
        return score;
    }

    //Generates food at a random position on the grid that is not occupied
    // by the snake or walls
    public void addFood() {
        int[] p;
        do {
            p = new int[]{rand.nextInt(width), rand.nextInt(height)};
        } while (occupied(p));
        food = p;
    }

    //for testing purposes manually places a food
    public void setFood(int x, int y) {
        food = new int[]{x, y};
    }

    //Checks if a position is already taken by the snake or inside a wall.
    // Returns true if the space is not available.
    private boolean occupied(int[] p) {
        for (int[] s : snake)
            if (s[0] == p[0] && s[1] == p[1]) return true;

        for (int[] w : walls)
            if (p[0] >= w[0] && p[0] <= w[2] &&
                    p[1] >= w[1] && p[1] <= w[3]) return true;

        return false;
    }

    //Updates the direction the snake will move in.
    // The values represent horizontal (dx) and vertical (dy) movement.
    public void setDirection(int newDx, int newDy) {
        dx = newDx;
        dy = newDy;
    }
//Returns the last event that happened in the game.
//used by the tester to verify behavior.
    public String getLastEvent() {
        return lastEvent;
    }

    //Moves the snake one step in the current direction,checks for collisions,
    // and handles food consumption.It updates the game state and records any
    // event
    public void update() {
        lastEvent = "";

        int[] head = snake.getFirst();
        int[] next = new int[]{head[0] + dx, head[1] + dy};

        // boundary collision
        if (next[0] < 0 || next[0] >= width ||
                next[1] < 0 || next[1] >= height) {
            lastEvent = "Hit wall";
            return;
        }

        // wall collision
        for (int[] w : walls) {
            if (next[0] >= w[0] && next[0] <= w[2] &&
                    next[1] >= w[1] && next[1] <= w[3]) {
                lastEvent = "Hit wall";
                return;
            }
        }

        // self collision
        for (int[] s : snake) {
            if (s[0] == next[0] && s[1] == next[1]) {
                lastEvent = "Hit self";
                return;
            }
        }

        // move
        snake.addFirst(next);

        // food
        if (food != null && next[0] == food[0] && next[1] == food[1]) {
            lastEvent = "Ate food";
            score++;
            addFood();
        } else {
            snake.removeLast();
        }
    }

    //creates the test i.e #,S,F,
    public String toString() {
        char[][] grid = new char[height][width];

        for (int y = 0; y < height; y++)
            Arrays.fill(grid[y], '.');

        // walls
        for (int[] w : walls)
            for (int y = w[1]; y <= w[3]; y++)
                for (int x = w[0]; x <= w[2]; x++)
                    grid[y][x] = '#';

        // food
        if (food != null)
            grid[food[1]][food[0]] = 'F';

        // snake
        for (int[] s : snake)
            grid[s[1]][s[0]] = 'S';

        String out = "";
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++)
                out += grid[y][x];
            out += "\n";
        }
        return out;
    }
}