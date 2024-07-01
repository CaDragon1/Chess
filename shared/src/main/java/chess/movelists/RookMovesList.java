package chess.movelists;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;

// Move list calculator for the rook
public class RookMovesList extends MovesList{
    boolean continueChecking = false;

    RookMovesList(ChessBoard board, ChessPosition currentPosition) {
        super(board, currentPosition);
        pieceMoves = new ArrayList<ChessMove>();
        possibleMove = new ChessMove();
        checkingPosition = new ChessPosition();
        calculateMove(currentPosition);
    }

    // Calculate moves for the rook (left, right, up, down)
    @Override
    void calculateMove(ChessPosition myPosition) {
        // Check left, right, up, and down. This adds elements to the array list of possible moves.
        checkLeft(myPosition);
        checkRight(myPosition);
        checkUp(myPosition);
        checkDown(myPosition);
    }

    /** Methods for checking left, right, up, and down*/
    // Method to check all the leftward spaces of the rook. Adds those moves to the movelist.
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

    /** Methods for adding to the index */
    // Method for adding new row positions to the array list
    void getRowMoves(ChessPosition myPosition, int column) {
        if(continueChecking) {
            checkingPosition.setColValue(column);
            checkingPosition.setRowValue(myPosition.getRow());
            // If there is another piece there, we stop adding moves to the array list.
            if (board.getPiece(checkingPosition) != null) {
                continueChecking = false;
            }
            addMove(myPosition, checkingPosition);
        }
    }

    // Method for adding new column positions to the array list
    void getColumnMoves(ChessPosition myPosition, int row) {
        if(continueChecking) {
            checkingPosition.setRowValue(row);
            checkingPosition.setColValue(myPosition.getColumn());
            // If there is another piece there, we stop adding moves to the array list.
            if (board.getPiece(checkingPosition) != null) {
                continueChecking = false;
            }
            addMove(myPosition, checkingPosition);
        }
    }

    // Method for setting the possible move variable and adding it to the list of possible moves
    void addMove(ChessPosition myPosition, ChessPosition newPosition) {
        // If the position is empty or occupied by a piece of different color, add the move to the list.
        if (board.getPiece(checkingPosition) == null
                || board.getPiece(checkingPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
            possibleMove.setStart(myPosition);
            possibleMove.setEnd(checkingPosition);
            pieceMoves.add(possibleMove);
        }
    }

    @Override
    ArrayList<ChessMove> getPossibleMoves(ChessPosition myPosition) {
        return pieceMoves;
    }
}
