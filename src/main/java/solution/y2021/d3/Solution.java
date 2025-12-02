package solution.y2021.d3;

import solution.ISolution;

import java.util.List;
import java.util.Optional;

public class Solution implements ISolution {

    @Override
    public Optional<String> solvePartOne(List<String> input) {
        DiagnosticReport diagnosticReport = new DiagnosticReport(input);
        int powerConsumption = diagnosticReport.calculatePowerConsumption();
        return Optional.of(String.valueOf(powerConsumption));
    }

    @Override
    public Optional<String> solvePartTwo(List<String> input) {
        DiagnosticReport diagnosticReport = new DiagnosticReport(input);
        int lifeSupportRating = diagnosticReport.calculateLifeSupportRating();
        return Optional.of(String.valueOf(lifeSupportRating));
    }
}
