/**
 * Abstract class for reversi player
 */
public abstract class Player {

    /**
     * Number of player
     */
    private final byte numberOfPlayer;

    public Player(byte numberOfPlayer){
        this.numberOfPlayer=numberOfPlayer;
    }

    /**
     * Get number of player
     * @return Number of player
     */
    public byte getNumberOfPlayer() {
        return numberOfPlayer;
    }

    /**
     * Method to override in child class, that returns coordinates for the next move
     * @param board State of the board
     * @return Coordinates for the next move
     */
    public abstract PairOfBytes GetCoordinatesForMove(Board board);
}
