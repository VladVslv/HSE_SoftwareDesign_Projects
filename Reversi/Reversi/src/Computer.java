import java.util.Scanner;

/**
 * Class for the computer player
 */
public class Computer extends Player {

    /**
     * Is advance mode enabled?
     */
    boolean advancedMode;

    /**
     * Constructor to create new instance of the class with this number of player
     * @param numberOfPlayer Number of player
     */
    public Computer(byte numberOfPlayer) {
        super(numberOfPlayer);
        System.out.print("\nEnable advanced mode?\ny - yes\nn - no\nEnter your answer: ");
        Scanner in = new Scanner(System.in);
        String answer = in.next();
        while (answer.length() != 1 || (!answer.equals("y") && !answer.equals("n"))) {
            System.out.print(Colors.Red + "Incorrect input!" + Colors.Reset +
                    "\n\nEnable advanced mode?\ny - yes\nn - no\nEnter your answer: ");
            in = new Scanner(System.in);
            answer = in.next();
        }
        advancedMode = answer.equals("y");
    }

    /**
     * Get coordinates for move in easy mode
     * @param board Playing field
     * @return Coordinates for move
     */
    PairOfBytes EasyModeGetCoordinates(Board board) {
        byte x = 0, y = 0;
        double max = 0;
        double points;
        for (byte i = 0; i < 8; ++i) {
            for (byte j = 0; j < 8; ++j) {
                points = board.PointsForMove(i, j, getNumberOfPlayer());
                if (points > max) {
                    x = i;
                    y = j;
                    max = points;
                }
            }
        }
        return new PairOfBytes(x, y);
    }

    /**
     * Get coordinates for move in advanced mode
     * @param board Playing field
     * @return Coordinates for move
     */
    PairOfBytes AdvancedModeGetCoordinates(Board board) {
        Board newBoard;
        byte x = 0, y = 0;
        double max = -500;
        double points;
        for (byte i = 0; i < 8; ++i) {
            for (byte j = 0; j < 8; ++j) {
                if (board.CanMakeMove(i, j, getNumberOfPlayer())) {
                    newBoard = new Board(board);
                    points = newBoard.PointsForMove(i, j, getNumberOfPlayer());
                    newBoard.TryToMakeMove(i, j, getNumberOfPlayer());
                    points -= GetMaxPointsInEasyMode(newBoard);
                    if (points > max) {
                        x = i;
                        y = j;
                        max = points;
                    }
                }
            }
        }
        return new PairOfBytes(x, y);
    }

    /**
     * Get max points for the next move in easy mode
     * @param newBoard Playing field
     * @return Points
     */
    private double GetMaxPointsInEasyMode(Board newBoard) {
        double newMax = 0;
        double newPoints;
        for (byte k = 0; k < 8; ++k) {
            for (byte l = 0; l < 8; ++l) {
                newPoints = newBoard.PointsForMove(k, l, getNumberOfPlayer());
                newMax = Math.max(newPoints, newMax);
            }
        }
        return newMax;
    }

    /**
     * Get coordinates for move
     * @param board Playing field
     * @return Coordinates for move
     */
    @Override
    public PairOfBytes GetCoordinatesForMove(Board board) {
        if (!advancedMode) {
            return EasyModeGetCoordinates(board);
        } else {
            return AdvancedModeGetCoordinates(board);
        }
    }
}
