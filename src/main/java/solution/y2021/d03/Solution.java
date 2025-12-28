package solution.y2021.d03;

import solution.ISolution;

import java.util.List;
import java.util.Optional;

public class Solution implements ISolution {

    @Override
    public Optional<String> solveLevelOne(List<String> input) {
        DiagnosticReport diagnosticReport = new DiagnosticReport(input);
        int powerConsumption = diagnosticReport.calculatePowerConsumption();
        return Optional.of(String.valueOf(powerConsumption));
    }

    @Override
    public Optional<String> solveLevelTwo(List<String> input) {
        DiagnosticReport diagnosticReport = new DiagnosticReport(input);
        int lifeSupportRating = diagnosticReport.calculateLifeSupportRating();
        return Optional.of(String.valueOf(lifeSupportRating));
    }
}
