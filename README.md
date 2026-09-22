Snake Game – README

Author: Aedan Lara-Smith
Course: CS251

Game Play
How to Play
Press the Start button to begin the game.
Use the arrow keys to control the snake:
↑ Up
↓ Down
← Left
→ Right
The snake will continue moving in the current direction(it is possible to go into a direction opposite to yourself, which will cause a game over if you have more than 1 score, i.e., if you go up and press down with a higher score than 1, you will game over)
Press Pause (same button as Start) to temporarily stop the game.
Press Restart to reset the game at any time.
Objective
Guide the snake to eat food while avoiding collisions.
Each time the snake eats food, it grows longer, and your score increases.
Scoring
The score increases by 1 point for each piece of food eaten.
The current score is displayed at the top of the window.
Game Over
The game ends when the snake hits a wall or the snake runs into itself. A “Game Over” message will appear. After losing, the player must press Restart to play again.
Map Behavior
The edges of the map act as solid walls. If the snake moves beyond the boundary, the game ends.
Program Internals
Class Structure
Main
Entry point of the program.
Uses SwingUtilities.invokeLater to safely start the GUI on the Event Dispatch Thread.
SnakeGame
Main GUI controller.
Responsibilities: Creates the game window, manages UI components (buttons, score label), Controls game state (paused, running, game over), runs the game loop using a Timer, and coordinates interaction between GameManager and GamePanel
GamePanel
Responsible for:
Drawing the snake and food on screen
Handling keyboard input (arrow keys)
Extends JPanel and overrides paintComponent to render the game
GameManager
Contains all core game logic and data structures.
Responsibilities: Stores snake as a LinkedList, Tracks food position, handles movement, collisions, growth, generates food in valid positions, and keeps track of the score and the last game event
Algorithm Details
Snake Movement
The snake’s direction is stored as (dx, dy)
Each update:
A new head position is calculated:
newX = headX + dx
newY = headY + dy
The new head is added to the front of the snake. If no food is eaten, the tail is removed. Snake Growth
When the snake eats food:
The tail is not removed. This increases the snake’s length by 1. Food is then randomly placed in an unoccupied location
Food Generation
A random position is generated within the grid
The position is checked to ensure it is: Not inside the snake, not inside a wall. This repeats until a valid position is found
Collision Detection
Wall Collision occurs when:
Snake moves outside the grid boundaries, OR
Snake enters a wall region (if loaded from file)
Self Collision
Occurs when: The snake’s head moves into a position already occupied by its body
End of Game Detection
After each update: 
If a collision is detected, the game state is set to game over. The timer stops updating the movement. A “Game Over” message is displayed
Command Line Arguments
The program accepts an optional argument:
java Main <level_file>
If provided:
The game loads the map size and wall configuration from the file
If not provided: A default 20x20 grid is used

