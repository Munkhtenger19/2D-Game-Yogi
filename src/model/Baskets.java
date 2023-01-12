package model;
/**
 * 
 * @author Munkhtenger
 */
public class Baskets extends Base_sprite {       
    /**
     * Constructor for Baskets class. It gets x and y coordinates 
     * and construct the basket.
     * @param x1 horizontal coordinate
     * @param y1 vertical coordinate
     */
    public Baskets(int x1, int y1) {
        super(x1, y1, "src/resources/basket_pic.png");
    }
    /**
     * getter and setter for basket's coordinates.
     * @return 
     */
    @Override
    public int getX_coor(){
        return x_coor;
    }
    @Override
    public int getY_coor(){
        return y_coor;
    }
    @Override
    public void setX_coor(int x_coor){
        this.x_coor=x_coor;
    }
    @Override
    public void setY_coor(int y_coor){
        this.y_coor=y_coor;
    }
}