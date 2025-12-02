package solution.y2021.d5;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class VentMap {

    private final List<VentLine> ventLines;

    VentMap(List<String> input) {
        this.ventLines = input.stream().map(lineSegment -> {
            String[] coordinates = lineSegment.split(" -> ");
            String[] startCoordinates = coordinates[0].split(",");
            String[] endCoordinates = coordinates[1].split(",");
            Point start = new Point(Integer.parseInt(startCoordinates[0]), Integer.parseInt(startCoordinates[1]));
            Point end = new Point(Integer.parseInt(endCoordinates[0]), Integer.parseInt(endCoordinates[1]));
            return VentLine.draw(start, end);
        }).toList();
    }

    long countOverlappingVentPoints(Predicate<VentLine> ventLineFilter) {
        return ventLines.stream()
                .filter(ventLineFilter)
                .flatMap(ventLine -> ventLine.points().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .filter(counter -> counter.getValue() > 1)
                .count();
    }
}
