package exec.mode;

import cache.LocalCache;
import config.Configuration;
import http.AocClient;
import solution.ISolution;
import solution.SolutionProvider;

import java.util.List;

public class TestSolutionExecutor implements IModeExecutor {

    @Override
    public ApplicationMode mode() {
        return ApplicationMode.TEST;
    }

    @Override
    public void run() {
        final int year = Configuration.getYear();
        final int day = Configuration.getDay();

        final List<String> input = LocalCache.getInput(year, day)
                .orElseGet(() -> {
                    List<String> fetchedInput = new AocClient().getInput(year, day);
                    LocalCache.persistInput(year, day, fetchedInput);
                    return fetchedInput;
                });

        final ISolution solution = SolutionProvider.getSolution(year, day);
        solution.solvePartOne(input)
                .ifPresentOrElse(
                        answer -> System.out.printf("Part 1 answer: %s%n", answer),
                        () -> System.out.println("No answer for part 1"));
        solution.solvePartTwo(input)
                .ifPresentOrElse(
                        answer -> System.out.printf("Part 2 answer: %s%n", answer),
                        () -> System.out.println("No answer for part 2"));
    }
}
