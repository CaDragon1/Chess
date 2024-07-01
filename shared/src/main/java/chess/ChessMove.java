package chess;

import java.util.Objects;

/**
 * Represents moving a chess piece on a chessboard
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessMove {

    private ChessPosition start;
    private ChessPosition end;
    private ChessPiece.PieceType piece = null;

    // Default constructor
    public ChessMove(){
        start = null;
        end = null;
    }
    // Full instantiation constructor
    public ChessMove(ChessPosition startPosition, ChessPosition endPosition,
                     ChessPiece.PieceType promotionPiece) {
        start = startPosition;
        end = endPosition;
        piece = promotionPiece;
    }

    /**
     * @return ChessPosition of starting location
     */
    public ChessPosition getStartPosition() {
        return start;
    }
    // Sets start position
    public void setStart(ChessPosition startPosition) {
        this.start = startPosition;
    }

    /**
     * @return ChessPosition of ending location
     */
    public ChessPosition getEndPosition() {
        return end;
    }
    // Sets end position
    public void setEnd(ChessPosition endPosition) {
        this.end = endPosition;
    }
    /**
     * Gets the type of piece to promote a pawn to if pawn promotion is part of this
     * chess move
     *
     * @return Type of piece to promote a pawn to, or null if no promotion
     */
    public ChessPiece.PieceType getPromotionPiece() {
        return piece;
    }
    // Sets promotion piece
    public void setPromotionPiece(ChessPiece.PieceType promotionPiece) {
        this.piece = promotionPiece;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessMove chessMove)) return false;
        return Objects.equals(start, chessMove.start) && Objects.equals(end, chessMove.end) && piece == chessMove.piece;
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, piece);
    }
}
