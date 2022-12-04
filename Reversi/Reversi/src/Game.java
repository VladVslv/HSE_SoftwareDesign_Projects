import java.util.ArrayList;

/**
 * Class of game between players
 */
public class Game {
    /**
     * Players
     */
    private final ArrayList<Player> players;
    /**
     * Best result of players
     */
    private final PairOfBytes bestResult;
    /**
     * Constructor to create game between two players
     * @param firstPlayer First player
     * @param secondPlayer Second player
     */
    Game(Player firstPlayer, Player secondPlayer) {
        players = new ArrayList<>();
        players.add(firstPlayer);
        players.add(secondPlayer);
        bestResult = new PairOfBytes((byte) 0, (byte) 0);
    }

    /**
     * Get indo abour game mode
     * @return Is game in PVP mode?
     */
    boolean IsPVPMode() {
        for (Player p : players) {
            if (p instanceof Computer) {
                return false;
            }
        }
        return true;
    }

    /**
     * Play a new round of the game
     */
    void newRound() {
        Board board = new Board();
        boolean gameEnded;
        byte firstPlayerScore, secondPlayerScore;
        do {
            gameEnded = true;
            for (Player p : players) {
                if (board.ExistsMove(p.getNumberOfPlayer())) {
                    gameEnded = false;
                    PairOfBytes coordinates;
                    PrintGameInfo(board, p);
                    do {
                        coordinates = p.GetCoordinatesForMove(board);
                        if (coordinates.getFirst() != -1 && coordinates.getSecond() != -1) {
                            break;
                        }
                        try {
                            board.CancelMove(p.getNumberOfPlayer());
                            PrintGameInfo(board, p);
                        } catch (RuntimeException e) {
                            System.out.print(Colors.Red + e.getMessage() + "\n\n" + Colors.Reset);
                        }
                    } while (true);
                    board.TryToMakeMove(coordinates.getFirst(), coordinates.getSecond(), p.getNumberOfPlayer());
                }
            }
        } while (!gameEnded);
        firstPlayerScore = board.GetScore((byte) 1);
        secondPlayerScore = board.GetScore((byte) 2);
        PrintRoundResult(board, firstPlayerScore, secondPlayerScore);
        bestResult.setFirst(bestResult.getFirst() > firstPlayerScore ?
                bestResult.getFirst() : firstPlayerScore);
        bestResult.setSecond(bestResult.getSecond() > secondPlayerScore ?
                bestResult.getSecond() : secondPlayerScore);
    }

    public PairOfBytes getBestResult() {
        return bestResult;
    }

    /**
     * Print info of the game for current move
     * @param board Current playing field
     * @param p Player, that is making the move
     */
    private void PrintGameInfo(Board board, Player p) {
        System.out.print("\nScore of the first player ● : " + board.GetScore((byte) 1) + "\n");
        System.out.print("Score of the second player ◯ : " + board.GetScore((byte) 2) + "\n");
        board.Print(p.getNumberOfPlayer());
        if (IsPVPMode()) {
            if (p.getNumberOfPlayer() == 1) {
                System.out.print("Move of the first player ●.\n");
            } else {
                System.out.print("Move of the second player ◯.\n");
            }
        }
    }

    /**
     * Print result of the game
     * @param board Final state of the board
     * @param firstPlayerScore First player
     * @param secondPlayerScore Second player
     */
    private void PrintRoundResult(Board board, byte firstPlayerScore, byte secondPlayerScore) {
        System.out.print("\nScore of the first player ● : " + firstPlayerScore + "\n");
        System.out.print("Score of the second player ◯ : " + secondPlayerScore + "\n");
        board.Print((byte) 0);
        if (firstPlayerScore > secondPlayerScore) {
            System.out.print(IsPVPMode() ? "\nFirst player ● won!" : "\nYou won!");
        } else if (firstPlayerScore < secondPlayerScore) {
            System.out.print(IsPVPMode() ? "\nSecond player ◯ won!" : "\nYou lose.");
        } else {
            System.out.print("\nDrawn game!");
        }
    }
}
