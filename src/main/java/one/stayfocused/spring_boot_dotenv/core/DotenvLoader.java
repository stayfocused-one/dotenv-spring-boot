package one.stayfocused.spring_boot_dotenv;

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

import static one.stayfocused.spring_boot_dotenv.DotenvUtils.*;

@Slf4j
public class DotenvLoader {

    private DotenvLoader() {
        throw new UnsupportedOperationException("DotenvLoader is a utility class and cannot be instantiated.");
    }

    public static Map<String, String> load(Environment environment) {
        String dotenvPath = environment.getProperty(DOTENV_PATH_KEY, DEFAULT_ENV_PATH);
        boolean failOnMissing = isFailOnMissing(environment);
        return loadFromPath(dotenvPath, failOnMissing);
    }

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
            log.info("[Dotenv] Successfully loaded {} variables from {}", envVariables.size(), envPath);
        } catch (IOException e) {
            log.error("[Dotenv] Failed to load .env file from {}: {}", envPath, e.getMessage());
        }

        return envVariables;
    }

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
