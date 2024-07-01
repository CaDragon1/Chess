package chess.movelists;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;

public class KingMovesList extends MovesList {
    KingMovesList(ChessBoard board, ChessPosition currentPosition) {
        super(board, currentPosition);
        pieceMoves = new ArrayList<ChessMove>();
        possibleMove = new ChessMove();
        checkingPosition = new ChessPosition();
        calculateMove(currentPosition);
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
                    // Don't add the king's position to the list
                    if(!checkingPosition.equals(myPosition)){
                        possibleMove.setStart(myPosition);
                        possibleMove.setEnd(myPosition);
                        pieceMoves.add(possibleMove);
                    }
                }
            }
        }
    }

    @Override
    ArrayList<ChessMove> getPossibleMoves(ChessPosition myPosition) {
        return pieceMoves;
    }

}
