package one.stayfocused.spring_boot_dotenv.core;

import lombok.extern.slf4j.Slf4j;
import one.stayfocused.spring_boot_dotenv.exception.DotenvParseException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Parser for .env file content.
 */
@Slf4j
public class DotenvParser implements EnvParser {

    /**
     * Parses a list of lines from a .env file into a map of environment variables.
     *
     * @param lines the lines to parse
     * @return a map of environment variables
     * @throws DotenvParseException if parsing fails
     */
    public Map<String, String> parse(List<String> lines) {
        Map<String, String> envVariables = new HashMap<>();
        for (String line : lines) {
            parseLine(line, envVariables);
        }
        return envVariables;
    }

    private void parseLine(String line, Map<String, String> envVariables) {
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
