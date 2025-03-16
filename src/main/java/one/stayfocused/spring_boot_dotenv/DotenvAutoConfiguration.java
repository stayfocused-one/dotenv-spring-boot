package one.stayfocused.spring_boot_dotenv;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;

import java.util.Map;

@Slf4j
@Configuration
public class DotenvAutoConfiguration {

    private static final String DOTENV_ENABLED_KEY = "dotenv.enabled";
    private static final String DEFAULT_DOTENV_ENABLED = "true";

    @Bean
    public DotenvPropertySource dotenvPropertySource(@NonNull Environment environment) {
        boolean dotenvEnabled = isDotenvEnabled(environment);

        if (!dotenvEnabled) {
            log.info("Dotenv support disabled via 'dotenv.enabled=false'. Skipping .env loading.");
            return new DotenvPropertySource("dotenv", Map.of());
        }

        log.info("Initializing DotenvPropertySource...");
        Map<String, String> dotenvVariables = DotenvPropertySourceLoader.loadDotenvFromFile(environment);

        if (dotenvVariables.isEmpty()) {
            log.warn("DotenvPropertySource initialized, but no environment variables were loaded from .env.");
        } else {
            log.info("DotenvPropertySource initialized. Loaded {} variables.", dotenvVariables.size());
            log.debug("Loaded variables: {}", dotenvVariables.keySet());
        }

        return new DotenvPropertySource("dotenv", dotenvVariables);
    }

    private boolean isDotenvEnabled(@NonNull Environment environment) {
        return Boolean.parseBoolean(environment.getProperty(
                DOTENV_ENABLED_KEY, String.valueOf(DEFAULT_DOTENV_ENABLED)
        ));
    }
}
