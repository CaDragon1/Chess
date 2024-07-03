package chess.movelists;

import chess.*;

import java.util.HashSet;

// This is the move list calculator for the pawn.
public class PawnMovesList extends MovesList {
    public PawnMovesList(ChessBoard board, ChessPosition currentPosition) {
        super(board, currentPosition);
        pieceMoves = new HashSet<ChessMove>();
        //possibleMove = new ChessMove();
        calculateMove(currentPosition);
        //ChessPosition checkingPosition = new ChessPosition();

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
        ChessPosition checkingPosition;
        if (board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
            System.out.println("    White move");
            checkingPosition = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn());
            System.out.println("Checking if pawn can move:");
            if (checkingPosition.getRow() != 9 && board.getPiece(checkingPosition) == null) {
                System.out.println("    Pawn move");

                // Check to see if piece can get promoted
                if(checkingPosition.getRow() == 8) {
                    addingPromotion(myPosition, checkingPosition);
                }
                else{
                    addingMove(myPosition, checkingPosition);
                }
                // Check double move at start
                if(myPosition.getRow() == 2) {
                    doubleMoveCheck(myPosition, checkingPosition);
                }

            }
            printList();
            // Check to see if you can attack
            if (checkingPosition.getRow() < 8) {
                checkingPosition.setRowValue(myPosition.getRow() + 1);
                attack(myPosition, checkingPosition);
            }



        }
        // Otherwise, piece is black
        else {
            System.out.println("    Black move");
            checkingPosition = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn());
            System.out.println("Checking if pawn can move:");
            if (checkingPosition.getRow() != 0 && board.getPiece(checkingPosition) == null) {
                System.out.println("    Pawn move");


                // Check to see if piece can get promoted
                if(checkingPosition.getRow() == 1) {
                    addingPromotion(myPosition, checkingPosition);
                }
                else{
                    addingMove(myPosition, checkingPosition);
                }

                // Check double move at start
                if(myPosition.getRow() == 7) {
                    doubleMoveCheck(myPosition, checkingPosition);
                }
            }
            else{
                System.out.println("    Pawn cannot move forward");
            }
            // Check to see if you can attack
            checkingPosition.setRowValue(myPosition.getRow() - 1);
            if (checkingPosition.getRow() >= 1) {
                System.out.println("Checking attack angles:");
                attack(myPosition, checkingPosition);
            }
        }
        System.out.println("    _function end_");
        printList();
    }

    // Method to determine if a pawn can move twice
    private void doubleMoveCheck(ChessPosition myPosition, ChessPosition checkingPosition) {
        // Double move on first move
        // Check my piece color
        int myRow;
        int checkRow;
        if(board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
            myRow = 2;
            checkRow = 4;
        }
        else{
            myRow = 7;
            checkRow = 5;
        }
        System.out.println("Checking if pawn can double move:");
        checkingPosition.setRowValue(checkRow);
        if (board.getPiece(checkingPosition) == null) {
            System.out.println("    Pawn double move");
            addingMove(myPosition, checkingPosition);
        }
        else if (board.getPiece(checkingPosition) != null) {
            System.out.println("    Obstructed pawn move");
        }
    }

    private void addingMove(ChessPosition myPosition, ChessPosition checkingPosition) {
        System.out.print("ADDING MOVE\n");
        if (board.getPiece(checkingPosition) != null ){
            System.out.println(board.getPiece(checkingPosition).getTeamColor() + " " + board.getPiece(checkingPosition).getPieceType());
        }
        //possibleMove = new ChessMove(myPosition, checkingPosition, null);
        pieceMoves.add(new ChessMove(myPosition, new ChessPosition(checkingPosition.getRow(), checkingPosition.getColumn()), null));
        System.out.println("--Added new move: (" + checkingPosition.getRow() + ", " + checkingPosition.getColumn() + ")");
        System.out.println("--Piece color: " + board.getPiece(myPosition).getTeamColor() + " Piece position: (" + myPosition.getRow() + ", " + myPosition.getColumn() + ")\n");
        printList();
    }

    // Promotion adding function for when a pawn reaches the end
    private void addingPromotion(ChessPosition myPosition, ChessPosition checkingPosition) {
        System.out.print("ADDING PROMOTIONS\n");
        if (board.getPiece(checkingPosition) != null && board.getPiece(checkingPosition).getTeamColor()  == board.getPiece(myPosition).getTeamColor()){
            System.out.println(board.getPiece(checkingPosition).getTeamColor() + " " + board.getPiece(checkingPosition).getPieceType());
        }
        else {
            pieceMoves.add(new ChessMove(myPosition, new ChessPosition(checkingPosition.getRow(), checkingPosition.getColumn()), ChessPiece.PieceType.QUEEN));
            pieceMoves.add(new ChessMove(myPosition, new ChessPosition(checkingPosition.getRow(), checkingPosition.getColumn()), ChessPiece.PieceType.BISHOP));
            pieceMoves.add(new ChessMove(myPosition, new ChessPosition(checkingPosition.getRow(), checkingPosition.getColumn()), ChessPiece.PieceType.KNIGHT));
            pieceMoves.add(new ChessMove(myPosition, new ChessPosition(checkingPosition.getRow(), checkingPosition.getColumn()), ChessPiece.PieceType.ROOK));
        }
    }

    // Attack move logic: If there is a piece at the diagonal spaces, the pawn can add those spaces to its movelist.
    public void attack(ChessPosition myPosition, ChessPosition checkingPosition){

        System.out.println("Checking column-1:");
        if(myPosition.getColumn() > 1) {
            checkingPosition.setColValue(myPosition.getColumn() - 1);

            if(board.getPiece(checkingPosition) != null
                    && board.getPiece(checkingPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                System.out.println("    Can Attack");

                if(checkingPosition.getRow() == 8) {
                    addingPromotion(myPosition, checkingPosition);
                }
                else{
                    addingMove(myPosition, checkingPosition);
                }
            }
        }
        System.out.println("Checking column+1:");
        if(myPosition.getColumn() < 8) {
            checkingPosition.setColValue(myPosition.getColumn() + 1);
            if(board.getPiece(checkingPosition) != null
                    && board.getPiece(checkingPosition).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                System.out.println("    Can attack");
                //possibleMove = new ChessMove(myPosition, checkingPosition, null);
                pieceMoves.add(new ChessMove(myPosition, new ChessPosition(checkingPosition.getRow(), checkingPosition.getColumn()), null));
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
