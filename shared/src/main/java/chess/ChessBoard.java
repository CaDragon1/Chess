package chess;

import java.util.Arrays;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

    private ChessPiece[][] board;

    public ChessBoard() {
        board = new ChessPiece[8][8];
        //resetBoard();
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        if(position.colValue > 0 && position.rowValue > 0) {
            if(position.colValue <= 8 && position.rowValue <= 8) {
                board[position.getRow() - 1][position.getColumn() - 1] = piece;
            }
        }
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        if(position.colValue > 0 && position.rowValue > 0) {
            return board[position.getRow() - 1][position.getColumn() - 1];
        }
        else return null;
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        clearBoard();
        /*System.out.println("Board cleared. Current state:");
        printBoard();*/
        setBackRow(ChessGame.TeamColor.BLACK, 7);
        setPawns(ChessGame.TeamColor.BLACK, 6);
        setPawns(ChessGame.TeamColor.WHITE, 1);
        setBackRow(ChessGame.TeamColor.WHITE, 0);
        /*System.out.println("Board reset. Current state:");
        printBoard();*/
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
    private void setPawns(ChessGame.TeamColor teamColor, int row) {
        if (row == 1 || row == 6) {
            for (int column = 0; column < 8; column++) {
                board[row][column] = new ChessPiece(teamColor, ChessPiece.PieceType.PAWN);
            }
        }
    }

    // This function sets up the back starting row of a normal chess game at a given row index.
    private void setBackRow(ChessGame.TeamColor teamColor, int row) {
        if(row == 0 || row == 7) {
            for (int column = 0; column < 8; column++) {

                if (column == 0 || column == 7) {
                    board[row][column] = new ChessPiece(teamColor, ChessPiece.PieceType.ROOK);
                } else if (column == 1 || column == 6) {
                    board[row][column] = new ChessPiece(teamColor, ChessPiece.PieceType.KNIGHT);
                } else if (column == 2 || column == 5) {
                    board[row][column] = new ChessPiece(teamColor, ChessPiece.PieceType.BISHOP);
                } else if (column == 3) {
                    board[row][column] = new ChessPiece(teamColor, ChessPiece.PieceType.QUEEN);
                } else {
                    board[row][column] = new ChessPiece(teamColor, ChessPiece.PieceType.KING);
                }
            }
        }
    }

    // Additional function to print the board state for testing purposes.
    public void printBoard () {
        System.out.println("Printing board:\n________________");
        for (int row = 7; row >= 0; row--) {
            for (int column = 0; column < 8; column++) {
                /*if (board[row][column] != null) {
                    if (board[row][column].piece == ChessPiece.PieceType.PAWN) {
                        System.out.print("P ");
                    } else if (board[row][column].piece == ChessPiece.PieceType.ROOK) {
                        System.out.print("R ");
                    } else if (board[row][column].piece == ChessPiece.PieceType.KNIGHT) {
                        System.out.print("KN");
                    } else if (board[row][column].piece == ChessPiece.PieceType.BISHOP) {
                        System.out.print("B ");
                    } else if (board[row][column].piece == ChessPiece.PieceType.QUEEN) {
                        System.out.print("Q ");
                    } else if (board[row][column].piece == ChessPiece.PieceType.KING) {
                        System.out.print("K ");
                    }*/

                if (board[row][column] != null) {
                    System.out.print(board[row][column].color + " " + board[row][column].piece + " | ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
        System.out.println("_________________");
    }

    // Overridden equals and hashCode methods to test for deep equality
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that = (ChessBoard) o;
        printBoard();
        System.out.println(Arrays.deepToString(board) + "\n^^ This board == Test Board vv\n"
                + Arrays.deepToString(that.board));
        that.printBoard();
        return Arrays.deepEquals(board, that.board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }
}
