package breakout;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GCanvas;

public class Ball extends GOval {

    private double deltaX = 1;
    private double deltaY = -1;

    private Breakout parent;
    private GCanvas screen;

    public boolean lost;

    AudioHandler audioHandler;

    public Ball(double size, Breakout parent) {
        this(100.0, 100.0 ,size, parent);
    }

    public Ball(double x, double y, double size ,Breakout parent) {
        super(x, y, size, size);
        setFilled(true);

        this.parent = parent;
        this.screen = parent.getGCanvas();

        audioHandler = new AudioHandler();
        audioHandler.playBrickBreak();
    }

    public void handleMove() {
        move(deltaX, -deltaY);

        checkEdges();
        checkCollisions();

    }

    private void checkEdges(){

        if (getY() <= 0) {
            deltaY = -Math.abs(deltaY);
        }
        if (getY() >= screen.getHeight()-getHeight()) {
            lost = true;
        }
        if (getX()<=0){
            deltaX = Math.abs(deltaX);
        }
        if (getX()>=(screen.getWidth()-getWidth())){
            deltaX = -1*Math.abs(deltaX);
        }
    }

    private void checkCollisions(){
        //if there was a collision, check to see what it was the ball hit
        GObject obj = null;

        //TODO: Updated collision logic

        //check the top right corner for collision
        if (obj == null) {
            obj = screen.getElementAt(getX() + getWidth(), getY());
        }
        //check the top left corner for collision
        if (obj == null) {
            obj = screen.getElementAt(getX(), getY());
        }
        //check the bottom right corner for collision
        if (obj == null) {
            obj = screen.getElementAt(getX() + getWidth(), getY() + getHeight());
        }
        //check the bottom left corner for collision
        if (obj == null) {
            obj = screen.getElementAt(getX(), getY() + getHeight());
        }

        if (obj != null) {

            //what happens when we hit a paddle?
            if (obj instanceof Paddle) {

                if (getX() < (obj.getX() + (obj.getWidth() * 0.2))) { //if we hit the left edge of the paddle
                    bounceleft();
                } else if (getX() > (obj.getX() + (obj.getWidth() * 0.8))) { //if we hit the right edge of the paddle
                    bounceright();
                } else { //if we hit the middle of the paddle
                    bounce();
                }
            }


            //what happens if we hit a brick?
            if (obj instanceof Brick) {
                if (((Brick) obj).getHitpoints() > 1) { //TODO: New code for bricks, gives them hitpoints
                    ((Brick) obj).takeDamage();
                    bounce();
                    audioHandler.playBrickBreak();
                } else {
                    screen.remove(obj);
                    audioHandler.playBrickBreak();
                    bounce();

                    parent.update((Brick) obj);
                }
            }
        }
    }

    private void bounce(){
        deltaY = -deltaY;
    }

    private void bounceleft(){
        deltaY = -deltaY;
        deltaX = -Math.abs(deltaX);
    }
    private void bounceright(){
        deltaY = -deltaY;
        deltaX = Math.abs(deltaX);
    }

}
