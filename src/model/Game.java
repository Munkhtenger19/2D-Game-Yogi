package model;

import view.gameGUI;
import java.util.List;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

/**
 * 
 * @author Munkhtenger
 */
public class Game extends JPanel implements ActionListener {
    Yogi yogi;
    int lives_available;
    boolean is_alive;
    int yogi_x_coor = 0;
    int yogi_y_coor = 30;
    Rectangle sArea = new Rectangle(yogi_x_coor,yogi_y_coor,70,80);
    int basketCollected;
    int game_level;
    int game_score;
    int basketNum;
    int RangerNum;
    int ObstacleNum;
    String[][] table;
    int width_fra=1500;
    int height_fra=800;    
    gameGUI gui;
//    int a;
    Timer elapsed_time;
    long  time_start;
    long get_time;
    int aux;
    List<Rangers> listRangers;
    List<Baskets> listBaskets;
    List<Obstacles> listObstacles;
    
    /**
     * Constructor for Game class that initializes
     * the game to be playable.
     * @param y GUI of the game
     */
    public Game(gameGUI y) {
        gui = y;
        lives_available = 3;
        game_level = 1;
        game_score = 0;
//      int s
        is_alive = true;
        basketNum = 1;
        RangerNum = 1;
        ObstacleNum = 2;
        addKeyListener(new YogiAdapter());
        setFocusable(true);
        setPreferredSize(new Dimension(width_fra, height_fra));
        get_time = System.currentTimeMillis();
        time_start = System.currentTimeMillis();
        yogi = new Yogi(yogi_x_coor,yogi_y_coor);
        
        obstacle_setup();
        Basket_setup();       
        ranger_setup();
        elapsed_time = new Timer(10, this);
        elapsed_time.start();
        
    }
    /**
     * Class for listening the keyboard keys W,A,S,D
     */
    class YogiAdapter extends KeyAdapter {
        /**
         * function for typing.
         * @param k key that is typed.
         */
        @Override
        public void keyTyped(KeyEvent k){
            return;
        }
        
        /**
         * Function to check which key is released and 
         * stops yogi's movement respectively.
         * @param k key that is released 
         */
        @Override
        public void keyReleased(KeyEvent k) {
            int gk = k.getKeyCode();
            if (gk == KeyEvent.VK_S||gk == KeyEvent.VK_W) {
                yogi.y_acc = 0;
            }
            if (gk == KeyEvent.VK_D ||gk == KeyEvent.VK_A) {
                yogi.x_acc = 0;
            }            
        }
        
        /**
         * Function to check which key from W,A,S,D is 
         * pressed and commences yogi's movement respectively.
         * @param k key that is pressed 
         */
        @Override
        public void keyPressed(KeyEvent k) {
            int gk = k.getKeyCode();
            if (gk == KeyEvent.VK_W) {
                yogi.y_acc = -1;
                System.out.println("W is pressed");
            }
            if (gk == KeyEvent.VK_S) {
                yogi.y_acc = 1;
                System.out.println("S is pressed");
            }
            if (gk == KeyEvent.VK_A) {
                yogi.x_acc = -1;
                System.out.println("A is pressed");
            }
            if (gk == KeyEvent.VK_D) {
                yogi.x_acc = 1;
                System.out.println("D is pressed");
            }           
        }
    }
    /**
     * Function to paint the sprites and frames of the game
     * and initializes option panes and saving the name of
     * the player into database.
     * @param graphics base to paint the components.
     */
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        ImageIcon aux = new ImageIcon("src/resources/nature.jpg");
        ImageIcon aux2 = new ImageIcon("src/resources/yogi_menu.jpg");
        Image bg = aux.getImage();
        
        graphics.drawImage(bg,0,0,null);
        
