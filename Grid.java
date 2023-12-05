public class Grid {
    private String[][] grid;
    public Grid(int rows, int cols) {
        grid = new String[rows][cols];
        grid[0][0] = " ";
        for(int k = 1; k < rows; k++) {
            grid[0][k] = k + "";
        }
        for(int i = 1; i < rows; i++) {
            grid[i][0] = "A"; //change
            for (int j = 1; j < cols; j++) {
                grid[i][j] = "■";
            }
        }
    }

    public void setCell(int row, int col, String value) {
        grid[row][col] = value;
    }

    public String[][] getGrid() {
        return grid;
    }
}
