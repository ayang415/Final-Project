import java.util.ArrayList;

public class Player {
    private ArrayList<Character> hand = new ArrayList<Character>();
    private ArrayList<Character> preppedTiles = new ArrayList<Character>();
    public boolean win = false;
    public int id = 0;
    public int points = 0;
    public int addAmt = 0;

    public Player(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Character> getHand() {
        return hand;
    }

    public ArrayList<Character> getPreppedTiles() {
        return preppedTiles;
    }

    public int getPoints() {
        return points;
    }

    public boolean getWin() {
      return win;
    }
}
