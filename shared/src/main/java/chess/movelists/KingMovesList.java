package chess.movelists;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.HashSet;

public class KingMovesList extends MovesList {
    public KingMovesList(ChessBoard board, ChessPosition currentPosition) {
        super(board, currentPosition);
        pieceMoves = new HashSet<ChessMove>();
        calculateMove(currentPosition);
        System.out.println("King moves initialized");
    }

    // Calculate moves for the king in a box around him.
    @Override
    void calculateMove(ChessPosition myPosition) {
        // Iterate through rows around king
        for(int row = myPosition.getRow() + 1; row >= myPosition.getRow() - 1; row--) {
            // Iterate through columns around king
            System.out.println("ROW " + row);
            for(int col = myPosition.getColumn() - 1; col <= myPosition.getColumn() + 1; col++) {
                ChessPosition checkingPosition = new ChessPosition(row, col);
                // Check that row and column are valid indexes
                if(row < 9 && row > 0 && col < 9 && col > 0){
                    System.out.println("Checking (" + row + "," + col + "): ");
                    // Don't add the king's position to the list or any piece occupied by team pieces
                    if(!checkingPosition.equals(myPosition)){
                        if (board.getPiece(checkingPosition) == null || board.getPiece(checkingPosition).getTeamColor()
                                != board.getPiece(myPosition).getTeamColor()) {
                            addElement(myPosition, row, col);
                        }
                    }
                }
            }
        }
    }

    public void printList(){
        System.out.println("Printing list of king moves:");
        //for(ChessMove element : pieceMoves){
            System.out.println(pieceMoves);
        //}
    }

    public void addElement(ChessPosition myPosition, int row, int col){

        System.out.println("Possible move added: ");
        pieceMoves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
        printList();
    }

    @Override
    public HashSet<ChessMove> getPossibleMoves() {
        printList();
        System.out.println("Returning king moves");
        return pieceMoves;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
