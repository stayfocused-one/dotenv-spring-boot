package one.stayfocused.spring.dotenv.core;

import org.springframework.core.env.Environment;

import java.util.Map;

/**
 * Interface for loading environment variables into a Spring {@link Environment}.
 */
public interface EnvLoader {

    /**
     * Loads environment variables from a source (e.g., a {@code .env} file) into the Spring {@link Environment}.
     *
     * @param environment the Spring {@link Environment} to load variables into
     * @return a map of loaded key-value pairs
     */
    Map<String, String> load(Environment environment);
}