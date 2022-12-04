import java.util.ArrayList;

//Class for Reversi playing field
public class Board {

    //Current state of the board
    private byte[][] board;

    //Previous moves for board
    private final ArrayList<CopyOfMove> boardMoves;

    /**
     * Parameterless constructor
     */
    public Board() {
        board = new byte[8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                board[i][j] = 0;
            }
        }
        board[3][3] = 1;
        board[3][4] = 2;
        board[4][3] = 2;
        board[4][4] = 1;
        boardMoves = new ArrayList<>();
    }

    /**
     * Constructor to copy the current state of other board, but not the moves
     * @param otherBoard Other board, which current state we copy
     */
    public Board(Board otherBoard) {
        this.board = new byte[8][8];
        for (int i = 0; i < 8; ++i) {
            System.arraycopy(otherBoard.board[i], 0, this.board[i], 0, 8);
        }
        boardMoves = new ArrayList<>();
    }

    /**
     * Method to receive points for move on (x,y) in direction (i,j)
     * @param x First coordinate of field for move
     * @param y Second coordinate of field for move
     * @param i First coordinate of direction vector
     * @param j Second coordinate of direction vector
     * @param numberOfPlayer The number of player who makes the move
     * @return Number of points for this direction
     */
    private byte PointsForMoveInDirection(byte x, byte y, byte i, byte j, byte numberOfPlayer) {
        byte result = 0;
        x += i;
        y += j;
        while (0 <= x && 0 <= y && x < 8 && y < 8 && board[x][y] + numberOfPlayer == 3) {
            if (x == 0 || x == 7 || y == 0 || y == 7) {
                result += 2;
            } else {
                result += 1;
            }
            x += i;
            y += j;
        }
        if (0 <= x && 0 <= y && x < 8 && y < 8 && board[x][y] == numberOfPlayer) {
            return result;
        }
        return 0;
    }

    /**
     * Method to receive points for move on (x,y)
     * @param x First coordinate of field for move
     * @param y Second coordinate of field for move
     * @param numberOfPlayer The number of player who makes the move
     * @return Number of points for move
     */
    public double PointsForMove(byte x, byte y, byte numberOfPlayer) {
        if (x < 0 || y < 0 || x > 7 || y > 7 || board[x][y] != 0) {
            return 0;
        }
        double numberOfPoints = 0;
        for (byte i = -1; i <= 1; ++i) {
            for (byte j = -1; j <= 1; ++j) {
                if (i != 0 || j != 0) {
                    numberOfPoints += PointsForMoveInDirection(x, y, i, j, numberOfPlayer);
                }
            }
        }
        if ((x == 0 || y == 0 || x == 7 || y == 7) && numberOfPoints > 0) {
            numberOfPoints += 0.4;
            if ((x == 0 && y == 0) || (x == 0 && y == 7) || (x == 7 && y == 0) || (x == 7 && y == 7)) {
                numberOfPoints += 0.4;
            }
        }
        return numberOfPoints;
    }

    /**
     * Method to receive information about the occurrence of closure for move on (x,y) in direction (i,j)
     * @param x First coordinate of field for move
     * @param y Second coordinate of field for move
     * @param i First coordinate of direction vector
     * @param j Second coordinate of direction vector
     * @param numberOfPlayer The number of player
     * @return The closure appears (true) or not (false)
     */
    private boolean CanMakeMoveInDirection(byte x, byte y, byte i, byte j, byte numberOfPlayer) {
        boolean wasOther = false;
        x += i;
        y += j;
        while (0 <= x && 0 <= y && x < 8 && y < 8 && board[x][y] + numberOfPlayer == 3) {
            wasOther = true;
            x += i;
            y += j;
        }
        if (0 <= x && 0 <= y && x < 8 && y < 8 && board[x][y] == numberOfPlayer) {
            return wasOther;
        }
        return false;
    }

    /**
     * Method to receive information about possibility to move on (x,y)
     * @param x First coordinate of field for move
     * @param y Second coordinate of field for move
     * @param numberOfPlayer The number of player
     * @return The move is possible (true) or not (false)
     */
    public boolean CanMakeMove(byte x, byte y, byte numberOfPlayer) {
        if (x < 0 || y < 0 || x > 7 || y > 7 || board[x][y] != 0) {
            return false;
        }
        for (byte i = -1; i <= 1; ++i) {
            for (byte j = -1; j <= 1; ++j) {
                if (i != 0 || j != 0) {
                    if (CanMakeMoveInDirection(x, y, i, j, numberOfPlayer)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Method to make move on (x,y) in direction (i,j), if it is possible
     * @param x First coordinate of field for move
     * @param y Second coordinate of field for move
     * @param i First coordinate of direction vector
     * @param j Second coordinate of direction vector
     * @param numberOfPlayer The number of player, who is trying to make the move
     * @return The closure appears (true) or not (false)
     */
    public boolean TryToMakeMoveInDirection(byte x, byte y, byte i, byte j, byte numberOfPlayer) {
        if (CanMakeMoveInDirection(x, y, i, j, numberOfPlayer)) {
            x += i;
            y += j;
            while (0 <= x && 0 <= y && x < 8 && y < 8 && board[x][y] + numberOfPlayer == 3) {
                board[x][y] = numberOfPlayer;
                x += i;
                y += j;
            }
            return true;
        }
        return false;
    }

    /**
     * Method to make move on (x,y), if it is possible
     * @param x First coordinate of field for move
     * @param y Second coordinate of field for move
     * @param numberOfPlayer The number of player, who is trying to make the move
     * @return The move is possible (true) or not (false)
     */
    public boolean TryToMakeMove(byte x, byte y, byte numberOfPlayer) {
        if (x < 0 || y < 0 || x > 7 || y > 7 || board[x][y] != 0) {
            return false;
        }
        byte[][] boardCopy = new byte[8][8];
        for (byte i = 0; i < 8; ++i) {
            for (byte j = 0; j < 8; ++j) {
                boardCopy[i][j] = board[i][j];
            }
        }
        boolean wasChanged = false;
        for (byte i = -1; i <= 1; ++i) {
            for (byte j = -1; j <= 1; ++j) {
                if (i != 0 || j != 0) {
                    wasChanged = TryToMakeMoveInDirection(x, y, i, j, numberOfPlayer) || wasChanged;
                }
            }
        }
        if (wasChanged) {
            boardMoves.add(new CopyOfMove(boardCopy, numberOfPlayer));
            board[x][y] = numberOfPlayer;
        }
        return wasChanged;
    }

    /**
     * Method to receive information about existence of move for player with this number
     * @param numberOfPlayer The number of player
     * @return The move exists (true) or not (false)
     */
    public boolean ExistsMove(byte numberOfPlayer) {
        for (byte i = 0; i < 8; ++i) {
            for (byte j = 0; j < 8; ++j) {
                if (CanMakeMove(i, j, (byte) 1)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Method to cancel last move for player with this number
     * @param numberOfPlayer The number of player
     * @throws RuntimeException Throws an exception there is ni possibility to cancel the move
     */
    public void CancelMove(byte numberOfPlayer) throws RuntimeException {
        if (boardMoves.size() < 2) {
            throw new RuntimeException("No moves to cancel!");
        }
        for (int i = boardMoves.size() - 1; i >= 0; --i) {
            if (boardMoves.get(i).getNumberOfPlayer() == numberOfPlayer) {
                board = boardMoves.get(i).getBoardCopy();
                while (boardMoves.size() > i) {
                    boardMoves.remove(boardMoves.size() - 1);
                }
                break;
            }
        }
    }

    /**
     * Method to get score (number of his pieces on board) for player with this number
     * @param numberOfPlayer Number of player
     * @return The score
     */
    public byte GetScore(byte numberOfPlayer) {
        byte score = 0;
        for (byte i = 0; i < 8; ++i) {
            for (byte j = 0; j < 8; ++j) {
                if (board[i][j] == numberOfPlayer) {
                    ++score;
                }
            }
        }
        return score;
    }

    /**
     * Method to print board with hints for the next move for the player or
     * to print without hints (if numberOfPlayer==0)
     * @param numberOfPlayer Number of player
     */
    public void Print(byte numberOfPlayer) {
        System.out.print("\t");
        for (byte i = 1; i < 9; ++i) {
            System.out.print("\t" + i);
        }
        System.out.print("\n");
        for (byte i = 0; i < 8; ++i) {
            System.out.print("\t" + (i + 1));
            for (byte j = 0; j < 8; ++j) {
                switch (board[i][j]) {
                    case 0:
                        if (numberOfPlayer != 0 && CanMakeMove(i, j, numberOfPlayer)) {
                            if (numberOfPlayer == 1) {
                                System.out.print(Colors.Red + "\t●" + Colors.Reset);
                            } else {
                                System.out.print(Colors.Red + "\t◯" + Colors.Reset);
                            }
                        } else {
                            System.out.print("\t" + (char) 183);
                        }
                        break;
                    case 1:
                        System.out.print("\t●");
                        break;
                    case 2:
                        System.out.print("\t◯");
                        break;
                }
            }
            System.out.print("\n");
        }
    }
}
