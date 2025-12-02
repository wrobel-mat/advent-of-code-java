package exec.mode;

import cache.LocalCache;
import config.Configuration;
import http.AocClient;
import solution.ISolution;
import solution.SolutionProvider;

import java.util.List;
import java.util.Properties;

public class TestSolutionExecutor implements IModeExecutor {

    @Override
    public ApplicationMode mode() {
        return ApplicationMode.TEST;
    }

    @Override
    public void run() {
        final Properties props = Configuration.getProperties();
        final int year = Integer.parseInt(props.getProperty("year"));
        final int day = Integer.parseInt(props.getProperty("day"));

        final List<String> input = LocalCache.getInput(year, day)
                .orElseGet(() -> {
                    List<String> fetchedInput = new AocClient().getInput(year, day);
                    LocalCache.persistInput(year, day, fetchedInput);
                    return fetchedInput;
                });

        final ISolution solution = SolutionProvider.getSolution(year, day);
        solution.solvePartOne(input).ifPresent(answer -> System.out.printf("Part 1 answer: %s%n", answer));
        solution.solvePartTwo(input).ifPresent(answer -> System.out.printf("Part 2 answer: %s%n", answer));
    }
}
