package one.stayfocused.spring_boot_dotenv;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.nio.file.Files;
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
        String dotenvPath = environment.getProperty("dotenv.path", ".env");
        Map<String, String> dotenvVariables = new HashMap<>();

        try {
            log.info("Loading .env file from: {}", dotenvPath);
            List<String> lines =  Files.readAllLines(Paths.get(dotenvPath));

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
            log.warn("Failed to load .env file: {}", dotenvPath, e);
        }

        return dotenvVariables;
    }
}
