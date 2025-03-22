package one.stayfocused.spring.dotenv.core;

import org.springframework.core.env.Environment;

import java.util.Map;

/**
 * Interface for loading environment variables.
 */
public interface EnvLoader {
    Map<String, String> load(Environment environment);
}