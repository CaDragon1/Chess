package Models;

import chess.ChessBoard;
import chess.ChessGame;

/**
 * GameData object contains all necessary data to identify and run a chess game.
 * Getters and Setters are provided, and the ChessGame object itself has all the chess logic.
 */
public class GameData {

    /**
     * Initialize all variables relating to the chess game instance
     */
    private String gameName;
    private String whiteName;
    private String blackName;
    private int gameID;
    private ChessGame game;
    private ChessBoard board;

    /**
     * Object initialization
     * @param gameName is the name of the chess game
     * @param gameID is the unique ID of the chess game
     * @param whiteName is the username of the white player
     * @param blackName is the username of the black player
     * @param game is the ChessGame object that runs a chess game
     */
    public GameData(String gameName, int gameID, String whiteName, String blackName, ChessGame game) {
        this.gameName = gameName;
        this.gameID = gameID;
        this.whiteName = whiteName;
        this.blackName = blackName;
        this.game = game;
        board = game.getBoard();
    }
}
