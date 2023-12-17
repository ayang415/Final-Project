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
            System.out.print("\nInvalid. Try again... ");
            choice = myObj.nextInt();
            myObj.nextLine();
        }

        if(choice == 1) {
            System.out.println();
            System.out.println("This is clasic Scrabble");
            System.out.println("Go to the following website for the official rules: ");
            System.out.println("      https://www.scrabblepages.com/scrabble/rules/");
            System.out.println("Other than that, read the prompts and type in the prefered action");
            System.out.println("Also not that the scoring is only based on the length of the word due to time constraints. I hope to add more robust scoring soon.");
            System.out.println();
            System.out.println("2 - Play");
            System.out.println("3 - Quit");
            System.out.println("----------------");
            choice = myObj.nextInt();
            myObj.nextLine();
            while(choice != 2 && choice != 3) {
                System.out.print("\nInvalid. Try again... ");
                choice = myObj.nextInt();
                myObj.nextLine();
            }
        }
        if(choice == 2) {
            Grid model = new Grid(16, 16);
            GridView view = new GridView();
            Player p1 = new Player(1);
            Player p2 = new Player(2);
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
                
                boolean letterPlaced = false;
                Player temp = new Player(0);
                if(turn % 2 == 1) { 
                    temp = p1; 
                    temp.id = 1;
                } else { 
                    temp = p2; 
                    temp.id = 2;
                }

                //add time wait- in progress
                System.out.print("\033\143");
                controller.updateView(temp, p1, p2);

                System.out.println("1 - Pass");
                System.out.println("2 - Swap Letters");
                System.out.println("3 - Play a Word");
                System.out.println("----------------");
                int option = myObj.nextInt();
                myObj.nextLine();

                while(option != 1 && option != 2 && option != 3) {
                    System.out.print("\nInvalid. Try again... ");
                    option = myObj.nextInt();
                    myObj.nextLine();
                }
              
                System.out.println();
                if(option == 2) {
                    System.out.print("Choose letter to switch out: ");
                    swap = myObj.nextLine().toUpperCase();
                    while(temp.getHand().contains(swap.charAt(0)) == false) {
                        System.out.print("Invalid letter. Try again... ");
                        swap = myObj.nextLine().toUpperCase();
                    }
                    if(controller.getPile().size() != 0) {
                        controller.draw(temp.getHand(), 1);
                        controller.getPile().add(swap.charAt(0));
                        temp.getHand().remove(Character.valueOf(swap.charAt(0)));
                    } else {
                        System.out.println("No letters left in the pile.");
                    }
                } else if(option == 3) {
                    if(!model.getGrid()[8][8].equals("★")){
                        letterPlaced = true;
                    }
                    
                    System.out.print("What word do you want to place? ");
                    word = myObj.nextLine().toUpperCase();
                    temp.addAmt = word.length();

                    if(letterPlaced) {
                        System.out.print("What coordinate point/space would you like to start your word at (letter + number)? ");
                        location = myObj.nextLine().toUpperCase();
                    }
                  
                    System.out.print("Place the word horizontal or vertical (h or v)? ");
                    orient = myObj.nextLine();
                    while(orient.equals("h") == false && orient.equals("v") == false) {
                        System.out.print("Invalid. Try again (h or v): ");
                        orient = myObj.nextLine();
                    }
                    
                    while(controller.spacesCheck(word, location, orient, temp, letterPlaced) == false) {
                        System.out.println("Invalid placement. Try again: ");
                        System.out.print("What word do you want to place? ");
                        word = myObj.nextLine().toUpperCase();
                        if(letterPlaced) {
                            System.out.print("Where do you want to place it (letter + number)? ");
                            location = myObj.nextLine().toUpperCase();
                        }
                        System.out.print("Place the word horizontal or vertical (h or v)? ");
                        orient = myObj.nextLine();
                    }
                  
                    controller.addWord(word, location, orient, temp);
                    //count points, add letters to hand
                    if(controller.getPile().size() != 0) {
                        controller.draw(temp.getHand(), temp.addAmt);
                    }
                    temp.points += word.length();
                    if(controller.getPile().size() == 0 && temp.getHand().size() == 0) {
                        temp.win = true;
                    }
                }
                turn++;
            } 
            System.out.println("You win!");
        } 
        if(choice == 3) {
            System.out.println("Thanks for playing");
        }
        myObj.close();
    }

    static void key() {
        //show what color corresponds to double points, etc
        //not used currently b/c of the lack of a robust point system
    }
}
