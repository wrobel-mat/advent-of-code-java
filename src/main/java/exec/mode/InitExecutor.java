package exec.mode;

import config.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

public class InitExecutor implements IModeExecutor {

    @Override
    public ApplicationMode mode() {
        return ApplicationMode.INIT;
    }

    @Override
    public void run() {
        final Properties props = Configuration.getProperties();
        final int year = Integer.parseInt(props.getProperty("year"));
        final int day = Integer.parseInt(props.getProperty("day"));
        final Path templatePath = Path.of("src", "main", "resources", "templates", "Solution.java");
        final Path solutionPath = Path.of("src", "main", "java", "solution", String.format("y%d", year), String.format("d%d", day), "Solution.java");
        try {
            String solutionTemplate = new String(Files.readAllBytes(templatePath));
            solutionTemplate = solutionTemplate
                    .replace("${yearNum}", String.valueOf(year))
                    .replace("${dayNum}", String.valueOf(day));
            Files.createDirectories(solutionPath.getParent());
            Files.write(solutionPath, solutionTemplate.getBytes(), CREATE_NEW);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
