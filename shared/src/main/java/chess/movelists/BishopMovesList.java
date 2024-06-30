package chess.movelists;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

public class BishopMovesList extends MovesList {
    BishopMovesList(ChessBoard board, ChessPosition currentPosition) {
        super(board, currentPosition);
    }

    @Override
    ChessMove calculateMove(ChessPosition myPosition) {
        return null;
    }
}
