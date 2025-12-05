package solution.y2025.d2;

import solution.ISolution;

import java.util.*;
import java.util.function.Function;

public class Solution implements ISolution {

    @Override
    public Optional<String> solveLevelOne(List<String> input) {
        List<Range> ranges = getRanges(input);
        Function<String, String> matchingRegex = pattern -> "^(?:" + pattern + "){2}$";
        long result = 0;
        for (Range range : ranges) {
            for (long id = range.start(); id <= range.end(); id++) {
                String currentId = String.valueOf(id);
                String pattern = currentId.substring(0, currentId.length() / 2);
                if (currentId.matches(matchingRegex.apply(pattern))) {
                    result += id;
                }
            }
        }
        return Optional.of(String.valueOf(result));
    }

    @Override
    public Optional<String> solveLevelTwo(List<String> input) {
        List<Range> ranges = getRanges(input);
        Function<String, String> matchingRegex = pattern -> "^(?:" + pattern + "){2,}$";
        long result = 0;
        Set<Long> invalidIds = new HashSet<>();
        for (Range range : ranges) {
            for (long id = range.start(); id <= range.end(); id++) {
                String currentId = String.valueOf(id);
                int endIdx = 1;
                while (endIdx <= currentId.length() / 2 && !invalidIds.contains(id)) {
                    String pattern = currentId.substring(0, endIdx);
                    if (currentId.matches(matchingRegex.apply(pattern))) {
                        invalidIds.add(id);
                        result += id;
                    }
                    endIdx++;
                }
            }
        }
        return Optional.of(String.valueOf(result));
    }

    private List<Range> getRanges(List<String> input) {
        return Arrays.stream(input.get(0).split(","))
                .map(range -> {
                    String[] split = range.split("-");
                    return new Range(Long.parseLong(split[0]), Long.parseLong(split[1]));
                }).toList();
    }
}