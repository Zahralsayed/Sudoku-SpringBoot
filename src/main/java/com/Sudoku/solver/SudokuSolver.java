package com.Sudoku.solver;

import com.Sudoku.model.Sudoku;
import com.Sudoku.model.SudokuCell;

public class SudokuSolver {

    private static final int SIZE = 9;

    public boolean solve(Sudoku sudoku) {
        SudokuCell[][] board = sudoku.getBoard();
        return solveBoard(board);
    }


    private boolean solveBoard(SudokuCell[][] board) {

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {

                if (board[row][col].isEmpty()) {

                    for (int num = 1; num <= SIZE; num++) {
                        if (isSafe(board, row, col, num)) {
                            board[row][col].setValue(num);

                            // Recurse
                            if (solveBoard(board)) {
                                return true;
                            }

                            // Backtrack
                            board[row][col].setValue(0);
                        }
                    }
                    return false;
                }
            }
        }

        return true;
    }


    private boolean isSafe(SudokuCell[][] board, int row, int col, int num) {
        return isValidInRow(board, row, num)
                && isValidInColumn(board, col, num)
                && isValidInSubGrid(board, row, col, num);
    }

    private boolean isValidInRow(SudokuCell[][] board, int row, int num) {
        for (int col = 0; col < SIZE; col++) {
            if (board[row][col].getValue() == num) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidInColumn(SudokuCell[][] board, int col, int num) {
        for (int row = 0; row < SIZE; row++) {
            if (board[row][col].getValue() == num) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidInSubGrid(SudokuCell[][] board, int row, int col, int num) {
        int startRow = row - row % 3;
        int startCol = col - col % 3;

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[startRow + r][startCol + c].getValue() == num) {
                    return false;
                }
            }
        }
        return true;
    }
}

