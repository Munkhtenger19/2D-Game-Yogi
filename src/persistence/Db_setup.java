package persistence;

import java.util.*;
import java.sql.*;

/**
 * 
 * @author Munkhtenger
 */
public class Db_setup {
    int size;
//    int leastSco;
    Connection conn;
    PreparedStatement insertS;
    PreparedStatement deleteS;
    
    /**
     * Constructor for the Db_setup class
     * that makes a connection with SQL server.
     */
    public Db_setup(){
        try{
            this.size=10;
            String Conn_URL="jdbc:mysql://localhost:3306/yogi_bear";
            String username="root";
            String password="Sorrytobotheryou0619";
            conn=DriverManager.getConnection(Conn_URL,username,password);
            
            String insertQ="INSERT INTO highscore (Name, Score) VALUES (?, ?)";
            insertS=conn.prepareStatement(insertQ);
            //System.out.println("s");
//            String deleteQ="DELETE FROM highscore VALUES (?, ?)";
            String deleteQ="DELETE FROM highscore WHERE Score=?";
            deleteS=conn.prepareStatement(deleteQ);
        }
        catch(SQLException ex){
            System.out.println("Error Code = " + ex.getErrorCode());
            System.out.println("SQL state = " + ex.getSQLState());
            System.out.println("Message = " + ex.getMessage());
        }
    }
    
    /**
     * Function for inserting the player
     * details into database.
     * @param player_name
     * @param pl_score 
     */
    private void insScore(String player_name, int pl_score){
        try{            
            insertS.setString(1, player_name);
            insertS.setInt(2, pl_score);
            insertS.executeUpdate();
        }
        catch(SQLException ex){
            System.out.println("Error Code = " + ex.getErrorCode());
            System.out.println("SQL state = " + ex.getSQLState());
            System.out.println("Message = " + ex.getMessage());
        }
    }

    /**
     * Function for deleting the player
     * details from the database.
     * @param score 
     */
    private void delScore(int score){
        try{
            deleteS.setInt(2,score);
            deleteS.executeUpdate();
        }
        catch(SQLException ex){
            System.out.println("Error Code = " + ex.getErrorCode());
            System.out.println("SQL state = " + ex.getSQLState());
            System.out.println("Message = " + ex.getMessage());
        }
        
    }
    
    /**
     * Function for inserting player details
     * into database when there are more than 10 players.
     * @param pla_name
     * @param score 
     */
    public void insertHighs(String pla_name, int score){
        String[][] s=BuildTable();
        ArrayList<HighscoreSetup> scores = getHighs();
        if (scores.size()>=size) {
            int lowestScore=scores.get(scores.size()-1).getScore();
            if (lowestScore<score) {
                delScore(lowestScore);
                insScore(pla_name,score);
            }
        } else {
            insScore(pla_name, score);
        }
        
    }
    
    /**
     * Function for querying all table data
     * from the database and returns the sorted
     * players data.
     * @return 
     */
    public ArrayList<HighscoreSetup> getHighs(){
        ArrayList<HighscoreSetup> highSco= new ArrayList<>();
        try{
            String q="SELECT * FROM highscore";
            Statement s=conn.createStatement();
            ResultSet resultSet=s.executeQuery(q);
            while (resultSet.next()){
                String pl_name=resultSet.getString("Name");
                int score=resultSet.getInt("Score");
                highSco.add(new HighscoreSetup(pl_name, score));
            }
            sortScores(highSco);
        }
        catch(SQLException ex){
            System.out.println("Error Code = " + ex.getErrorCode());
            System.out.println("SQL state = " + ex.getSQLState());
            System.out.println("Message = " + ex.getMessage());
        }
        return highSco;
    }
        
    /**
     * Function for constructing string matrix
     * and puts each player data in it.
     * @return 
     */
    public String[][] BuildTable(){
        String[][] table = new String[10][3];
        int row = 0;
        ArrayList<HighscoreSetup> score = getHighs();        
        for(HighscoreSetup hs : score){
            table[row][0] = Integer.toString(row+1);
            table[row][1] = hs.getPlayer_name();
            table[row][2] = Integer.toString(hs.getScore());
            row++;
        }
        return table;
    }    
    
    /**
     * Function for sorting scores by comparing them individually.
     * @param scores 
     */
    private void sortScores(ArrayList<HighscoreSetup> scores) {
        Collections.sort(scores,(HighscoreSetup hs1, HighscoreSetup hs2) -> hs2.getScore() - hs1.getScore());
    }
}
