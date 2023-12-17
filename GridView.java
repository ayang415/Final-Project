public class GridView {
    public void displayGrid(String[][] grid, Player p, Player p1, Player p2) {
        System.out.println("Scores: p1: " + p1.getPoints() + "   p2: " + p2.getPoints());
        System.out.println();
        for(String[] row : grid) {
            for(String cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("Player " + p.getId() + " Hand:");
        for(Character piece : p.getHand()) {
            System.out.print(piece + " ");
        }
        System.out.println();
        System.out.println();
    }
}
