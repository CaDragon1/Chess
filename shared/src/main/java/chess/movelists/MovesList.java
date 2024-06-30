package chess.movelists;


import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;

// This abstract class will be the framework for every individual chess piece MoveList class.
abstract class MovesList {
    protected ChessBoard board;
    protected ChessPosition position;
    protected ArrayList<ChessMove> pieceMoves;

    // Constructor to set variables used by all subclasses
    MovesList(ChessBoard board, ChessPosition currentPosition) {
        this.board = board;
        this.position = currentPosition;
    }

    // Calculate Move will add to the ArrayList every possible move the piece can make.
    abstract ChessMove calculateMove(ChessPosition myPosition);
}
