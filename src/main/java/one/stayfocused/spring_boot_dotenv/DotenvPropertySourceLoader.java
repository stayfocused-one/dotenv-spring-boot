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

/**
 * Utility class responsible for loading environment variables from a `.env` file.
 * <p>
 * This class reads a `.env` file, parses its contents, and loads key-value pairs
 * into a {@link Map}. It is designed to integrate with Spring Boot applications
 * by injecting values into the {@link Environment}.
 * </p>
 * <p>
 * The file path can be configured via the `dotenv.path` property. If the file
 * is missing and `dotenv.fail-on-missing=true`, an exception is thrown.
 * </p>
 *
 * @author Augustin (StayFocused)
 * @since 0.0.1
 */
@Slf4j
public class DotenvPropertySourceLoader {

    /**
     * Private constructor to prevent instantiation.
     *
     * @throws UnsupportedOperationException if an attempt to instantiate the class is made.
     */
    private DotenvPropertySourceLoader() {
        throw new UnsupportedOperationException("DotenvPropertySourceLoader is a utility class and cannot be instantiated.");
    }

    /**
     * Loads environment variables from a `.env` file.
     * <p>
     * The method checks if the `.env` file exists at the path specified by
     * `dotenv.path`. If the file is missing and `dotenv.fail-on-missing=true`,
     * an exception is thrown.
     * </p>
     *
     * @param environment the Spring {@link Environment} to retrieve configuration properties.
     * @return a map of key-value pairs representing the environment variables.
     */
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

    /**
     * Reads lines from the `.env` file.
     *
     * @param envFilePath the path to the `.env` file.
     * @return a list of lines read from the file.
     */
    private static List<String> loadLinesFromFile(Path envFilePath) {
        try {
            log.info("Loading .env file from: {}", envFilePath);
            return Files.readAllLines(envFilePath);
        } catch (IOException e) {
            log.error("Error loading .env file: {}", envFilePath, e);
            return Collections.emptyList();
        }
    }

    /**
     * Parses lines from the `.env` file into a key-value map.
     * <p>
     * Ignores empty lines and comments (`#` at the start of a line).
     * </p>
     *
     * @param lines the list of lines read from the `.env` file.
     * @return a map of environment variables extracted from the file.
     */
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
