package one.stayfocused.spring_boot_dotenv.core;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DefaultEnvLoader implements EnvLoader {

    private final EnvParser parser;

    public DefaultEnvLoader() {
        this(new DotenvParser());
    }

    public DefaultEnvLoader(EnvParser parser) {
        this.parser = parser;
    }

    @Override
    public Map<String, String> load(Environment environment) {
        return DotenvLoader.load(environment, parser);
    }
}
