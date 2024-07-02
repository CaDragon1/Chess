package chess.movelists;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.HashSet;

// Move list calculator for the bishop
public class BishopMovesList extends MovesList {
    boolean continueChecking = false;

    public BishopMovesList(ChessBoard board, ChessPosition currentPosition) {
        super(board, currentPosition);
        pieceMoves = new HashSet<ChessMove>();
        //possibleMove = new ChessMove();
        ChessPosition checkingPosition = new ChessPosition();
        calculateMove(currentPosition);
    }

    @Override
    void calculateMove(ChessPosition myPosition) {
        System.out.println("Calculating moves:");
        System.out.println("UP LEFT DIAGONAL\n");
        checkUpLeft(myPosition);
        System.out.println("UP RIGHT DIAGONAL\n");
        checkUpRight(myPosition);
        System.out.println("DOWN LEFT DIAGONAL\n");
        checkDownLeft(myPosition);
        System.out.println("DOWN RIGHT DIAGONAL\n");
        checkDownRight(myPosition);
        printList();
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
                System.out.println("   checkUpLeft row: " + row + " column: " + column);
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
                System.out.println("   checkUpRight row: " + row + " column: " + column);
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
                System.out.println("   checkDownLeft row: " + row + " column: " + column);
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
                row--;
                column++;
                continueChecking = checkingLogicDownRight(row, column, myPosition, continueChecking);
                System.out.println("   checkDownRight row: " + row + " column: " + column);
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
        ChessPosition checkingPosition = new ChessPosition(row, column);
        if (board.getPiece(checkingPosition) == null
                || board.getPiece(checkingPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
            addMove(row, column, checkingPosition);
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
        ChessPosition checkingPosition = new ChessPosition(row, column);
        if (board.getPiece(checkingPosition) == null
                || board.getPiece(checkingPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
            addMove(row, column, checkingPosition);
        }
        // Three things must be true in order to continue checking. There can't be a piece in that position on the
        // board, the row must not be equal to 8, and the column must not be equal to 8.
        if (board.getPiece(checkingPosition) != null && row != 8 && column != 8) {
            continueChecking = false;
        }
        return continueChecking;
    }

    // Check down left diagonal
    boolean checkingLogicDownLeft(int row, int column, ChessPosition myPosition,boolean continueChecking) {
        ChessPosition checkingPosition = new ChessPosition(row, column);
        if (board.getPiece(checkingPosition) == null
                || board.getPiece(checkingPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
            addMove(row, column, checkingPosition);
        }
        // Three things must be true in order to continue checking. There can't be a piece in that position on the
        // board, the row must not be equal to 8, and the column must not be equal to 1.
        if (board.getPiece(checkingPosition) != null && row != 1 && column != 1) {
            continueChecking = false;
        }
        return continueChecking;
    }

    // Check down right diagonal
    boolean checkingLogicDownRight(int row, int column, ChessPosition myPosition,boolean continueChecking) {
        ChessPosition checkingPosition = new ChessPosition(row, column);
        if (board.getPiece(checkingPosition) == null
                || board.getPiece(checkingPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
            addMove(row, column, checkingPosition);
        }
        // Three things must be true in order to continue checking. There can't be a piece in that position on the
        // board, the row must not be equal to 8, and the column must not be equal to 1.
        if (board.getPiece(checkingPosition) != null && row != 1 && column != 8) {
            continueChecking = false;
        }
        return continueChecking;
    }

    // Logic to add a move to the list
    void addMove(int row, int column, ChessPosition myPosition){
        ChessPosition checkingPosition = new ChessPosition(row, column);
        System.out.println("Adding move");
        // If the position is empty or occupied by a piece of different color, add the move to the list.
        if (board.getPiece(checkingPosition) == null
                || board.getPiece(checkingPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
            pieceMoves.add(new ChessMove(myPosition, checkingPosition, null));
        }
    }

    public void printList(){
        System.out.println("Printing list of bishop moves:");
        for(ChessMove element : pieceMoves){
            System.out.println("start (" + element.getStartPosition() + "), end (" + element.getEndPosition() + ")");
        }
    }

    @Override
    public HashSet<ChessMove> getPossibleMoves() {
        return pieceMoves;
    }
}
