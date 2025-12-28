package solution.y2025.d09;

import solution.ISolution;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Solution implements ISolution {

    @Override
    public Optional<String> solveLevelOne(List<String> input) {
        long maxArea = 0;
        for (int i = 0; i < input.size(); i++) {
            int[] currentPosition = parsePosition(input.get(i));
            for (int j = i + 1; j < input.size(); j++) {
                int[] otherPosition = parsePosition(input.get(j));
                long width = Math.abs(currentPosition[0] - otherPosition[0]) + 1;
                long height = Math.abs(currentPosition[1] - otherPosition[1]) + 1;
                maxArea = Math.max(maxArea, width * height);
            }
        }
        return Optional.of(String.valueOf(maxArea));
    }

    @Override
    public Optional<String> solveLevelTwo(List<String> input) {
        Polygon polygon = new Polygon();
        for (String line : input) {
            int[] position = parsePosition(line);
            polygon.addPoint(position[0], position[1]);
        }
        long maxArea = 0;
        for (int i = 0; i < polygon.npoints; i++) {
            for (int j = i + 1; j < polygon.npoints; j++) {
                int x = Math.min(polygon.xpoints[i], polygon.xpoints[j]) + 1;
                int y = Math.min(polygon.ypoints[i], polygon.ypoints[j]) + 1;
                int width = Math.abs(polygon.xpoints[i] - polygon.xpoints[j]) - 1;
                int height = Math.abs(polygon.ypoints[i] - polygon.ypoints[j]) - 1;
                if (polygon.contains(x, y, width, height)) {
                    maxArea = Math.max(maxArea, (long) (width + 2) * (height + 2));
                }
            }
        }
        return Optional.of(String.valueOf(maxArea));
    }

    private static int[] parsePosition(String input) {
        return Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();
    }
}