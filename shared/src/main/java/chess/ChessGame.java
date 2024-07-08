package chess;

import javax.swing.text.Position;
import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor teamTurn;
    private ChessBoard board;
    private ChessBoard checkingBoard;

    public ChessGame() {
        teamTurn = TeamColor.WHITE;
        board = new ChessBoard();
        checkingBoard = new ChessBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {

        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {

        teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {

        return board.getPiece(startPosition).pieceMoves(board, startPosition);
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        checkingBoard = board;
        if (validMoves(move.getStartPosition()).contains(move)){
            checkingBoard.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));

        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Given a board, determines if a team is in checkmate on that board.
     * This is useful for checking if a move will put your king in check, and it can also be called by isInCheck.
     * @param gameBoard is the board used to determine if said team is in check (a temporary board is given when
     *                    determining if a move will put the king in check).
     * @param teamColor is the team who we are determining check status for.
     * @return false if the team is not in check, true if it is.
     */
    public boolean putsInCheck(ChessBoard gameBoard, TeamColor teamColor) {
        ChessPosition kingPosition = findKing(gameBoard, teamColor);
        if (kingPosition != null) {

        }
        return false;
    }

    /**
     * Checks all positions diagonal to a given ChessPosition to determine if the position is in danger to
     * bishops or queens.
     * @param gameBoard is the given board to check (either the actual game board or a copy to ensure that a given move
     *                  is legal)
     * @param kingPosition is the position we are checking the safety of
     * @return true if the spot is in danger, false otherwise
     */
    public boolean checkAllDiagonals(ChessBoard gameBoard, ChessPosition kingPosition) {
        boolean inDanger = false;
        for (int i = -1; i <= 1; i+=2){
            for (int j = -1; j <= 1; j+=2){
                if(!inDanger){
                    inDanger = checkDiagonal(i, j, kingPosition,
                            gameBoard.getPiece(kingPosition).getTeamColor(), gameBoard);
                }
            }
        }
        return inDanger;
    }

    /**
     * This method iterates over a given diagonal direction (-1 to go left or down, 1 to go right or up) and returns
     * true if the position is reachable by an enemy queen or bishop.
     * @param rowIncrement if -1, row decreases (down). If 1, row increases (up).
     * @param colIncrement if -1, column decreases (left). If 1, column increases (right).
     * @param kingPosition is the position that we are checking.
     * @param teamColor is the color of the king that we use to determine who is friend or foe.
     * @return true if the position is reachable by enemy queen or bishop, false otherwise
     */
    private boolean checkDiagonal(int rowIncrement, int colIncrement,
                                  ChessPosition kingPosition, TeamColor teamColor, ChessBoard gameBoard) {
        boolean keepChecking = true;
        int incrementer = 1;
        // Verify that the next position is within bounds.
        keepChecking = isInBounds(kingPosition.getRow() + rowIncrement, kingPosition.getColumn() + colIncrement);

        ChessPosition checkingPosition = new ChessPosition(kingPosition.getRow() + (incrementer * rowIncrement),
                kingPosition.getColumn() + (incrementer * colIncrement));
        while (keepChecking) {
            if (board.getPiece(checkingPosition) == null) {
                incrementer++;
                checkingPosition.setColValue(kingPosition.getColumn() + (incrementer * colIncrement));
                checkingPosition.setRowValue(kingPosition.getRow() + (incrementer * rowIncrement));
                keepChecking = isInBounds(checkingPosition.getRow(), checkingPosition.getColumn());
            }
            else if (board.getPiece(checkingPosition).getTeamColor() == teamColor) {
                keepChecking = false;
            }
            else if (board.getPiece(checkingPosition).getPieceType() == ChessPiece.PieceType.BISHOP
                    || board.getPiece(checkingPosition).getPieceType() == ChessPiece.PieceType.QUEEN) {
                keepChecking = false;
                return true;
            }
            else {
                keepChecking = false;
            }
        }
        return false;
    }

    /**
     * Given a row and column integer, checks to make sure the coordinate is within the board.
     * @param row = row value
     * @param col = column value
     * @return false if out of bounds, true otherwise
     */
    private boolean isInBounds(int row, int col) {
        if(row <= 0 || row >= 9 || col <= 0 || col >= 9 ){
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * findKing locates what position the king of the given teamColor is in.
     * @param gameBoard is the board we are looking at (typically the regular board).
     * @param teamColor is the team we are finding the king of.
     * @return the position of the king. If no king is found, returns null.
     */
    public ChessPosition findKing(ChessBoard gameBoard, TeamColor teamColor) {
        ChessPosition currentPosition = new ChessPosition();

        for (int row = 1; row <= 8; row++){
            currentPosition.setRowValue(row);
            for (int col = 1; col <= 8; col++){
                currentPosition.setColValue(col);
                if (gameBoard.getPiece(currentPosition) != null){
                    if (gameBoard.getPiece(currentPosition).getTeamColor() == teamColor
                    && gameBoard.getPiece(currentPosition).getPieceType() == ChessPiece.PieceType.KING){
                        return currentPosition;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        throw new RuntimeException("Not implemented");
    }
}
