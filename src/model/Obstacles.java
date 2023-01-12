package model;

import java.util.*;
import java.awt.*;

/**
 * 
 * @author Munkhtenger
 */
public class Obstacles extends Base_sprite {
    /**
     * Constructor for Obstacles class that
     * initializes Obstacle with given coordinates and image.
     * @param x1 horizontal coordinate
     * @param y1 vertical coordinate
     */
    public Obstacles(int x1, int y1) {
        super(x1, y1,null);
        Random random = new Random();
        if(random.nextInt(2)==1){
            setImg("src/resources/obstacle.png");
        }else{
            setImg("src/resources/tree.png");
        }
    }
    public int getX(){
        return x_coor;
    }
    public int getY(){
        return y_coor;
    }
    public void setX_coor(int x_coor) {this.x_coor = x_coor;}
    public void setY_coor(int y_coor) {this.y_coor = y_coor;}
    
    /**
     * Function for getting bounded rectangle
     * of obstacle.
     * @return Rectangle
     */
    public Rectangle obstacleRect() {
        return new Rectangle(x_coor+900, y_coor, width_size+50, height_size+50);
    }
}
