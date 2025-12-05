package exec.mode;

import cache.LocalCache;
import config.Configuration;
import http.AocClient;
import solution.ISolution;
import solution.SolutionFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static java.lang.String.format;

public class TestSolutionExecutor implements IModeExecutor {

    private static final Logger LOG = Logger.getLogger(TestSolutionExecutor.class.getSimpleName());

    @Override
    public ApplicationMode mode() {
        return ApplicationMode.TEST;
    }

    @Override
    public void run() {
        final int year = Configuration.getYear();
        final int day = Configuration.getDay();

        LOG.info(format("Testing solution for year [%d] day [%d]", year, day));

        final ISolution solution = SolutionFactory.getSolution(year, day);

        final List<String> input = LocalCache.getInput(year, day)
                .orElseGet(() -> {
                    List<String> fetchedInput = new AocClient().getInput(year, day);
                    LocalCache.persistInput(year, day, fetchedInput);
                    return fetchedInput;
                });

        long start1 = System.nanoTime();
        solution.solveLevelOne(input)
                .ifPresentOrElse(
                        answer -> {
                            long end1 = System.nanoTime();
                            LOG.info(format("Level 1 execution time: %d ms", TimeUnit.NANOSECONDS.toMillis(end1 - start1)));
                            LOG.info(format("Level 1 answer: %s", answer));
                        },
                        () -> LOG.info("Level 1: no answer"));
        long start2 = System.nanoTime();
        solution.solveLevelTwo(input)
                .ifPresentOrElse(
                        answer -> {
                            long end2 = System.nanoTime();
                            LOG.info(format("Level 2 execution time: %d ms", TimeUnit.NANOSECONDS.toMillis(end2 - start2)));
                            LOG.info(format("Level 2 answer: %s", answer));
                        },
                        () -> LOG.info("Level 2: no answer"));
    }
}
