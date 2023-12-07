import java.util.Scanner;

//Store all words into a ArrayList after parsing with BufferList

public class Main {
    public static void main(String[] args) {
        int turn = 1;
        Scanner myObj = new Scanner(System.in);

        System.out.println("WELCOME TO SCRABBLE\n");
        System.out.println("1 - Instructions");
        System.out.println("2 - Play");
        System.out.println("3 - Quit");
        System.out.println("----------------");
        int choice = myObj.nextInt();

        while(choice != 1 && choice != 2 && choice != 3) {
            System.out.println("\nInvalid. Try again...");
            choice = myObj.nextInt();
        }

        if(choice == 1) {
            //instructions
        } else if(choice == 2) {
            Grid model = new Grid(16, 16);
            GridView view = new GridView();
            Player p1 = new Player();
            Player p2 = new Player();
            GridController controller = new GridController(model, view, p1, p2);

            controller.makeLibrary();
            controller.makePile();
            System.out.println(controller.getPile().size());
            controller.setCell(8, 8, "★");
            view.displayGrid(model.getGrid());

            System.out.println("What word do you want to place? ");
            String word = myObj.nextLine();
            System.out.println("Where do you want to place it (letter + number)? ");
            String location = myObj.nextLine();
            //if(can go 2 ways)
            System.out.println("Place the word horizontal or vertical (h or v)? ");
            String orient = myObj.nextLine();    
        } else {
            System.out.println("Thanks for playing");
        }
        myObj.close();
    }

    static void key() {
        //show what color corresponds to double points, etc
    }
}