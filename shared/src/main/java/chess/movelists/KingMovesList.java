package chess.movelists;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.HashSet;

public class KingMovesList extends MovesList {
    public KingMovesList(ChessBoard board, ChessPosition currentPosition) {
        super(board, currentPosition);
        pieceMoves = new HashSet<ChessMove>();
        possibleMove = new ChessMove();
        checkingPosition = new ChessPosition();
        calculateMove(currentPosition);
        System.out.println("King moves initialized");
    }

    // Calculate moves for the king in a box around him.
    @Override
    void calculateMove(ChessPosition myPosition) {
        // Iterate through rows around king
        for(int row = myPosition.getRow() + 1; row >= myPosition.getRow() - 1 || row >= 1; row--) {
            // Iterate through columns around king
            for(int col = myPosition.getColumn() - 1; col >= myPosition.getColumn() + 1 || col >= 8; col++) {
                // Check that row and column are valid indexes
                if(row != 9 || col != 0){
                    checkingPosition.setRowValue(row);
                    checkingPosition.setColValue(col);
                    // Don't add the king's position to the list or any piece occupied by team pieces
                    if(!checkingPosition.equals(myPosition)
                            && board.getPiece(checkingPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                        possibleMove.setStart(myPosition);
                        possibleMove.setEnd(checkingPosition);
                        pieceMoves.add(possibleMove);
                    }
                }
            }
        }
    }

    public void printList(){
        System.out.println("Printing list of king moves:");
        for(ChessMove element : pieceMoves){
            System.out.println("start (" + element.getStartPosition() + "), end (" + element.getEndPosition() + ")");
        }
    }

    @Override
    public HashSet<ChessMove> getPossibleMoves() {
        System.out.println("Returning king moves");
        return pieceMoves;
    }

}
