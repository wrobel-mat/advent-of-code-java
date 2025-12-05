package exec;

import exec.mode.ExecutionMode;
import exec.mode.IModeExecutor;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.stream.Collectors;


public class AdventOfCodeRunner {

    static void main(String[] args) {
        if (args.length != 1) {
            throw new RuntimeException("""
                    Only one of the following arguments must be provided:
                    [1] init - to initialize new day
                    [2] test - to run the solution without submitting
                    [3] submit - to run the solution and submit the answer""");
        }

        ExecutionMode mode = ExecutionMode.valueOf(args[0].toUpperCase());
        IModeExecutor executor = getExecutor(mode);
        executor.run();
    }

    private static IModeExecutor getExecutor(ExecutionMode mode) {
        Map<ExecutionMode, IModeExecutor> executors = ServiceLoader.load(IModeExecutor.class).stream()
                .map(ServiceLoader.Provider::get)
                .collect(Collectors.toMap(IModeExecutor::mode, iModeExecutor -> iModeExecutor));
        return executors.get(mode);
    }
}