package exec.mode;

import cache.LocalCache;
import config.Configuration;
import http.AocClient;
import solution.ISolution;
import solution.SolutionProvider;

import java.util.List;
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

        final List<String> input = LocalCache.getInput(year, day)
                .orElseGet(() -> {
                    List<String> fetchedInput = new AocClient().getInput(year, day);
                    LocalCache.persistInput(year, day, fetchedInput);
                    return fetchedInput;
                });

        final ISolution solution = SolutionProvider.getSolution(year, day);
        solution.solveLevelOne(input)
                .ifPresentOrElse(
                        answer -> LOG.info(format("Level 1: %s", answer)),
                        () -> LOG.info("Level 1: no answer"));
        solution.solveLevelTwo(input)
                .ifPresentOrElse(
                        answer -> LOG.info(format("Level 2: %s", answer)),
                        () -> LOG.info("Level 2: no answer"));
    }
}
