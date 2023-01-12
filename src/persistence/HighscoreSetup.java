package persistence;

/**
 * 
 * @author Munkhtenger
 */
public class HighscoreSetup {
    
    private String player_name;
    private int score;

    /**
     * Constructor for HighscoreSetup class.
     * @param player_name
     * @param score 
     */
    public HighscoreSetup(String player_name, int score) {
        this.player_name = player_name;
        this.score = score;
    }
    
    /**
     * setter for player name.
     * @param player_name 
     */
    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }
    
    /**
     * setter for score
     * @param score 
     */
    public void setScore(int score) {
        this.score = score;
    }
    
    /**
     * getter for player name
     * @return 
     */
    public String getPlayer_name() {
        return player_name;
    }
    
    /**
     * getter for score
     * @return 
     */
    public int getScore() {
        return score;
    }
    
}
