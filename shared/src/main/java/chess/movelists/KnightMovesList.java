package chess.movelists;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

public class KnightMovesList extends MovesList {
    KnightMovesList(ChessBoard board, ChessPosition currentPosition) {
        super(board, currentPosition);
    }

    @Override
    ChessMove calculateMove(ChessPosition myPosition) {
        return null;
    }
}
