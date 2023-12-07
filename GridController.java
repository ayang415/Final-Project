import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class GridController {
    private Grid model;
    private GridView view;
    private ArrayList<String> dict = new ArrayList<String>();
    private ArrayList<Character> pile = new ArrayList<Character>();

    public GridController(Grid model, GridView view) {
        this.model = model;
        this.view = view;
    }

    public void makeLibrary() {
        try (BufferedReader br = new BufferedReader(new FileReader("words.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                dict.add(line);
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

    public void updateView() {
        view.displayGrid(model.getGrid());
    }

    public void setCell(int row, int col, String value) {
        model.setCell(row, col, value);
    }

    public ArrayList<String> getDict() {
        return dict;
    }

    public ArrayList<Character> getPile() {
        return pile;
    }
}
