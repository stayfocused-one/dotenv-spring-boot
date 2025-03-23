package one.stayfocused.spring.dotenv.core;

import java.util.List;
import java.util.Map;

/**
 * Interface for parsing environment variable files, such as {@code .env}.
 */
public interface EnvParser {

    /**
     * Parses a list of lines from an environment variable file into key-value pairs.
     *
     * @param lines the lines to parse
     * @return a map of key-value pairs
     */
    Map<String, String> parse(List<String> lines);
}