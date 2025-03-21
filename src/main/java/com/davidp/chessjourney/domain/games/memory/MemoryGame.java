package com.davidp.chessjourney.domain.games.memory;

import com.davidp.chessjourney.domain.ChessBoard;
import com.davidp.chessjourney.domain.Game;
import com.davidp.chessjourney.domain.Player;
import com.davidp.chessjourney.domain.common.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Base class for memory games.
 * @param <T> The type of response the user sends.
 */
public abstract class MemoryGame<T> extends Game {

    protected final Player player;
    protected ChessBoard chessBoard;
    protected final long timeToShowPiecesOnTheCurrentExerciseInSeconds = 5;
    protected final TimeControl timeControl;
    protected final List<Fen> positions = new ArrayList<>();
    protected int currentExerciseIndex;
    protected Instant startTime;
    protected Instant partialTime;
    protected final List<MemoryGamePartialStat> stats = new ArrayList<>();
    protected final DifficultyLevel difficultyLevel;
    protected final List<Exercise> exercises;
    protected UUID currentExerciseId;
    protected GameState gameState = GameState.WAITING_TO_START;

    public MemoryGame(Player player, ChessBoard board, TimeControl timeControl,
                      DifficultyLevel difficultyLevel, List<Exercise> exercises) {

        super();
        this.player = player;
        this.chessBoard = board;
        this.timeControl = timeControl;
        this.difficultyLevel = difficultyLevel;
        this.exercises = exercises;
        this.currentExerciseIndex = 0;
        // Se obtienen las posiciones FEN a partir de los ejercicios.
        exercises.forEach(exercise -> positions.add(Fen.createCustom(exercise.getFen())));
    }

    /**
     * Inicia el juego desde el primer ejercicio.
     */
    public void startGame() {

        if (gameState != GameState.WAITING_TO_START && gameState != GameState.GAME_OVER) {

            throw new IllegalStateException("El juego ya ha comenzado.");
        }

        startTime = Instant.now();

        gameState = GameState.SHOWING_PIECES;
        loadExercise();
    }

    public boolean isTimeToHidePiecesOnTheCurrentExercise() {

        if (gameState != GameState.SHOWING_PIECES) {

            return false;
        }

        Instant currentTime = Instant.now();
        boolean timeToHide = java.time.Duration.between(partialTime, currentTime).getSeconds()
                > timeToShowPiecesOnTheCurrentExerciseInSeconds;

        if (timeToHide) {

            gameState = GameState.GUESSING_PIECES;
        }

        return timeToHide;
    }

    /**
     * Devuelve el tiempo transcurrido desde que se inició el juego.
     */
    public long getElapsedTimeInSeconds() {

        if (startTime == null){

            return 0;
        }

        return java.time.Duration.between(startTime, Instant.now()).getSeconds();
    }

    public int getCurrentExerciseNumber(){

        return  currentExerciseIndex + 1;
    }


    /**
     * For Example: 2 pieces to guess on Guess MemoryGame
     * @return total steps per exercise
     */
    public abstract int getTotalStepsPerExercise();

    /**
     * For Example: the user try to guess 1 piece over 2 pieces to guess on Guess MemoryGame
     * @return The current exercise step over the total steps.
     */
    public abstract int getPartialStepCounter();


    public String getFormatedElapsedTime() {

        long seconds = getElapsedTimeInSeconds();
        long minutes = seconds / 60;
        long remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }

    /**
     * Each memory game has a different way to load the exercise
     */
    public abstract void loadExercise();

    /**
     * Get the success percentage of the game.
     * @return  The success percentage.
     */
    public  abstract  int getSuccessPercentage();

    /**
     * Registra la respuesta del usuario.
     * El parámetro T representa el tipo de respuesta (por ejemplo, Boolean o String).
     */
    public abstract boolean submitAnswer(T answer);


    /**
     * Get the hidden piece positions.
     * @return  The hidden piece positions.
     */
    public abstract  List<PiecePosition> getHiddenPiecePositions();

    /**
     * Go to the next exercise.
     */
    public void nextExercise() {

        if (!hasMoreExercises()) {

            gameState = GameState.GAME_OVER;
            return;
        }

        currentExerciseIndex++;
        loadExercise();
    }

    public abstract boolean isTimeToMoveToNextExercise();


    public boolean hasMoreExercises() {

        return currentExerciseIndex < positions.size() - 1;
    }



    public enum GameState {

        WAITING_TO_START,
        SHOWING_PIECES,
        GUESSING_PIECES,
        GAME_OVER
    }

    public abstract GameKind getGameKind();

    public enum GameKind{

        GUESS_MEMORY_GAME,
        DEFEND_MEMORY_GAME
    }

    public static class MemoryGamePartialStat {

        private int level;
        private boolean succeeded;
        private long time;

        public MemoryGamePartialStat(int level, boolean succeeded, long time) {

            this.level = level;
            this.succeeded = succeeded;
            this.time = time;
        }
        // Se pueden agregar getters y setters si fuera necesario.
    }

    @Override
    public Fen getFen() {

        return chessBoard.getFen();
    }

    public UUID getCurrentExerciseId(){

        return currentExerciseId;
    }

    public GameState getGameState(){

        return gameState;
    }

    public DifficultyLevel getDifficultyLevel() {

        return difficultyLevel;
    }
}