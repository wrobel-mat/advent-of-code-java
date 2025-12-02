package solution.y2021.d9;

import java.util.ArrayList;
import java.util.List;

class Point {

    private static final int MAX_HEIGHT = 9;
    private final int height;
    private final List<Point> neighbours;

    Point(int height) {
        if (height < 0 || height > MAX_HEIGHT) {
            throw new IllegalArgumentException("Height must be in range [0, 9]");
        }
        this.height = height;
        this.neighbours = new ArrayList<>();
    }

    int height() {
        return height;
    }

    List<Point> neighbours() {
        return neighbours;
    }

    boolean lowerThan(Point other) {
        return other != null && this.height < other.height;
    }

    boolean isEdgePoint() {
        return this.height == MAX_HEIGHT;
    }

    boolean isLowPoint() {
        return this.neighbours.stream().allMatch(this::lowerThan);
    }

    void addNeighbours(Point... neighbours) {
        for (Point neighbour : neighbours) {
            if (neighbour != null) {
                this.neighbours.add(neighbour);
            }
        }
    }
}
