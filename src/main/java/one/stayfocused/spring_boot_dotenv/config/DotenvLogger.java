package one.stayfocused.spring_boot_dotenv.config;

import lombok.extern.slf4j.Slf4j;
import one.stayfocused.spring_boot_dotenv.environment.DotenvPropertySource;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

import static one.stayfocused.spring_boot_dotenv.core.DotenvUtils.*;

@Slf4j
@Component
public class DotenvLogger {

    private final ConfigurableEnvironment environment;

    public DotenvLogger(ConfigurableEnvironment environment) {
        this.environment = environment;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void logDotenvInfo() {
        boolean enabled = environment.getPropertySources().contains(PROPERTY_SOURCE_NAME);
        String dotenvPath = environment.getProperty(DOTENV_PATH_KEY, DEFAULT_ENV_PATH);
        String priority = environment.getProperty(DOTENV_PRIORITY_KEY, DEFAULT_DOTENV_PRIORITY);
        String failOnMissing = environment.getProperty(DOTENV_FAIL_ON_MISSING_KEY, String.valueOf(DEFAULT_FAIL_ON_MISSING));
        boolean reload = Boolean.parseBoolean(environment.getProperty(DOTENV_RELOAD_ENABLED_KEY, String.valueOf(DEFAULT_RELOAD_ENABLED)));

        int variableCount = Optional.ofNullable(environment.getPropertySources().get(PROPERTY_SOURCE_NAME))
                .filter(DotenvPropertySource.class::isInstance)
                .map(DotenvPropertySource.class::cast)
                .map(DotenvPropertySource::getSource)
                .map(Map::size)
                .orElse(0);

        log.info("[Dotenv] Status: {}", enabled ? "enabled" : "disabled");
        log.info("[Dotenv] Path: {}", dotenvPath);
        log.info("[Dotenv] Fail on missing: {}", failOnMissing);
        log.info("[Dotenv] Priority: {}", priority);
        log.info("[Dotenv] Reload in runtime: {}", reload ? "enabled" : "disabled");
        log.info("[Dotenv] Loaded variables: {} ", variableCount);
    }
}
