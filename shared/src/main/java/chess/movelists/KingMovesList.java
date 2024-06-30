package chess.movelists;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;

public class KingMovesList extends MovesList {
    KingMovesList(ChessBoard board, ChessPosition currentPosition) {
        super(board, currentPosition);
    }

    @Override
    void calculateMove(ChessPosition myPosition) {
    }

    @Override
    ArrayList<ChessMove> getPossibleMoves(ChessPosition myPosition) {
        return null;
    }

}
