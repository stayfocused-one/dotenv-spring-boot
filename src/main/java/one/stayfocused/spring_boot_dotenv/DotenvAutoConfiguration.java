package one.stayfocused.spring_boot_dotenv;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.lang.NonNull;

import java.util.Map;

import static one.stayfocused.spring_boot_dotenv.DotenvUtils.*;

/**
 * Autoconfiguration for loading environment variables from a `.env` file in a Spring Boot application.
 *
 * <p>This configuration automatically loads the `.env` file and integrates its variables into the Spring
 * {@link ConfigurableEnvironment}. The `.env` file can be loaded from the root directory or a custom path
 * specified via {@code dotenv.path}.
 *
 * <p>Priority of environment variables can be configured with {@code dotenv.priority}:
 * - {@code high}: Variables override those in `application.properties`.
 * - {@code low} (default): Variables have lower precedence than `application.properties`.
 *
 * <p>By default, the `.env` file is optional. If {@code dotenv.fail-on-missing=true} is set,
 * the application will fail to start if the file is not found.
 *
 * @author Augustin (StayFocused)
 * @since 0.0.1
 */
@Slf4j
@Configuration
public class DotenvAutoConfiguration {

    @Bean
    public DotenvPropertySource dotenvPropertySource(@NonNull ConfigurableEnvironment environment) {
        boolean dotenvEnabled = DotenvUtils.getBooleanProperty(environment, DOTENV_ENABLED_KEY, DEFAULT_DOTENV_ENABLED);

        if (!dotenvEnabled) {
            log.info("Dotenv support disabled via 'dotenv.enabled=false'. Skipping .env loading.");
            return new DotenvPropertySource(PROPERTY_SOURCE_NAME, Map.of());
        }

        log.info("Initializing DotenvPropertySource...");
        Map<String, String> dotenvVariables = DotenvPropertySourceLoader.loadDotenvFromFile(environment);

        if (dotenvVariables.isEmpty()) {
            log.warn("DotenvPropertySource initialized, but no environment variables were loaded from .env.");
        } else {
            log.info("DotenvPropertySource initialized. Loaded {} variables.", dotenvVariables.size());
            log.debug("Loaded variables: {}", dotenvVariables.keySet());
        }

        DotenvPropertySource propertySource = new DotenvPropertySource(PROPERTY_SOURCE_NAME, dotenvVariables);

        if (isHighPriority(environment)) {
            environment.getPropertySources().addFirst(propertySource);
        } else {
            environment.getPropertySources().addLast(propertySource);
        }

        return propertySource;
    }
}
