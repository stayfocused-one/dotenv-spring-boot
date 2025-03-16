package one.stayfocused.spring_boot_dotenv;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class DotenvPropertySourceLoader {

    // Private constructor to prevent instantiation
    private DotenvPropertySourceLoader() {
        throw new UnsupportedOperationException("DotenvPropertySourceLoader is a utility class and cannot be instantiated.");
    }

    public static Map<String, String> loadDotenv(Environment environment) {

        String dotenvPath = getDotenvPath(environment);
        boolean failOnMissing = isFailOnMissing(environment);

        Path envFilePath = Paths.get(dotenvPath);
        if (!Files.exists(envFilePath)) {
            handleMissingFile(dotenvPath, failOnMissing);
            return Map.of();
        }

        return loadDotenvFromFile(envFilePath);
    }

    private static boolean isFailOnMissing(Environment environment) {
        return Boolean.parseBoolean(environment.getProperty("dotenv.fail-on-missing", "false"));
    }

    private static String getDotenvPath(Environment environment) {
        return environment.getProperty("dotenv.path", ".env");
    }

    private static void handleMissingFile(String dotenvPath, boolean failOnMissing) {
        if (failOnMissing) {
            throw new IllegalStateException(".env file not found at: " + dotenvPath);
        } else {
            log.warn(".env file not found at: {}", dotenvPath);
        }
    }

    private static Map<String, String> loadDotenvFromFile(Path  envFilePath) {
        Map<String, String> dotenvVariables = new HashMap<>();

        try {
            log.info("Loading .env file from: {}", envFilePath);
            List<String> lines =  Files.readAllLines(envFilePath);

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
        } catch (IOException e) {
            log.error("Error loading .env file: {}", envFilePath, e);
        }
        return dotenvVariables;
    }
}
