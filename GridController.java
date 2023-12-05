import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class GridController {
    private Grid model;
    private GridView view;
    private ArrayList<String> dict = new ArrayList<String>();

    public GridController(Grid model, GridView view) {
        this.model = model;
        this.view = view;
    }

    public void updateView() {
        view.displayGrid(model.getGrid());
    }

    public void setCell(int row, int col, String value) {
        model.setCell(row, col, value);
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
}
