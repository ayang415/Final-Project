import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

public class GridController {
    private Grid model;
    private GridView view;
    private ArrayList<String> dict = new ArrayList<String>();
    private ArrayList<Character> pile = new ArrayList<Character>();

    public GridController(Grid model, GridView view) {
        this.model = model;
        this.view = view;
    }

    public ArrayList<String> getDict() {
        return dict;
    }

    public ArrayList<Character> getPile() {
        return pile;
    }

    public void makeLibrary() {
        try (BufferedReader br = new BufferedReader(new FileReader("words.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                dict.add(line.toUpperCase());
            }
        } catch(Exception ex) {

        }
    }

    public void makePile() {
        try (BufferedReader br = new BufferedReader(new FileReader("letterFrequencies.txt"))) {
            br.readLine();
            char line = (char)br.read();
            br.skip(1);
            int count = 12;
            for(int i = 0; i < count; i++) {
                    pile.add(line);
            }
            while ((br.readLine()) != null) {
                line = (char)br.read();
                br.skip(1);
                count = br.read() - 48;
                for(int i = 0; i < count; i++) {
                    pile.add(line);
                }
            }
        } catch(Exception ex) {

        }
    }

    public void shuffle() {
      Random rand = new Random(System.currentTimeMillis());
      for (int i = pile.size() - 1; i > 0; i--) {
        int j = rand.nextInt(i+1);/*rand() % (i + 1);*/
        Character temp = pile.get(j);
        pile.set(j, pile.get(i));
        pile.set(i, temp);
      }
    }

    public void draw(ArrayList<Character> hand, int num) {
        for(int i = 0; i < num; i++) {
            hand.add(pile.get(0));
            pile.remove(0);
        }
    }

    public void updateView(Player p) {
        view.displayGrid(model.getGrid(), p);
    }

    public void setCell(int row, int col, String value) {
        model.setCell(row, col, value);
    }

    public boolean inHand(String word, Player p) {
        int count = 0;
        for(int i = 0; i < word.length(); i++) {
            if(!p.getHand().contains(word.charAt(i))) {
                /*if(count > 0) {
                    for(int j = 0; j < p.getPreppedTiles().size(); j++) {
                        p.getHand().add(p.getPreppedTiles().get(j));
                    }
                }*/
                return false;
            }
            p.getHand().remove(Character.valueOf(word.charAt(i)));
            p.getPreppedTiles().add(word.charAt(i));
            count++;
        }
        return true;
    }

    public boolean isWord(String word) {
        for(int i = 0; i < word.length(); i++) {
            for(int j = 0; j < dict.size(); j++) {
                if(dict.get(j).equals(word)) {
                    return true;
                }
            }
        }
        return false;
    }

    /*public boolean checkIntersect(String word, String location, String orient) {
      
    }*/

    public boolean spacesCheck(String word, String location, String orient, int turn) {
        //is it blank or does it go out of bounds
        int count = 0;
        if(orient.equals("h")) {
            for(int i = 0; i < word.length(); i++) {
                int letter = location.charAt(0) - 64;
                int num = Integer.parseInt(location.substring(1)) + i;
                if(letter < 0 || letter > 15 || num < 0 || num > 15 ) {
                    return false;
                }
                if(!model.getGrid()[letter][num].equals("■") == true && !model.getGrid()[letter][num].equals("★") == true) {
                    String placedLetter = model.getGrid()[letter][num];
                    String attemptedLetter = word.charAt(i) + "";
                    if(!placedLetter.equals(attemptedLetter)) {
                        return false;
                    }
                    count++;
                }
            }
        } else if(orient.equals("v")) {
            for(int i = 0; i < word.length(); i++) {
                int letter = location.charAt(0) - 64 + i;
                int num = Integer.parseInt(location.substring(1));
                if(letter < 0 || letter > 15 || num < 0 || num > 15 ) {
                    return false;
                }
                if(!model.getGrid()[letter][num].equals("■") == true && !model.getGrid()[letter][num].equals("★") == true) {
                    String placedLetter = model.getGrid()[letter][num];
                    String attemptedLetter = word.charAt(i) + "";
                    if(!placedLetter.equals(attemptedLetter)) {
                        return false;
                    }
                    count++;
                }
            }
        }
        /*for(int i = 0; i < word.length(); i++) {
            //modify for if going h or v
            if(orient == "h") {
                int letter = location.charAt(0) - 64;
                int num = Integer.parseInt(location.substring(1)) + i;
            } else {
                int letter = location.charAt(0) - 64 + i;
                int num = Integer.parseInt(location.substring(1));
            }
            
            if(letter < 0 || letter > 15 || num < 0 || num > 15 ) {
                return false;
            }
            if(!model.getGrid()[letter][num].equals("■") == true && !model.getGrid()[letter][num].equals("★") == true) {
                String placedLetter = model.getGrid()[letter][num];
                String attemptedLetter = word.charAt(i) + "";
                if(!placedLetter.equals(attemptedLetter)) {
                    return false;
                }
                count++;
            }
        }*/
        if(count == 0 && turn != 1) {
          return false;
        }
        //is it touching anything
        //does it make a valid word (isWord)
        return true;
    }

    public void addWord(String word, String location, String orient, Player p) {
        if(orient.equals("h")) {
            for(int i = 0; i < word.length(); i++) {
                int letter = location.charAt(0) - 64;
                int num = Integer.parseInt(location.substring(1)) + i;
                setCell(letter, num, p.getPreppedTiles().get(i) + "");
            }
        } else if(orient.equals("v")) {
            for(int i = 0; i < word.length(); i++) {
                int letter = location.charAt(0) - 64 + i;
                int num = Integer.parseInt(location.substring(1));
                setCell(letter, num, p.getPreppedTiles().get(i) + "");
            }
        }
    }
}
