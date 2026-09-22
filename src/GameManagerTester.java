/**
 * Class that will test the game manager using methods such as testmovment,
 * test food, etc basically the core mechanics of the game Snake. Along with a
 * simple board also uses the NEW-STYLE maze-cross. x-bad |/- good
 * @author Aedan Lara-Smith
 * CS251
 */

public class GameManagerTester {

    // prints game state and updates
    public static void step(GameManager gm) {
        System.out.println(gm);
        gm.update();
        System.out.println(gm);

        if (!gm.getLastEvent().isEmpty()) {
            System.out.println("Event: " + gm.getLastEvent());
        }
    }

    // Basic movement
    public static void testMovement(GameManager gm) {
        System.out.println("Initial:");
        System.out.println(gm);

        step(gm);

        gm.setDirection(1, 0); // right
        System.out.println("Change direction (RIGHT):");
        step(gm);

        gm.setDirection(0, 1); // down
        System.out.println("Change direction (DOWN):");
        step(gm);
    }

    // force food to be eaten
    public static void testFood(GameManager gm) {
        System.out.println("Testing FOOD:");

        int[] head = gm.getHead();

        // place food directly to the right of the snake
        int fx = head[0] + 1;
        int fy = head[1];

        gm.setDirection(1, 0);
        gm.setFood(fx, fy);

        System.out.println("Before eating:");
        System.out.println(gm);

        gm.update();

        System.out.println("After eating:");
        System.out.println(gm);

        if (gm.getLastEvent().equals("Ate food")) {
            System.out.println("|/ Food eaten → Snake grew");
        } else {
            System.out.println("x Food test failed");
        }
    }

    // force wall collision
    public static void testWall(GameManager gm) {
        System.out.println("Testing WALL collision:");

        gm.setDirection(-1, 0); // force left into boundary wall

        for (int i = 0; i < 25; i++) {
            gm.update();
            System.out.println(gm);

            if (gm.getLastEvent().equals("Hit wall")) {
                System.out.println("|/ Wall collision detected");
                return;
            }
        }
    }

    // Force self collision through growing itself first
    public static void testSelf(GameManager gm) {
        System.out.println("Testing SELF collision:");

        for (int i = 0; i < 3; i++) {
            int[] head = gm.getHead();
            gm.setFood(head[0] + 1, head[1]);
            gm.setDirection(1, 0);
            gm.update();
        }

        // loop into itself
        gm.setDirection(0, 1); gm.update();
        gm.setDirection(-1, 0); gm.update();
        gm.setDirection(0, -1); gm.update();

        System.out.println(gm);

        if (gm.getLastEvent().equals("Hit self")) {
            System.out.println("|/ Self collision detected");
        } else {
            System.out.println("x Self collision failed");
        }
    }

    //creates 2 game instances(one simple and one with the maze-cross(NEW-STYLE)
    public static void main(String[] args) throws Exception {

        // Manager 1 (no file)
        GameManager gm1 = new GameManager(10, 10);
        gm1.addSnake();
        gm1.addFood();

        // Manager 2 (maze-cross)
        GameManager gm2 = new GameManager(20, 20);
        gm2.loadLevel("maze-cross.txt");
        gm2.setSnakePosition(1, 1);
        gm2.addFood();

        System.out.println("=== Manager 1 ===");
        testMovement(gm1);
        testFood(gm1);
        testSelf(gm1);

        System.out.println("=== Manager 2 ===");
        testMovement(gm2);
        testWall(gm2);
        testSelf(gm2);

        System.out.println("=== Back to Manager 1 (independence check) ===");
        testMovement(gm1);
    }
}