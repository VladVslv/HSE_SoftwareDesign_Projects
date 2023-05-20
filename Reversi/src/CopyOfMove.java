/**
 * Class that contains state of the board and number of player, who made move in this state
 */
public class CopyOfMove {

    /**
     * State of the board
     */
    public byte[][] boardCopy;

    /**
     * Number of player, who made move in this state
     */
    public byte numberOfPlayer;

    /**
     * Constructor create to instance of class
     * @param boardCopy State of the board
     * @param numberOfPlayer Number of player, who made move in this state
     */
    public CopyOfMove(byte[][] boardCopy, byte numberOfPlayer) {
        this.numberOfPlayer = numberOfPlayer;
        this.boardCopy = boardCopy;
    }

    /**
     * Get number of player for this move
     * @return Number of player
     */
    public byte getNumberOfPlayer() {
        return numberOfPlayer;
    }

    /**
     * Get state of board for this move
     * @return State of board
     */
    public byte[][] getBoardCopy() {
        return boardCopy;
    }
}
