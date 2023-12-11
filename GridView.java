public class GridView {
    public void displayGrid(String[][] grid, Player p) {
        for(String[] row : grid) {
            for(String cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("Hand:");
        for(Character piece : p.getHand()) {
            System.out.print(piece + " ");
        }
        System.out.println();
        System.out.println();
    }
}
