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
        checkUpLeft(myPosition);
        checkUpRight(myPosition);
        checkDownLeft(myPosition);
        checkDownRight(myPosition);
        checkLeft(myPosition);
        checkRight(myPosition);
        checkUp(myPosition);
        checkDown(myPosition);
    }

    // Calculate the up-left diagonal
    void checkUpLeft(ChessPosition myPosition) {
        continueChecking = true;
        int row = myPosition.getRow();
        int column = myPosition.getColumn();
        while(continueChecking) {
            if(row < 8 && column > 1) {
                row++;
                column--;
                continueChecking = checkingLogicUpLeft(row, column, myPosition, continueChecking);
            }
            else {
                continueChecking = false;
            }
        }
    }

    // Calculate the up-right diagonal
    void checkUpRight(ChessPosition myPosition) {
        continueChecking = true;
        int row = myPosition.getRow();
        int column = myPosition.getColumn();
        while(continueChecking) {
            if(row < 8 && column < 8) {
                row++;
                column++;
                continueChecking = checkingLogicUpRight(row, column, myPosition, continueChecking);
            }
            else {
                continueChecking = false;
            }
        }
    }

    // Calculate the down-left diagonal
    void checkDownLeft(ChessPosition myPosition) {
        continueChecking = true;
        int row = myPosition.getRow();
        int column = myPosition.getColumn();
        while(continueChecking) {
            if(row > 1 && column > 1) {
                row--;
                column--;
                continueChecking = checkingLogicDownLeft(row, column, myPosition, continueChecking);
            }
            else {
                continueChecking = false;
            }
        }
    }

    // Calculate the down-right diagonal
    void checkDownRight(ChessPosition myPosition) {
        continueChecking = true;
        int row = myPosition.getRow();
        int column = myPosition.getColumn();
        while(continueChecking) {
            if(row > 1 && column < 8) {
                row--;
                column++;
                continueChecking = checkingLogicDownRight(row, column, myPosition, continueChecking);
            }
            else {
                continueChecking = false;
            }
        }
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

    // Check up left diagonal
    boolean checkingLogicUpLeft(int row, int column, ChessPosition myPosition,boolean continueChecking) {
        addDiagonalMove(row, column,myPosition);
        // Three things must be true in order to continue checking. There can't be a piece in that position on the
        // board, the row must not be equal to 8, and the column must not be equal to 1.
        if (board.getPiece(new ChessPosition(row, column)) != null && row != 8 && column != 1) {
            continueChecking = false;
        }
        return continueChecking;
    }

    // Check up right diagonal
    boolean checkingLogicUpRight(int row, int column, ChessPosition myPosition,boolean continueChecking) {
        addDiagonalMove(row, column,myPosition);
        // Three things must be true in order to continue checking. There can't be a piece in that position on the
        // board, the row must not be equal to 8, and the column must not be equal to 8.
        if (board.getPiece(new ChessPosition(row, column)) != null && row != 8 && column != 8) {
            continueChecking = false;
        }
        return continueChecking;
    }

    // Check down left diagonal
    boolean checkingLogicDownLeft(int row, int column, ChessPosition myPosition,boolean continueChecking) {
        addDiagonalMove(row, column,myPosition);
        // Three things must be true in order to continue checking. There can't be a piece in that position on the
        // board, the row must not be equal to 8, and the column must not be equal to 1.
        if (board.getPiece(new ChessPosition(row, column)) != null && row != 1 && column != 1) {
            continueChecking = false;
        }
        return continueChecking;
    }

    // Check down right diagonal
    boolean checkingLogicDownRight(int row, int column, ChessPosition myPosition,boolean continueChecking) {
        addDiagonalMove(row, column,myPosition);
        // Three things must be true in order to continue checking. There can't be a piece in that position on the
        // board, the row must not be equal to 8, and the column must not be equal to 1.
        if (board.getPiece(new ChessPosition(row, column)) != null && row != 1 && column != 8) {
            continueChecking = false;
        }
        return continueChecking;
    }

    // Logic to add a move to the list
    void addDiagonalMove(int row, int column, ChessPosition myPosition){
        addMove(myPosition, new ChessPosition(row, column));
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
