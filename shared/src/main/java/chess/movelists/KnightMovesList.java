package chess.movelists;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.HashSet;

public class KnightMovesList extends MovesList {
    public KnightMovesList(ChessBoard board, ChessPosition currentPosition) {
        super(board, currentPosition);
        pieceMoves = new HashSet<ChessMove>();
        calculateMove(currentPosition);
    }

    /** Knights have 8 possible moves.
     * - o - o -
     * o - - - o
     * - - K - -
     * o - - - o
     * - o - o -
     * these positions are, relative to the knight's position, in (column, row) terms:
     * (-1, +2) (+1, +2) (-2, +1) (2, +1) (-2, -1) (2, -1) (-1, -2) (+1, -2)
     * Essentially, if the absolute value of (knightPosition.getRow() - row) + abs(knightPosition.getCol() - col) = 3,
     * then as long as that spot is within the board, it is a possible move.
     */

    @Override
    void calculateMove(ChessPosition myPosition) {
        for (int column = myPosition.getColumn() - 2; column <= myPosition.getColumn() + 2; column++) {
            System.out.println("Column: " + column);
            for (int row = myPosition.getRow() + 2; row >= myPosition.getRow() - 2; row--) {
                System.out.println("Row: " + row);
                if(Math.abs(myPosition.getColumn() - column) + Math.abs(myPosition.getRow() - row) == 3
                && row > 0 && row <= 8 && column > 0 && column <= 8) {
                    System.out.println("Checking position " + row + " , " + column);
                    ChessPosition checkingPosition = new ChessPosition(row, column);
                    // Make sure positions aren't taken up by friendly pieces
                    if(board.getPiece(checkingPosition) == null || board.getPiece(checkingPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()){
                        System.out.println("Adding!");
                        pieceMoves.add(new ChessMove(myPosition, new ChessPosition(checkingPosition.getRow(), checkingPosition.getColumn()), null));
                    }
                }
            }
        }
    }

    @Override
    public HashSet<ChessMove> getPossibleMoves() {
        return pieceMoves;
    }
}
