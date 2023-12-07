import java.util.ArrayList;

public class Player {
    private ArrayList<Character> hand = new ArrayList<Character>();
    private int points = 0;

    public ArrayList<Character> getHand() {
        return hand;
    }

    public int getPoints() {
        return points;
    }
}
