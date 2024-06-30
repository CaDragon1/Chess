package chess.movelists;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

public class PawnMovesList extends MovesList {
    PawnMovesList(ChessBoard board, ChessPosition currentPosition) {
        super(board, currentPosition);
    }

    @Override
    ChessMove calculateMove(ChessPosition myPosition) {
        return null;
    }
}
