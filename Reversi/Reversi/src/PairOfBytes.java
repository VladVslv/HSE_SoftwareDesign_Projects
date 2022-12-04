/**
 * Class that store pair of bytes
 */
public class PairOfBytes {

    /**
     * First element
     */
    byte first;

    /**
     * Second element
     */
    byte second;

    /**
     * Constructor to create instance of class
     * @param x First element
     * @param y Second element
     */
    public PairOfBytes(byte x, byte y){
        this.first =x;
        this.second =y;
    }

    /**
     * Get copy of first element
     * @return First element
     */
    public byte getFirst() {
        return first;
    }

    /**
     * Get copy of second element
     * @return Second element
     */
    public byte getSecond() {
        return second;
    }

    /**
     * Set new value for the first element
     * @param first New value of the element
     */
    public void setFirst(byte first) {
        this.first = first;
    }

    /**
     * Set new value for the second element
     * @param second New value of the element
     */
    public void setSecond(byte second) {
        this.second = second;
    }
}
