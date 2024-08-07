package chess;

import java.util.*;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor teamTurn;
    private ChessBoard board;

    public ChessGame() {
        teamTurn = TeamColor.WHITE;
        board = new ChessBoard();
        board.resetBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {

        return teamTurn;
    }

    /**
     * Sets which team's turn it is
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
        Set<ChessMove> validMoves = new HashSet<ChessMove>();

        if(board.getPiece(startPosition) != null){

            // To get accurate returns, temporarily sets the turn to startPosition's team
            //TeamColor currentTeamTurn = getTeamTurn();
            //setTeamTurn(board.getPiece(startPosition).getTeamColor());

            Collection<ChessMove> allMoves
                    = new HashSet<ChessMove>(board.getPiece(startPosition).pieceMoves(board, startPosition));
            validMoves.addAll(allMoves);
            TeamColor selectedTeam = board.getPiece(startPosition).getTeamColor();
            board.printBoard();

            /// Step 0: Cycle through the set using an iterator
            for (ChessMove move : allMoves) {
                ChessPiece takenPiece;

                // Step 1: Make the possible move on the checking board.
                System.out.println("\n ---- Checking move " + move + ": ----");
                System.out.println("    >> Starting position: (" + startPosition.getRow() + ", " + startPosition.getColumn() + ")");
                System.out.println("    >> Ending position: (" + move.getEndPosition().getRow() + ", " + move.getEndPosition().getColumn() + ")");
                System.out.println("    >> Promotion: " + move.getPromotionPiece());
                takenPiece = addMove(move);

                // Step 2: Does the move put the king into check? If so, remove the move from the set.
                if (isInCheck(selectedTeam)){
                    validMoves.remove(move);
                    System.out.println("Removed.\n");
                }

                revertMove(move, takenPiece);
            }
            // Return the team's turn to the correct team
            //setTeamTurn(currentTeamTurn);

            // Step 3: Return the final list of valid moves that DON'T put the king in check.
            return validMoves;
        }
        return validMoves;
    }

    /**
     * Sets the board to a given movestate without changing the team color.
     * @param move is the move we want to make.
     * @return returns the piece at the position we move to.
     */
    public ChessPiece addMove(ChessMove move) {
        ChessPiece piece = board.getPiece(move.getEndPosition());

        if (move.getPromotionPiece() != null){
            board.addPiece(move.getEndPosition(), new ChessPiece(teamTurn, move.getPromotionPiece()));
            board.addPiece(move.getStartPosition(), null);
            System.out.println("move has been made and PAWN is now a " + move.getPromotionPiece());
        }
        else {
            board.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
            board.addPiece(move.getStartPosition(), null);
            System.out.println("move has been made");
        }
        return piece;
    }

    /**
     * Returns the board to its state before a move was made.
     * @param move is the move we want to undo.
     */
    public void revertMove(ChessMove move, ChessPiece takenPiece) {
        if (move.getPromotionPiece() != null){
            board.addPiece(move.getStartPosition(), new ChessPiece(teamTurn, ChessPiece.PieceType.PAWN));
            board.addPiece(move.getEndPosition(), takenPiece);
            System.out.println("move has been reverted and promotion piece " + move.getPromotionPiece() + " is now a "
                + board.getPiece(move.getStartPosition()).getPieceType());
        }
        else {
            board.addPiece(move.getStartPosition(), board.getPiece(move.getEndPosition()));
            board.addPiece(move.getEndPosition(), takenPiece);
            System.out.println("move has been reverted");
        }
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        //checkingBoard = board;
        board.printBoard();
        if (board.getPiece(move.getStartPosition()) != null && validMoves(move.getStartPosition()).contains(move)
                && board.getPiece(move.getStartPosition()).getTeamColor() == teamTurn){
            addMove(move);
            switch(teamTurn){
                case WHITE:
                    teamTurn = TeamColor.BLACK;
                    break;
                case BLACK:
                    teamTurn = TeamColor.WHITE;
                    break;
            }
        }
        else {
            throw new InvalidMoveException();
        }

    }

    public boolean isInCheck(TeamColor teamColor) {

        // Create a set containing every enemy move possible.
        Set<ChessMove> allEnemyMoves = new HashSet<ChessMove>();
        ChessPosition checkingPosition;
        for (int i = 1; i <9; i++){
            for (int j = 1; j < 9; j++){
                checkingPosition = new ChessPosition(i, j);
                if (board.getPiece(checkingPosition) != null && board.getPiece(checkingPosition).getTeamColor() != teamColor) {
                    System.out.println("____moves added for " + board.getPiece(checkingPosition).getPieceType() + " at ("
                            + i + ", " + j + ")____");
                    allEnemyMoves.addAll(board.getPiece(checkingPosition).pieceMoves(board, checkingPosition));

                }
            }
        }
        System.out.println("     isInCheck allEnemyMoves: " + allEnemyMoves.toString() + "\n     Seeing if "
                + teamColor + " is in check");

        // If the set of enemy moves has a move with an end position equaling the king's position, return true.
        for (ChessMove move : allEnemyMoves) {
            if (move.getEndPosition().equals(findKing(teamColor))){
                System.out.println(teamColor + " king under attack by " + board.getPiece(move.getStartPosition()).getPieceType()
                        + " at (" + move.getEndPosition().getRow() + ", " + move.getEndPosition().getColumn() + ")");
                return true;
            }
        }
        System.out.println("No enemies in range of " + teamColor + "king.");
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        HashSet<ChessMove> allMoves = getAllMoves(teamColor);
        ChessPiece takenPiece;

        for (ChessMove move : allMoves) {
            takenPiece = addMove(move);
            if (!isInCheck(teamColor)){
                if(board.getPiece(move.getStartPosition()) != null){
                    System.out.println("KING NOT IN CHECKMATE from " + board.getPiece(move.getStartPosition()).getPieceType() + " at ("
                            + move.getEndPosition().getRow() + ", " + move.getEndPosition().getColumn() + ")" );
                    System.out.println("to (" + move.getStartPosition().getRow() + ", " + move.getStartPosition().getColumn() + ")");
                }
                else {
                    System.out.println("KING NOT IN CHECKMATE [error] TESTED MOVE IS NULL: ");
                    board.printBoard();
                    System.out.println(move.getStartPosition().getRow() + ", " + move.getStartPosition().getColumn());
                    //board.getPiece(move.getEndPosition());
                }
                return false;
            }
            revertMove(move, takenPiece);
        }
        System.out.println("KING IN CHECKMATE");
        return true;
    }

    /**
     * In order to determine checkmate, we need to be certain that no moves the given team makes can result in the
     * king leaving check. Therefore, we need a list of every move a team can make.
     *
     * @param teamColor the team who we are getting all possible moves of
     * @return a hashset containing every move possible
     */
    public HashSet<ChessMove> getAllMoves(TeamColor teamColor) {
        HashSet<ChessMove> allMoves = new HashSet<>();

        for (int column = 1; column <= 8; column++) {
            for (int row = 1; row <= 8; row++) {

                if (board.getPiece(new ChessPosition(row, column)) != null) {

                    if (board.getPiece(new ChessPosition(row, column)).getTeamColor() == teamColor) {
                        System.out.println("Getting piece at position " + row + ", " + column);
                        allMoves.addAll(board.getPiece(new ChessPosition(row, column)).pieceMoves(board, new ChessPosition(row, column)));
                    }
                }
            }
        }
        return allMoves;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {

        if (isInCheck(teamColor)){
            return false;
        }

        Set<ChessMove> allMoves = getAllMoves(teamColor);
        board.printBoard();

        for (ChessMove move : allMoves) {

            if (move == null){
                System.out.println("Move is null");
            }
            else if (move.getStartPosition() == null){
                System.out.println("Start position is null for move " + move.toString());
            }
            else if (move.getEndPosition() == null){
                System.out.println("End position is null for move " + move.toString());
            }

            System.out.println("Checking move from (" + move.getStartPosition().getRow() + ", " + move.getStartPosition().getColumn()
                    + ") to (" + move.getEndPosition().getRow() + ", " + move.getEndPosition().getColumn() + ")");

            Collection<ChessMove> validMoves = validMoves(move.getStartPosition());
            if (!validMoves(move.getStartPosition()).isEmpty()){
                return false;
            }
        }
        return true;
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
        else {
            return true;
        }
    }

    /**
     * findKing locates what position the king of the given teamColor is in.
     * @param teamColor is the team we are finding the king of.
     * @return the position of the king. If no king is found, returns null.
     */
    public ChessPosition findKing(TeamColor teamColor) {
        ChessPosition currentPosition = new ChessPosition();

        for (int row = 1; row <= 8; row++){
            currentPosition.setRowValue(row);
            for (int col = 1; col <= 8; col++){
                currentPosition.setColValue(col);
                if (board.getPiece(currentPosition) != null){
                    if (board.getPiece(currentPosition).getTeamColor() == teamColor
                    && board.getPiece(currentPosition).getPieceType() == ChessPiece.PieceType.KING){
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
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessGame chessGame)) return false;
        return teamTurn == chessGame.teamTurn && Objects.equals(board, chessGame.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamTurn, board);
    }
}
