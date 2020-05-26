package project;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Snake {

    private final int[] x = new int[1600];
    private final int[] y = new int[1600];

    private int xApple;
    private int yApple;

    private int circlesOnBody;

    private final int down = KeyEvent.VK_DOWN;
    private final int right = KeyEvent.VK_RIGHT;
    private final int left = KeyEvent.VK_LEFT;
    private final int up = KeyEvent.VK_UP;


    private boolean directionRight = true;
    private boolean directionLeft = false;
    private boolean directionUp = false;
    private boolean directionDown = false;
    private boolean firstKeyPressed = false;

    private boolean contGame = true;

    public Snake() {
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setXscale(0, 400);
        StdDraw.setYscale(0, 400);
    }

    public void gameStart() {
        circlesOnBody = 1;
        x[0] = 200;
        y[0] = 200;
        updateApple();
        drawScene();
        while (!firstKeyPressed) {
            if (StdDraw.isKeyPressed(left) || StdDraw.isKeyPressed(right) || StdDraw.isKeyPressed(up) || StdDraw.isKeyPressed(down)) {
                firstKeyPressed = true;
            }
        }
    }

    public void updateApple() {
        xApple = (int) (Math.random() * 390 + 10);
        yApple = (int) (Math.random() * 390 + 10);
    }

    public void drawScene() {
        StdDraw.setPenColor(Color.RED);
        StdDraw.filledCircle(xApple, yApple, 5);
    }

    public void paintSnakeBody() {
        checkKey();
        move();
        for (int i = 0; i < circlesOnBody; i++) {
            if (i == 0) {
                Color c = new Color(0, 128, 0);
                StdDraw.setPenColor(c);
                StdDraw.filledCircle(x[i], y[i], 5);
            } else {
                Color c1 = new Color(50, 205, 50);
                StdDraw.setPenColor(c1);
                StdDraw.filledCircle(x[i], y[i], 5);
            }
        }
    }

    public void checkKey() {
        if (StdDraw.isKeyPressed(down) && (!directionUp)) {
            directionDown = true;
            directionRight = false;
            directionLeft = false;
        }

        if (StdDraw.isKeyPressed(up) && (!directionDown)) {
            directionUp = true;
            directionRight = false;
            directionLeft = false;
        }

        if (StdDraw.isKeyPressed(right) && (!directionLeft)) {
            directionRight = true;
            directionDown = false;
            directionUp = false;
        }

        if (StdDraw.isKeyPressed(left) && (!directionRight)) {
            directionLeft = true;
            directionDown = false;
            directionUp = false;
        }
    }

    public void move() {
        for (int i = circlesOnBody; i > 0; i--) {
            x[i] = x[(i - 1)];
            y[i] = y[(i - 1)];
        }

        if (directionDown && !directionUp && !directionLeft && !directionRight) {
            y[0] -= 10;
        } else if (directionUp && !directionDown && !directionLeft && !directionRight) {
            y[0] += 10;
        } else if (directionRight && !directionLeft && !directionUp && !directionDown) {
            x[0] += 10;
        } else if (directionLeft && !directionRight && !directionUp && !directionDown) {
            x[0] -= 10;
        }
    }

    public boolean collision() {
        // Check to see if head overlaps body
        for (int i = circlesOnBody - 1; i > 0; i--) {
            double diffXValue = x[0] - x[i];
            double diffYValue = y[0] - y[i];
            double distance = Math.sqrt((Math.pow(diffXValue, 2)) + (Math.pow(diffYValue, 2)));

            if ((Math.abs(x[circlesOnBody] - x[0]) <= 5) && (Math.abs(y[circlesOnBody] - y[0]) <= 5)) {
                contGame = false;
            }
            if ((i > 1) && distance <= 10) {
                System.out.println(distance);
                contGame = false;
            }
            //checks the i value with all the other previous i values and if they match up then contGame is false
        }
        // See if head hits borders of game board
        if (y[0] >= 400) {
            contGame = false;
        }
        if (y[0] <= 0) {
            contGame = false;
        }
        if (x[0] <= 0) {
            contGame = false;
        }
        if (x[0] >= 400) {
            contGame = false;
        }
        return contGame;
    }

    public static void exitScreen() {
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.filledRectangle(200, 200, 200, 100);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.text(200, 200, "Game over!");
    }

    public void checkApple() {
        double diffX = x[0] - xApple;
        double diffY = y[0] - yApple;
        double sq = Math.sqrt((Math.pow(diffX, 2)) + (Math.pow(diffY, 2)));
        if (sq <= 10) {
            circlesOnBody++;
            updateApple();
        }
        drawScene();
    }

    public static void main(final String[] args) {
        Snake s = new Snake();
        s.gameStart();
        StdDraw.enableDoubleBuffering();
        while (s.collision()) {
            StdDraw.clear(Color.BLACK);
            s.paintSnakeBody();
            s.drawScene();
            s.checkApple();
            if (!s.collision()) {
                exitScreen();
            }
            StdDraw.show(100);
        }
    }
}




