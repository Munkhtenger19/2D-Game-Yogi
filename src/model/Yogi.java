package model;
/**
 * 
 * @author Munkhtenger
 */
public class Yogi extends Base_sprite{
    int x_acc;
    int y_acc;     
    /**
     * Constructor for Yogi class that
     * initializes Yogi with given coordinate and image.
     * @param xs x-coordinate
     * @param ys y-coordinate
     */
    public Yogi(int xs, int ys) {
        super(xs, ys, "src/resources/yogi.png");        
    }
}
