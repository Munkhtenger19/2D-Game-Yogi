package view;

import persistence.Db_setup;
import model.Game;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;
import java.awt.event.*;

/**
 * 
 * @author Munkhtenger
 */
public class gameGUI{
    public JFrame frame_game,frame_menu,frame_table;
    JPanel pane = new JPanel(new GridBagLayout());
    JMenuBar menuBarF,menuBarS;
    JMenu menuF,menuS;
    int widthGame = 1500;
    int heightGame = 800;    
    
    JButton startButton = new JButton("Start the game");
    JButton scoreButton = new JButton("See the scoreboard");
    Db_setup highscores = new Db_setup();    
    
    /**
     * Constructor for gameGUI class
     * that constructs main view of the game.
     */
    public gameGUI(){  
        frame_menu = new JFrame("Main Menu");
//        frame_menu.setBackground(Color.yellow);
        menuS = new JMenu("");
        menuBarS = new JMenuBar();
        JMenuItem res = new JMenuItem("Do you want to restart the game?");
        JMenuItem ex = new JMenuItem("Exit the game");
        frame_menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame_table = new JFrame("Highscore board");
        GridBagConstraints c = new GridBagConstraints();
        pane.add(startButton,c);
        pane.add(Box.createVerticalStrut(60));
        c.gridy=2;       
        pane.add(scoreButton,c);
//        ArrayList<persistence.Setup_HighScore> result = highscores.getHighs();
//        tableInit();

        scoreButton.addActionListener((ActionEvent e) -> {
            if(frame_game != null){
                frame_game.dispose();
            }
            JTable table = new JTable(highscores.BuildTable(),tableColNames());
            tableInit(table);        
            
            frame_table.setSize(790,660);
            frame_table.setLocationRelativeTo(null);
            frame_table.setVisible(true);
        });
        
        startButton.addActionListener((ActionEvent e) -> {
            mainSetup();
            frame_game.setVisible(true);           
        });
        pane.setBackground(Color.yellow);
        frame_menu.add(pane);
        //frame.menu
        
        frame_menu.setJMenuBar(menuBarS);
         
        res.addActionListener((ActionEvent e) -> {
            frame_menu.dispose();
            frame_menu.setVisible(true);
        });
        
        menuS.add(res);
        menuS.add(ex);
             
        ex.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });
        menuBarS.add(menuS);   
        
        frame_menu.setSize(650,400);
        frame_menu.setLocationRelativeTo(null);        
        frame_menu.setVisible(true);
    }
    
    /**
     * Returns each column names of the table.
     * @return 
     */
    private String[] tableColNames (){
        String[] col = {"Rank", "Player name", "Baskets score"};
        return col;
    }
    
    /**
     * Function for centering each cell data alignment
     * of the table.
     * @param cm 
     */
    void tableOpt(TableColumnModel cm){
        DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
        cr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        int i=0;
        while(i<3){
            cm.getColumn(i).setCellRenderer(cr);
            i++;
        }
    }
    
    /**
     * Function for dealing with table view.
     * @param table 
     */
    void tableInit(JTable table){        
        table.setRowHeight(60);
        table.setEnabled(false);
        JScrollPane scrollpane = new JScrollPane(table);
        TableColumnModel cm = table.getColumnModel();
        tableOpt(cm);
        frame_table.add(scrollpane);
    }
    
    /**
     * Function for initializing game on the frame
     * and adding menubar and menu items.
     */
    public void mainSetup(){
        frame_game = new JFrame("Collect the baskets Yogi!");
        frame_game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame_game.add(new Game(this));
                
        menuBarF = new JMenuBar();
        menuF = new JMenu("Options");
        frame_game.setJMenuBar(menuBarF);                
        
        JMenuItem restart = new JMenuItem("Restart the game");
        restart.addActionListener((ActionEvent e) -> {
            frame_game.dispose();
            mainSetup();
            frame_game.setVisible(true);
        });
        
        menuF.add(restart);        
        menuBarF.add(menuF);
        
        frame_game.setSize(widthGame,heightGame);
        frame_game.setLocationRelativeTo(null);
    }
}
