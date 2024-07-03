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
        ChessPosition checkingPosition = new ChessPosition();
        calculateMove(currentPosition);
    }

    @Override
    void calculateMove(ChessPosition myPosition) {
        System.out.println("Calculating moves:");
        System.out.println("UP LEFT DIAGONAL\n");
        checkDiagonal(1, -1, myPosition);
        System.out.println("UP RIGHT DIAGONAL\n");
        checkDiagonal(1, 1, myPosition);
        System.out.println("DOWN LEFT DIAGONAL\n");
        checkDiagonal(-1, -1, myPosition);
        System.out.println("DOWN RIGHT DIAGONAL\n");
        checkDiagonal(-1, 1, myPosition);
        printList();
    }

    // Function to check any diagonal, with the rowIncrement and colIncrement being either -1 or 1 depending on
    // the direction being checked.
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
                addMove(checkingPosition.getRow(), checkingPosition.getColumn(), myPosition);
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
    void addMove(int row, int column, ChessPosition myPosition){
        ChessPosition checkingPosition = new ChessPosition(row, column);
        System.out.println("Adding move");
        pieceMoves.add(new ChessMove(myPosition, checkingPosition, null));
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
