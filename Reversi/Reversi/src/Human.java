import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class for the human player
 */
public class Human extends Player {

    /**
     * Constructor to create new instance of the class with this number of player
     * @param numberOfPlayer Number of player
     */
    public Human(byte numberOfPlayer) {
        super(numberOfPlayer);
    }

    /**
     * Get coordinates for move from console, (-1,-1) - to cancel move
     * @return Coordinates for move
     */
    public PairOfBytes getXY() {
        int x = 0, y = 0;
        System.out.print("Enter coordinates (separated by space) for move 1<=x,y<=8 (x-vertical, y-horizontal) " +
                "or print two zero coordinates to cancel move\n");
        Scanner in;
        boolean correctInput;
        do {
            System.out.print("x y: ");
            correctInput = true;
            try {
                in = new Scanner(System.in);
                x = in.nextInt();
                y = in.nextInt();
            } catch (InputMismatchException e) {
                correctInput = false;
            }
            correctInput = correctInput && ((x >= 1 && x <= 8) || (x == 0 && y == 0));
            if (!correctInput) {
                System.out.print(Colors.Red + "Incorrect input!" + Colors.Reset +
                        "\n\nEnter coordinates (separated by space) for move 1<=x,y<=8 (x-vertical, y-horizontal) " +
                        "or print two zero coordinates to cancel move\n");
            }
        } while (!correctInput);
        return new PairOfBytes((byte) (x - 1), (byte) (y - 1));
    }

    /**
     * Get coordinates for next move that is possible or (-1,-1) - to cancel move
     * @param board Current state of the board
     * @return Coordinates for move
     */
    @Override
    public PairOfBytes GetCoordinatesForMove(Board board) {
        PairOfBytes coordinates;
        coordinates = getXY();
        if (!(coordinates.getFirst() == -1 && coordinates.getSecond() == -1)) {
            while (!board.CanMakeMove(coordinates.getFirst(), coordinates.getSecond(), getNumberOfPlayer())) {
                System.out.print(Colors.Red + "You can't make this move.\n\n" + Colors.Reset);
                coordinates = getXY();
                if (coordinates.getFirst() == -1 && coordinates.getSecond() == -1) {
                    break;
                }
            }
        }
        return coordinates;
    }
}
