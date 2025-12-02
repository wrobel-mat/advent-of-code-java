package solution.y2021.d1;

import solution.ISolution;

import java.util.List;
import java.util.Optional;

public class Solution implements ISolution {

    @Override
    public Optional<String> solvePartOne(List<String> input) {
        SonarSweepMeasurements measurements = new SonarSweepMeasurements(input);
        int answer = measurements.countDepthIncreases(1);
        return Optional.of(String.valueOf(answer));
    }

    @Override
    public Optional<String> solvePartTwo(List<String> input) {
        SonarSweepMeasurements measurements = new SonarSweepMeasurements(input);
        int answer = measurements.countDepthIncreases(3);
        return Optional.of(String.valueOf(answer));
    }
}
