import java.util.Scanner;

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

            while(p1.getWin() == false && p2.getWin() == false) {
                String word, orient, swap;
                String location = "H8";
                int addAmt = 0;
                Player temp = new Player();
                if(turn % 2 == 1) { temp = p1; }
                else { temp = p2; }

                System.out.print("\033\143");
                controller.updateView(temp, p1, p2);

                System.out.println("1 - Pass");
                System.out.println("2 - Exchange");
                System.out.println("3 - Play");
                System.out.println("----------------");
                int option = myObj.nextInt();
                myObj.nextLine();
                if(option == 2) {
                    System.out.print("Choose letter to switch out: ");
                    swap = myObj.nextLine().toUpperCase();
                    controller.draw(temp.getHand(), 1);
                    controller.getPile().add(swap.charAt(0));
                    //do letter removal
                    temp.getHand().remove(swap.charAt(0));
                } else if(option == 3) {
                    System.out.print("What word do you want to place? ");
                    word = myObj.nextLine().toUpperCase();

                    addAmt = word.length();

                    if(turn != 1) {
                        System.out.print("Where do you want to place it (letter + number)? ");
                        location = myObj.nextLine().toUpperCase();
                    }
                    System.out.print("Place the word horizontal or vertical (h or v)? ");
                    orient = myObj.nextLine();
                    while(orient.equals("h") == false && orient.equals("v") == false) {
                        System.out.print("Invalid. Try again (h or v): ");
                        orient = myObj.nextLine();
                    }
                
                    while(controller.spacesCheck(word, location, orient, temp, turn, addAmt) == false) {
                        System.out.println("Invalid placement. Try again: ");
                        System.out.print("What word do you want to place? ");
                        word = myObj.nextLine().toUpperCase();
                        if(turn != 1) {
                            System.out.print("Where do you want to place it (letter + number)? ");
                            location = myObj.nextLine().toUpperCase();
                        }
                        System.out.print("Place the word horizontal or vertical (h or v)? ");
                        orient = myObj.nextLine();
                    }

                    controller.addWord(word, location, orient, temp);
                    //count points, add letters to hand
                    controller.draw(temp.getHand(), addAmt);
                    temp.points += addAmt;
                }

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
