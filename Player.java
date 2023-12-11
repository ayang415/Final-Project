import java.util.ArrayList;

public class Player {
    private ArrayList<Character> hand = new ArrayList<Character>();
    private ArrayList<String> preppedTiles = new ArrayList<String>();
    private int points = 0;
    private boolean win = false;

    public ArrayList<Character> getHand() {
        return hand;
    }

    public ArrayList<String> getPreppedTiles() {
        return preppedTiles;
    }

    public int getPoints() {
        return points;
    }

    public boolean getWin() {
      return win;
    }
}
