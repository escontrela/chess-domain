package com.davidp.chessjourney.domain.services;

import com.davidp.chessjourney.domain.common.PieceType;
import com.davidp.chessjourney.domain.common.Pos;

import java.util.ArrayList;
import java.util.List;

public class PosChessMove {
    private final List<PieceMove> pieceMoves;
    private final boolean isCapture;
    private final boolean isCheck;
    private final boolean isMate;
    private final boolean isCastling;
    private final boolean isPromotion;
    private final PieceType promotionPiece;
    private final boolean isEnPassant;

    public PosChessMove(List<PieceMove> pieceMoves, boolean isCapture, boolean isCheck,
                        boolean isMate, boolean isCastling, boolean isPromotion,
                        PieceType promotionPiece, boolean isEnPassant) {
        this.pieceMoves = new ArrayList<>(pieceMoves);
        this.isCapture = isCapture;
        this.isCheck = isCheck;
        this.isMate = isMate;
        this.isCastling = isCastling;
        this.isPromotion = isPromotion;
        this.promotionPiece = promotionPiece;
        this.isEnPassant = isEnPassant;
    }

    public List<PieceMove> getPieceMoves() {
        return new ArrayList<>(pieceMoves);
    }

    public boolean isCapture() {
        return isCapture;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public boolean isMate() {
        return isMate;
    }

    public boolean isCastling() {
        return isCastling;
    }

    public boolean isPromotion() {
        return isPromotion;
    }

    public PieceType getPromotionPiece() {
        return promotionPiece;
    }

    public boolean isEnPassant() {
        return isEnPassant;
    }

    public PieceMove getPrimaryMove() {
        return pieceMoves.isEmpty() ? null : pieceMoves.get(0);
    }

    public PieceMove getSecondaryMove() {
        return pieceMoves.size() < 2 ? null : pieceMoves.get(1);
    }

    public static class PieceMove {
        private final Pos from;
        private final Pos to;

        public PieceMove(Pos from, Pos to) {
            this.from = from;
            this.to = to;
        }

        public Pos getFrom() {
            return from;
        }

        public Pos getTo() {
            return to;
        }

        @Override
        public String toString() {
            return String.format("%s -> %s", from, to);
        }
    }

    public enum ChessMoveCastlingType {
        KINGSIDE,   // O-O
        QUEENSIDE   // O-O-O
    }
}
