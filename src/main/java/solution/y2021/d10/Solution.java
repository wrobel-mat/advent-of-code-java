package solution.y2021.d10;

import solution.ISolution;

import java.util.*;

public class Solution implements ISolution {

    @Override
    public Optional<String> solvePartOne(List<String> input) {
        long result = new NavigationSubsystemProcessor(input).calculateSyntaxErrorsScore();
        return Optional.of(String.valueOf(result));
    }

    @Override
    public Optional<String> solvePartTwo(List<String> input) {
        long result = new NavigationSubsystemProcessor(input).calculateAutocompleteScoreWinner();
        return Optional.of(String.valueOf(result));
    }
}
