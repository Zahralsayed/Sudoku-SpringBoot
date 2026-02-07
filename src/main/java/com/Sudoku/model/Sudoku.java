package com.Sudoku.model;

import com.Sudoku.exception.InvalidCharacterException;
import com.Sudoku.exception.SudokuFileNotFoundException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Sudoku {
    private static final int SIZE = 9;
    private SudokuCell[][] board = new SudokuCell[SIZE][SIZE];

    public Sudoku() {

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = new SudokuCell(0, false);
            }
        }
    }

    public SudokuCell[][] getBoard() {
        return board;
    }

    public void loadFromFile(String filename) throws SudokuFileNotFoundException, InvalidCharacterException {
        List<String> lines;

        try {
            lines = Files.readAllLines(Paths.get(filename));
        } catch (IOException e) {
            throw new SudokuFileNotFoundException("Puzzle file not found: " + filename);
        }

        int row = 0;
        for (String line : lines) {
            line = line.replaceAll("[|\\s]", "");
            if (line.isEmpty()) continue;

            if (line.length() != SIZE) {
                throw new InvalidCharacterException("Invalid row length at row " + row);
            }

            for (int col = 0; col < SIZE; col++) {
                char c = line.charAt(col);
                if (c < '0' || c > '9') {
                    throw new InvalidCharacterException("Invalid character '" + c + "' at row " + row + ", col " + col);
                }

                int value = Character.getNumericValue(c);
                boolean fixed = value != 0;
                board[row][col] = new SudokuCell(value, fixed);
            }
            row++;
        }

        if (row != SIZE) {
            throw new InvalidCharacterException("File must contain exactly 9 rows");
        }
    }

    public void printBoard() {
        for (int i = 0; i < SIZE; i++) {
            if (i % 3 == 0) {
                System.out.println("+-------+-------+-------+");
            }

            for (int j = 0; j < SIZE; j++) {
                if (j % 3 == 0) System.out.print("| ");
                System.out.print(board[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("+-------+-------+-------+");
    }

    public void saveToFile(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    writer.write(board[i][j].toString());
                    if (j < SIZE - 1) writer.write(" ");
                }
                writer.newLine();
            }
        }
    }

}
