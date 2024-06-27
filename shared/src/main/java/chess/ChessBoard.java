package chess;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

    ChessPiece[][] board;

    public ChessBoard() {
        board = new ChessPiece[8][8];
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        board[position.getRow()][position.getColumn()] = piece; /* This does not check to see if the space is empty.
                                                               Depending on how the tests work, may need to be fixed. */
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return board[position.getRow()][position.getColumn()];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        clearBoard();
        setPawns(ChessGame.TeamColor.BLACK, 2);
        setPawns(ChessGame.TeamColor.WHITE, 7);
        setPawns(ChessGame.TeamColor.BLACK, 1);
        setBackRow(ChessGame.TeamColor.WHITE, 8);

    }

    //This function clears the board entirely.
    private void clearBoard(){
        for(int row = 0; row < 8; row++){
            for(int column = 0; column < 8; column++){
                board[row][column] = null;
            }
        }
    }

    // This function sets a row of pawns of a given color to a given row index.
    private void setPawns(ChessGame.TeamColor teamColor, int row){
        for (int column = 0; column < 8; column++) {
            board[row][column] = new ChessPiece(teamColor, ChessPiece.PieceType.PAWN);
        }
    }

    // This function sets up the back starting row of a normal chess game at a given row index.
    private void setBackRow(ChessGame.TeamColor teamColor, int row){
        for (int column = 0; column < 8; column++) {

            /*if(column == 0 || column == 7){
                board[row][column] = new ChessPiece(teamColor, ChessPiece.PieceType.ROOK);
            }
            else if(column == 1 || column == 6){
                board[row][column] = new ChessPiece(teamColor, ChessPiece.PieceType.KNIGHT);
            }
            else if(column == 2 || column == 5){
                board[row][column] = new ChessPiece(teamColor, ChessPiece.PieceType.BISHOP);
            }
            else if(column == 3){
                board[row][column] = new ChessPiece(teamColor, ChessPiece.PieceType.QUEEN);
            }
            else{
                board[row][column] = new ChessPiece(teamColor, ChessPiece.PieceType.KING);*/

            switch (column){
                case 0:
                case 7:
                    board[row][column] = new ChessPiece(teamColor, ChessPiece.PieceType.ROOK);
                case 1:
                case 6:
                    board[row][column] = new ChessPiece(teamColor, ChessPiece.PieceType.KNIGHT);
                case 2:
                case 5:
                    board[row][column] = new ChessPiece(teamColor, ChessPiece.PieceType.BISHOP);
                case 3:
                    board[row][column] = new ChessPiece(teamColor, ChessPiece.PieceType.QUEEN);
                case 4:
                    board[row][column] = new ChessPiece(teamColor, ChessPiece.PieceType.KING);
            }

        }
    }
}
