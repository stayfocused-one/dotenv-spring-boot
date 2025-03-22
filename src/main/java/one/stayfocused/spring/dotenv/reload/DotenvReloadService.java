package one.stayfocused.spring.dotenv.reload;

import lombok.extern.slf4j.Slf4j;
import one.stayfocused.spring.dotenv.core.EnvLoader;
import one.stayfocused.spring.dotenv.environment.DotenvPropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static one.stayfocused.spring.dotenv.core.DotenvUtils.*;

/**
 * Service for reloading environment variables from a {@code .env} file at runtime.
 * <p>
 * Updates Spring's {@link Environment} dynamically when the {@code .env} file changes.
 * Requires {@code dotenv.reload.enabled=true} in application properties to enable reloading.
 * </p>
 *
 * @author Augustin (StayFocused)
 * @since 1.0.0
 */
@Slf4j
@Service
public class DotenvReloadService implements ReloadService {

    private final ConfigurableEnvironment environment;
    private final EnvLoader envLoader;
    private final Map<String, String> dotenvCache = new ConcurrentHashMap<>();

    /**
     * Constructs a {@code DotenvReloadService} and preloads environment variables.
     *
     * @param environment the Spring {@link ConfigurableEnvironment} to manage properties
     * @param envLoader the {@link EnvLoader} to load environment variables
     */
    public DotenvReloadService(ConfigurableEnvironment environment, EnvLoader envLoader) {
        this.environment = environment;
        this.envLoader = envLoader;
        reloadDotenvCache();
    }

    /**
     * Reloads environment variables from the {@code .env} file into the Spring {@link Environment}.
     * <p>
     * Returns {@code false} if reloading is disabled (via {@code dotenv.reload.enabled=false}).
     * Updates the property sources and logs the result.
     * </p>
     *
     * @return {@code true} if reloading succeeded, {@code false} if disabled
     */
    public boolean reload() {
        if (!isReloadEnabled(environment)) {
            log.warn("[Dotenv] Dotenv reload is disabled. Enable it with `dotenv.reload.enabled=true`");
            return false;
        }

        log.info("[Dotenv] Reloading .env file...");
        reloadDotenvCache();

        environment.getPropertySources().remove(PROPERTY_SOURCE_NAME);
        DotenvPropertySource newPropertySource = new DotenvPropertySource(PROPERTY_SOURCE_NAME, dotenvCache);

        if (isHighPriority(environment)) {
            environment.getPropertySources().addFirst(newPropertySource);
        } else {
            environment.getPropertySources().addLast(newPropertySource);
        }

        log.info("[Dotenv] .env file successfully reloaded ({} variables)",  dotenvCache.size());

        return true;
    }

    /**
     * Clears and reloads the cached environment variables from the {@code .env} file.
     */
    private void reloadDotenvCache() {
        dotenvCache.clear();
        dotenvCache.putAll(envLoader.load(environment));
    }
}
