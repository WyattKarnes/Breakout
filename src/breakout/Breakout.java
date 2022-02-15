package breakout;

import acm.graphics.GLabel;
import acm.program.GraphicsProgram;

import java.awt.Color;
import java.awt.event.MouseEvent;

import svu.csc213.Dialog;

public class Breakout extends GraphicsProgram {

    private int row;
    private int col;
    private int numBricksInRow;
    private int remainingBricks;

    private static final double SPACE = 5.0;
    private Ball ball;
    private Paddle paddle;

    private Color[] rowColors = {Color.RED, Color.RED, Color.ORANGE, Color.ORANGE, Color.YELLOW, Color.YELLOW, Color.GREEN,
            Color.GREEN, Color.CYAN, Color.CYAN};

    private int lives = 3;
    private int score = 0;

    private GLabel scoreLabel = new GLabel("Score: " + (score));
    private GLabel livesLabel = new GLabel("Lives: " + (lives));

    private Brick[] destBricks;

    @Override
    public void init() { //draw the game board
        add(scoreLabel, 420, 20);
        add(livesLabel, 220, 20);

        //calculate number of bricks leaves 10 units of space in between.
        numBricksInRow = (int) (getWidth() / (Brick.WIDTH + SPACE));
        remainingBricks = (numBricksInRow * 10);

        //draws 10 rows with 4 bricks worth of space from top of screen.
        for (row = 0; row < 10; ++row) {
            for (col = 0; col < numBricksInRow; ++col) {
                double brickX = 10 + col * (Brick.WIDTH + SPACE);
                double brickY = 4 * Brick.HEIGHT + row * (Brick.HEIGHT + SPACE);
                Brick brick = new Brick(brickX, brickY, rowColors[row], row);
                add(brick);
            }
        }

        destBricks = new Brick[remainingBricks];

        ball = new Ball(getWidth() / 2, 350, 10, this);
        add(ball);

        paddle = new Paddle(230, 430, 50, 10);
        add(paddle);

    }

    @Override
    public void run() {
        addMouseListeners();
        waitForClick();
        gameLoop();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // constrain the paddle to the edges of the window
        if ((e.getX() < getWidth() - paddle.getWidth() / 2) && (e.getX() > paddle.getWidth() / 2)) {
            paddle.setLocation(e.getX() - paddle.getWidth() / 2, getHeight() - 60 - paddle.getHeight());
        }
    }

    public void gameLoop() {

        while (true) {

            //move the ball
            ball.handleMove();

            //handleCollision();

            //check to see if the ball went off the screen, or if we ran out of lives at some point
            if (ball.lost) {
                handleLoss();
            }

            if (remainingBricks == 0) {
                handleWin();
            }
            //ensure a locked framerate
            pause(5);
        }

    }

    public void update(Brick brick){
        remainingBricks--;
        score++;
        scoreLabel.setLabel("Score: " + score);

        destBricks[score - 1] = brick;
    }

    public void handleLoss() {
        lives--;
        livesLabel.setLabel("Lives: " + lives);
        ball.lost = false;

        if (lives <= 0) {
            Dialog.showMessage("You have run out of lives");
            if (Dialog.getYesOrNo("Try again?")) {
                lives = 3;
                score = 0;
                livesLabel.setLabel("Lives: " + lives);
                scoreLabel.setLabel("Score: " + score);
                drawDeceasedBricks();
                reset();
            } else {
                System.exit(0);
            }
        } else {
            reset();
        }

    }

    public void handleWin() {
        Dialog.showMessage("You is Win");
        System.exit(0);
    }

    private void drawDeceasedBricks() {
        for (int i = 0; destBricks[i] != null; i++) {
            add(destBricks[i]);
            destBricks[i] = null;
            remainingBricks++;
            pause(50);
        }
    }

    public void reset() {
        ball.setLocation(getWidth() / 2, 350);
        paddle.setLocation(230, 430);
        waitForClick();
    }

    public static void main(String[] args) {
        new Breakout().start();
    }

}
