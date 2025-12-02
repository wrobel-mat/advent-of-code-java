package exec.mode;

import answer.Result;
import cache.LocalCache;
import config.Configuration;
import http.AocClient;
import http.AocSubmitResult;
import solution.ISolution;
import solution.SolutionProvider;

import java.util.List;
import java.util.Properties;

public class SubmitExecutor implements IModeExecutor {

    @Override
    public ApplicationMode mode() {
        return ApplicationMode.SUBMIT;
    }

    @Override
    public void run() {
        final Properties props = Configuration.getProperties();
        final int year = Integer.parseInt(props.getProperty("year"));
        final int day = Integer.parseInt(props.getProperty("day"));
        final Result result = LocalCache.getResult(year, day);
        if (result.bothPartsCompleted()) {
            printResult(result);
            System.exit(0);
        }

        final AocClient aocClient = new AocClient();
        final List<String> input = LocalCache.getInput(year, day)
                .orElseGet(() -> {
                    List<String> fetchedInput = aocClient.getInput(year, day);
                    LocalCache.persistInput(year, day, fetchedInput);
                    return fetchedInput;
                });

        final ISolution solution = SolutionProvider.getSolution(year, day);
        if (result.partNotCompleted(1)) {
            solution.solvePartOne(input)
                    .map(answer -> aocClient.submitAnswer(year, day, 1, answer))
                    .filter(AocSubmitResult::isCorrectAnswer)
                    .ifPresent(submitResult -> result.completePart(1, submitResult.submittedAnswer()));
        }
        if (result.partNotCompleted(2)) {
            solution.solvePartTwo(input)
                    .map(answer -> aocClient.submitAnswer(year, day, 2, answer))
                    .filter(AocSubmitResult::isCorrectAnswer)
                    .ifPresent(submitResult -> result.completePart(2, submitResult.submittedAnswer()));
        }

        printResult(result);
        LocalCache.persistResult(result);
    }

    private static void printResult(Result result) {
        System.out.println(result);
    }
}
