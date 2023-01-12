package model;

import javax.swing.*;
import java.awt.*;

/**
 * 
 * @author Munkhtenger
 */
public class Base_sprite {
    protected int width_size;
    protected int height_size;
    protected int x_coor;
    protected int y_coor;
    protected Image img;
    protected boolean is_visible;
    
    /**
     * Constructor for Base_sprite class that sets 
     * coordinates,width, height, image and visibility
     * @param x_coor horizontal coordinate
     * @param y_coor vertical coordinate
     * @param imgN image
     */
    public Base_sprite(int x_coor, int y_coor, String imgN) {
        this.x_coor = x_coor;
        this.y_coor = y_coor;
        ImageIcon imgI = new ImageIcon(imgN);
        this.img = imgI.getImage();
        this.height_size = this.img.getHeight(null);
        this.width_size = this.img.getWidth(null);        
        this.is_visible = true;
    }
    
    /**
     * Getters and setters of coordinates.
     * @return 
     */
    public int getX_coor() {return x_coor;}
    public int getY_coor() {return y_coor;}
    public void setX_coor(int x_coor) {this.x_coor = x_coor;}
    public void setY_coor(int y_coor) {this.y_coor = y_coor;}
    
    /**
     * getter and setter for visibility of the sprite. 
     * @return 
     */
    public boolean getVisibility() {return is_visible;}
    public void setVisibility(Boolean visible) {this.is_visible = visible;}
    
    /**
     * Getter of rectangle bound of the sprite.
     * @return Rectangle
     */
    public Rectangle GetRect() {
        return new Rectangle(x_coor, y_coor, width_size, height_size);
    }
    /**
     * getter and setter for image of the sprite.
     * @return 
     */
    public Image getImg() {return img;}
    public void setImg(String img_name) {
        if(img_name!=null){
            ImageIcon imageicon = new ImageIcon(img_name);
            img = imageicon.getImage();
            height_size = img.getHeight(null);
            width_size = img.getWidth(null);
        }
    }        
}