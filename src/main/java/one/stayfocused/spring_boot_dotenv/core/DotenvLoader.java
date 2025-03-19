package one.stayfocused.spring_boot_dotenv.core;

import lombok.extern.slf4j.Slf4j;
import one.stayfocused.spring_boot_dotenv.exception.DotenvFileNotFoundException;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static one.stayfocused.spring_boot_dotenv.core.DotenvUtils.*;
/**
 * Utility class for loading environment variables from a `.env` file.
 * <p>
 * This class is responsible for reading a `.env` file from the specified path,
 * parsing its contents, and providing the environment variables as a {@link Map}.
 * The class cannot be instantiated.
 * </p>
 *
 * <p>
 * The file path is determined from the application properties (`dotenv.path`),
 * and the behavior for missing files is controlled by `dotenv.fail-on-missing`.
 * </p>
 *
 * @author Augustin (StayFocused)
 * @since 1.0.0
 */
@Slf4j
public class DotenvLoader {

    /**
     * Private constructor to prevent instantiation.
     *
     * @throws UnsupportedOperationException if an attempt to instantiate the class is made.
     */
    private DotenvLoader() {
        throw new UnsupportedOperationException("DotenvLoader is a utility class and cannot be instantiated.");
    }

    /**
     * Loads environment variables from a `.env` file based on the application's configuration.
     * <p>
     * The path to the file is retrieved from {@code dotenv.path} in the Spring {@link Environment}.
     * If the file is missing and {@code dotenv.fail-on-missing=true}, an exception is thrown.
     * </p>
     *
     * @param environment the Spring {@link Environment} to retrieve configuration properties.
     * @return a {@link Map} containing environment variables.
     * @throws DotenvFileNotFoundException if the file is missing and {@code dotenv.fail-on-missing} is enabled.
     */
    public static Map<String, String> load(Environment environment) {
        String dotenvPath = environment.getProperty(DOTENV_PATH_KEY, DEFAULT_ENV_PATH);
        boolean failOnMissing = isFailOnMissing(environment);
        return loadFromPath(dotenvPath, failOnMissing);
    }

    /**
     * Loads environment variables from a specific `.env` file path.
     * <p>
     * If the file is missing and {@code failOnMissing} is enabled, an exception is thrown.
     * Otherwise, an empty map is returned.
     * </p>
     *
     * @param path the path to the `.env` file.
     * @param failOnMissing whether to throw an exception if the file is missing.
     * @return a {@link Map} containing the parsed environment variables.
     * @throws DotenvFileNotFoundException if the file is missing and {@code failOnMissing} is true.
     */
    private static Map<String, String> loadFromPath(String path, boolean failOnMissing) {
        Path envPath = Paths.get(path);

        if (!Files.exists(envPath)) {
            log.warn("[Dotenv] .env file not found at: {}", envPath);
            if (failOnMissing) {
                throw new DotenvFileNotFoundException(path);
            }
            return Collections.emptyMap();
        }

        Map<String, String> envVariables = new HashMap<>();
        try (Stream<String> lines = Files.lines(envPath)) {
            lines.forEach(line -> parseLine(line, envVariables));
            log.debug("[Dotenv] Successfully loaded {} variables from {}", envVariables.size(), envPath);
        } catch (IOException e) {
            log.error("[Dotenv] Failed to load .env file from {}: {}", envPath, e.getMessage());
        }

        return envVariables;
    }

    /**
     * Parses a line from the `.env` file and adds it to the environment variables map.
     * <p>
     * Lines that are blank or start with {@code #} are ignored as comments.
     * Values enclosed in single or double quotes have their quotes removed.
     * </p>
     *
     * @param line the line to parse.
     * @param envVariables the map where parsed variables are stored.
     */
    private static void parseLine(String line, Map<String, String> envVariables) {
        if (line.isBlank() || line.startsWith("#")) return;

        String[] parts = line.split("=", 2);
        if (parts.length != 2) return;

        String key = parts[0].trim();
        String value = parts[1].trim();

        if ((value.startsWith("\"") && value.endsWith("\"")) || (value.startsWith("'") && value.endsWith("'"))) {
            value = value.substring(1, value.length() - 1);
        }

        envVariables.put(key, value);
        log.debug("[Dotenv] Loaded variable: {}=***", key);
    }
}
