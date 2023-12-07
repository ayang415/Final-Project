public class Grid {
    private String[][] grid;
    public Grid(int rows, int cols) {
        grid = new String[rows][cols];
        grid[0][0] = " ";
        for(int k = 1; k < rows; k++) {
            grid[0][k] = k + "";
        }
        int c = 1;
        for(char alphabet = 'A'; alphabet <='O'; alphabet++ ) {
            grid[c][0] = alphabet + "";
            c++;
        }
        for(int i = 1; i < rows; i++) {  
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
