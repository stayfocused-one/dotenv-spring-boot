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

@Slf4j
public class DotenvPropertySourceLoader {

    private static final String DOTENV_PATH_KEY = "dotenv.path";
    private static final String DOTENV_FAIL_ON_MISSING_KEY = "dotenv.fail-on-missing";
    private static final String DEFAULT_ENV_PATH = ".env";
    private static final boolean DEFAULT_FAIL_ON_MISSING = false;

    // Private constructor to prevent instantiation
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

    private static boolean isFailOnMissing(Environment environment) {
        return Boolean.parseBoolean(environment.getProperty(
                DOTENV_FAIL_ON_MISSING_KEY, String.valueOf(DEFAULT_FAIL_ON_MISSING)
        ));
    }

    private static String getDotenvPath(Environment environment) {
        return environment.getProperty(DOTENV_PATH_KEY, DEFAULT_ENV_PATH);
    }

    private static void handleMissingFile(String dotenvPath, boolean failOnMissing) {
        if (failOnMissing) {
            throw new IllegalStateException(".env file not found at: " + dotenvPath);
        } else {
            log.warn(".env file not found at: {}", dotenvPath);
        }
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
