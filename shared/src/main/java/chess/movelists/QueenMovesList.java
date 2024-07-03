package chess.movelists;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.HashSet;

public class QueenMovesList extends MovesList {
    boolean continueChecking = false;
    public QueenMovesList(ChessBoard board, ChessPosition currentPosition) {
        super(board, currentPosition);
        pieceMoves = new HashSet<ChessMove>();
        //possibleMove = new ChessMove();
        //checkingPosition = new ChessPosition();
        calculateMove(currentPosition);
    }

    // The queen's calculate move function copies the bishop and rook methods.
    @Override
    void calculateMove(ChessPosition myPosition) {
        // Check up left
        checkDiagonal(1, -1, myPosition);
        // Check up right
        checkDiagonal(1, 1, myPosition);
        // Check down left
        checkDiagonal(-1, -1, myPosition);
        // Check down right
        checkDiagonal(-1, 1, myPosition);
        checkLeft(myPosition);
        checkRight(myPosition);
        checkUp(myPosition);
        checkDown(myPosition);
    }

    // Functions to add a valid chess move to the move list.
    // These functions are a little messy. Given more time I'd like to rework them
    /** Methods for adding diagonal moves to the move list */

    void checkLeft(ChessPosition myPosition) {
        continueChecking = true;
        for (int i = myPosition.getColumn() - 1; i > 0; i--){
            getRowMoves(myPosition, i);
        }
    }

    // Method to check all the rightward spaces of the rook. Adds those moves to the movelist.
    void checkRight(ChessPosition myPosition) {
        continueChecking = true;
        for (int i = myPosition.getColumn() + 1; i < 9; i++){
            getRowMoves(myPosition, i);
        }
    }

    // Method to check all the upward spaces of the rook. Adds those moves to the movelist.
    void checkUp(ChessPosition myPosition) {
        continueChecking = true;
        for (int i = myPosition.getRow() + 1; i < 9; i++){
            getColumnMoves(myPosition, i);
        }
    }

    // Method to check all the downward spaces of the rook. Adds those moves to the movelist.
    void checkDown(ChessPosition myPosition) {
        continueChecking = true;
        for (int i = myPosition.getRow() - 1; i > 0; i--){
            getColumnMoves(myPosition, i);
        }
    }

    private void checkDiagonal(int rowIncrement, int colIncrement, ChessPosition myPosition) {
        boolean keepChecking = true;
        int incrementer = 1;
        // Verify that the next position is within bounds.
        keepChecking = isInBounds(myPosition.getRow() + rowIncrement, myPosition.getColumn() + colIncrement);

        ChessPosition checkingPosition = new ChessPosition(myPosition.getRow() + (incrementer * rowIncrement),
                myPosition.getColumn() + (incrementer * colIncrement));
        while (keepChecking) {
            if (board.getPiece(checkingPosition) == null
                    || board.getPiece(checkingPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                addDiagonalMove(checkingPosition.getRow(), checkingPosition.getColumn(), myPosition);
                if(board.getPiece(checkingPosition) != null){
                    keepChecking = false;
                }
                else{
                    incrementer++;
                    checkingPosition.setColValue(myPosition.getColumn() + (incrementer * colIncrement));
                    checkingPosition.setRowValue(myPosition.getRow() + (incrementer * rowIncrement));
                    keepChecking = isInBounds(checkingPosition.getRow(), checkingPosition.getColumn());
                }
            }
            else{
                keepChecking = false;
            }
        }
    }

    // Make sure that the given row and column are within the bounds of the game board.
    private boolean isInBounds(int row, int col) {
        if(row <= 0 || row >= 9 || col <= 0 || col >= 9 ){
            return false;
        }
        else{
            return true;
        }
    }

    // Logic to add a move to the list
    void addDiagonalMove(int row, int column, ChessPosition myPosition){
        ChessPosition checkingPosition = new ChessPosition(row, column);
        System.out.println("Adding move");
        pieceMoves.add(new ChessMove(myPosition, checkingPosition, null));
    }


    /** Methods for adding horizontal moves to the index */
    // Method for adding new row positions to the array list
    void getRowMoves(ChessPosition myPosition, int column) {
        if(continueChecking) {
            // If there is another piece there, we stop adding moves to the array list.
            if (board.getPiece(new ChessPosition(myPosition.getRow(), column)) != null) {
                continueChecking = false;
            }
            addMove(myPosition, new ChessPosition(myPosition.getRow(), column));
        }
    }

    // Method for adding new column positions to the array list
    void getColumnMoves(ChessPosition myPosition, int row) {
        if(continueChecking) {
            // If there is another piece there, we stop adding moves to the array list.
            if (board.getPiece(new ChessPosition(row, myPosition.getColumn())) != null) {
                continueChecking = false;
            }
            addMove(myPosition, new ChessPosition(row, myPosition.getColumn()));
        }
    }

    // Method for setting the possible move variable and adding it to the list of possible moves
    void addMove(ChessPosition myPosition, ChessPosition newPosition) {
        // If the position is empty or occupied by a piece of different color, add the move to the list.
        if (board.getPiece(newPosition) == null
                || board.getPiece(newPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
            //possibleMove.setStart(myPosition);
            //possibleMove.setEnd(checkingPosition);
            pieceMoves.add(new ChessMove(myPosition, newPosition, null));
        }
    }
    @Override
    public HashSet<ChessMove> getPossibleMoves() {
        return pieceMoves;
    }
}
