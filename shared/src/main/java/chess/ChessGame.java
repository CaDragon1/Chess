package chess;

import javax.swing.text.Position;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
        Collection<ChessMove> validMoves = board.getPiece(startPosition).pieceMoves(board, startPosition);
        checkingBoard = board;
        for (ChessMove move : validMoves) {
            checkingBoard.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
            checkingBoard.addPiece(move.getStartPosition(), null);
            if (putsInCheck(checkingBoard, checkingBoard.getPiece(move.getEndPosition()).getTeamColor())){
                validMoves.remove(move);
            }
        }

        return validMoves;
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
            checkingBoard.addPiece(move.getStartPosition(), null);
        }
        if (!putsInCheck(checkingBoard, teamTurn)){
            board = checkingBoard;
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

            checkingBoard = board;
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        return putsInCheck(board, teamColor);
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        HashSet<ChessMove> allMoves = getAllMoves(board, teamColor);

        for (ChessMove move : allMoves) {
            checkingBoard.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
            checkingBoard.addPiece(move.getStartPosition(), null);
            if (!putsInCheck(checkingBoard, teamColor)){
                checkingBoard = board;
                return false;
            }
            checkingBoard = board;
        }
        return true;
    }

    /**
     * In order to determine checkmate, we need to be certain that no moves the given team makes can result in the
     * king leaving check. Therefore, we need a list of every move a team can make.
     *
     * @param gameBoard the board we are checking all moves for
     * @param teamColor the team who we are getting all possible moves of
     * @return a hashset containing every move possible
     */
    public HashSet<ChessMove> getAllMoves(ChessBoard gameBoard, TeamColor teamColor) {
        HashSet<ChessMove> allMoves = new HashSet<>();
        ChessPosition checkingPosition = new ChessPosition();

        for (int column = 1; column <= 8; column++) {
            for (int row = 1; row <= 8; row++) {

                checkingPosition.setColValue(column);
                checkingPosition.setRowValue(row);
                // The position has to have a piece of the team color in order to add the moves of.
                if (gameBoard.getPiece(checkingPosition) != null
                        && gameBoard.getPiece(checkingPosition).getTeamColor() == teamColor) {
                    allMoves.addAll(board.getPiece(checkingPosition).pieceMoves(gameBoard, checkingPosition));
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
        boolean inDanger;
        System.out.println("'I shall scout for danger, my king!'");
        if (kingPosition != null) {
            inDanger = checkAllDiagonals(gameBoard, kingPosition);
            if (!inDanger) {
                inDanger = checkAllStraights(gameBoard, kingPosition);
            }
        }
        else {
            inDanger = false;
        }
        return inDanger;
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
        System.out.println("'COMMENCING DIAGONAL CHECKING PATTERN, MY LIEGE.'");
        boolean inDanger = false;
        for (int i = -1; i <= 1; i+=2){
            for (int j = -1; j <= 1; j+=2){
                if(!inDanger){
                    System.out.print(">> checking diagonal (" + i + ", " + j + "): ");
                    inDanger = checkDiagonal(i, j, kingPosition,
                            gameBoard.getPiece(kingPosition).getTeamColor(), gameBoard);
                    System.out.println("inDanger = " + inDanger);
                }
            }
        }
        return inDanger;
    }

    public boolean checkAllStraights(ChessBoard gameBoard, ChessPosition kingPosition) {
        System.out.println("'COMMENCING STRAIGHT CHECKING PATTERN, MY LIEGE.'");
        boolean inDanger = false;
        for (int i = -1; i <= 1; i+=2){
            if(!inDanger){
                System.out.print(">> checking vertical line in direction " + i + ": ");
                inDanger = checkRow(i, kingPosition, gameBoard.getPiece(kingPosition).getTeamColor(), gameBoard);
                System.out.println("in danger = " + inDanger);
                if(!inDanger){
                    System.out.print(">> checking horizontal line in direction " + i + ": ");
                    inDanger = checkCol(i, kingPosition, gameBoard.getPiece(kingPosition).getTeamColor(), gameBoard);
                    System.out.println("inDanger = " + inDanger);
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
                System.out.println("    'Verily, there be'eth no piece in this diagonal at "
                        + checkingPosition.getColumn() + " " + checkingPosition.getRow() + ")'.");
                incrementer++;
                checkingPosition.setColValue(kingPosition.getColumn() + (incrementer * colIncrement));
                checkingPosition.setRowValue(kingPosition.getRow() + (incrementer * rowIncrement));
                keepChecking = isInBounds(checkingPosition.getRow(), checkingPosition.getColumn());
            }
            else if (board.getPiece(checkingPosition).getTeamColor() == teamColor) {
                keepChecking = false;
                System.out.println("    'Hark! A friend doth stand at " + checkingPosition.getColumn() + " " + checkingPosition.getRow() + ")'.");
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
     * checkRow checks the vertical straights originating at the king's position.
     * @param rowIncrement -1 if down, 1 if up
     * @param kingPosition Self-explanatory. Determining if the king is in danger
     * @param teamColor The current team's color
     * @param gameBoard The game board
     * @return true if the king is in danger, false if not
     */
    private boolean checkRow(int rowIncrement, ChessPosition kingPosition, TeamColor teamColor, ChessBoard gameBoard) {
        boolean keepChecking = true;
        int incrementer = 1;
        keepChecking = isInBounds(kingPosition.getRow() + rowIncrement, kingPosition.getColumn());

        ChessPosition checkingPosition = new ChessPosition(kingPosition.getRow() + (incrementer * rowIncrement),
                kingPosition.getColumn());
        while (keepChecking) {
            if (board.getPiece(checkingPosition) == null) {
                System.out.println("    'Verily, there be'eth no piece in this row at "
                        + checkingPosition.getColumn() + " " + checkingPosition.getRow() + ")'.");
                incrementer++;
                checkingPosition.setRowValue(kingPosition.getRow() + (incrementer * rowIncrement));
                keepChecking = isInBounds(checkingPosition.getRow(), checkingPosition.getColumn());
            }
            else {
                System.out.println("    'The column is unsafe from a rook or queen at (" + checkingPosition.getColumn() + ", " + checkingPosition.getRow() + ")'!!!");
                keepChecking = false;
                return rookThreaten(checkingPosition, gameBoard, teamColor);
            }
        }
        return false;
    }

    /**
     * checkRow checks the vertical straights originating at the king's position.
     * @param colIncrement -1 if left, 1 if right
     * @param kingPosition Self-explanatory. Determining if the king is in danger
     * @param teamColor The current team's color
     * @param gameBoard The game board
     * @return true if the king is in danger, false if not
     */
    private boolean checkCol(int colIncrement, ChessPosition kingPosition, TeamColor teamColor, ChessBoard gameBoard) {
        boolean keepChecking = true;
        int incrementer = 1;
        keepChecking = isInBounds(kingPosition.getRow(), kingPosition.getColumn() + colIncrement);

        ChessPosition checkingPosition = new ChessPosition(kingPosition.getRow(),
                kingPosition.getColumn() + (incrementer * colIncrement));
        while (keepChecking) {
            if (board.getPiece(checkingPosition) == null) {
                System.out.println("    'Verily, there be'eth no piece in this column at "
                        + checkingPosition.getColumn() + " " + checkingPosition.getRow() + ")'.");
                incrementer++;
                checkingPosition.setColValue(kingPosition.getColumn() + (incrementer * colIncrement));
                keepChecking = isInBounds(checkingPosition.getRow(), checkingPosition.getColumn());
            }
            else {
                System.out.println("    'The row is unsafe from a rook or queen at (" + checkingPosition.getColumn() + ", " + checkingPosition.getRow() + ")'!!!");
                keepChecking = false;
                return rookThreaten(checkingPosition, gameBoard, teamColor);
            }
        }
        return false;
    }

    /**
     * Checks to see if an enemy rook (or a queen) can reach the given position
     * @param checkingPosition
     * @param gameBoard
     * @param teamColor
     * @return true if reachable by enemy rook or queen, otherwise false
     */
    private boolean rookThreaten(ChessPosition checkingPosition, ChessBoard gameBoard, TeamColor teamColor) {
        if (board.getPiece(checkingPosition).getTeamColor() == teamColor) {
            System.out.println("    'Hark! A friend doth stand at " + checkingPosition.getColumn() + " " + checkingPosition.getRow() + ")'.");
            return false;
        }
        else {
            System.out.println("    'Lo, the king is under attack from a rook or queen at (" + checkingPosition.getColumn() + ", " + checkingPosition.getRow() + ")'!!!");
            return board.getPiece(checkingPosition).getPieceType() == ChessPiece.PieceType.ROOK
                    || board.getPiece(checkingPosition).getPieceType() == ChessPiece.PieceType.QUEEN;

        }

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
