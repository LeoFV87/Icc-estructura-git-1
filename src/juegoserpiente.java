import java.awt.Point;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class SnakeGame {
    public static final int WIDTH = 20;
    public static final int HEIGHT = 10;

    private LinkedList<Point> snake;
    private Point fruit;
    private Direction direction;
    private boolean isGameOver;

    public SnakeGame() {
        snake = new LinkedList<>();
        snake.add(new Point(WIDTH / 2, HEIGHT / 2));
        fruit = generateFruit();
        direction = Direction.RIGHT;
        isGameOver = false;
    }

    public void run() {
        while (!isGameOver) {
            display();
            move();
            checkCollision();
            delay(200);
        }
        System.out.println("Game Over!");
    }

    private void display() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Point p = new Point(x, y);
                if (snake.contains(p))
                    System.out.print("*");
                else if (fruit.equals(p))
                    System.out.print("@");
                else
                    System.out.print(".");
            }
            System.out.println();
        }
    }

    private void move() {
        Point head = new Point(snake.getFirst());
        switch (direction) {
            case UP:
                head.translate(0, -1);
                break;
            case DOWN:
                head.translate(0, 1);
                break;
            case LEFT:
                head.translate(-1, 0);
                break;
            case RIGHT:
                head.translate(1, 0);
                break;
        }
        snake.addFirst(head);
        if (!head.equals(fruit))
            snake.removeLast();
        else
            fruit = generateFruit();
    }

    private void checkCollision() {
        Point head = snake.getFirst();
        if (head.x < 0 || head.x >= WIDTH || head.y < 0 || head.y >= HEIGHT || snake.lastIndexOf(head) > 0)
            isGameOver = true;
    }

    private Point generateFruit() {
        Random random = new Random();
        int x, y;
        do {
            x = random.nextInt(WIDTH);
            y = random.nextInt(HEIGHT);
        } while (snake.contains(new Point(x, y)));
        return new Point(x, y);
    }

    private void delay(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SnakeGame game = new SnakeGame();

        while (!game.isGameOver) {
            System.out.print("Enter direction (W/A/S/D): ");
            char input = scanner.next().toUpperCase().charAt(0);
            switch (input) {
                case 'W':
                    game.direction = Direction.UP;
                    break;
                case 'S':
                    game.direction = Direction.DOWN;
                    break;
                case 'A':
                    game.direction = Direction.LEFT;
                    break;
                case 'D':
                    game.direction = Direction.RIGHT;
                    break;
                default:
                    System.out.println("Invalid input!");
            }
        }
        scanner.close();
    }
}
