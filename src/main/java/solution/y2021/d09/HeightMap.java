package solution.y2021.d09;

import java.util.*;

class HeightMap {

    private final List<List<Point>> map;

    HeightMap(List<String> input) {
        this.map = new ArrayList<>(input.size());
        for (String row : input) {
            map.add(Arrays.stream(row.split("")).map(Integer::parseInt).map(Point::new).toList());
        }
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).size(); j++) {
                Point current = map.get(i).get(j);
                Point top = i> 0 ? map.get(i - 1).get(j) : null;
                Point left = j > 0 ? map.get(i).get(j - 1) : null;
                Point down = i < map.size() - 1 ? map.get(i + 1).get(j) : null;
                Point right = j < map.get(i).size() - 1 ? map.get(i).get(j + 1) : null;

                current.addNeighbours(top, left, down, right);
            }
        }
    }

    long countLowPointsRiskLevel() {
        return map.stream()
                .flatMap(Collection::stream)
                .filter(Point::isLowPoint)
                .map(p -> p.height() + 1)
                .reduce(Integer::sum)
                .orElse(0);
    }

    long threeLargestBasinSizesMultiplied() {
        List<Integer> basinSizes = new ArrayList<>();
        Set<Point> visited = new HashSet<>();
        map.stream()
                .flatMap(Collection::stream)
                .filter(Point::isLowPoint)
                .forEach(p -> basinSizes.add(this.calculateBasinSizeRecursively(0, p, visited)));
        basinSizes.sort(Comparator.reverseOrder());
        return (long) basinSizes.get(0) * basinSizes.get(1) * basinSizes.get(2);
    }

    private int calculateBasinSizeRecursively(int i, Point point, Set<Point> visited) {
        if (visited.contains(point) || point.isEdgePoint()) {
            return i;
        }

        visited.add(point);
        int size = i + 1;

        for (Point neighbour : point.neighbours()) {
            if (point.lowerThan(neighbour)) {
                size = this.calculateBasinSizeRecursively(size, neighbour, visited);
            }
        }

        return size;
    }
}
