package com.Sudoku.model;
public class SudokuCell {

    private int value;
    private boolean fixed;

    public SudokuCell(int value, boolean fixed) {
        this.value = value;
        this.fixed = fixed;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        if (!fixed) {
            this.value = value;
        } else {
            throw new IllegalStateException("Cannot modify a fixed cell!");
        }
    }

    public boolean isFixed() {
        return fixed;
    }

    public boolean isEmpty() {
        return value == 0;
    }

    @Override
    public String toString() {
        return value == 0 ? "." : String.valueOf(value);
    }
}

