package com.repelliuss.npuzzle.game;

import com.repelliuss.npuzzle.utils.Index2D;

public interface GameBoard<E extends BoardPiece> extends Game {
    int getRow();
    int getColumn();
    E getPiece(Index2D pos);
}