        if (!is_alive) {
            String player_name = JOptionPane.showInputDialog(this, "Player name: ", "You lose!", JOptionPane.PLAIN_MESSAGE);
            System.out.println(player_name);
            if(player_name != null){                   
                new persistence.Db_setup().insertHighs(player_name, game_score);                     
            }
            //int s = JOptionPane.showConfirmDialog(gui, "Start again?", "Game Over", JOptionPane.PLAIN_MESSAGE);
            int op = JOptionPane.showConfirmDialog(this, "Start again?", "Game Over", JOptionPane.PLAIN_MESSAGE);
            System.out.println(op);
            if (op != JOptionPane.OK_OPTION) {
                System.exit(0);
            }
            else{        
                gui.frame_game.dispose();
                gui.mainSetup();
                gui.frame_game.setVisible(true); 
            }
        } else {
            get_time = System.currentTimeMillis();
            graphics.drawString("Lives left: " + lives_available, 15, 18);
            graphics.drawString("Collected baskets: " + game_score, 420, 18);
            graphics.drawString("Level: " + game_level, 950, 18);
            graphics.drawString("Elapsed time: "+(int)((get_time/1000-time_start/1000)) ,1370,18);
            if (yogi.getVisibility()) {
                graphics.drawImage(yogi.getImg(), yogi.getX_coor(), yogi.getY_coor(), this);
            }
            for (Baskets each : listBaskets) {
                if (each.getVisibility())graphics.drawImage(each.getImg(), each.getX_coor(), each.getY_coor(), this);
            }
            for (Rangers each : listRangers) {
                if (each.getVisibility())graphics.drawImage(each.getImg(), each.getX_coor(), each.getY_coor(), this);
            }
            for (Obstacles each : listObstacles) {
                if (each.getVisibility())graphics.drawImage(each.getImg(), each.getX_coor(), each.getY_coor(), this);
            }
        }
        Toolkit.getDefaultToolkit().sync();
    }
    
    /**
     * Function that checks game level and
     * if the level is below 10, the game gets harder
     * else it shows a pane to the winner.
     */
    public void gameRefresh(){
        if(game_level<10){            
//            is_alive = true;
            basketNum=basketNum+1;
            ObstacleNum=ObstacleNum+1;
            RangerNum=RangerNum+1;
//            Basket_setup();
            yogi = new Yogi(yogi_x_coor, yogi_y_coor);
            obstacle_setup();
            Basket_setup();
            ranger_setup();
            game_level++;
        }else if(game_level==10){                     
            is_alive = false;
            
            String player_name = JOptionPane.showInputDialog(this, "Enter your player name: ", "Amazing skill!", JOptionPane.PLAIN_MESSAGE);
//            while(player_name!=null){
//                new yogi.Db_setup().insertHighs(player_name, game_score);
//            }
            if(player_name != null)  {                   
                new persistence.Db_setup().insertHighs(player_name, game_score);
            }
            System.out.println(player_name);
            int s = JOptionPane.showConfirmDialog(this, "Start again?", "Game Over", JOptionPane.PLAIN_MESSAGE);
//            while(s != JOptionPane.OK_OPTION) { 
//                System.exit(0);
//            }
            System.out.println(s);
            if (s != JOptionPane.OK_OPTION) { 
                System.exit(0);
            }
            else{
//                gui.dispose();
                gui.frame_game.dispose();
//                gui.setVisible(true);
                gui.mainSetup();
                gui.frame_game.setVisible(true);
            }
        }
    }    
    
    /**
     * Function for generating and placing obstacles
     * randomly.
     */
    private void ranger_setup(){
        int i=0;
        listRangers = new ArrayList<>();
        while(i<RangerNum){
            Random rand = new Random();
            boolean finished = false;
            while (!finished){
                boolean is_done = true;
                Rangers aux = new Rangers(rand.nextInt(width_fra-200)+45, rand.nextInt(height_fra-200)+45);
                Rectangle rect_aux = aux.GetRect();
               
                for(int z=0;z<listObstacles.size()&&is_done;z++){
//                while (is_done){
                    Obstacles obstacle_b = listObstacles.get(z);
                    Rectangle obstacle_rect = obstacle_b.GetRect();
                    if (sArea.intersects(rect_aux)||sArea.contains(rect_aux)||(obstacle_rect.contains(rect_aux)) || (obstacle_rect.intersects(rect_aux)) ){
                        is_done = false;
                    }
                }
                // while(is_done){
                if (is_done){
                    listRangers.add(aux);
                    finished = true;
                }
            }
            i++;
        }
    }
    
    /**
     * Function for generating and placing baskets
     * randomly.
     */
    private void Basket_setup(){
        int i=0;
        listBaskets = new ArrayList<>();
        while(i<basketNum){
            boolean finished = false;
            Random rand = new Random();
            // 
            while (!finished){
                boolean is_done = true;
                Baskets aux = new Baskets(rand.nextInt(width_fra-270)+50,rand.nextInt(height_fra-250)+50);
                Rectangle auxRect = aux.GetRect();
                int z=0;
//                for(int z=0;z<listObstacles.size()&&is_done;z++){
                while (is_done && z<listObstacles.size()){
                    Obstacles obstacleB = listObstacles.get(z);
                    Rectangle obstacle_rect = obstacleB.GetRect();
                    if ((obstacle_rect.intersects(auxRect))||(obstacle_rect.contains(auxRect))){
                        is_done = false;
                    }
                    z++;
                }
                if (is_done){
                    listBaskets.add(aux);
                    finished = true;
                }
            }
            i++;
        }
    }
    
    /**
     * Function that handles the game's
     * action when player triggers any kind of event.
     * @param k any action
     */
    @Override
    public void actionPerformed(ActionEvent k) {
        if (!is_alive) {
            elapsed_time.stop();
        }
        if(yogi.getVisibility()){
            yogiMove();
        }
        if(!listBaskets.isEmpty()){
            for (int i = 0; i < listBaskets.size(); i++){
                Baskets each = listBaskets.get(i);
                if(!each.getVisibility()){
                    listBaskets.remove(i);
                }
            }
        }else{
            gameRefresh();
        }
        rangersMove();
        basket_yogi();
        blockCheck();
        yogi_obstacle();
        repaint();
    }
    
    /**
     * Function for handling Yogi's movement
     * and checks for the boundaries.
     */
    public void yogiMove(){
        yogi.x_coor+=yogi.x_acc;
        yogi.y_coor+=yogi.y_acc;
        
        if (yogi.x_coor > width_fra-(yogi.width_size+22)){
            yogi.x_coor=width_fra-(yogi.width_size+22);
        }
        if(yogi.y_coor>height_fra-(yogi.height_size+65)){
            yogi.y_coor=height_fra-(yogi.height_size+65);            
        }
        if (yogi.y_coor<=0){yogi.y_coor=1;}
        if (yogi.x_coor<=0){yogi.x_coor=1;}
    }
    
    /**
     * Function for handling Ranger's movement
     * and direction change when frame boundaries
     * encounter.
     */
    public void rangersMove(){       
        for (Rangers each : listRangers){           
            if(!each.dir && !each.dirChange){
                each.y_coor-=5;
                if(each.y_coor<=0){
                    each.dirChange = true;
                }
            }
            else if(!each.dir && each.dirChange){
                each.y_coor += 5;
                if(each.height_size+each.y_coor>=height_fra-60) {
                    each.dirChange = false;
                }
            }
            else if(each.dir && !each.dirChange){
                each.x_coor-=5;
                if(each.x_coor<=0){
                    each.dirChange = true;
                }
            }
            else if(each.dir && each.dirChange){
                each.x_coor += 5;
                if(each.x_coor>=width_fra-55) {
                    each.dirChange = false;
                }
            }
        }
    }
    
    /**
     * Function for handling when yogi touches the basket.
     */
    public void basket_yogi(){
        Rectangle yogi_rec = yogi.GetRect();
        for(Baskets each:listBaskets){
            Rectangle aux2 = each.GetRect();
            if(yogi_rec.intersects(aux2)){
                each.setVisibility(false);
                game_score=game_score+1;
            }
        }
    }
    
    /**
     * Function that handles Ranger coordinates
     * and direction by checking for its directions.
     * @param each given ranger
     */
    public void ranger_check(Rangers each){
        if (!each.dir&&!each.dirChange){                    
            each.setY_coor(each.getY_coor()+1);
            each.dirChange=true;
        }
        else if(each.dir&&!each.dirChange){
            each.setX_coor(each.getX_coor()+1);
            each.dirChange=true;
        }
        else if(each.dir&& each.dirChange){
            each.setX_coor(each.getX_coor()-1);
            each.dirChange=false;
        }           
        else if(!each.dir&& each.dirChange){
            each.setY_coor(each.getY_coor()-1);
            each.dirChange=false;
        }
    }
    
    /**
     * Function that handles situation where
     * yogi run into obstacle.
     */
    public void yogi_obstacle(){
        Rectangle yogi_rec = yogi.GetRect(); 
        for(Obstacles each : listObstacles){
            Rectangle raux = each.GetRect();
            if(yogi_rec.intersects(raux)){
                if(yogi.y_acc>0){yogi.setY_coor(yogi.getY_coor()-3);}
                if(yogi.x_acc>0){yogi.setX_coor(yogi.getX_coor()-3);}
                if(yogi.y_acc<0){yogi.setY_coor(yogi.getY_coor()+3);}
                if(yogi.x_acc<0){yogi.setX_coor(yogi.getX_coor()+3);}    
            }
        }
    }
    
    /**
     * Function that handles when ranger
     * encounter an obstacle and yogi touches
     * ranger and yogi has no more life.
     */
    public void blockCheck() {
        Rectangle yogi_rec = yogi.GetRect(); 
        for(Rangers each : listRangers){
            Rectangle aux = each.GetRect();
            Rectangle aux2 = each.RectangleSprite();
            if(aux.contains(sArea)||sArea.contains(aux)||sArea.intersects(aux)|| aux.intersects(sArea)){
                ranger_check(each);    
            }
            if (!sArea.contains(yogi_rec)&& yogi_rec.intersects(aux2) ){
                yogi = new Yogi(yogi_x_coor, yogi_y_coor);
                lives_available--;                              
                if (lives_available==0){
                    is_alive = false;
                    String pl_name = JOptionPane.showInputDialog(this, "Enter your player name: ", "You lost!", JOptionPane.PLAIN_MESSAGE);
                    System.out.println(pl_name);
                    if(pl_name != null)  {                   
                        new persistence.Db_setup().insertHighs(pl_name, game_score);      
                    }
                    int s = JOptionPane.showConfirmDialog(this, "Restart?", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                    if (s != JOptionPane.OK_OPTION) {
                        System.exit(0);
                    }
                    else{
                        gui.frame_game.dispose();
                        gui.mainSetup();
                        gui.frame_game.setVisible(true);
                    }
                }
            }
            for(Obstacles obstacle : listObstacles){
                Rectangle ob_aux = obstacle.GetRect();
                if(aux.intersects(ob_aux)){
                    ranger_check(each);
                }
            }
        }
    }
    
    /**
     * Function for generating and placing
     * obstacles randomly.
     */
    private void obstacle_setup(){
        listObstacles = new ArrayList<>();
        int i=0;
        while(i<ObstacleNum){
            Random rand = new Random();
            if (i!=0) {
                boolean possible = true,finished = false;                
                while (possible&&!finished){
                    boolean is_done = true;
                    Obstacles aux = new Obstacles(rand.nextInt(width_fra-220)+35, rand.nextInt(height_fra-220)+35);
                    Rectangle aux_rect = aux.obstacleRect();
//                    Rectangle aux_rect = aux.GetRect();

                    for(int z=0;z<listObstacles.size()&&is_done;z++){
//                    while (is_done){
                        Obstacles obstacle_b = listObstacles.get(z);
                        Rectangle obstacle_rect = obstacle_b.obstacleRect();
//                        Rectangle obstacle_rect = obstacle_b.GetRect();
                        if (aux_rect.contains(sArea) ||obstacle_rect.contains(aux_rect)|| aux_rect.intersects(sArea)|| (obstacle_rect.intersects(aux_rect)) ){
                            is_done = false;
                        }
                    }

                    if (is_done){
                        listObstacles.add(aux);
                        finished = true;
                    }                                                     
                }              
            }else{
                 listObstacles.add(new Obstacles(rand.nextInt(width_fra-220)+35, rand.nextInt(height_fra-220)+35));
            }
            i++;
        }
    }
    
}
