package chess.movelists;

import chess.*;

import java.util.ArrayList;

// This is the move list calculator for the pawn.
public class PawnMovesList extends MovesList {
    PawnMovesList(ChessBoard board, ChessPosition currentPosition) {
        super(board, currentPosition);
        pieceMoves = new ArrayList<ChessMove>();
        possibleMove = new ChessMove();
        calculateMove(currentPosition);
    }

    // We need to check the space in front of the pawn (+1 if white, -1 if black) and see if it is 1) a space on the
    // board, and 2) there is no piece in front of the pawn. Then we append that space to our array list.
    // Only pawns move unidirectionally, so only pawns require a teamColor check.
    @Override
    void calculateMove(ChessPosition myPosition) {
        // Check if white
        if (board.getPiece(position).getTeamColor() == ChessGame.TeamColor.WHITE) {
            checkingPosition = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn());
            if (checkingPosition.getRow() != 9 && board.getPiece(checkingPosition) == null) {
                possibleMove = new ChessMove(position, checkingPosition, null);
                pieceMoves.add(possibleMove);
            }
        }
        // Otherwise, piece is black
        else {
            checkingPosition = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn());
            if (checkingPosition.getRow() != 0 && board.getPiece(checkingPosition) == null) {
                possibleMove = new ChessMove(position, checkingPosition, null);
                pieceMoves.add(possibleMove);
            }
        }
    }

    @Override
    ArrayList<ChessMove> getPossibleMoves(ChessPosition myPosition) {
        return pieceMoves;
    }
}
