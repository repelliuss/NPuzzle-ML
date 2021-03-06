package com.repelliuss.npuzzle.game;

import com.repelliuss.npuzzle.utils.Index2D;

import java.lang.reflect.Array;

public final class NPuzzle extends SlidePuzzle<Integer> {

    private final Piece[] board;
    private int row, column;
    private final Index2D posBlank;

    public NPuzzle(int argRow, int argColumn) {
        row = argRow;
        column = argColumn;

        //noinspection unchecked
        board = (Piece[]) Array.newInstance(Piece.class, row * column);

        for(int i = 0; i < row * column - 1; ++i) board[i] = new Piece(Cell.VALUE, i + 1);
        board[row * column - 1] = new Piece(Cell.BLANK);
        posBlank = new Index2D(row - 1, column - 1);
    }

    @Override
    public int getRow() { return row; }

    @Override
    public int getColumn() { return column; }

    @Override
    public Piece getPiece(Index2D pos) {
        return board[pos.toLinear(getColumn())];
    }

    @Override
    public Index2D getPosBlank() { return posBlank; }

    @Override
    protected void setRow(int argRow) { row = argRow; }

    @Override
    protected void setColumn(int argColumn) { column = argColumn; }

    @Override
    protected void setPiece(Index2D pos, final Piece piece) {
        board[pos.toLinear(getColumn())].setPiece(piece);
    }

    @Override
    public void reset() {
        int number = 1;
        Index2D pos = new Index2D();

        for(; pos.getY() < getRow(); pos.incY()) {
            for(pos.setX(0); pos.getX() < getColumn() - 1; pos.incX()) {
                getPiece(pos).setPiece(Cell.VALUE, number++);
            }
            ++number;
        }

        number = getColumn();

        for(pos.setTo(0, getColumn() - 1); pos.getY() < getRow() - 1; pos.incY()) {
            getPiece(pos).setPiece(Cell.VALUE, number);
            number += getColumn();
        }

        pos.setTo(getRow() - 1, getColumn() - 1);
        getPiece(pos).setId(Cell.BLANK);
        getPosBlank().setTo(pos);
    }

    @Override
    public boolean isSolved() {

        Index2D pos = new Index2D(getRow() - 1, getColumn() - 1);

        if(getPiece(pos).getId() != Cell.BLANK)
            return false;

        int number = 1;

        for (pos.setY(0); pos.getY() < getRow(); pos.incY()) {
            for (pos.setX(0); pos.getX() < getColumn() - 1; pos.incX()) {
                if (getPiece(pos).getValue() != number++) return false;
            }
            ++number;
        }

        number = getColumn();

        for (pos.setTo(0, getColumn() - 1); pos.getY() < getRow() - 1; pos.incY()) {
            if (getPiece(pos).getValue() != number) return false;
            number += getColumn();
        }

        return true;
    }

    @Override
    public float[][] getInputBoard() {

        float[][] inputBoard = new float[1][getColumn()*getRow()];

        Index2D pos = new Index2D();
        for (pos.setY(0); pos.getY() < getRow(); pos.incY()) {
            for (pos.setX(0); pos.getX() < getColumn(); pos.incX()) {
                Piece piece = getPiece(pos);

                if(piece.getId() == Cell.VALUE)
                    inputBoard[0][pos.getY() * getColumn() + pos.getX()] =
                            getPiece(pos).getValue() / ((float)(getRow() * getColumn() - 1));
                else inputBoard[0][pos.getY() * getColumn() + pos.getX()] = 0.0f;
            }
        }

        return inputBoard;
    }
}
