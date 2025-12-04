package cache;

import answer.Result;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static java.lang.String.format;

public class LocalCache {

    private static final Logger LOG = Logger.getLogger(LocalCache.class.getSimpleName());
    private static final ObjectMapper RESULT_MAPPER = new ObjectMapper();

    static {
        RESULT_MAPPER.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    public static Optional<List<String>> getInput(int year, int day) {
        Path inputPath = Path.of("src", "main", "resources", "inputs", String.valueOf(year), format("day%d.txt", day));
        if (Files.exists(inputPath)) {
            try {
                return Optional.of(Files.readAllLines(inputPath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return Optional.empty();
    }

    public static void persistInput(int year, int day, List<String> input) {
        Path inputPath = Path.of("src", "main", "resources", "inputs", String.valueOf(year), format("day%d.txt", day));
        try {
            if (Files.notExists(inputPath.getParent())) {
                Files.createDirectory(inputPath.getParent());
            }
            Files.write(inputPath, input);
            LOG.info(format("Cached puzzle input for year [%d] day [%d] under path: %s", year, day, inputPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Result getResult(int year, int day) {
        Path resultPath = Path.of("src", "main", "resources", "results", String.valueOf(year), format("day%d.json", day));
        Result result;
        if (Files.exists(resultPath)) {
            try {
                result = RESULT_MAPPER.readValue(Files.readString(resultPath), Result.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            result = Result.of(year, day);
        }
        return result;
    }

    public static void persistResult(Result result) {
        int year = result.year();
        int day = result.day();
        Path resultPath = Path.of("src", "main", "resources", "results", String.valueOf(year), format("day%d.json", day));
        try {
            if (Files.notExists(resultPath.getParent())) {
                Files.createDirectory(resultPath.getParent());
            }
            Files.write(resultPath, RESULT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsBytes(result));
            LOG.info(format("Saved puzzle result for year [%d] day [%d] under path: %s", year, day, resultPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
