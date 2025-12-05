package solution;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

import static java.lang.String.format;
import static java.nio.file.StandardOpenOption.CREATE_NEW;

public class SolutionFactory {

    private static final Logger LOG = Logger.getLogger(SolutionFactory.class.getSimpleName());

    private SolutionFactory() {
    }

    public static void initializeSolution(int year, int day) {
        final Path solutionPath = Path.of("src", "main", "java", "solution", format("y%d", year), format("d%d", day), "Solution.java");
        if (Files.exists(solutionPath)) {
            LOG.info(format("Year [%d] day [%d] is already initialized", year, day));
        }
        try {
            final Path templatePath = Path.of("src", "main", "resources", "templates", "Solution.java");
            String solutionTemplate = new String(Files.readAllBytes(templatePath));
            solutionTemplate = solutionTemplate
                    .replace("${yearNum}", String.valueOf(year))
                    .replace("${dayNum}", String.valueOf(day));
            Files.createDirectories(solutionPath.getParent());
            Files.write(solutionPath, solutionTemplate.getBytes(), CREATE_NEW);
            LOG.info(format("Year [%d] day [%d] initialized successfully", year, day));
            LOG.info(format("Solution class created under path: %s", solutionPath));
        } catch (IOException e) {
            LOG.severe(format("Year [%d] day [%d] initialization failed", year, day));
            throw new RuntimeException(e);
        }
    }

    public static ISolution getSolution(int year, int day) {
        try {
            return (ISolution) Class.forName(String.format("solution.y%d.d%d.Solution", year, day)).getConstructor().newInstance();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(String.format("Year [%d] day [%d] is not initialized - rerun the application with `init` argument%n%s", year, day, e));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
