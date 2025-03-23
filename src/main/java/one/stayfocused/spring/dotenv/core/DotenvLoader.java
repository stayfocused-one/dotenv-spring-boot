package one.stayfocused.spring.dotenv.core;

import lombok.extern.slf4j.Slf4j;
import one.stayfocused.spring.dotenv.exception.DotenvFileNotFoundException;
import one.stayfocused.spring.dotenv.exception.DotenvParseException;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static one.stayfocused.spring.dotenv.core.DotenvUtils.*;
/**
 * Utility class for loading environment variables from a {@code .env} file.
 * <p>
 * Reads and parses the file, returning variables as a {@link Map}. Not instantiable.
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
     * @throws UnsupportedOperationException if instantiation is attempted
     */
    private DotenvLoader() {
        throw new UnsupportedOperationException("DotenvLoader is a utility class and cannot be instantiated.");
    }

    /**
     * Loads environment variables from a {@code .env} file using a default parser.
     *
     * @param environment the Spring {@link Environment} to retrieve configuration from
     * @return a map of environment variables
     * @throws DotenvFileNotFoundException if the file is missing and {@code dotenv.fail-on-missing} is enabled
     */
    public static Map<String, String> load(Environment environment) {
        return load(environment, new DotenvParser());
    }

    /**
     * Loads environment variables from a {@code .env} file based on the application's configuration.
     * <p>
     * Uses the path from {@code dotenv.path} in the Spring {@link Environment}.
     * Throws an exception if the file is missing and {@code dotenv.fail-on-missing=true}.
     * </p>
     *
     * @param environment the Spring {@link Environment} to retrieve configuration from
     * @param parser the parser to use for the {@code .env} file
     * @return a map of environment variables
     * @throws DotenvFileNotFoundException if the file is missing and {@code dotenv.fail-on-missing} is enabled
     */
    public static Map<String, String> load(Environment environment, EnvParser parser) {
        String dotenvPath = getDotenvPath(environment);
        boolean failOnMissing = isFailOnMissing(environment);
        return loadFromPath(dotenvPath, failOnMissing, parser);
    }

    /**
     * Loads environment variables from a specific {@code .env} file path.
     * <p>
     * Returns an empty map if the file is missing and {@code failOnMissing} is disabled.
     * </p>
     *
     * @param path the path to the {@code .env} file
     * @param failOnMissing whether to throw an exception if the file is missing
     * @param parser the parser to use for the {@code .env} file
     * @return a map of parsed environment variables
     * @throws DotenvFileNotFoundException if the file is missing and {@code failOnMissing} is true
     */
    private static Map<String, String> loadFromPath(String path, boolean failOnMissing, EnvParser parser) {
        Path envPath = Paths.get(path);

        if (!Files.exists(envPath)) {
            log.warn("[Dotenv] .env file not found at: {}", envPath);
            if (failOnMissing) {
                throw new DotenvFileNotFoundException(path);
            }
            return Collections.emptyMap();
        }

        try {
            List<String> lines = Files.readAllLines(envPath);
            Map<String, String> envVariables = parser.parse(lines);
            log.debug("[Dotenv] Successfully loaded {} variables from {}", envVariables.size(), envPath);
            return envVariables;
        } catch (IOException e) {
            log.error("[Dotenv] Failed to read .env file from {}: {}", envPath, e.getMessage());
            throw new DotenvParseException("Failed to read .env file from " + envPath, e);
        }
    }
}
