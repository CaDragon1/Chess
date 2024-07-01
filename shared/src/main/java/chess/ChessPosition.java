package chess;

import java.util.Objects;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {
    int rowValue;
    int colValue;

    // Default constructor
    public ChessPosition() {
        rowValue = 0;
        colValue = 0;
    }
    // Full instantiation constructor
    public ChessPosition(int row, int col) {
        rowValue = row;
        colValue = col;
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {
        return rowValue;
    }
    // Set row value
    public void setRowValue(int rowValue) {
        this.rowValue = rowValue;
    }

    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    public int getColumn() {
        return colValue;
    }
    // Set column value
    public void setColValue(int colValue) {
        this.colValue = colValue;
    }

    // Overridden equals and hashCode methods to test for deep equality
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPosition that = (ChessPosition) o;
        return rowValue == that.rowValue && colValue == that.colValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowValue, colValue);
    }
}
