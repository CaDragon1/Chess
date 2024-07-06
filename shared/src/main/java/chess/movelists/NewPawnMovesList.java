package chess.movelists;

import chess.*;

import java.util.HashSet;

public class NewPawnMovesList extends MovesList{

    ChessGame.TeamColor color;
    int endRow;

    public NewPawnMovesList(ChessBoard board, ChessPosition currentPosition) {
        super(board, currentPosition);
        this.board = board;
        endRow = setColor(currentPosition);
        pieceMoves = new HashSet<ChessMove>();
        calculateMove(currentPosition);
    }

    @Override
    void calculateMove(ChessPosition myPosition) {
        if (color == ChessGame.TeamColor.WHITE) {
            teamCalculateMove(myPosition, 1);
        }
        else {
            teamCalculateMove(myPosition, -1);
        }

    }

    @Override
    public HashSet<ChessMove> getPossibleMoves() {
        return pieceMoves;
    }

    // teamCalculateMove takes an int 1 if white and a -1 if black. This is multiplied by the row modifier to get
    // direction of travel.
    private void teamCalculateMove(ChessPosition myPosition, int direction) {
        checkAttack(myPosition, myPosition.getRow() + (direction), myPosition.getColumn() - 1);
        checkAttack(myPosition, myPosition.getRow() + (direction), myPosition.getColumn() + 1);
        // Check double move while checking single move
        if(checkMove(myPosition, myPosition.getRow() + (direction), myPosition.getColumn())){
            if (color == ChessGame.TeamColor.WHITE && myPosition.getRow() == 2) {
                checkMove(myPosition, myPosition.getRow() + (direction * 2), myPosition.getColumn());
            }
            else if (color == ChessGame.TeamColor.BLACK && myPosition.getRow() == 7) {
                checkMove(myPosition, myPosition.getRow() + (direction * 2), myPosition.getColumn());
            }

        }
    }

    private boolean checkMove(ChessPosition currentPosition, int row, int col){
        if (isWithinBounds(row, col)){
            ChessPosition checkPosition = new ChessPosition(row, col);
            if(board.getPiece(checkPosition) == null){
                if(!checkPromotion(currentPosition, row, col)) {
                    addMove(currentPosition, row, col);
                    return true;
                }
                else {
                    return false;
                }
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    // checkAttack checks whether there is a piece of the opposite team's color in the given coordinate and adds
    // that position to the move list if valid.
    private void checkAttack(ChessPosition currentPosition, int row, int col){
        if (isWithinBounds(row, col)){
            ChessPosition checkPosition = new ChessPosition(row, col);
            if(board.getPiece(checkPosition) != null){
                if (board.getPiece(checkPosition).getTeamColor() != color){
                    if (!checkPromotion(currentPosition, row, col)){
                        addMove(currentPosition, row, col);
                    }
                }
            }
        }
        else{
        }
    }

    // checkPromotion checks whether a promotion is possible at the given space. If so, it returns true. Otherwise,
    // returns false.
    private boolean checkPromotion(ChessPosition currentPosition, int row, int col){
        if (row == endRow){
            addPromotion(currentPosition, col);
            return true;
        }
        else {
            return false;
        }
    }

    // addPromotion adds all promotion moves to the list. This is done at the given column index in the endRow.
    private void addPromotion (ChessPosition currentPosition, int col) {

            pieceMoves.add(new ChessMove(currentPosition, new ChessPosition(endRow, col), ChessPiece.PieceType.ROOK));
            pieceMoves.add(new ChessMove(currentPosition, new ChessPosition(endRow, col), ChessPiece.PieceType.KNIGHT));
            pieceMoves.add(new ChessMove(currentPosition, new ChessPosition(endRow, col), ChessPiece.PieceType.BISHOP));
            pieceMoves.add(new ChessMove(currentPosition, new ChessPosition(endRow, col), ChessPiece.PieceType.QUEEN));

    }

    // addMove simply adds a move to the move list.
    private void addMove(ChessPosition myPosition, int row, int col) {
        pieceMoves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
    }

    // setColor sets the color variable and returns the value of endRow depending on the color.
    // The value of endRow is 8 if the piece is white, and 1 if the piece is black.
    private int setColor(ChessPosition myPosition) {
        if (board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
            color = ChessGame.TeamColor.WHITE;
            return 8;
        }
        else {
            color = ChessGame.TeamColor.BLACK;
            return 1;
        }
    }

    // isWithinBounds checks to make sure that the row and column are within movable space.
    private boolean isWithinBounds(int checkRow, int checkCol) {
        // Pawn cannot be out of bounds to move
        if (checkRow >= 1 && checkRow <= 8){
            // Position must be within the board
            if (checkCol >= 1 && checkCol <= 8){
                return true;
            }
        }
        return false;
    }

}
