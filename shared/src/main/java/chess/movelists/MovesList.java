package chess.movelists;


import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.HashSet;
import java.util.Objects;

// This abstract class will be the framework for every individual chess piece MoveList class.
public abstract class MovesList {
    protected ChessBoard board;
    //protected ChessPosition position;
    protected HashSet<ChessMove> pieceMoves;
    /*
    protected ChessPosition checkingPosition;
    protected ChessMove possibleMove;
    */

    // Constructor to set variables used by all subclasses
    MovesList(ChessBoard board, ChessPosition currentPosition) {
        this.board = board;
        //this.position = currentPosition;
    }

    // Calculate Move will add to the ArrayList every possible move the piece can make.
    abstract void calculateMove(ChessPosition myPosition);
    // getPossibleMoves will return the ArrayList of possible moves.
    public abstract HashSet<ChessMove> getPossibleMoves();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovesList movesList)) return false;
        return Objects.deepEquals(pieceMoves, movesList.pieceMoves);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pieceMoves);
    }
}
