package com.repelliuss.npuzzle.game;

import androidx.annotation.NonNull;

import com.repelliuss.npuzzle.utilities.Index2D;
import com.repelliuss.npuzzle.utilities.Move;

public abstract class SlidePuzzle<T> implements Puzzle<SlidePuzzle<T>.Piece> {

    private Move lastMove;
    private int moveCount;
    private final Index2D posBlank;

    enum Cell {
        VALUE,
        BLANK
    }

    public class Piece implements BoardPiece<Cell, T> {
        Cell id;
        T value;

        public Cell getId() { return id; }
        public T getValue() { return value; }

        public void setId(final Cell argId) { id = argId; }
        public void setValue(final T argValue) { value = argValue; }
    }

    protected SlidePuzzle(Index2D argPosBlank) {

        if(argPosBlank == null) throw new IllegalArgumentException("argPosBlank is null");

        lastMove = Move.STAY;
        moveCount = 0;
        posBlank = argPosBlank;
    }

    public Move getLastMove() {
        return lastMove;
    }

    public int getMoveCount() {
        return moveCount;
    }

    private void setLastMove(Move argLastMove) {
        lastMove = argLastMove;
    }

    private void incMoveCount() {
        ++moveCount;
    }

    private Index2D getPosBlank() {
        return posBlank;
    }

    protected abstract void setRow(int argColumn);
    protected abstract void setColumn(int argColumn);
    protected abstract void setPiece(Index2D pos, Piece piece);

    public boolean move(@NonNull final Move move) {
        if(checkMove(move)) {
            switch(move) {
                case LEFT:
                    swapCell(getPosBlank(), Index2D.getRelativeToX(getPosBlank(), -1));
                    break;
                case RIGHT:
                    swapCell(getPosBlank(), Index2D.getRelativeToX(getPosBlank(), 1));
                    break;
                case UP:
                    swapCell(getPosBlank(), Index2D.getRelativeToY(getPosBlank(), -1));
                    break;
                case DOWN:
                    swapCell(getPosBlank(), Index2D.getRelativeToY(getPosBlank(), 1));
                    break;
            }

            incMoveCount();
            setLastMove(move);

            return true;
        }

        return false;
    }

    public boolean checkMove(@NonNull final Move move) {
        switch(move) {
            case LEFT:
                return checkMove(0, -1);
            case RIGHT:
                return checkMove(0, 1);
            case UP:
                return checkMove(-1, 0);
            case DOWN:
                return checkMove(1, 0);
            default:
                return false;
        }
    }

    public boolean checkMove(int yMove, int xMove) {

        return  getPosBlank().getY() + yMove < getRow() &&
                getPosBlank().getY() + yMove >= 0 &&
                getPosBlank().getX() + xMove < getColumn() &&
                getPosBlank().getX() + xMove >= 0;
    }

    private void swapCell(Index2D left, Index2D right) {
        Piece temp = getPiece(left);
        setPiece(left, getPiece(right));
        setPiece(right, temp);
    }
}