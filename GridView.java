public class GridView {
    public void displayGrid(String[][] grid) {
        for(String[] row : grid) {
            for(String cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
}
