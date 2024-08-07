package chess;

import chess.movelists.*;

import java.util.*;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    ChessGame.TeamColor color;
    PieceType piece;
    private Set<ChessMove> moves;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        color = pieceColor;
        piece = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return color;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return piece;
    }

    // Overridden equals and hashCode methods to test for deep equality
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        System.out.println(color + " " + piece + " =? " + that.color + " " + that.piece);
        return color == that.color && piece == that.piece;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, piece);
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        moves = new HashSet<ChessMove>();
        MovesList moveList;
        piece = board.getPiece(myPosition).getPieceType();
        switch (piece) {
            case KING:
                moveList = new KingMovesList(board, myPosition);
                moves = moveList.getPossibleMoves();
                break;
            case PAWN:
                moveList = new NewPawnMovesList(board, myPosition);
                moves = moveList.getPossibleMoves();
                break;
            case ROOK:
                moveList = new RookMovesList(board, myPosition);
                moves = moveList.getPossibleMoves();
                break;
            case BISHOP:
                moveList = new BishopMovesList(board, myPosition);
                moves = moveList.getPossibleMoves();
                break;
            case KNIGHT:
                moveList = new KnightMovesList(board, myPosition);
                moves = moveList.getPossibleMoves();
                break;
            case QUEEN:
                moveList = new QueenMovesList(board, myPosition);
                moves = moveList.getPossibleMoves();
                break;
        }
        return moves;
    }
}
