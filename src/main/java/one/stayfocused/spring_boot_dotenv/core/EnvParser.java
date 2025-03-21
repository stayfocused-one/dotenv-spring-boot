package one.stayfocused.spring_boot_dotenv.core;

import java.util.List;
import java.util.Map;

/**
 * Interface for parsing environment variable files.
 */
public interface EnvParser {
    Map<String, String> parse(List<String> lines);
}
