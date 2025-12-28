package solution.y2021.d08;

import solution.ISolution;

import java.util.List;
import java.util.Optional;

public class Solution implements ISolution {
    
    @Override
    public Optional<String> solveLevelOne(List<String> input) {
        DisplaysReport displaysReport = new DisplaysReport(input);
        long answer = displaysReport.countEasyDigits();
        return Optional.of(String.valueOf(answer));
    }

    @Override
    public Optional<String> solveLevelTwo(List<String> input) {
        DisplaysReport displaysReport = new DisplaysReport(input);
        long answer = displaysReport.decodeAndSumOutputValues();
        return Optional.of(String.valueOf(answer));
    }
}
