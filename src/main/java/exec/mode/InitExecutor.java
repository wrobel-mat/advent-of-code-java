package exec.mode;

import config.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

public class InitExecutor implements IModeExecutor {

    @Override
    public ApplicationMode mode() {
        return ApplicationMode.INIT;
    }

    @Override
    public void run() {
        final int year = Configuration.getYear();
        final int day = Configuration.getDay();
        final Path solutionPath = Path.of("src", "main", "java", "solution", String.format("y%d", year), String.format("d%d", day), "Solution.java");
        if (Files.exists(solutionPath)) {
            System.out.printf("Year: [%d] day: [%d] is already initialized%n", year, day);
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
            System.out.printf("Year: [%d] day: [%d] initialized successfully%n", year, day);
        } catch (IOException e) {
            System.out.printf("Year: [%d] day: [%d] initialization failed%n", year, day);
            throw new RuntimeException(e);
        }
    }
}
