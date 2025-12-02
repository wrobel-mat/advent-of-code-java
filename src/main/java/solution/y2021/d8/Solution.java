package solution.y2021.d8;

import solution.ISolution;

import java.util.List;
import java.util.Optional;

public class Solution implements ISolution {
    
    @Override
    public Optional<String> solvePartOne(List<String> input) {
        DisplaysReport displaysReport = new DisplaysReport(input);
        long answer = displaysReport.countEasyDigits();
        return Optional.of(String.valueOf(answer));
    }

    @Override
    public Optional<String> solvePartTwo(List<String> input) {
        DisplaysReport displaysReport = new DisplaysReport(input);
        long answer = displaysReport.decodeAndSumOutputValues();
        return Optional.of(String.valueOf(answer));
    }
}
