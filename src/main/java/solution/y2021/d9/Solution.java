package solution.y2021.d9;

import solution.ISolution;

import java.util.List;
import java.util.Optional;

public class Solution implements ISolution {

    @Override
    public Optional<String> solvePartOne(List<String> input) {
        HeightMap heightMap = new HeightMap(input);
        long answer = heightMap.countLowPointsRiskLevel();
        return Optional.of(String.valueOf(answer));
    }

    @Override
    public Optional<String> solvePartTwo(List<String> input) {
        HeightMap heightMap = new HeightMap(input);
        long answer = heightMap.threeLargestBasinSizesMultiplied();
        return Optional.of(String.valueOf(answer));
    }
}
