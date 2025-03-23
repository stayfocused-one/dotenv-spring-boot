package one.stayfocused.spring.dotenv.core;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import java.util.Map;

/**
 * Default {@link EnvLoader} implementation using {@link DotenvLoader} to load {@code .env} files.
 */
@Component
public class DefaultEnvLoader implements EnvLoader {

    private final EnvParser parser;

    /**
     * Constructs a new loader with a default {@link DotenvParser}.
     */
    public DefaultEnvLoader() {
        this(new DotenvParser());
    }

    /**
     * Constructs a new loader with the specified parser.
     *
     * @param parser the parser for {@code .env} files
     */
    public DefaultEnvLoader(EnvParser parser) {
        this.parser = parser;
    }

    /**
     * Loads environment variables from a {@code .env} file.
     *
     * @param environment the Spring {@link Environment}
     * @return a map of environment variables
     */
    @Override
    public Map<String, String> load(Environment environment) {
        return DotenvLoader.load(environment, parser);
    }
}