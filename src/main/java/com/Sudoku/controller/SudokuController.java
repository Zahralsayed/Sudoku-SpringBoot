package com.Sudoku.controller;

import com.Sudoku.exception.InvalidCharacterException;
import com.Sudoku.exception.SudokuFileNotFoundException;
import com.Sudoku.model.Sudoku;
import com.Sudoku.solver.SudokuSolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/api/sudoku")
public class SudokuController {

    @PostMapping("/solve-file")
    public ResponseEntity<String> solveFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No file uploaded");
        }

        try {
            File tempFile = File.createTempFile("puzzle", ".txt");
            file.transferTo(tempFile);

            Sudoku sudoku = new Sudoku();
            sudoku.loadFromFile(tempFile.getAbsolutePath());

            SudokuSolver solver = new SudokuSolver();
            boolean solved = solver.solve(sudoku);

            if (!solved) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body("Puzzle could not be solved.");
            }

            String fileName = "solution_" + System.currentTimeMillis() + ".txt";
            File solutionFile = new File(fileName);
            sudoku.saveToFile(solutionFile.getAbsolutePath());

            System.out.println("Saved at: " + solutionFile.getAbsolutePath());


            return ResponseEntity.ok("Puzzle solved successfully. Solution saved at: "
                    + solutionFile.getAbsolutePath());

        } catch (SudokuFileNotFoundException | InvalidCharacterException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error reading puzzle: " + e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Server error: " + e.getMessage());
        }
    }
}
