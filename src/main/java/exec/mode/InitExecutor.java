package exec.mode;

import cache.LocalCache;
import config.Configuration;
import http.AocClient;
import solution.SolutionFactory;

import java.util.List;

public class InitExecutor implements IModeExecutor {

    @Override
    public ExecutionMode mode() {
        return ExecutionMode.INIT;
    }

    @Override
    public void run() {
        final int year = Configuration.getYear();
        final int day = Configuration.getDay();
        SolutionFactory.initializeSolution(year, day);
        if (!LocalCache.inputIsCached(year, day)) {
            List<String> fetchedInput = new AocClient().getInput(year, day);
            LocalCache.persistInput(year, day, fetchedInput);
        }
    }
}
