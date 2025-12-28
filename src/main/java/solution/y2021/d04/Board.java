package solution.y2021.d04;

import java.util.List;

record Board(List<Row> rows, List<Column> columns) {

    static final int SIZE = 5;

    boolean wins() {
        return rows.stream().anyMatch(row -> row.numbers().stream().allMatch(Number::marked)) ||
                columns.stream().anyMatch(column -> column.numbers().stream().allMatch(Number::marked));
    }

    record Row(List<Number> numbers) {
    }

    record Column(List<Number> numbers) {
    }

    static class Number {

        private final Integer value;
        private boolean marked;

        Number(Integer value) {
            this.value = value;
        }

        Integer value() {
            return this.value;
        }

        void mark() {
            this.marked = true;
        }

        boolean marked() {
            return this.marked;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }
}
