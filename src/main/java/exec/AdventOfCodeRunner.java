package exec;

import exec.mode.ApplicationMode;
import exec.mode.IModeExecutor;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.stream.Collectors;


public class AdventOfCodeRunner {

    static void main(String[] args) {
        if (args.length != 1) {
            throw new RuntimeException("Only one of the following arguments must be provided:\n" +
                    "[1] init - to initialize new day\n" +
                    "[2] submit - to run the solution and submit the answer\n" +
                    "[3] test - to run the solution without submitting");
        }

        ApplicationMode mode = ApplicationMode.valueOf(args[0].toUpperCase());
        IModeExecutor executor = getExecutor(mode);
        executor.run();
    }

    private static IModeExecutor getExecutor(ApplicationMode mode) {
        Map<ApplicationMode, IModeExecutor> executors = ServiceLoader.load(IModeExecutor.class).stream()
                .map(ServiceLoader.Provider::get)
                .collect(Collectors.toMap(IModeExecutor::mode, iModeExecutor -> iModeExecutor));
        return executors.get(mode);
    }
}