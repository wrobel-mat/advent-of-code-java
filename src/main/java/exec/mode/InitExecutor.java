package exec.mode;

import config.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

import static java.lang.String.format;
import static java.nio.file.StandardOpenOption.CREATE_NEW;

public class InitExecutor implements IModeExecutor {

    private static final Logger LOG = Logger.getLogger(InitExecutor.class.getSimpleName());

    @Override
    public ApplicationMode mode() {
        return ApplicationMode.INIT;
    }

    @Override
    public void run() {
        final int year = Configuration.getYear();
        final int day = Configuration.getDay();
        final Path solutionPath = Path.of("src", "main", "java", "solution", format("y%d", year), format("d%d", day), "Solution.java");
        if (Files.exists(solutionPath)) {
            LOG.info(format("Year [%d] day [%d] is already initialized", year, day));
            System.exit(0);
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
}
