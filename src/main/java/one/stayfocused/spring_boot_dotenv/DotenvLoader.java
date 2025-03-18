package one.stayfocused.spring_boot_dotenv;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class DotenvLoader {

    private final Map<String, String> variables = new HashMap<>();

    private void loadEnvFile(String path) {
        Path envPath = Paths.get(path);

        if (!Files.exists(envPath)) {
            return;
        }

        try (Stream<String> lines = Files.lines(envPath)) {
            lines.forEach(this::parseLine);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load .env file", e);
        }
    }

    private void parseLine(String line) {
        if (line.isBlank() || line.startsWith("#")) return;
        String[] parts = line.split("=", 2);
        if (parts.length == 2) {
            variables.put(parts[0].trim(), parts[1].trim());
        }
    }

    public String get(String key) {
        return variables.getOrDefault(key, System.getenv(key));
    }

    public Map<String, String> getEntries() {
        return new HashMap<>(variables);
    }
}
