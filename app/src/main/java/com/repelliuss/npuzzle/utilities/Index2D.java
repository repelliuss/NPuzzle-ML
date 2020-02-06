package com.repelliuss.npuzzle.utilities;

public final class Index2D {
    private int y;
    private int x;

    public Index2D(int argY, int argX) {
        y = argY;
        x = argX;
    }

    public int getY() {
        return y;
    }

    public int getX() { return x; }

    public void setY(int argY) { y = argY; }

    public void setX(int argX) { x = argX; }

    public void incX() {
        ++x;
    }

    public void incY() {
        ++y;
    }

    public void decX() {
        --x;
    }

    public void decY() {
        --y;
    }

    public static Index2D getRelativeToY(final Index2D index, int deltaY) {
        return getRelativeTo(index, deltaY, 0);
    }

    public static Index2D getRelativeToX(final Index2D index, int deltaX) {
        return getRelativeTo(index, 0, deltaX);
    }

    public static Index2D getRelativeTo(final Index2D index, int deltaY, int deltaX) {
        return new Index2D(index.getY() + deltaY, index.getX() + deltaX);
    }
}
