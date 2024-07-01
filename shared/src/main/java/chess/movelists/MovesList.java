package chess.movelists;


import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;

// This abstract class will be the framework for every individual chess piece MoveList class.
public abstract class MovesList {
    protected ChessBoard board;
    protected ChessPosition position;
    protected ArrayList<ChessMove> pieceMoves;
    protected ChessPosition checkingPosition;
    protected ChessMove possibleMove;

    // Constructor to set variables used by all subclasses
    MovesList(ChessBoard board, ChessPosition currentPosition) {
        this.board = board;
        this.position = currentPosition;
    }

    // Calculate Move will add to the ArrayList every possible move the piece can make.
    abstract void calculateMove(ChessPosition myPosition);
    // getPossibleMoves will return the ArrayList of possible moves.
    abstract ArrayList<ChessMove> getPossibleMoves(ChessPosition myPosition);
}
