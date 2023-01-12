package model;

import java.util.*;
import java.awt.*;

/**
 * 
 * @author Munkhtenger
 */
public class Rangers extends Base_sprite{
    boolean dir;
    boolean dirChange;
    int ranger_sz = 45;
    
    /**
     * Constructor for Rangers class that
     * initializes Ranger with given coordinates and image.
     * @param x horizontal coordinate
     * @param y vertical coordinate
     */
    public Rangers(int x, int y) {
        super(x, y,"src/resources/ranger.png");
        dirChange=true;
        Random rand=new Random();
        this.dir=(rand.nextInt(2)!= 0);         
    }
        
    /**
     * Function for handling movement of Ranger
     * when moved and returns the coordinate-altered rectangle
     * @return Rectangle
     */
    public Rectangle RectangleSprite() {
        if(dir){
            if(dirChange){
                return new Rectangle(x_coor, y_coor, width_size+ranger_sz, height_size);
            }else{
                return new Rectangle(x_coor-5, y_coor, width_size+ranger_sz, height_size);
            }
        }
        else if(!dir){
            if(dirChange){
                return new Rectangle(x_coor, y_coor, width_size, height_size+ranger_sz);
            }
            else{
                return new Rectangle(x_coor, y_coor-5, width_size, height_size+ranger_sz);
            }
        }
        else{
            return null;
        }        
    }
}

