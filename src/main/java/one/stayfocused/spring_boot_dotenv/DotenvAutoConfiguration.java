package one.stayfocused.spring_boot_dotenv;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.lang.NonNull;

import java.util.Map;

import static one.stayfocused.spring_boot_dotenv.DotenvUtils.*;

@Slf4j
@Configuration
public class DotenvAutoConfiguration {

    @Bean
    public DotenvPropertySource dotenvPropertySource(@NonNull ConfigurableEnvironment environment) {
        boolean dotenvEnabled = DotenvUtils.getBooleanProperty(environment, DOTENV_ENABLED_KEY, DEFAULT_DOTENV_ENABLED);

        if (!dotenvEnabled) {
            log.info("Dotenv support disabled via 'dotenv.enabled=false'. Skipping .env loading.");
            return new DotenvPropertySource(DOTENV_KEY, Map.of());
        }

        log.info("Initializing DotenvPropertySource...");
        Map<String, String> dotenvVariables = DotenvPropertySourceLoader.loadDotenvFromFile(environment);

        if (dotenvVariables.isEmpty()) {
            log.warn("DotenvPropertySource initialized, but no environment variables were loaded from .env.");
        } else {
            log.info("DotenvPropertySource initialized. Loaded {} variables.", dotenvVariables.size());
            log.debug("Loaded variables: {}", dotenvVariables.keySet());
        }

        DotenvPropertySource propertySource = new DotenvPropertySource(DOTENV_KEY, dotenvVariables);

        if (isHighPriority(environment)) {
            environment.getPropertySources().addFirst(propertySource);
        } else {
            environment.getPropertySources().addLast(propertySource);
        }

        return propertySource;
    }
}
