package chess;

/**
 * Based on Forestf90 AI.java
 * This code is the core for the BoardAI, this implements how the AI moves in the game
 * I did not write this one, just use it as it is to be able to play against the machine
 */

import chess.board.Board;
import chess.pieces.Chess_piece;
import chess.pieces.King;
import chess.pieces.Pawn;
import chess.pieces.Queen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AI 
{
    static int currentDepth;
    static int maxDepth = 4;

    public static Move minMax(Chess_piece[][] board, SideColor col, int alpha, int beta) 
    {
        int bestScore = alpha;
        int bestID = 0;
        int move, score;
        int i = 0;
        ArrayList<Move> allMoves = getAIMoves(col, board);
        Collections.shuffle(allMoves);
        Chess_piece[][] tempBoard = Arrays.stream(board).map(Chess_piece[]::clone).toArray(Chess_piece[][]::new);
        for (Move p : allMoves) {
            score = getScore(tempBoard, p.getEnd());
            moveChess_pieceAI(p.getEnd(), p.getStart(), tempBoard);
            move = min(currentDepth + 1, col.swapColor(), tempBoard, alpha, beta);
            score += move;
            moveChess_pieceAI(p.getStart(), p.getEnd(), tempBoard);
            if (score > bestScore) {
                bestID = i;
                bestScore = score;
            }
            i++;
        }
        return allMoves.get(bestID);
    }

    public static int min(int depth, SideColor col, Chess_piece[][] board, int alpha, int beta) 
    {
        ArrayList<Move> allMoves = getAIMoves(col, board);
        if (depth == maxDepth || allMoves.size() == 0) {
            return 0;
        }
        int move;
        int score;
        int bestScore = beta;
        Chess_piece[][] tempBoard = Arrays.stream(board).map(Chess_piece[]::clone).toArray(Chess_piece[][]::new);
        for (Move p : allMoves) {
            score = -getScore(tempBoard, p.getEnd());
            moveChess_pieceAI(p.getEnd(), p.getStart(), tempBoard);
            move = max(depth + 1, col.swapColor(), tempBoard, alpha, beta);
            score += move;
            moveChess_pieceAI(p.getStart(), p.getEnd(), tempBoard);
            if (score < bestScore) {
                bestScore = score;
            }
            if (bestScore < alpha) {
                return bestScore;
            }
            if (bestScore < beta) {
                beta = bestScore;
            }
        }
        return bestScore;
    }

    public static int max(int depth, SideColor col, Chess_piece[][] board, int alpha, int beta) 
    {
        ArrayList<Move> allMoves = getAIMoves(col, board);
        if (depth == maxDepth || allMoves.size() == 0)
            return 0;

        int move;
        int score;
        int bestScore = alpha;
        Chess_piece[][] tempBoard = Arrays.stream(board).map(Chess_piece[]::clone).toArray(Chess_piece[][]::new);
        for (Move p : allMoves) {
            score = getScore(tempBoard, p.getEnd());
            moveChess_pieceAI(p.getEnd(), p.getStart(), tempBoard);
            move = min(depth + 1, col.swapColor(), tempBoard, alpha, beta);
            score += move;
            moveChess_pieceAI(p.getStart(), p.getEnd(), tempBoard);
            if (score > bestScore)
                bestScore = score;

            if (bestScore > beta)
                return bestScore;

            if (bestScore > alpha)
                alpha = bestScore;
        }
        return bestScore;
    }

    public static int getScore(Chess_piece[][] board, Position newPosition)
    {
        return board[newPosition.getX()][newPosition.getY()] == null ? 0 : board[newPosition.getX()][newPosition.getY()].getValue();
    }

    public static void moveChess_pieceAI(Position newPosition, Position oldPosition, Chess_piece[][] board) 
    {
        Chess_piece piece = board[oldPosition.getX()][oldPosition.getY()];
        
        if (piece instanceof King && piece.getNotmoved())
            Board.castling(newPosition, piece, board);
        
        else if (piece instanceof Pawn) 
        {
            ((Pawn) piece).startPosition = false;
            if (newPosition.getY() == 7 || newPosition.getY() == 0)
            {
                Position tempP = piece.getPos();
                SideColor tempC = piece.getColor();
                piece = new Queen(tempC, tempP.getX(), tempP.getY());
            }
        }
        
        board[newPosition.getX()][newPosition.getY()] = piece;
        board[piece.getPos().getX()][piece.getPos().getY()] = null;
        piece.setPos(newPosition);
        piece.setNotmoved(false);
    }

    public static ArrayList<Move> getAIMoves(SideColor col, Chess_piece[][] board) 
    {
        ArrayList<Move> moves = new ArrayList<Move>();
        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                if (board[i][j] != null) {
                    if (board[i][j].getColor() == col) {
                        ArrayList<Position> allMoves = new ArrayList<>(Board.preventCheck(board[i][j].GetMoves(board), board, board[i][j]));
                        for (Position p : allMoves) {
                            moves.add(new Move(board[i][j].getPos(), p));
                        }
                    }
                }
            }
        }
        return moves;
    }
}