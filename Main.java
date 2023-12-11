import java.util.Scanner;

//Store all words into a ArrayList after parsing with BufferList

public class Main {
    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);

        System.out.println("WELCOME TO SCRABBLE\n");
        System.out.println("1 - Instructions");
        System.out.println("2 - Play");
        System.out.println("3 - Quit");
        System.out.println("----------------");
        int choice = myObj.nextInt();
        myObj.nextLine();

        while(choice != 1 && choice != 2 && choice != 3) {
            System.out.println("\nInvalid. Try again...");
            choice = myObj.nextInt();
            myObj.nextLine();
        }

        if(choice == 1) {
            //instructions
        } else if(choice == 2) {
            Grid model = new Grid(16, 16);
            GridView view = new GridView();
            Player p1 = new Player();
            Player p2 = new Player();
            GridController controller = new GridController(model, view);
            int turn = 1;

            controller.makeLibrary();
            controller.makePile();
            controller.shuffle();
            controller.draw(p1.getHand(), 7);
            controller.draw(p2.getHand(), 7);
            //controller.setCell(8, 8, "★");
            
            while(p1.getWin() == false && p2.getWin() == false) {
                String word, orient;
                String location = "H8";
                Player temp = new Player();
                if(turn % 2 == 1) { temp = p1; }
                else { temp = p2; }
              
                System.out.println("\033[H\033[2J");
                controller.updateView(temp);
                
                System.out.print("What word do you want to place? ");
                word = myObj.nextLine().toUpperCase();
                while(controller.inHand(word, temp) == false || controller.isWord(word) == false) {
                    System.out.print("That word is not in your hand or is not valid. Try again: ");
                    word = myObj.nextLine().toUpperCase();
                }
                

                if(turn != 1) {
                    System.out.print("Where do you want to place it (letter + number)? ");
                    location = myObj.nextLine().toUpperCase();
                    /*while(!controller.isOpen(location)) {
                        System.out.print("Invalid location. Try again: ");
                        location = myObj.nextLine().toUpperCase();
                    }*/
                }
          
                
                System.out.print("Place the word horizontal or vertical (h or v)? ");
                orient = myObj.nextLine();
                while(orient.equals("h") == false && orient.equals("v") == false) {
                    System.out.print("Invalid. Try again (h or v): ");
                    orient = myObj.nextLine();
                }
              
                while(controller.spacesCheck(word, location, orient, turn) == false) {
                    System.out.println("Invalid placement. Try again: ");
                    if(turn != 1) {
                        System.out.print("Where do you want to place it (letter + number)? ");
                        location = myObj.nextLine().toUpperCase();
                    }
                    System.out.print("Place the word horizontal or vertical (h or v)? ");
                    orient = myObj.nextLine();
                }

                controller.addWord(word, location, orient, temp);
                /*if(all checks are true)
                    substring word, then place starting at location
                    if orient is h, move right, v moves down*/
                //count points, add letters to hand
                turn++;
            }  
        } else {
            System.out.println("Thanks for playing");
        }
        myObj.close();
    }

    static void key() {
        //show what color corresponds to double points, etc
    }
}
