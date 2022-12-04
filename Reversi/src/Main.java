import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Game game;
        System.out.print("PVE or PVP game mode?\ne - PVE\np - PVP\nEnter your answer: ");
        Scanner in = new Scanner(System.in);
        String answer = in.next();
        while (answer.length() != 1 || (!answer.equals("e") && !answer.equals("p"))) {
            System.out.print(Colors.Red + "Incorrect input!" + Colors.Reset +
                    "\n\nPVE or PVP game mode?\ne - PVE\np - PVP\nEnter your answer: ");
            in = new Scanner(System.in);
            answer = in.next();
        }
        if (answer.equals("e")) {
            System.out.print("\nYou are the first player ●, the computer is the second player ◯.\n");
            game = new Game(new Human((byte) 1), new Computer((byte) 2));
        } else {
            System.out.print("\nThe first player is ●, the second player is ◯.\n");
            game = new Game(new Human((byte) 1), new Human((byte) 2));
        }
        do {
            System.out.print("\nDo you want to finish this session?\ny - yes\nn - no\nEnter your answer: ");
            in = new Scanner(System.in);
            answer = in.next();
            while (answer.length() != 1 || (!answer.equals("y") && !answer.equals("n"))) {
                System.out.print(Colors.Red + "Incorrect input!" + Colors.Reset +
                        "\n\nDo you want to finish this session?\ny - yes\nn - no\nEnter your answer: ");
                in = new Scanner(System.in);
                answer = in.next();
            }
            if (answer.equals("y")) {
                break;
            }
            game.newRound();
            if (game.IsPVPMode()) {
                System.out.print("\nBest score of the first player: " + game.getBestResult().getFirst() + "\n");
                System.out.print("\nBest score of the second player: " + game.getBestResult().getSecond() + "\n");
            }
            else{
                System.out.print("\nYour best score: " + game.getBestResult().getFirst() + "\n");
            }
        } while (true);
    }
}