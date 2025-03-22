package one.stayfocused.spring.dotenv.core;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import java.util.Map;

/**
 * Default implementation of {@link EnvLoader} using {@link DotenvLoader}.
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
     * @param parser the parser to use for .env files
     */
    public DefaultEnvLoader(EnvParser parser) {
        this.parser = parser;
    }

    /**
     * Loads environment variables from a .env file.
     *
     * @param environment the Spring environment
     * @return a map of environment variables
     */
    @Override
    public Map<String, String> load(Environment environment) {
        return DotenvLoader.load(environment, parser);
    }
}