package one.stayfocused.spring_boot_dotenv;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static one.stayfocused.spring_boot_dotenv.DotenvUtils.*;

@Slf4j
public class DotenvPropertySourceLoader {

    private DotenvPropertySourceLoader() {
        throw new UnsupportedOperationException("DotenvPropertySourceLoader is a utility class and cannot be instantiated.");
    }

    public static Map<String, String> loadDotenvFromFile(Environment environment) {
        String dotenvPath = getDotenvPath(environment);
        boolean failOnMissing = isFailOnMissing(environment);
        Path envFilePath = Paths.get(dotenvPath);

        if (!Files.exists(envFilePath)) {
            handleMissingFile(dotenvPath, failOnMissing);
            return Collections.emptyMap();
        }

        return parseDotenvLines(loadLinesFromFile(envFilePath));
    }

    private static List<String> loadLinesFromFile(Path envFilePath) {
        try {
            log.info("Loading .env file from: {}", envFilePath);
            return Files.readAllLines(envFilePath);
        } catch (IOException e) {
            log.error("Error loading .env file: {}", envFilePath, e);
            return Collections.emptyList();
        }
    }

    private static Map<String, String> parseDotenvLines(List<String> lines) {
        Map<String, String> dotenvVariables = new ConcurrentHashMap<>();

        for (String line : lines) {
            if (!line.isBlank() && !line.startsWith("#")) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    dotenvVariables.put(parts[0].trim(), parts[1].trim());
                    log.debug("Loaded variable: {}=***", parts[0].trim());
                }
            }
        }

        log.info(".env file successfully loaded ({} variables)", dotenvVariables.size());
        return dotenvVariables;
    }
}
