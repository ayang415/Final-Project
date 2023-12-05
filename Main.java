import java.util.Scanner;

//Store all words into a ArrayList after parsing with BufferList

public class Main {
    public static void main(String[] args) {
        Grid model = new Grid(16, 16);
        GridView view = new GridView();
        GridController controller = new GridController(model, view);

        Scanner myObj = new Scanner(System.in);

        controller.makeLibrary();
        controller.setCell(2, 2, "X");
        view.displayGrid(model.getGrid());
        System.out.println("What word do you want to place? ");
        String word = myObj.nextLine();
        System.out.println("Where do you want to place it (letter + number)? ");
        String location = myObj.nextLine();
        //if(can go 2 ways)
        System.out.println("Place the word horizontal or vertical (h or v)? ");
        String orient = myObj.nextLine();

        myObj.close();
    }

    void key() {
        //show what color corresponds to double points, etc
    }
}