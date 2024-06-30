package chess.movelists;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

public class KingMovesList extends MovesList {
    KingMovesList(ChessBoard board, ChessPosition currentPosition) {
        super(board, currentPosition);
    }

    @Override
    ChessMove calculateMove(ChessPosition myPosition) {
        return null;
    }

}
