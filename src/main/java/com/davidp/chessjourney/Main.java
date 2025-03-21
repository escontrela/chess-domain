package com.davidp.chessjourney;


import com.davidp.chessjourney.domain.ChessGame;
import com.davidp.chessjourney.domain.ChessGameFactory;
import com.davidp.chessjourney.domain.Player;
import com.davidp.chessjourney.domain.common.Fen;
import com.davidp.chessjourney.domain.common.TimeControl;

/** Example of main - nonsense on this library */
public class Main {

    public static void main(String[] args) {

        Player whitePlayer = new Player("Alice",1L);
        Player blackPlayer = new Player("Bob",2L);

        ChessGame chessGame =
                ChessGameFactory.createFrom(
                        Fen.createInitial(), whitePlayer, blackPlayer, TimeControl.fifteenPlusTen());

        // Print the initial board
        chessGame.printBoard();

    }
}