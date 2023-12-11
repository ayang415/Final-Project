import java.util.ArrayList;

public class Player {
    private ArrayList<Character> hand = new ArrayList<Character>();
    private ArrayList<Character> preppedTiles = new ArrayList<Character>();
    private int points = 0;
    private boolean win = false;

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
