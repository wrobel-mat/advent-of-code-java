package solution.y2021.d05;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

record VentLine(Set<Point> points) {

    static VentLine draw(Point start, Point end) {
        if (start.equals(end)) {
            return new VentLine(Set.of(start));
        }

        Set<Point> points = new HashSet<>();
        if (start.x() == end.x()) {
            int x = start.x();
            int startY = Math.min(start.y(), end.y());
            int endY = Math.max(start.y(), end.y());
            points.addAll(IntStream.range(startY, endY + 1).mapToObj(y -> new Point(x, y)).collect(Collectors.toSet()));
        } else if (start.y() == end.y()) {
            int y = start.y();
            int startX = Math.min(start.x(), end.x());
            int endX = Math.max(start.x(), end.x());
            points.addAll(IntStream.range(startX, endX + 1).mapToObj(x -> new Point(x, y)).collect(Collectors.toSet()));
        } else {
            Point startPoint = start.x() < end.x() ? start : end;
            Point endPoint = start.x() < end.x() ? end : start;
            int direction = startPoint.y() < endPoint.y() ? 1 : -1;
            for (int x = startPoint.x(), y = startPoint.y(); x <= endPoint.x(); x++, y += direction) {
                points.add(new Point(x, y));
            }
        }

        return new VentLine(points);
    }

    boolean isHorizontal() {
        int y = points.stream().findFirst().get().y();
        return points.stream().allMatch(p -> p.y() == y);
    }

    boolean isVertical() {
        int x = points.stream().findFirst().get().x();
        return points.stream().allMatch(p -> p.x() == x);
    }
}
