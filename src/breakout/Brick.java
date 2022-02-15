package breakout;

import acm.graphics.*;

import java.awt.Color;
import java.awt.Dialog;
/**
 * @author Wyatt
 */
public class Brick extends GRect {

    public static final int WIDTH = 44;
    public static final int HEIGHT = 20;

    private int hitpoints;
    private int maxHitpoints; //TODO: Change made
    private Color color;

    public Brick(double x, double y, Color color, int row) { //TODO: Change made
        super(x, y, WIDTH, HEIGHT);
        this.setFillColor(color);
        this.setFilled(true);
        this.color = color;

        switch (row) { //TODO: Change made
            case 0:
            case 1:
                maxHitpoints = 5;
                break;
            case 2:
            case 3:
                maxHitpoints = 4;
                break;
            case 4:
            case 5:
                maxHitpoints = 3;
                break;
            case 6:
            case 7:
                maxHitpoints = 2;
                break;
            case 8:
            case 9:
                maxHitpoints = 1;
                break;
        }

        hitpoints = maxHitpoints; //TODO:
    }

    public int getHitpoints() {
        return hitpoints;
    } //TODO

    public void takeDamage() { //TODO
        hitpoints--;
        changeColor();
    }

    private void changeColor() {
        this.setFillColor(this.getFillColor().darker());
    } //TODO

    private void resetHitpoints(){ //TODO
        hitpoints = maxHitpoints;
        setFillColor(color);
    }


}
