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
        int j = rand.nextInt(i+1);
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

    public void updateView(Player p, Player p1, Player p2) {
        view.displayGrid(model.getGrid(), p, p1, p2);
    }

    public void setCell(int row, int col, String value) {
        model.setCell(row, col, value);
    }

    public boolean inHand(String word, Player p) {
        //int count = 0;
        for(int i = 0; i < word.length(); i++) {
            if(!p.getHand().contains(word.charAt(i))) {
                for(int j = 0; j < p.getPreppedTiles().size(); j++) {
                    p.getHand().add(p.getPreppedTiles().get(j));
                }
                p.getPreppedTiles().clear();
                return false;
                
            }
            p.getHand().remove(Character.valueOf(word.charAt(i)));
            p.getPreppedTiles().add(word.charAt(i));
            //count++;
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

    public boolean checkAround(String word, int letter, int num, String orient) {
        String bottom = "";
        String right = "";
        int tempLetter = letter;
        int tempNum = num;
      
        while(tempLetter - 1 > 0 && !model.getGrid()[tempLetter - 1][tempNum].equals("■")) {
            tempLetter--;
        }
        bottom += model.getGrid()[tempLetter][tempNum];
        while(!model.getGrid()[tempLetter + 1][tempNum].equals("■")) {
            bottom += model.getGrid()[tempLetter + 1][tempNum];
            tempLetter++;
        }
        tempLetter = letter;
          
        while(tempNum - 1 > 0 && !model.getGrid()[tempLetter][tempNum - 1].equals("■")) {
            tempNum--;
        }
        right += model.getGrid()[tempLetter][tempNum];
        while(!model.getGrid()[tempLetter][tempNum + 1].equals("■")) {
            right += model.getGrid()[tempLetter][tempNum + 1];
            tempNum++;
        }
        tempNum = num;
      
        if(isWord(bottom) == false || isWord(right) == false) { return false; }

        return true;
    }

    public boolean spacesCheck(String word, String location, String orient, Player temp, int turn, int addAmt) {
        int count = 0;
        if(orient.equals("h")) {
            for(int i = 0; i < word.length(); i++) {
                int letter = location.charAt(0) - 64;
                int num = Integer.parseInt(location.substring(1)) + i;
                //out-of-bounds
                if(letter < 0 || letter > 15 || num < 0 || num > 15 ) {
                    return false;
                }
                //empty space and crossover
                if(!model.getGrid()[letter][num].equals("■") == true && !model.getGrid()[letter][num].equals("★") == true) {
                    String placedLetter = model.getGrid()[letter][num];
                    String attemptedLetter = word.charAt(i) + "";
                    if(!placedLetter.equals(attemptedLetter)) {
                        return false;
                    }
                    count++;
                    //check surrounding intersection
                    if(checkAround(word, letter, num, orient) == false) {
                        return false;
                    };
                    //Add attemptedLetter to hand if it's not already in hand
                    if(temp.getHand().contains(word.charAt(i)) == false) {
                        addAmt--;
                        temp.getHand().add(word.charAt(i));
                    }
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
                    if(checkAround(word, letter, num, orient) == false) {
                        return false;
                    };

                    if(temp.getHand().contains(word.charAt(i)) == false) {
                        addAmt--;
                        temp.getHand().add(word.charAt(i));
                    }
                }
            }
        }
    
        if(count == 0 && turn != 1) {
            return false;
        }

        if(inHand(word, temp) == false || isWord(word) == false) {
            if(inHand(word, temp) == true && isWord(word) == false) {
                for(int j = 0; j < temp.getPreppedTiles().size(); j++) {
                    temp.getHand().add(temp.getPreppedTiles().get(j));
                }
                temp.getPreppedTiles().clear();
            }
            return false;
        }
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
        p.getPreppedTiles().clear();
    }
}
