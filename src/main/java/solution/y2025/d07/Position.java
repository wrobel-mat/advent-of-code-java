package solution.y2025.d07;

import java.util.Comparator;

record Position(int row, int col) implements Comparable<Position> {

    private static final Comparator<Position> COMPARING_ROW_AND_COL =
            Comparator.comparingInt(Position::row)
                    .thenComparingInt(Position::col);

    @Override
    public int compareTo(Position other) {
        return COMPARING_ROW_AND_COL.compare(this, other);
    }
}
