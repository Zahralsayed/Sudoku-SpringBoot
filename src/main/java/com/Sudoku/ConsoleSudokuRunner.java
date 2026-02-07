package com.Sudoku;

import com.Sudoku.model.Sudoku;
import com.Sudoku.solver.SudokuSolver;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Scanner;

@Component
public class ConsoleSudokuRunner implements CommandLineRunner {

    private static final String PUZZLE_DIR = "src/main/java/com/Sudoku/resources";

    @Override
    public void run(String... args) throws Exception {

        Scanner kbd = new Scanner(System.in);

        while (true) {

            System.out.println("=================================");
            System.out.println("        SUDOKU GAME");
            System.out.println("=================================");

            File folder = new File(PUZZLE_DIR);
            File[] puzzles = folder.listFiles((dir, name) -> name.endsWith(".txt"));

            if (puzzles == null || puzzles.length == 0) {
                System.out.println("No puzzle files found.");
                return;
            }

            System.out.println("Available puzzles:");
            for (int i = 0; i < puzzles.length; i++) {
                System.out.println((i + 1) + ". " + puzzles[i].getName());
            }

            System.out.print("\nSelect puzzle number: ");

            if (!kbd.hasNextInt()) {
                System.out.println("\nInvalid input. Press Enter to try again...");
                kbd.nextLine();
                kbd.nextLine();
                continue;
            }

            int choice = kbd.nextInt();
            kbd.nextLine();

            if (choice < 1 || choice > puzzles.length) {
                System.out.println("\nInvalid puzzle number.");
                System.out.println("Press Enter to try again...");
                kbd.nextLine();
                continue;
            }

            File selectedFile = puzzles[choice - 1];

            Sudoku sudoku = new Sudoku();
//            sudoku.loadFromFile(selectedFile.getAbsolutePath());

            try {
                sudoku.loadFromFile(selectedFile.getAbsolutePath());
            } catch (Exception e) {
                System.out.println("\n❌ Error loading puzzle: " + e.getMessage());
                System.out.println("Press Enter to try again...");
                kbd.nextLine();
                continue;
            }

            System.out.println("\nPuzzle " +choice+ " Loaded: ");
            sudoku.printBoard();

            SudokuSolver solver = new SudokuSolver();
            boolean solved = solver.solve(sudoku);

            if (solved) {
                System.out.println("\nPuzzle " +choice+ " Solved:");
                sudoku.printBoard();
            } else {
                System.out.println("\nPuzzle " +choice+ " could not be solved.");
            }

            System.out.print("\nSolve another puzzle? (y/n): ");
            String again = kbd.nextLine();

            if (!again.equalsIgnoreCase("y")) {
                System.out.println("\nGoodbye 👋");
                break;
            }
        }
    }
}
