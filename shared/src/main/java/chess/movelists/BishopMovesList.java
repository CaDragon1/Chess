package chess.movelists;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.HashSet;

// Move list calculator for the bishop
public class BishopMovesList extends MovesList {
    boolean continueChecking = false;

    public BishopMovesList(ChessBoard board, ChessPosition currentPosition) {
        super(board, currentPosition);
        pieceMoves = new HashSet<ChessMove>();
        possibleMove = new ChessMove();
        checkingPosition = new ChessPosition();
        calculateMove(currentPosition);
    }

    @Override
    void calculateMove(ChessPosition myPosition) {
        checkUpLeft(myPosition);
        checkUpRight(myPosition);
        checkDownLeft(myPosition);
        checkDownRight(myPosition);
    }

    // Calculate the up-left diagonal
    void checkUpLeft(ChessPosition myPosition) {
        continueChecking = true;
        int row = myPosition.getRow();
        int column = myPosition.getColumn();
        while(continueChecking) {
            if(row != 8 && column != 1) {
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
            if(row != 8 && column != 8) {
                row++;
                column--;
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
            if(row != 1 && column != 1) {
                row++;
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
            if(row != 1 && column != 8) {
                row++;
                column--;
                continueChecking = checkingLogicDownRight(row, column, myPosition, continueChecking);
            }
            else {
                continueChecking = false;
            }
        }
    }

    // Functions to add a valid chess move to the move list.
    // These functions are a little messy. Given more time I'd like to rework them
    // Check up left diagonal
    boolean checkingLogicUpLeft(int row, int column, ChessPosition myPosition,boolean continueChecking) {
        // If the position is empty or occupied by a piece of different color, add the move to the list.
        if (board.getPiece(checkingPosition) == null
                || board.getPiece(checkingPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
            addMove(row, column,myPosition);
        }
        // Three things must be true in order to continue checking. There can't be a piece in that position on the
        // board, the row must not be equal to 8, and the column must not be equal to 1.
        if (board.getPiece(checkingPosition) != null && row != 8 && column != 1) {
            continueChecking = false;
        }
        return continueChecking;
    }

    // Check up right diagonal
    boolean checkingLogicUpRight(int row, int column, ChessPosition myPosition,boolean continueChecking) {
        addMove(row, column,myPosition);
        // Three things must be true in order to continue checking. There can't be a piece in that position on the
        // board, the row must not be equal to 8, and the column must not be equal to 8.
        if (board.getPiece(checkingPosition) != null && row != 8 && column != 8) {
            continueChecking = false;
        }
        return continueChecking;
    }

    // Check down left diagonal
    boolean checkingLogicDownLeft(int row, int column, ChessPosition myPosition,boolean continueChecking) {
        addMove(row, column,myPosition);
        // Three things must be true in order to continue checking. There can't be a piece in that position on the
        // board, the row must not be equal to 8, and the column must not be equal to 1.
        if (board.getPiece(checkingPosition) != null && row != 1 && column != 1) {
            continueChecking = false;
        }
        return continueChecking;
    }

    // Check down right diagonal
    boolean checkingLogicDownRight(int row, int column, ChessPosition myPosition,boolean continueChecking) {
        addMove(row, column,myPosition);
        // Three things must be true in order to continue checking. There can't be a piece in that position on the
        // board, the row must not be equal to 8, and the column must not be equal to 1.
        if (board.getPiece(checkingPosition) != null && row != 1 && column != 8) {
            continueChecking = false;
        }
        return continueChecking;
    }

    // Logic to add a move to the list
    void addMove(int row, int column, ChessPosition myPosition){
        checkingPosition.setRowValue(row);
        checkingPosition.setColValue(column);
        // If the position is empty or occupied by a piece of different color, add the move to the list.
        if (board.getPiece(checkingPosition) == null
                || board.getPiece(checkingPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
            possibleMove.setStart(myPosition);
            possibleMove.setEnd(checkingPosition);
            pieceMoves.add(possibleMove);
        }
    }


    @Override
    public HashSet<ChessMove> getPossibleMoves() {
        return pieceMoves;
    }
}
