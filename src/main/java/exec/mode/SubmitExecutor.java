package exec.mode;

import answer.Result;
import cache.LocalCache;
import config.Configuration;
import http.AocClient;
import http.AocSubmitResult;
import solution.ISolution;
import solution.SolutionFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static java.lang.String.format;

public class SubmitExecutor implements IModeExecutor {

    private static final Logger LOG = Logger.getLogger(SubmitExecutor.class.getSimpleName());

    @Override
    public ExecutionMode mode() {
        return ExecutionMode.SUBMIT;
    }

    @Override
    public void run() {
        final int year = Configuration.getYear();
        final int day = Configuration.getDay();

        LOG.info(format("Submitting solution for year [%d] day [%d]", year, day));

        final Result result = LocalCache.getResult(year, day);
        if (result.bothLevelsCompleted()) {
            LOG.info("Both levels are already completed");
            LOG.info(result.toString());
            System.exit(0);
        }

        final ISolution solution = SolutionFactory.getSolution(year, day);

        final AocClient aocClient = new AocClient();
        final List<String> input = LocalCache.getInput(year, day)
                .orElseGet(() -> {
                    List<String> fetchedInput = aocClient.getInput(year, day);
                    LocalCache.persistInput(year, day, fetchedInput);
                    return fetchedInput;
                });

        if (result.levelNotCompleted(1)) {
            long start1 = System.nanoTime();
            solution.solveLevelOne(input)
                    .map(answer -> {
                        long end1 = System.nanoTime();
                        LOG.info(format("Level 1 execution time: %d ms", TimeUnit.NANOSECONDS.toMillis(end1 - start1)));
                        return aocClient.submitAnswer(year, day, 1, answer);
                    })
                    .filter(AocSubmitResult::isCorrectAnswer)
                    .ifPresent(submitResult -> {
                        result.completeLevel(1, submitResult.submittedAnswer());
                        LOG.info("Completed level 1");
                    });
        }
        if (result.levelNotCompleted(2)) {
            long start2 = System.nanoTime();
            solution.solveLevelTwo(input)
                    .map(answer -> {
                        long end2 = System.nanoTime();
                        LOG.info(format("Level 2 execution time: %d ms", TimeUnit.NANOSECONDS.toMillis(end2 - start2)));
                        return aocClient.submitAnswer(year, day, 2, answer);
                    })
                    .filter(AocSubmitResult::isCorrectAnswer)
                    .ifPresent(submitResult -> {
                        result.completeLevel(2, submitResult.submittedAnswer());
                        LOG.info("Completed level 2");
                    });
        }

        LOG.info(result.toString());
        LocalCache.persistResult(result);
    }
}
