package chess.movelists;

import chess.*;

import java.util.ArrayList;
import java.util.HashSet;

// This is the move list calculator for the pawn.
public class PawnMovesList extends MovesList {
    public PawnMovesList(ChessBoard board, ChessPosition currentPosition) {
        super(board, currentPosition);
        pieceMoves = new HashSet<ChessMove>();
        possibleMove = new ChessMove();
        calculateMove(currentPosition);

    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    // We need to check the space in front of the pawn (+1 if white, -1 if black) and see if it is 1) a space on the
    // board, and 2) there is no piece in front of the pawn. Then we append that space to our array list.
    // Only pawns move unidirectionally, so only pawns require a teamColor check.
    @Override
    void calculateMove(ChessPosition myPosition) {
        System.out.println("CURRENT POSITION: (" + myPosition.getRow() + ", " + myPosition.getColumn()
                + ") PIECE IN POSITION: " + board.getPiece(myPosition).getTeamColor() + " " + board.getPiece(myPosition).getPieceType());
        System.out.println("Checking if white:");
        // Check if white
        if (board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
            System.out.println("    White move");
            checkingPosition = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn());
            System.out.println("Checking if pawn can move:");
            if (checkingPosition.getRow() != 9 && board.getPiece(checkingPosition) == null) {
                System.out.println("    Pawn move");
                addingMove(myPosition);
                // Double move on first move
                System.out.println("Checking if pawn can double move:");
                checkingPosition.setRowValue(4);
                if (myPosition.getRow() == 2 && board.getPiece(checkingPosition) == null) {
                    System.out.println("    Pawn double move");
                    addingMove(myPosition);
                }
                else if (myPosition.getRow() == 2 && board.getPiece(checkingPosition) != null) {
                    System.out.println("    Obstructed pawn move");
                }
            }
            printList();
            // Check to see if you can attack
            if (checkingPosition.getRow() < 8) {
                checkingPosition.setRowValue(myPosition.getRow() + 1);
                attack(myPosition);
            }

            // Check to see if piece can get promoted
            if(checkingPosition.getRow() == 8) {

            }

        }
        // Otherwise, piece is black
        else {
            System.out.println("    Black move");
            checkingPosition = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn());
            System.out.println("Checking if pawn can move:");
            if (checkingPosition.getRow() != 0 && board.getPiece(checkingPosition) == null) {
                System.out.println("    Pawn move");
                addingMove(myPosition);
                // Double move on first move
                System.out.println("Checking if pawn can double move:");
                checkingPosition.setRowValue(5);
                if (myPosition.getRow() == 7 && board.getPiece(checkingPosition) == null) {
                    System.out.println("    Pawn double move");
                    addingMove(myPosition);
                }
                else if (myPosition.getRow() == 2 && board.getPiece(checkingPosition) != null) {
                    System.out.println("    Obstructed pawn move");
                }
            }
            else{
                System.out.println("    Pawn cannot move forward");
            }
            // Check to see if you can attack
            if (checkingPosition.getRow() <= 8) {
                System.out.println("Checking attack angles:");
                checkingPosition.setRowValue(myPosition.getRow() + 1);
                attack(myPosition);
            }
        }
        System.out.println("    _function end_");
        printList();
    }

    private void addingMove(ChessPosition myPosition) {
        System.out.print("ADDING MOVE\n--Piece in checking position: \n");
        if (board.getPiece(checkingPosition) != null ){
            System.out.println(board.getPiece(checkingPosition).getTeamColor() + " " + board.getPiece(checkingPosition).getPieceType());
        }
        possibleMove = new ChessMove(myPosition, checkingPosition, null);
        pieceMoves.add(possibleMove);
        System.out.println("--Added new move: (" + checkingPosition.getRow() + ", " + checkingPosition.getColumn() + ")");
        System.out.println("--Piece color: " + board.getPiece(myPosition).getTeamColor() + " Piece position: (" + myPosition.getRow() + ", " + myPosition.getColumn() + ")\n");
        printList();
    }

    // Attack move logic: If there is a piece at the diagonal spaces, the pawn can add those spaces to its movelist.
    public void attack(ChessPosition myPosition){
        System.out.println("Checking column-1:");
        if(myPosition.getColumn() > 1) {
            checkingPosition.setColValue(myPosition.getColumn() - 1);
            if(board.getPiece(checkingPosition) != null
                    && board.getPiece(checkingPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                System.out.println("    Can Attack");
                possibleMove = new ChessMove(myPosition, checkingPosition, null);
                pieceMoves.add(possibleMove);
            }
        }
        System.out.println("Checking column+1:");
        if(myPosition.getColumn() < 8) {
            checkingPosition.setRowValue(myPosition.getRow() + 1);
            if(board.getPiece(checkingPosition) != null
                    && board.getPiece(checkingPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                System.out.println("    Can attack");
                possibleMove = new ChessMove(myPosition, checkingPosition, null);
                pieceMoves.add(possibleMove);
            }
        }
    }

    public void printList(){
        System.out.println("Printing list of pawn moves:");
        for(ChessMove element : pieceMoves){
            System.out.println("start (" + element.getStartPosition() + "), end (" + element.getEndPosition() + ")");
        }
    }

    @Override
    public HashSet<ChessMove> getPossibleMoves() {
        return pieceMoves;
    }
}
