package one.stayfocused.spring_boot_dotenv;

import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Map;

@Slf4j
@Configuration
public class DotenvAutoConfiguration {

    @Bean
    public DotenvPropertySource dotenvPropertySource(@Nonnull Environment environment) {
        boolean dotenvEnabled = Boolean.parseBoolean(environment.getProperty("dotenv.enabled", "true"));

        if (!dotenvEnabled) {
            log.info("Dotenv support disabled via 'dotenv.enabled=false'. Skipping .env loading.");
            return new DotenvPropertySource("dotenv", Map.of());
        }

        log.info("Initializing DotenvPropertySource...");
        Map<String, String> dotenvVariables = DotenvPropertySourceLoader.loadDotenv(environment);

        if (dotenvVariables.isEmpty()) {
            log.warn("DotenvPropertySource initialized, but no environment variables were loaded from .env.");
        } else {
            log.info("DotenvPropertySource initialized. Loaded {} variables.", dotenvVariables.size());
            log.debug("Loaded variables: {}", dotenvVariables.keySet());
        }

        return new DotenvPropertySource("dotenv", dotenvVariables);
    }
}
