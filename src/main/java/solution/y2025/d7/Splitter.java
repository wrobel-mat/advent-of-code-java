package solution.y2025.d7;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

class Splitter implements Comparable<Splitter> {

    private static final AtomicInteger ID = new AtomicInteger();
    final int id;
    final Position position;
    Splitter left;
    Splitter right;
    long timelines = -1;

    Splitter(Position position) {
        this.id = ID.getAndIncrement();
        this.position = position;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Splitter other && this.position == other.position;
    }

    @Override
    public String toString() {
        return String.format(
                "{ id: %d, row: %d, col: %d, left_id: %d, right_id: %d }",
                id,
                position.row(),
                position.col(),
                left != null ? left.id : null,
                right != null ? right.id : null);
    }

    @Override
    public int compareTo(Splitter other) {
        return this.position.compareTo(other.position);
    }
}
