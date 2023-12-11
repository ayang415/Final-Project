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
                return false;
            }
            p.getHand().remove('e');
            //i--;
            p.getPreppedTiles().add(word.charAt(i) + "");
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
        for(int i = 0; i < word.length(); i++) {
          //modify for if going h or v
            int letter = location.charAt(0) - 64 + i;
            int num = Integer.parseInt(location.substring(1)) + i;
            if(letter < 0 || letter > 15 || num < 0 || num > 15 ) {
                return false;
            }
            if(!model.getGrid()[letter][num].equals("■") && !model.getGrid()[letter][num].equals("★")) {
                String placedLetter = model.getGrid()[letter][num];
                String attemptedLetter = word.charAt(i) + "";
                if(!placedLetter.equals(attemptedLetter)) {
                    return false;
                }
                count++;
                //append both the letter that is currently on the space and the attempted value and compare- if the same, good, if not return false
                //this if statement must trigger for the word to be true (is it touching)
            }
        }
        if(count == 0 && turn != 1) {
          return false;
        }
        //is it touching anything
        //does it make a valid word (isWord)
        return true;
    }

    public void addWord(String word, String location, String orient, Player p) {
        /*ArrayList<String> seperated = new ArrayList<String>();
        for(int i = 0; i < word.length(); i++) {
            seperated.add(word.substring(i, i + 1));
        }*/
        if(orient.equals("h")) {
            for(int i = 0; i < word.length(); i++) {
                int letter = location.charAt(0) - 64;
                int num = Integer.parseInt(location.substring(1)) + i;
                setCell(letter, num, p.getPreppedTiles().get(i));
            }
        } else if(orient.equals("v")) {
            for(int i = 0; i < word.length(); i++) {
                int letter = location.charAt(0) - 64 + i;
                int num = Integer.parseInt(location.substring(1));
                setCell(letter, num, p.getPreppedTiles().get(i));
            }
        }
        //remove from hand
        /*for(int i = 0; i < seperated.size(); i++) {
            for(int j = 0; j < p.getHand().size(); j++) {
                System.out.println(seperated.get(i));
                System.out.println(p.getHand().get(j)+"");
                if(seperated.get(i).equals(p.getHand().get(j) + "")) {
                    System.out.println("test");
                    p.getHand().remove(p.getHand().get(j));
                    break;
                }
            }
        }*/
    }
}
